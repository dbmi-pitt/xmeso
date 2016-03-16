package edu.pitt.dbmi.xmeso.ctakes.pipeline;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;

import edu.pitt.dbmi.xmeso.ctakes.ae.PassThruAnnotationEngine;

public class UimaFitTester {

	public static void main(String[] args) {
		UimaFitTester tester = new UimaFitTester();
		try {
			tester.execute();
		} catch (UIMAException e) {
			e.printStackTrace();
		}

	}

	private void execute() throws UIMAException {
		JCas jCas = JCasFactory.createJCas();
		AnalysisEngine analysisEngine = AnalysisEngineFactory.createEngine(
				PassThruAnnotationEngine.class);
		analysisEngine.process(jCas);
		
		
	}

}
