package edu.pitt.dbmi.xmeso.negex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;

import edu.pitt.dbmi.xmeso.model.Model.XmesoSentence;
import edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken;

public class SentenceTokenizationEngine extends org.apache.uima.fit.component.JCasAnnotator_ImplBase {

	private final String spaceSplitRegEx = "[^\\s]+";
	private Pattern sentenceSplitter;

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		System.out.println(getClass().getName() + " being called via a Ruta script..");
		sentenceTokenizer(aJCas);
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

}
