package edu.pitt.dbmi.xmeso.negex;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import edu.pitt.dbmi.xmeso.model.Model.Part;
import edu.pitt.dbmi.xmeso.model.Model.XmesoSentence;

public class SentenceBoundaryDetectionEngine extends org.apache.uima.fit.component.JCasAnnotator_ImplBase {

	private SentenceDetectorConfigurator configurator; 
	private SentenceDetector boundaryDetector;

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
//		System.out.println(getClass().getName() + " being called via a Ruta script..");
		initialize();
		initializeSentenceBoundaryDetection(aJCas);
	}

	public void initializeSentenceBoundaryDetection(JCas jCas){
//		System.out.println(getClass().getName() + " being called via a Ruta script..");
		Collection<Part> parts = JCasUtil.select(jCas, Part.class);
		initialize();
		for (Part part : parts) {
//			System.out.println("Section: " + part.getSectionName() + " Part: " + part.getPartNumber());
			if(part != null) {
				String scope = part.getCoveredText();
				if(scope != null && !scope.trim().isEmpty()) {
					sentenceBoundaryTokenizer(jCas, part, scope);
				}
			}
		}
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

	public void initialize() {
		configurator = new SentenceDetectorConfigurator(false,true);
		configurator.setForceFinalStopsFlag(false);
		configurator.setBalanceParenthesesFlag(true);

		configurator.addPossibleStop(":");
		configurator.addPossibleStop("-");

		boundaryDetector = new SentenceDetector(configurator.getPossibleStops(), 
				configurator.getImpossiblePenUltimates(), 
				configurator.getImpossibleSentenceStarts(),
				configurator.getForceFinalStopsFlag(),
				configurator.getBalanceParenthesesFlag());
	}

}
