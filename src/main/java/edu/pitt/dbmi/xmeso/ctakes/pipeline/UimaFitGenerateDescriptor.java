package edu.pitt.dbmi.xmeso.ctakes.pipeline;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.xml.sax.SAXException;

import edu.pitt.dbmi.xmeso.ctakes.ae.PassThruAnnotationEngine;

public class UimaFitGenerateDescriptor {

	public static void main(String[] args) {
		UimaFitGenerateDescriptor descriptor = new UimaFitGenerateDescriptor();
		try {
			descriptor.generate();
		} catch (ResourceInitializationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	private void generate() throws FileNotFoundException, SAXException, IOException, ResourceInitializationException {
		AnalysisEngine analysisEngine = AnalysisEngineFactory.createEngine(
				  PassThruAnnotationEngine.class);
		analysisEngine.getAnalysisEngineMetaData().toXML(
				  new FileOutputStream("GetStartedQuickAE.xml"));	
	}

}
