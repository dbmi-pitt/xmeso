package edu.pitt.dbmi.xmeso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



// These org.apache.uima packages are installed via Maven
// the org package generated by JCasGen should be deleted
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.runner.commonsio.FileUtils;
import org.xml.sax.SAXException;

import edu.pitt.dbmi.xmeso.i2b2.I2b2DataSourceManager;
import edu.pitt.dbmi.xmeso.i2b2.I2b2DataWriter;
// These Model packages are generated by JCasGen
import edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm;
import edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm;

public class Xmeso {

	private static final Logger logger = LoggerFactory.getLogger(Xmeso.class);
	
	// This is also the date format used in the linkage file nmvb_path_report_event_date.csv
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private String xmesoDataDir;

	private final Map<String, String> visitDateMap = new HashMap<String, String>();
	private final Map<String, String> patientMap = new HashMap<String, String>();
	private final Map<String, String> reportMap = new HashMap<String, String>();

	private I2b2DataSourceManager i2b2DataSourceManager;

	private I2b2DataWriter i2b2DataWriter;

	private AnalysisEngine engine;
	
	private Properties xmesoProperties;

	// Entry point
	public static void main(String[] args) throws IOException, ParseException, AnalysisEngineProcessException, InvalidXMLException, ResourceInitializationException, SAXException {
		// Create instance of this class
		final Xmeso xmeso = new Xmeso();
		xmeso.execute();
	}

	public Xmeso() throws IOException {
		// Load the application.properties
		loadXmesoProperties();
	}
	
	private void loadXmesoProperties() throws IOException {
    	File file = new File("xmeso.properties");
		FileInputStream fileInput = new FileInputStream(file);
		xmesoProperties = new Properties();
		xmesoProperties.load(fileInput);
    }

	public void execute() throws InvalidXMLException, ResourceInitializationException, IOException, AnalysisEngineProcessException, SAXException, ParseException {
		// Get the xmeso_data path from application.properties
		xmesoDataDir = xmesoProperties.getProperty("xmeso_data");
		
		System.out.println("Input data folder path: " + xmesoDataDir);
		System.out.println(System.lineSeparator());
		
		createMappings();

		// Create instance of I2b2DemoDataSourceManager and pass the connection settings
		// The data source manager will only use the database related properties of xmesoProperties
		i2b2DataSourceManager = new I2b2DataSourceManager(xmesoProperties);
		
		// Value to be passed to instantiate the data writer
		String sourcesystemCd = xmesoProperties.getProperty("sourcesystem_cd");
		String providerId = xmesoProperties.getProperty("provider_id");
		
		// Instantiate the data writer by passing xmesoProperties
		i2b2DataWriter = new I2b2DataWriter(i2b2DataSourceManager, sourcesystemCd, providerId);

		// Delete old records (if exist) before inserting new ones
		i2b2DataWriter.cleanOldRecordsIfExist();
		
		// Create provider record based on configuration
		String providerPath = xmesoProperties.getProperty("provider_path");
		String providerNameChar = xmesoProperties.getProperty("provider_name_char");
        // We have only one provider in the XMESO_PROVIDER_DIMENSION table
		// The provider_id will be referenced in the XMESO_OBSERVATION_FACT table
		i2b2DataWriter.createProvider(providerId, providerPath, providerNameChar);
		
		processReports();
		
		// Notify the users that how many records are added into each table
		i2b2DataWriter.resultsSummary();
		i2b2DataSourceManager.destroy();
	}

	/**
	 * Map report file name with patient ID, report ID, and visit date
	 * 
	 * @throws IOException
	 */
	private void createMappings() throws IOException {
		/*
		 * linkage.csv contains content looks like this:
		 * Note: the `EVENT_DATE` must be in the format of "YYYY-MM-DD"
		 * 
			REPORT_ID,NMVB_ID,PATIENT_NUM,EVENT_DATE,REPORT_FILE
			15869,MVB0002,1000000083,1991-12-31,Report_file_1.txt
			15887,MVB0003,1000000084,1984-05-10,Report_file_2.txt
			17555,MVB0004,1000000085,1987-08-08,Report_file_3.txt
			15979,MVB0006,1000000086,1979-02-28,Report_file_4.txt
		 */
		File linkageFile = new File(xmesoDataDir + File.separator + "linkage.csv");
		
		for (String line : FileUtils.readLines(linkageFile)) {
			// Skip the header line
			if (line.contains("REPORT_ID")) {
				continue;
			}
			String[] fields = line.split(",");
			String reportId = fields[0]; // Here the reportId is string
			// NMVB_ID is in the linkage table only for easy-reference purpose, not being used
			String patientId = fields[2]; // Here the patientId is string
			String formattedDate = fields[3];
			// Use report file name as the array key
			// Report file name can be anything
			String key = fields[4];
			
			// Map report file name with visit date
			if (visitDateMap.get(key) != null) {
				System.err.println("FYI, visit date mapped to " + key + " already exists");
			}
			visitDateMap.put(key, formattedDate);
			
			// Map report file name with patient ID
			if (patientMap.get(key) != null) {
				System.err.println("FYI, patient mapped to " + key + " already exists");
			}
			patientMap.put(key, patientId);
			
			// Map report file name with report ID
			if (reportMap.get(key) != null) {
				System.err.println("FYI, report mapped to " + key + " already exists");
			}
			reportMap.put(key, reportId);
		}
	}


	// Solely based on the file order
	private void processReports() throws InvalidXMLException, ResourceInitializationException, IOException, ParseException {
		//final String resourcePath = (new File("")).getAbsolutePath() + File.separator + "resources";
		//System.out.println("Setting resourcePath to " + resourcePath);
		//final String[] resourcePaths = { resourcePath };
		// "edu.pitt.dbmi.xmeso.XmesoEngine" is the descriptor name of that XML file
		// E.g. C:/Users/zhy19/workspace/xmeso/target/classes/edu/pitt/dbmi/xmeso/XmesoEngine.xml
		engine = AnalysisEngineFactory.createEngine("edu.pitt.dbmi.xmeso.XmesoEngine", 
				//"resourcePaths", resourcePaths, 
				"lowMemoryProfile", false);
		// All report files inside the "reports" folder
		File reportsDir = new File(xmesoDataDir + File.separator + "reports");
		
		// This file system based looping has to be consistent with the csv records
		// The listFiles method does not guarantee any order
		// Different filesystems and OS can give different sortings
		File[] reportFiles = reportsDir.listFiles();
		// Arrays.sort() can sort this sort-able array because File is comparable class
        // and by default it sorts pathnames lexicographically
		// Without this sorting, it came up with hibernate error during executing the final jar file
		// 
		// Error:
		// Exception in thread "main" org.hibernate.NonUniqueObjectException: 
		// A different object with the same identifier value was already associated with the session
		Arrays.sort(reportFiles);
		
		// Display the progress
		int count = 1;
		int total = reportFiles.length;
		
		// Tell user how many report files will be processed
		System.out.println(System.lineSeparator());
		System.out.println("Total " + total + " report files will be processed");

		// Process each individual report file
		for (File reportFile : reportFiles) {
			File currentReportFile = reportFile;

			// Report file name can be anything
			String reportFilename = currentReportFile.getName();
			
			// Get corresponding patient ID and report ID
			String currentPatientId = this.patientMap.get(reportFilename);
			String currentReportId = this.reportMap.get(reportFilename);
			
			// Tell user which report is getting processed
			System.out.println(System.lineSeparator());
			System.out.println("[" + count + "/" + total + "] Processing report file " + reportFilename);
			
			// This is the date string parsed from the linkage table, yyyy-MM-dd format
			String formattedDate = this.visitDateMap.get(reportFilename);
			// Now we convert the String into Date type
			Date visitDate = dateFormat.parse(formattedDate);
			// Without formatting, outputting visitDate (Date type) directly will give us this: Thu May 10 00:00:00 EDT 1984
			// dateFormat.format(visitDate) will give us the desired string format: 1984-05-10
			logger.debug("visit date ------- " + dateFormat.format(visitDate));

			
			// QA, add record into REPORT_INFO table
			i2b2DataWriter.createReportInfo(Integer.parseInt(currentReportId), reportFilename, visitDate);
			
			// Establish the patient
			// Fetch existing patient info if exists, otherwise create a fake patient record
			// One patient may have multiple reports
			i2b2DataWriter.fetchOrCreatePatient(Integer.parseInt(currentPatientId));
			
			// Create new visit record of the current report
			i2b2DataWriter.createVisit(Integer.parseInt(currentPatientId), Integer.parseInt(currentReportId), visitDate);
			
			// Ruta kicks in
			processReport(currentReportFile, currentPatientId, currentReportId);
			
			count++;
		}
		
		System.out.println(System.lineSeparator());
		System.out.println("Finished processing all reports.");
	}
	
	/**
	 * Process the current report file for current patient ID and report ID
	 * 
	 * @param currentReportFile
	 * @param patientId
	 * @param reportId
	 */
	private void processReport(File currentReportFile, String patientId, String reportId) {
		String content;
		try {
			content = FileUtils.readFileToString(currentReportFile);
			JCas jCas = engine.newJCas();
			// Extract information from this current report content (text to be analyzed)
			jCas.setDocumentText(content + "\n$\n");
			engine.process(jCas);
			
			populateCas(jCas, patientId, reportId);
			
			System.out.println("Successfully processed report file " + currentReportFile.getName() + " and added extracted information to XMESO_OBSERVATION_FACT table");
		} catch (Exception e) {
			System.err.println("Failed to process report file " + currentReportFile.getName());
			e.printStackTrace();
		}
	}

	/**
	 * Extracts the concept info and observation fact
	 * 
	 * @param jCas
	 */
	private void populateCas(JCas jCas, String patientId, String reportId) {
		// On this cycle we will extract the following Data Elements over the report set:
		//
		// Case level (whole report) information:
		//		    - Ultrastructural Findings
		//		    - Lymph Nodes Examined
		//		    - Special Stain Profile
		// Part (report part in each section) Level:
		//	        - Site of tumor
		//		    - Histological Type
		//		    - Tumor Configuration
		//		    - Tumor Differentiation

		// Case level information
		// Case leve variables only appear once per report
		// JCasUtil.select() returns a collection, in this case, the collection contains only one element
		for (AnnotationFS caseFormFs : JCasUtil.select(jCas, XmesoCaseForm.class)) {
			XmesoCaseForm caseForm = (XmesoCaseForm) caseFormFs;
			// Other info (surgicalProcedure) can be used from InformationExtractorAnnotationEngine,
			// other than just the below 3
			String lymphNodesExamined = caseForm.getLymphNodesExamined();
			String specialStain = caseForm.getSpecialStain();
			String ultraStructuralFindings = caseForm.getUltrastructuralFindings();
			
			logger.debug("============================DEBUGGING============================");
			logger.debug("lymphNodesExamined = " + lymphNodesExamined);
			logger.debug("specialStain = " + specialStain);
			logger.debug("ultraStructuralFindings = " + ultraStructuralFindings);

			// QA, add record into REPORT_CASE_LEVEL table
			i2b2DataWriter.createReportCaseLevel(Integer.parseInt(reportId), lymphNodesExamined, specialStain, ultraStructuralFindings);
			
			// A concept found in one report may also appear in another report
			// That's why we "fetch or create" to reuse previously added concepts
			i2b2DataWriter.fetchOrCreateConcept(lymphNodesExamined);
			i2b2DataWriter.fetchOrCreateConcept(specialStain);
			i2b2DataWriter.fetchOrCreateConcept(ultraStructuralFindings);
			
			// Here we use report id as the encounter number
			// The 0L means the number zero of type long, we'll increase the instance number using the currentPartNumber 
			// Won't be able to reuse previously added observation fact even if 
			// it's the same patient, same report, same concept code, same provider, same modifier, same start date.
			// Because the instance_num will always be different, and all these fields consist of the primary keys
			i2b2DataWriter.createObservation(Integer.parseInt(patientId), Integer.parseInt(reportId), lymphNodesExamined, 0L);
			i2b2DataWriter.createObservation(Integer.parseInt(patientId), Integer.parseInt(reportId), specialStain, 0L);
			i2b2DataWriter.createObservation(Integer.parseInt(patientId), Integer.parseInt(reportId), ultraStructuralFindings, 0L);
		}

		// TumorForm information

		// Part level variables may have multiple occurrences, thus why we need the instance number
		for (AnnotationFS tumorFormFS : JCasUtil.select(jCas, XmesoTumorForm.class)) {
			XmesoTumorForm tumorForm = (XmesoTumorForm) tumorFormFS;
			long currentPartNumber = Long.parseLong(tumorForm.getCurrentPart() + "");
			
			// Other info (sizeDimensionX, sizeDimensionY, sizeDimensionZ, sizeDimensionMax, and histopathologicalType) 
			// can also be used from InformationExtractorAnnotationEngine, other than just the below 3
			String tumorSiteCode = tumorForm.getTumorSite();
			String histologicTypeCode = tumorForm.getHistopathologicalType();
			String tumorConfigurationCode = tumorForm.getTumorConfiguration();
			String tumorDifferentiationCode = tumorForm.getTumorDifferentiation();

			logger.debug("tumorSiteCode = " + tumorSiteCode);
			logger.debug("histologicTypeCode = " + histologicTypeCode);
			logger.debug("tumorConfigurationCode = " + tumorConfigurationCode);
			logger.debug("tumorDifferentiationCode = " + tumorDifferentiationCode);

			// QA, add record into REPORT_PART_LEVEL table
			i2b2DataWriter.createReportPartLevel(Integer.parseInt(reportId), currentPartNumber, tumorSiteCode, histologicTypeCode, tumorConfigurationCode, tumorDifferentiationCode);
			
			// Write into i2b2 database
			i2b2DataWriter.fetchOrCreateConcept(tumorSiteCode);
			i2b2DataWriter.fetchOrCreateConcept(histologicTypeCode);
			i2b2DataWriter.fetchOrCreateConcept(tumorConfigurationCode);
			i2b2DataWriter.fetchOrCreateConcept(tumorDifferentiationCode);
			
			// The instance number starts from 0L, we'll increase it using the currentPartNumber 
			i2b2DataWriter.createObservation(Integer.parseInt(patientId), Integer.parseInt(reportId), tumorSiteCode, currentPartNumber);
			i2b2DataWriter.createObservation(Integer.parseInt(patientId), Integer.parseInt(reportId), histologicTypeCode, currentPartNumber);
			i2b2DataWriter.createObservation(Integer.parseInt(patientId), Integer.parseInt(reportId), tumorConfigurationCode, currentPartNumber);
			i2b2DataWriter.createObservation(Integer.parseInt(patientId), Integer.parseInt(reportId), tumorDifferentiationCode, currentPartNumber);
		}

	}

}