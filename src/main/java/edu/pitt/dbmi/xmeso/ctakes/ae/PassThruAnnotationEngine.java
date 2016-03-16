package edu.pitt.dbmi.xmeso.ctakes.ae;

import org.apache.ctakes.typesystem.type.structured.DocumentID;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

public class PassThruAnnotationEngine extends
		org.apache.uima.fit.component.JCasAnnotator_ImplBase {

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		DocumentID documentId = JCasUtil.selectSingle(jCas, DocumentID.class);
		System.out.println("Processing " + documentId.getDocumentID());
	}
}
