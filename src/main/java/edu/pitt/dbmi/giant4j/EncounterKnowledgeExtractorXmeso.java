package edu.pitt.dbmi.giant4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLSerializer;
import org.xml.sax.SAXException;

import edu.pitt.dbmi.giant4j.form.XmesoFormPartSet;
import edu.pitt.dbmi.giant4j.kb.KbEncounter;
import edu.pitt.dbmi.giant4j.kb.KbPatient;
import edu.pitt.dbmi.giant4j.kb.KbSummary;
import edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm;

@SuppressWarnings("unused")
public class EncounterKnowledgeExtractorXmeso implements
		EncounterKnowledgeExtractorInterface {

	private AnalysisEngine ae = null;

	public EncounterKnowledgeExtractorXmeso() {
	}

	public void executePatient(KbPatient kbPatient) {
		for (KbEncounter kbEncounter : kbPatient.getEncounters()) {
			executeEncounter(kbEncounter);
		}
		kbPatient.clearSummaries(); // Patient summaries must be
		// recomputed
	}

	//
	// Runs pipe over encounter note, xmi serialized the results,
	// stores the xmi serialized results in the encounter path attribute
	//
	@Override
	public void executeEncounter(KbEncounter kbEncounter) {
		try {
			buildXmesoAnalysisEngine();
			JCas encounterJCas = ae.newJCas();
			runXmesoPipeline(encounterJCas, kbEncounter);
			cacheFormData(encounterJCas, kbEncounter);
			cacheSerializedCasXmi(encounterJCas, kbEncounter);
		} catch (IOException | SAXException | UIMAException e) {
			e.printStackTrace();
		}
	}

	private void cacheFormData(JCas encounterJCas, KbEncounter kbEncounter) {

		XmesoFormPartSet partSet = new XmesoFormPartSet();
		
		for (AnnotationFS tumorFormFS : JCasUtil.select(encounterJCas, XmesoTumorForm.class)) {
			XmesoTumorForm tumorForm = (XmesoTumorForm) tumorFormFS;
			int currentPart = tumorForm.getCurrentPart();
			String histologicTypeCode = tumorForm.getHistopathologicalType();
			String tumorSiteCode = tumorForm.getTumorSite();
			String tumorConfigurationCode = tumorForm
					.getTumorConfiguration();
			String tumorDifferentiationCode = tumorForm
					.getTumorDifferentiation();	
			partSet.getFormData()[currentPart].setTumorSite(tumorSiteCode);
			partSet.getFormData()[currentPart].setHistologicType(histologicTypeCode);
			partSet.getFormData()[currentPart].setTumorConfiguration(tumorConfigurationCode);
			partSet.getFormData()[currentPart].setTumorDifferentiation(tumorDifferentiationCode);
		}
		
		KbSummary kbSummary = new KbSummary();
		kbSummary.setBaseCode("Xmeso:Machine");
		kbSummary.setCode(kbSummary.getBaseCode());
		kbSummary.setValue(new String(SerializationUtils.serialize(partSet)));
		kbEncounter.addSummary(kbSummary);
		
		
		
	}

	private void runXmesoPipeline(JCas encounterJCas, KbEncounter kbEncounter) throws AnalysisEngineProcessException {
		String documentText = kbEncounter.getContent();
		encounterJCas.setDocumentText(documentText);
		SimplePipeline.runPipeline(encounterJCas, ae);
	}

	private void cacheSerializedCasXmi(JCas encounterJCas, KbEncounter kbEncounter) throws IOException, SAXException {
		String xmi = serializeXmi(encounterJCas.getCas());
		kbEncounter.setXmi(xmi);
	}

	private void buildXmesoAnalysisEngine() throws InvalidXMLException, ResourceInitializationException, IOException {
		if (ae == null) {
			final String[] resourcePaths = {"C:\\ws\\ws-xmeso\\xmeso\\resources"};
			ae = AnalysisEngineFactory
					.createEngine("edu.pitt.dbmi.xmeso.XmesoEngine",
							"resourcePaths", resourcePaths, 
							"lowMemoryProfile", false);
		}
	}

	@Override
	public void setAnalysisEngine(AnalysisEngine ae) {
		this.ae = ae;
	}

	private void clearDerivedSummaries(KbEncounter encounter) {
		List<KbSummary> summariesToRemove = new ArrayList<KbSummary>();
		for (KbSummary removalCandidate : encounter.getSummaries()) {
			if (!removalCandidate.getCode().matches("Xmeso:Anafora|Xmeso:Machine")) {
				summariesToRemove.add(removalCandidate);
			}
		}
		encounter.getSummaries().removeAll(summariesToRemove);
	}
	
	public String serializeXmi(CAS aCas) throws IOException, SAXException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XmiCasSerializer ser = new XmiCasSerializer(aCas.getTypeSystem());
		XMLSerializer xmlSer = new XMLSerializer(out, true);
		ser.serialize(aCas, xmlSer.getContentHandler());
		return out.toString("utf-8");
	}

}
