package edu.pitt.dbmi.xmeso.negex;

import java.util.HashMap;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity;
import edu.pitt.dbmi.xmeso.model.Model.XmesoNegatedConcept;
import edu.pitt.dbmi.xmeso.model.Model.XmesoSentence;
import edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken;

public class NegExAnnotationEngine extends org.apache.uima.fit.component.JCasAnnotator_ImplBase {

	private NegExEngine negator;
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

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {	
		initialize();
		defineNegations(jCas);
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
												int start = scopeIndices.get(START);
												int end = scopeIndices.get(END);
												if(start >=0 && end < sentenceTokensList.size()) {
													XmesoSentenceToken startBoundary = sentenceTokensList.get(start);
													XmesoSentenceToken endBoundary = sentenceTokensList.get(end);
													List<XmesoNamedEntity> negatedEntities = JCasUtil.selectCovered(jCas,XmesoNamedEntity.class, sentenceAnnotation);
													for(XmesoNamedEntity namedEntity : negatedEntities) {
														markNamedEntity(jCas, namedEntity, startBoundary.getBegin(), endBoundary.getEnd());
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
			if(scopeId >= (scopeTokens.length)) {
				endIndex = sentenceId;
				if(endIndex == sentenceTokens.length) {  //NOTE : A special case where we have reached the end of String.
					endIndex--;
				}
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
		negator = new NegExEngine();
	}
}
