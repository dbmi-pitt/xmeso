package edu.pitt.dbmi.xmeso.qa;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.util.CasIOUtil;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.xml.sax.SAXException;

import edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm;
import edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm;

public class QaXmiReader {

	private final List<XmesoCase> xmesoCases = new ArrayList<XmesoCase>();
	private final Map<String, XmesoCase> xmesoCaseMap = new HashMap<String, XmesoCase>();
	private final List<XmesoCase> xmesoCasesCodified = new ArrayList<XmesoCase>();
	private final Map<String, XmesoCase> xmesoCaseCodifiedMap = new HashMap<String, XmesoCase>();

	public static void main(String[] args) {
		QaXmiReader qaXmiReader = new QaXmiReader();
		qaXmiReader.execute();
	}

	public void execute() {
		try {
			tryExecute();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UIMAException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public void tryExecute() throws IOException, UIMAException, SAXException {
		File xmiDirectory = new File("C:\\Users\\zhy19\\workspace\\xmeso\\output");
		Iterator<XmesoCase> xmesoCaseIterator = xmesoCases.iterator();
		Iterator<XmesoCase> xmesoCaseCodifiedIterator = xmesoCasesCodified.iterator();
		while (xmesoCaseIterator.hasNext()) {
			XmesoCase xmesoCase = xmesoCaseIterator.next();
			XmesoCase xmesoCaseCodified = xmesoCaseCodifiedIterator.next();
			String reportNumberAsString = xmesoCase.getReportNumber();
			reportNumberAsString = StringUtils.leftPad(reportNumberAsString, 5,
					"0");
			String reportName = "report_" + reportNumberAsString + ".txt.xmi";
			File reportFile = new File(xmiDirectory, reportName);
			if (reportFile.exists() && reportFile.isFile()) {
				JCas jCas = convertXmiToJCas(reportFile);
				xferCasToXmesoCase(reportNumberAsString, jCas, xmesoCase, xmesoCaseCodified);
				int partCount = getPartCount(jCas);
				System.out.println(reportName + " has " + partCount + " parts\n");
			} else {
				System.out.println("Failed to read raw xmi for "
						+ reportFile.getName());
			}
		}
	}

	private void xferCasToXmesoCase(String reportNumberAsString, JCas jCas,
			XmesoCase xmesoCase, XmesoCase xmesoCaseCodified) {

		XmesoCaseForm xmesoCaseForm = JCasUtil.selectSingle(jCas,
				XmesoCaseForm.class);
		if (xmesoCaseForm != null) {
			xmesoCase.setProcedureType(xmesoCaseForm.getSurgicalProcedureTerm());
			String ultraTerm = xmesoCaseForm.getUltrastructuralFindingsTerm();
			xmesoCase.setUltrastructuralFindings(ultraTerm);	
			xmesoCase.setLymphNodesExamined(xmesoCaseForm.getLymphNodesExaminedTerm());
			xmesoCase.setSpecialStainProfile(xmesoCaseForm.getSpecialStainTerm());
				
			xmesoCaseCodified.setProcedureType(xmesoCaseForm.getSurgicalProcedure());
			String ultraCode = xmesoCaseForm.getUltrastructuralFindings();
			xmesoCaseCodified.setUltrastructuralFindings(ultraCode);	
			xmesoCaseCodified.setLymphNodesExamined(xmesoCaseForm.getLymphNodesExamined());
			xmesoCaseCodified.setSpecialStainProfile(xmesoCaseForm.getSpecialStain());		
		}
		
		Collection<XmesoTumorForm> xmesoTumors = JCasUtil.select(jCas,
				XmesoTumorForm.class);
		for (XmesoTumorForm tumorForm : xmesoTumors) {
			int currentPart = tumorForm.getCurrentPart();
			if (currentPart > 0 && currentPart < 9) {
				XmesoPart tgtXmesoPart = xmesoCaseCodified.getXmesoParts()[currentPart-1];
				tgtXmesoPart.setHistologicalType(tumorForm.getHistopathologicalType());
				tgtXmesoPart.setTumorConfiguration(tumorForm.getTumorConfiguration());
				tgtXmesoPart.setTumorDifferentiationOrGrade(tumorForm.getTumorDifferentiation());
				tgtXmesoPart.setSiteOfTumor(tumorForm.getTumorSite());
				
				
				tgtXmesoPart = xmesoCase.getXmesoParts()[currentPart-1];
				tgtXmesoPart.setHistologicalType(tumorForm.getHistopathologicalTypeTerm());
				tgtXmesoPart.setTumorConfiguration(tumorForm.getTumorConfigurationTerm());
				tgtXmesoPart.setTumorDifferentiationOrGrade(tumorForm.getTumorDifferentiationTerm());
				tgtXmesoPart.setSiteOfTumor(tumorForm.getTumorSiteTerm());
			
				
			}
		}
	}

	private int getPartCount(JCas jCas) {
		int partCount = 0;
		Collection<XmesoTumorForm> xmesoTumors = JCasUtil.select(jCas,
				XmesoTumorForm.class);
		for (XmesoTumorForm tumorForm : xmesoTumors) {
			int currentPart = tumorForm.getCurrentPart();
			if (currentPart > partCount) {
				partCount = currentPart;
			}	
		}
		return partCount;
	}

	private JCas convertXmiToJCas(File reportFile) throws UIMAException,
			SAXException, IOException {
		final String xmesoTypeSystemPath = "C:\\Users\\zhy19\\workspace\\xmeso\\descriptor\\edu\\pitt\\dbmi\\xmeso\\XmesoTypeSystem.xml";
		File xmesoTypeDescriptor = new File(xmesoTypeSystemPath);
		TypeSystemDescription typeSystemDescription = TypeSystemDescriptionFactory
				.createTypeSystemDescriptionFromPath(xmesoTypeDescriptor.toURI().toString());
		JCas jCas = JCasFactory.createJCas(typeSystemDescription);
		CasIOUtil.readJCas(jCas, reportFile);
		return jCas;
	}

	public void replaceAllCases(List<XmesoCase> xmesoCases, List<XmesoCase> xmesoCasesCodified) {
		this.xmesoCases.clear();
		this.xmesoCases.addAll(xmesoCases);
		xmesoCaseMap.clear();
		for (XmesoCase xmesoCase : xmesoCases) {
			String formattedReportNumber = StringUtils.leftPad(xmesoCase.getReportNumber(), 5, "0");
			xmesoCaseMap.put(formattedReportNumber, xmesoCase);
		}
		
		this.xmesoCasesCodified.clear();
		this.xmesoCasesCodified.addAll(xmesoCasesCodified);
		xmesoCaseMap.clear();
		for (XmesoCase xmesoCase : xmesoCasesCodified) {
			String formattedReportNumber = StringUtils.leftPad(xmesoCase.getReportNumber(), 5, "0");
			xmesoCaseCodifiedMap.put(formattedReportNumber, xmesoCase);
		}
	}

}
