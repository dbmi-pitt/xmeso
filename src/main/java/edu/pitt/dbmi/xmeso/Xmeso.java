package edu.pitt.dbmi.xmeso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.I2b2UserHomeDataSourceManager;
import edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm;
import edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm;

public class Xmeso {

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	private String xmesoHome;
	private Properties xmesoProperties = new Properties();

	private final Map<String, String> visitDateMap = new HashMap<String, String>();

	private I2b2UserHomeDataSourceManager i2b2DataSourceManager;
	private I2B2DemoDataWriter i2b2DemoDataWriter = new I2B2DemoDataWriter();

	private String reportId;
	private String mvbName;
	private String patientId;
	private String formattedDate;

	private AnalysisEngine engine;
	private File currentReportFile;

	private boolean isDebugging = false;

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
	}

	public void execute() throws InvalidXMLException,
			ResourceInitializationException, IOException,
			AnalysisEngineProcessException, SAXException {

		Map<String, String> env = System.getenv();
		displaySystemEnvironmentVariables(env);
		xmesoHome = env.get("XMESO_HOME");
		System.out.println("XMESO_HOME is " + xmesoHome);

		loadXmesoProperties();

		mapVisitDates();

		i2b2DataSourceManager = new I2b2UserHomeDataSourceManager();
		i2b2DemoDataWriter.setDataSourceMgr(i2b2DataSourceManager);

		processReports();

		i2b2DataSourceManager.destroy();

	}

	private void displaySystemEnvironmentVariables(Map<String, String> env) {
		if (isDebugging) {
			final List<String> sortedKeys = new ArrayList<String>();
			sortedKeys.addAll(env.keySet());
			Collections.sort(sortedKeys);
			for (String envName : sortedKeys) {
				System.out.format("%s=%s%n", envName, env.get(envName));
			}
		}
	}

	private void mapVisitDates() throws IOException {
		File eventDatesFile = new File(xmesoHome + File.separator
				+ "nmvb_path_report_event_date.csv");
		for (String line : FileUtils.readLines(eventDatesFile)) {
			if (line.contains("REPORT_ID")) {
				continue;
			}
			String[] fields = line.split(",");
			reportId = fields[0];
			mvbName = fields[1];
			patientId = fields[2];
			formattedDate = fields[3];
			String key = mvbName + "_" + reportId + ".txt";
			if (visitDateMap.get(key) != null) {
				System.err
						.println("Replacing an existing visit date at " + key);
			}
			visitDateMap.put(key, formattedDate);
		}
	}

	private void loadXmesoProperties() {
		InputStream input = null;
		try {
			input = new FileInputStream(xmesoHome + File.separator
					+ "xmeso.properties");
			xmesoProperties.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void processReports() throws InvalidXMLException,
			ResourceInitializationException, IOException {
		final String resourcePath = (new File(".")).getAbsolutePath() + File.separator + "resources";
		System.out.println("Setting resourcePath to " + resourcePath);
		final String[] resourcePaths = { resourcePath };
		engine = AnalysisEngineFactory.createEngine(
				"edu.pitt.dbmi.xmeso.XmesoEngine", "resourcePaths",
				resourcePaths, "lowMemoryProfile", false);
		File inputDirectory = new File(xmesoHome + File.separator + "reports");
		File[] inputFiles = inputDirectory.listFiles();
		for (File inputFile : inputFiles) {
			currentReportFile = inputFile;
			processReport();
		}
	}

	private void processReport() {
		String content;
		try {
			cachePatientAndVisitFromName(currentReportFile.getName());
			content = FileUtils.readFileToString(currentReportFile);
			JCas jCas = engine.newJCas();
			jCas.setDocumentText(content + "\n$\n");
			engine.process(jCas);
			populateCas(jCas);
			System.out.println("Successfully processed report #" + reportId);
		} catch (Exception e) {
			System.err.println("Report #" + reportId + " failed");
		}
	}

	private void cachePatientAndVisitFromName(String name) {
		Pattern nameExtractionPattern = Pattern.compile("MVB(\\d{4})_(\\d{5})");
		Matcher matcher = nameExtractionPattern.matcher(name);
		if (matcher.find()) {
			patientId = matcher.group(1);
			reportId = matcher.group(2);
		}
	}

	private void populateCas(JCas jCas) throws ParseException {

		// Establish the patient
		i2b2DemoDataWriter.setPatientNum(Integer.parseInt(patientId));
		i2b2DemoDataWriter.fetchOrCreatePatient();

		i2b2DemoDataWriter.setVisitNum(Integer.parseInt(reportId));
		String eventKey = currentReportFile.getName();
		String formattedDate = this.visitDateMap.get(eventKey);
		Date visitDate = df.parse(formattedDate);
		i2b2DemoDataWriter.setVisitDate(visitDate);
		i2b2DemoDataWriter.fetchOrCreateVisit();

		// Case level information

		for (AnnotationFS caseFormFs : JCasUtil.select(jCas,
				XmesoCaseForm.class)) {
			XmesoCaseForm caseForm = (XmesoCaseForm) caseFormFs;
			String lymphNodesExamined = caseForm.getLymphNodesExamined();
			String specialStain = caseForm.getSpecialStain();
			String ultraStructuralFindings = caseForm
					.getUltrastructuralFindings();
			if (isDebugging) {
				System.out
						.println("lymphNodesExamined = " + lymphNodesExamined);
				System.out.println("specialStain = " + specialStain);
				System.out.println("ultraStructuralFindings = "
						+ ultraStructuralFindings);
			}

			i2b2DemoDataWriter.fetchOrCreateConcept(lymphNodesExamined);
			i2b2DemoDataWriter.fetchOrCreateConcept(specialStain);
			i2b2DemoDataWriter.fetchOrCreateConcept(ultraStructuralFindings);

			i2b2DemoDataWriter.writeObservation(Integer.parseInt(patientId),
					Integer.parseInt(reportId), lymphNodesExamined, 0L);
			i2b2DemoDataWriter.writeObservation(Integer.parseInt(patientId),
					Integer.parseInt(reportId), specialStain, 0L);
			i2b2DemoDataWriter.writeObservation(Integer.parseInt(patientId),
					Integer.parseInt(reportId), ultraStructuralFindings, 0L);

		}

		// TumorForm information

		for (AnnotationFS tumorFormFS : JCasUtil.select(jCas,
				XmesoTumorForm.class)) {
			XmesoTumorForm tumorForm = (XmesoTumorForm) tumorFormFS;
			long currentPartNumber = Long.parseLong(tumorForm.getCurrentPart()
					+ "");
			String histologicTypeCode = tumorForm.getHistopathologicalType();
			String tumorConfigurationCode = tumorForm.getTumorConfiguration();
			String tumorDifferentiationCode = tumorForm
					.getTumorDifferentiation();
			if (isDebugging) {
				System.out
						.println("histologicTypeCode = " + histologicTypeCode);
				System.out.println("tumorConfigurationCode = "
						+ tumorConfigurationCode);
				System.out.println("tumorDifferentiationCode = "
						+ tumorDifferentiationCode);
			}

			i2b2DemoDataWriter.fetchOrCreateConcept(histologicTypeCode);
			i2b2DemoDataWriter.fetchOrCreateConcept(tumorConfigurationCode);
			i2b2DemoDataWriter.fetchOrCreateConcept(tumorDifferentiationCode);

			i2b2DemoDataWriter.writeObservation(Integer.parseInt(patientId),
					Integer.parseInt(reportId), histologicTypeCode,
					currentPartNumber);
			i2b2DemoDataWriter.writeObservation(Integer.parseInt(patientId),
					Integer.parseInt(reportId), tumorConfigurationCode,
					currentPartNumber);
			i2b2DemoDataWriter.writeObservation(Integer.parseInt(patientId),
					Integer.parseInt(reportId), tumorDifferentiationCode,
					currentPartNumber);
		}

	}

	@SuppressWarnings("unused")
	private void displayCas(JCas jCas) {

		// TumorForm information

		for (AnnotationFS tumorFormFS : JCasUtil.select(jCas,
				XmesoTumorForm.class)) {
			XmesoTumorForm tumorForm = (XmesoTumorForm) tumorFormFS;
			String tumorSiteCode = tumorForm.getTumorSite();
			String histologicTypeCode = tumorForm.getHistopathologicalType();
			String tumorConfigurationCode = tumorForm.getTumorConfiguration();
			String tumorDifferentiationCode = tumorForm
					.getTumorDifferentiation();
			if (isDebugging) {
				System.out.println("tumorSiteCode = " + tumorSiteCode);
				System.out
						.println("histologicTypeCode = " + histologicTypeCode);
				System.out.println("tumorConfigurationCode = "
						+ tumorConfigurationCode);
				System.out.println("tumorDifferentiationCode = "
						+ tumorDifferentiationCode);
			}

		}

	}

	private void displayVisitMap() {
		for (String key : visitDateMap.keySet()) {
			System.out.println(key + ": " + visitDateMap.get(key));
		}
	}

}
