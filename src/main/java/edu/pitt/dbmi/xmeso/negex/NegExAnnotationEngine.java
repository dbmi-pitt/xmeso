package edu.pitt.dbmi.xmeso.negex;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity;
import edu.pitt.dbmi.xmeso.model.Model.XmesoNegatedConcept;
import edu.pitt.dbmi.xmeso.model.Model.XmesoSentence;
import edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken;

public class NegExAnnotationEngine extends
		org.apache.uima.fit.component.JCasAnnotator_ImplBase {

	private NegExEngine negator = new NegExEngine();

	private final String spaceSplitRegEx = "(\\w+|\\$[\\d\\.]+|\\S+)";
	private Pattern sentenceSplitter;

	private final String specialCharacterRemovalRegEx = "[^a-zA-Z0-9\\s\t]";

	private final String START = "START";
	private final String END = "END";

	/*
	 * PIPELINE : 1. Sentence Not Null 2. Sentence Tokens Not Null 3. Contains
	 * Named Entities 4. Contains Negation Concepts 5. Define Boundary Indices
	 * 6. Check if Named Entities fall within boundaries.
	 */

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		tokenizeSentences(jCas);
		defineNegations(jCas);
	}

	public void tokenizeSentences(JCas jCas) {
		sentenceSplitter = Pattern.compile(spaceSplitRegEx);
		for (XmesoSentence sentenceAnnotation : JCasUtil.select(jCas,
				XmesoSentence.class)) {
			int startingIndex = sentenceAnnotation.getBegin();
			String sentenceContent = sentenceAnnotation.getCoveredText();
			Matcher matcher = sentenceSplitter.matcher(sentenceContent);
			while (matcher.find()) {
				int start = (matcher.start() + startingIndex);
				int end = (matcher.end() + startingIndex);
				XmesoSentenceToken sentenceToken = new XmesoSentenceToken(jCas);
				sentenceToken.setBegin(start);
				sentenceToken.setEnd(end);
				sentenceToken.addToIndexes();
			}
		}
	}

	public void defineNegations(JCas jCas) {
		for (XmesoSentence sentenceAnnotation : JCasUtil.select(jCas,
				XmesoSentence.class)) {
			String sentenceContent = sentenceAnnotation.getCoveredText();
			sentenceContent = removeSpecialCharacters(sentenceContent);
			if (sentenceContent == null
					|| sentenceContent.isEmpty()
					|| !JCasUtil.contains(jCas, sentenceAnnotation,
							XmesoNamedEntity.class)) {
				continue;
			}
			List<NegPhrase> negationIndicatingPhrases = negator
					.findNegationIndicators(sentenceContent);
			if (negationIndicatingPhrases == null
					|| negationIndicatingPhrases.isEmpty())
				continue;
			List<XmesoSentenceToken> sentenceTokensList = JCasUtil
					.selectCovered(jCas, XmesoSentenceToken.class,
							sentenceAnnotation);
			String[] sentenceTokens = formSentenceWordSequences(sentenceTokensList);
			for (NegPhrase negExPhrase : negationIndicatingPhrases) {
				processNegPhrase(jCas, sentenceAnnotation, negExPhrase,
						sentenceTokensList, sentenceTokens);
			}
		}
	}

	private void processNegPhrase(JCas jCas, XmesoSentence sentenceAnnotation,
			NegPhrase negationIndicatingPhrase,
			List<XmesoSentenceToken> sentenceTokensList, String[] sentenceTokens) {
		String[] negationIndicatingPhraseTokens = tokenizeNegationIndicatingPhrase(negationIndicatingPhrase);
		HashMap<String, Integer> scopeIndices = defineScopeIndices(
				negationIndicatingPhraseTokens, sentenceTokens);
		int start = scopeIndices.get(START);
		int end = scopeIndices.get(END);
		if (start >= 0 && end < sentenceTokensList.size() && start != end) {
			XmesoSentenceToken startBoundary = sentenceTokensList.get(start);
			XmesoSentenceToken endBoundary = sentenceTokensList.get(end);
			List<XmesoNamedEntity> negatedEntities = JCasUtil.selectCovered(
					jCas, XmesoNamedEntity.class, sentenceAnnotation);
			for (XmesoNamedEntity namedEntity : negatedEntities) {
				markNamedEntity(jCas, namedEntity, startBoundary.getBegin(),
						endBoundary.getBegin());
			}
		}
	}

	public void markNamedEntity(JCas jCas, XmesoNamedEntity entity,
			int startBoundary, int endBoundary) {
		if (entity.getBegin() >= startBoundary
				&& entity.getEnd() <= endBoundary) {
			entity.setIsNegated(true);
			XmesoNegatedConcept negation = new XmesoNegatedConcept(jCas);
			negation.setBegin(entity.getBegin());
			negation.setEnd(entity.getEnd());
			negation.addToIndexes();
		}
	}

	public HashMap<String, Integer> defineScopeIndices(
			String[] negexPhraseTokens, String[] sentenceTokens) {
		final HashMap<String, Integer> indicesMap = new HashMap<String, Integer>();
		indicesMap.put(START, -1);
		indicesMap.put(END, Integer.MAX_VALUE);
		if (negexPhraseTokens.length == 0 || sentenceTokens.length == 0) {
			return indicesMap;
		}

		int negexPhraseIdx = 0;
		int sentenceIdx = 0;
		int startIndex = 0;
		int endIndex = 0;
		while (negexPhraseIdx < negexPhraseTokens.length
				&& sentenceIdx < sentenceTokens.length) {
			String sentenceToken = sentenceTokens[sentenceIdx].trim()
					.toLowerCase().replaceAll(specialCharacterRemovalRegEx, "");
			String negexPhraseToken = negexPhraseTokens[negexPhraseIdx];
			if (!sentenceToken.isEmpty()) {
				if (sentenceToken.equals(negexPhraseToken)) {
					if (negexPhraseIdx == 0) {
						startIndex = sentenceIdx;
					}
					negexPhraseIdx++;
					sentenceIdx++;
				} else {
					sentenceIdx++;
					negexPhraseIdx = 0;
				}
			} else {
				sentenceIdx++;
			}
		}
		if (negexPhraseIdx > (negexPhraseTokens.length - 1)) {
			endIndex = sentenceIdx;
			indicesMap.put(START, startIndex);
			indicesMap.put(END, endIndex);
		}

		return indicesMap;
	}

	public String removeSpecialCharacters(String sentence) {
		if (sentence != null) {
			sentence = sentence.trim().toLowerCase();
			sentence = sentence.replaceAll(specialCharacterRemovalRegEx, "");
		}
		return sentence;
	}

	public String[] formSentenceWordSequences(List<?> annotations) {
		if (annotations != null && !annotations.isEmpty()) {
			String[] words = new String[annotations.size()];
			Annotation annotation;
			for (int i = 0; i < annotations.size(); i++) {
				annotation = (Annotation) annotations.get(i);
				words[i] = annotation.getCoveredText();
			}
			return words;
		}
		return null;
	}

	public String[] tokenizeNegationIndicatingPhrase(
			NegPhrase negationIndicatingPhrase) {
		if (negationIndicatingPhrase.getCoveredText() != null) {
			String words[] = negationIndicatingPhrase.getCoveredText().split(
					"\\s+");
			return words;
		}
		return new String[0];
	}

}
