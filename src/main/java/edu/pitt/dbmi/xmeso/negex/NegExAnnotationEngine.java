package edu.pitt.dbmi.xmeso.negex;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import edu.pitt.dbmi.xmeso.model.Model.Part;
import edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity;
import edu.pitt.dbmi.xmeso.model.Model.XmesoNegatedConcept;
import edu.pitt.dbmi.xmeso.model.Model.XmesoSentence;
import edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken;

public class NegExAnnotationEngine extends org.apache.uima.fit.component.JCasAnnotator_ImplBase {

	private SentenceDetectorConfigurator configurator; 
	private SentenceDetector boundaryDetector;
	private NegExEngine negator;

	private final String spaceSplitRegEx = "(\\w+|\\$[\\d\\.]+|\\S+)";
	private Pattern sentenceSplitter; 
	
	private final String specialCharacterRemovalRegEx = "[^a-zA-Z0-9\\s\t]";
	
	private final String START = "START";
	private final String END = "END";
	
	/*PIPELINE :
	 * 1. Sentence Not Null
	 * 2. Sentence Tokens Not Null
	 * 3. Contains Named Entities
	 * 4. Contains Negation Concepts
	 * 5. Define Boundary Indices
	 * 6. Check if Named Entities fall within boundaries.
	 */

	/**
	 * Vamsi, 
	 *    Here is a place to implement the NegEx algorithm.
	 *  See below examples of pulling the Types you can work with.	
	 */

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {	
		System.out.println(getClass().getName() + " being called via a Ruta script..");
		Collection<Part> parts = JCasUtil.select(jCas, Part.class);
		initialize();
		for (Part part : parts) {
			System.out.println("Section: " + part.getSectionName() + " Part: " + part.getPartNumber());
			String scope = part.getCoveredText();
			if(scope != null && !scope.trim().isEmpty()) {
				sentenceBoundaryTokenizer(jCas, part, scope);
			}
		}
		sentenceTokenizer(jCas);
		defineNegations(jCas);
		System.out.println("Done...");
	}

	public void sentenceBoundaryTokenizer(JCas jCas, Part part, String scope) {
		Map<String, String[]> tokensMap = SentenceDetectorUtils.tokenizeText(scope);
		List<String> sentences = SentenceDetectorUtils.breakDownToSentences(boundaryDetector, tokensMap.get(SentenceDetectorUtils.TOKENS), tokensMap.get(SentenceDetectorUtils.WHITE_SPACES));
		int spanStart = trimPartTextStart(part);
		if(sentences != null && !sentences.isEmpty()) {
			for(int i=0; i<sentences.size(); i++) {
				XmesoSentence annotation = new XmesoSentence(jCas);
				annotation.setBegin(spanStart);
				annotation.setEnd(spanStart+sentences.get(i).length());
				annotation.addToIndexes();
				spanStart = spanStart+sentences.get(i).length();
			}
		}
	}

	public void sentenceTokenizer(JCas jCas){
		FSIndex<XmesoSentence> sentenceAnnotations = jCas.getAnnotationIndex(XmesoSentence.type);
		if(sentenceAnnotations != null){
			FSIterator<XmesoSentence> iter =  sentenceAnnotations.iterator();
			while(iter.hasNext()) {
				XmesoSentence sentenceAnnotation = iter.next();
				if(sentenceAnnotation != null) {
					int startingIndex = sentenceAnnotation.getBegin();
					String text = sentenceAnnotation.getCoveredText();
					if(text != null && !text.isEmpty()) {
						sentenceSplitter = Pattern.compile(spaceSplitRegEx);
						Matcher matcher = sentenceSplitter.matcher(text);
						while(matcher.find()){
							int start = (matcher.start()+startingIndex);
							int end = (matcher.end()+startingIndex);
							XmesoSentenceToken sentenceToken = new XmesoSentenceToken(jCas);
							sentenceToken.setBegin(start);
							sentenceToken.setEnd(end);
							sentenceToken.addToIndexes();
						}
					}
				}
			}
		}
	}

	public void defineNegations(JCas jCas) {
		FSIndex<XmesoSentence> sentenceAnnotations = jCas.getAnnotationIndex(XmesoSentence.type);
		if(sentenceAnnotations != null) {
			FSIterator<XmesoSentence> iter =  sentenceAnnotations.iterator();
			while(iter.hasNext()) {
				XmesoSentence sentenceAnnotation = iter.next();
				if(sentenceAnnotation != null){
					String text = sentenceAnnotation.getCoveredText();
					text = removeSpecialCharacters(text);
					if(text !=null && !text.isEmpty()){
						List<XmesoSentenceToken> sentenceTokensList = JCasUtil.selectCovered(jCas,XmesoSentenceToken.class, sentenceAnnotation);
						if(sentenceTokensList != null && !sentenceTokensList.isEmpty()) {
							if(JCasUtil.contains(jCas, sentenceAnnotation, XmesoNamedEntity.class)) {
								List<NegPhrase> negationConcepts = negator.findPossibleNegatedConcepts(text);
								if(negationConcepts != null && !negationConcepts.isEmpty()) {
									String[] sentenceTokens = formSentenceWordSequences(sentenceTokensList);
									for(NegPhrase phrs : negationConcepts) {
										String[] scopeTokens = formScopeWordSequences(phrs);
										if(scopeTokens != null) {
											HashMap<String, Integer> scopeIndices = defineScopeIndices(scopeTokens, sentenceTokens);
											if(scopeIndices != null) {
												if(scopeIndices.get(START) != scopeIndices.get(END)) {
													int start = scopeIndices.get(START);
													int end = scopeIndices.get(END);
													if(start >=0 && end < sentenceTokensList.size()) {
														XmesoSentenceToken startBoundary = sentenceTokensList.get(start);
														XmesoSentenceToken endBoundary = sentenceTokensList.get(end);
														List<XmesoNamedEntity> negatedEntities = JCasUtil.selectCovered(jCas,XmesoNamedEntity.class, sentenceAnnotation);
														for(XmesoNamedEntity namedEntity : negatedEntities) {
															markNamedEntity(jCas, namedEntity, startBoundary.getBegin(), endBoundary.getBegin());
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void markNamedEntity(JCas jCas, XmesoNamedEntity entity, int startBoundary, int endBoundary) {
		if(entity != null){
			if(entity.getBegin() >= startBoundary && entity.getEnd() <= endBoundary) {
				entity.setIsNegated(true);
				XmesoNegatedConcept negation = new XmesoNegatedConcept(jCas);
				negation.setBegin(entity.getBegin());
				negation.setEnd(entity.getEnd());
				negation.addToIndexes();
			}
		}
	}

	public HashMap<String,Integer> defineScopeIndices(String[]scopeTokens, String[]sentenceTokens) {
		if(scopeTokens.length > 0 && sentenceTokens.length > 0) {
			int scopeId = 0;
			int sentenceId = 0;
			int startIndex = 0;
			int endIndex = 0;
			while(scopeId < scopeTokens.length && sentenceId < sentenceTokens.length) {
				String sentenceToken = sentenceTokens[sentenceId].trim().toLowerCase().replaceAll(specialCharacterRemovalRegEx, "");
				String scopeToken = scopeTokens[scopeId];
				if(!sentenceToken.isEmpty()) {
					if(sentenceToken.equals(scopeToken)) {
						if(scopeId == 0){
							startIndex = sentenceId; 
						}
						scopeId++;
						sentenceId++;
					} else {
						sentenceId++;
						scopeId = 0;
					}
				} else {
					sentenceId++;
				}
			}
			if(scopeId > (scopeTokens.length - 1)) {
				endIndex = sentenceId;
				HashMap<String,Integer> indicesMap = new HashMap<String,Integer>();
				indicesMap.put(START, startIndex);
				indicesMap.put(END, endIndex);
				return indicesMap;
			} else {
				return null;
			}
		}
		return null;	
	}


	public String removeSpecialCharacters(String sentence) {
		if(sentence != null) {
			sentence = sentence.trim().toLowerCase();
			sentence = sentence.replaceAll(specialCharacterRemovalRegEx, "");
		}
		return sentence;
	}

	public String[] formSentenceWordSequences(List<?> annotations) {
		if(annotations!= null && !annotations.isEmpty()){
			String[] words = new String[annotations.size()];
			Annotation annotation;
			for(int i=0; i<annotations.size(); i++) {
				annotation = (Annotation) annotations.get(i);
				words[i] = annotation.getCoveredText();
			}
			return words;
		}
		return null;
	}

	public String[] formScopeWordSequences(NegPhrase scopePhrase) {
		if(scopePhrase != null){
			if(scopePhrase.getCoveredText() != null){
				String words[] = scopePhrase.getCoveredText().split("\\s+");
				return words;
			}
		}
		return null;
	}

	public void initialize() {
		configurator = new SentenceDetectorConfigurator(false,true);
		configurator.setForceFinalStopsFlag(true);
		configurator.setBalanceParenthesesFlag(true);

		configurator.addPossibleStop(":");
		configurator.addPossibleStop("-");

		boundaryDetector = new SentenceDetector(configurator.getPossibleStops(), 
				configurator.getImpossiblePenUltimates(), 
				configurator.getImpossibleSentenceStarts(),
				configurator.getForceFinalStopsFlag(),
				configurator.getBalanceParenthesesFlag());

		negator = new NegExEngine();
	}
	
	public int trimPartTextStart(Part part) {
        String text = part.getCoveredText();
        int startingIndex = part.getBegin();
        int index = 0;
        while(Character.isWhitespace(text.charAt(index))) {
            index++;
            startingIndex++;
        }
        return startingIndex;
    }

}
