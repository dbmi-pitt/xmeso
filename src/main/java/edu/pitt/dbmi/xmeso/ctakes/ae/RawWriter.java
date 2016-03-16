package edu.pitt.dbmi.xmeso.ctakes.ae;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.ctakes.typesystem.type.structured.DocumentID;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;


public class RawWriter extends
		org.apache.uima.fit.component.JCasAnnotator_ImplBase {

	public static final String PARAM_STRING = "outputDirectory";
	@ConfigurationParameter(name = PARAM_STRING)
	private String outputDirectory;

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		try {
			tryProcess(jCas);
		} catch (IOException e) {
			throw new  AnalysisEngineProcessException(e);
		}
	}
	
	private void tryProcess(JCas jCas) throws IOException {
		File outputDirectoryLocation = new File(outputDirectory);
		if (outputDirectoryLocation.exists() && 
				outputDirectoryLocation.isDirectory()) {
			DocumentID documentId = JCasUtil.selectSingle(jCas, DocumentID.class);
			File outputFile = new File(outputDirectoryLocation, documentId.getDocumentID() + ".txt");
			String content = jCas.getDocumentText();
			FileUtils.write(outputFile, content,  "utf-8");
		}
		
	}
}
