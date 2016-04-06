package edu.pitt.dbmi.xmeso;

import java.io.File;
import java.io.IOException;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.sonar.runner.commonsio.FileUtils;
import org.xml.sax.SAXException;

import edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm;

public class Xmeso {
	
	private AnalysisEngine engine;
	private int reportNumber = 0;

	public static void main(String[] args) {
		final Xmeso xmeso = new Xmeso();
		try {
			xmeso.execute();
		} catch (AnalysisEngineProcessException e) {
			e.printStackTrace();
		} catch (InvalidXMLException e) {
			e.printStackTrace();
		} catch (ResourceInitializationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public Xmeso() {
		System.out.println("Instantiating Xmeso");
	}

	public void execute() throws InvalidXMLException,
			ResourceInitializationException, IOException,
			AnalysisEngineProcessException, SAXException {
		final String[] resourcePaths = {"C:\\ws\\ws-xmeso\\xmeso\\resources"};
		engine = AnalysisEngineFactory
				.createEngine("edu.pitt.dbmi.xmeso.XmesoEngine",
						"resourcePaths", resourcePaths, 
						"lowMemoryProfile", false);
		File inputDirectory = new File("C:\\ws\\ws-xmeso\\xmeso\\data\\reports");
		inputDirectory = new File("C:\\ws\\ws-xmeso\\xmeso\\input");
		File[] inputFiles = inputDirectory.listFiles();
		for (File inputFile : inputFiles) {
			processFile(inputFile);
			reportNumber++;
		}
		
	}
	
	private void processFile(File inputFile) {
		String content;
		try {
			content = FileUtils.readFileToString(inputFile);
			JCas jCas = engine.newJCas();
			jCas.setDocumentText(content);
			engine.process(jCas);
//			displayCas(jCas);
			System.out.println("Report #" + reportNumber + " succeeded");
		} catch (Exception e) {
			System.err.println("Report #" + reportNumber + " failed");
		}
		
	}
	
	private void displayCas(JCas jCas) {
		
		// TumorForm information
	
		for (AnnotationFS tumorFormFS : JCasUtil.select(jCas, XmesoTumorForm.class)) {
			XmesoTumorForm tumorForm = (XmesoTumorForm) tumorFormFS;
			String surgicalProcedureCode = tumorForm.getSurgicalProcedure();
			String histologicTypeCode = tumorForm.getHistopathologicalType();
			System.out.println("surgicalProcedureCode = "
					+ surgicalProcedureCode);
			System.out.println("histologicTypeCode = " + histologicTypeCode);
			String tumorSiteCode = tumorForm.getTumorSite();
			String tumorConfigurationCode = tumorForm
					.getTumorConfiguration();
			String tumorDifferentiationCode = tumorForm
					.getTumorDifferentiation();
			System.out.println("tumorSiteCode = " + tumorSiteCode);
			System.out.println("tumorConfigurationCode = "
					+ tumorConfigurationCode);
			System.out.println("tumorDifferentiationCode = "
					+ tumorDifferentiationCode);
		}

	}

}
