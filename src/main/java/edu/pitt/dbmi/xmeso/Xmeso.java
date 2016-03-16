package edu.pitt.dbmi.xmeso;

import java.io.IOException;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.CasUtil;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.xml.sax.SAXException;

import edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoEncounterForm;
import edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm;

public class Xmeso {

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
		
		AnalysisEngine engine = AnalysisEngineFactory
				.createEngine("edu.pitt.dbmi.xmeso.xmesoEngine");
		CAS cas = engine.newCAS();
		cas.setDocumentText(generateTestDocument());
		engine.process(cas);
		displayCas(cas);
	}
	
	private void displayCas(CAS cas) {
		// EncounterForm information
		final Type encounterFormType = cas.getTypeSystem().getType(
				"edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoEncounterForm");
		for (AnnotationFS encounterFormFS : CasUtil.select(cas,
				encounterFormType)) {
			System.out.println("Found encounter form: "
					+ encounterFormFS.getCoveredText());
			XmesoEncounterForm encounterForm = (XmesoEncounterForm) encounterFormFS;
			String surgicalProcedureCode = encounterForm
					.getSurgicalProcedureCode();
			String histologicTypeCode = encounterForm.getHistologicTypeCode();
			System.out.println("surgicalProcedureCode = "
					+ surgicalProcedureCode);
			System.out.println("histologicTypeCode = " + histologicTypeCode);
		}

		// TumorForm information
		final Type tumorFormType = cas.getTypeSystem().getType(
				"edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
		for (AnnotationFS tumorFormFS : CasUtil.select(cas, tumorFormType)) {
			// System.out.println("Found tumor form: " +
			// tumorFormFS.getCoveredText());
			XmesoTumorForm tumorForm = (XmesoTumorForm) tumorFormFS;
			String tumorSiteCode = tumorForm.getTumorSiteCode();
			String tumorConfigurationCode = tumorForm
					.getTumorConfigurationCode();
			String tumorDifferentiationCode = tumorForm
					.getTumorDifferentiationCode();
			System.out.println("tumorSiteCode = " + tumorSiteCode);
			System.out.println("tumorConfigurationCode = "
					+ tumorConfigurationCode);
			System.out.println("tumorDifferentiationCode = "
					+ tumorDifferentiationCode);
		}

	}

	private String generateTestDocument() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("FINAL DIAGNOSIS:\n");
		sb.append("\n");
		sb.append("  Part 1:\n");
		sb.append("  \n");
		sb.append("  Biopsy of chest wall.\n");
		sb.append("  Desmoplastic mesothelioma. \n");
		sb.append("  1.233 x 3.45 x 2.1 cm.\n");
		sb.append("  Base of left lung.\n");
		sb.append("  Cystic.\n");
		sb.append("  Intermediate.\n");
		sb.append("  \n");
		sb.append("  Part 2:\n");
		sb.append("  \n");
		sb.append("  Biopsy of chest wall.\n");
		sb.append("  Desmoplastic mesothelioma. \n");
		sb.append("  Base of right lung.\n");
		sb.append("  Endophytic.\n");
		sb.append("  High.\n");
		sb.append("   2.2 x 2.44 x 2.13 cm.\n");
		sb.append("  \n");
		sb.append("  Part 3:\n");
		sb.append("  \n");
		sb.append("    Biopsy of chest wall.\n");
		sb.append("    Desmoplastic mesothelioma. \n");
		sb.append("    Diaphragmatic Pleura.\n");
		sb.append("    Diffuse.\n");
		sb.append("    Low.\n");
		sb.append("    1.7 x 1.34 x 0.45 CM.\n");
		sb.append("  \n");
		sb.append("  $\n");
		return sb.toString();
	}

}
