package edu.pitt.dbmi.xmeso.decomposition;

import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import edu.pitt.dbmi.xmeso.model.Model.Part;
import edu.pitt.dbmi.xmeso.model.Model.XmesoSentence;

public class SentenceCreatorAnnotationEngine extends org.apache.uima.fit.component.JCasAnnotator_ImplBase {

	private SentenceDetectorConfigurator detector = new SentenceDetectorConfigurator();

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		for (Part part : JCasUtil.select(aJCas, Part.class)) {
			String partContent = part.getCoveredText();
			if (partContent != null && !partContent.trim().isEmpty()) {
//				System.out.println("Processing section "
//						+ part.getSectionName() + " part # "
//						+ part.getPartNumber());
				sentenceBoundaryTokenizer(aJCas, part, partContent);
			}
		}
	}

	public void sentenceBoundaryTokenizer(JCas jCas, Part part, String partContent) {
		int spanStart = trimPartContentStart(part);
		int spanEnd = -1;
		List<String> sentences = detector.createSentencesForPart(partContent);
		int sentenceNumber = 0;
		for (String sentence : sentences) {
			XmesoSentence annotation = new XmesoSentence(jCas);
			spanEnd = spanStart + sentence.length();
			annotation.setBegin(spanStart);
			annotation.setEnd(spanEnd);
			annotation.setSectionName(part.getSectionName());
			annotation.setPartNumber(part.getPartNumber());
			annotation.setSentenceNumber(sentenceNumber++);
			annotation.addToIndexes();
//			System.out.println("\t\tAdded Sentence(" + spanStart + ","
//					+ spanEnd + ")");
			spanStart += sentence.length();
		}
	}

	public int trimPartContentStart(Part part) {
		String partContent = part.getCoveredText();
		int startingIndex = part.getBegin();
		int index = 0;
		while (Character.isWhitespace(partContent.charAt(index))) {
			index++;
		}
		startingIndex += index;
		return startingIndex;
	}

}
