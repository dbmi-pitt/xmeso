package edu.pitt.dbmi.xmeso.qa;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.I2b2DemoDataSourceManager;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ObservationFact;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ObservationFactId;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.PatientDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.I2b2MetaDataSourceManager;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.NcatsDemographics;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.NcatsIcd9Diag;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.NcatsIcd9Proc;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.NcatsVisitDetails;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.NmvbMesothelioma;

public class Qa {
	private static final String CONST_INPUT_FILE_PATH = "C:\\Users\\zhy19\\workspace\\xmeso\\data\\xmeso\\nmvb160520\\TrainingSetRawNeedsCleanup.tsv";
	private static final String CONST_OUTPUT_FILE_PATH = "C:\\Users\\zhy19\\workspace\\xmeso\\data\\xmeso\\nmvb160520\\Xmeso160607.csv";
	private static final String CONST_LOG_FILE_PATH = "C:\\Users\\zhy19\\workspace\\xmeso\\data\\xmeso\\nmvb160520\\XmesoLog160614.csv";

	private static final Integer[] ignoredReportNumbers = { 3, 12, 13, 15, 16,
			18, 33, 47 };

	private static final String[] encounterLevelColumns = { "Project",
			"Patient", "Patient Number", "Report Id", "Report fk",
			"Report Number", "Report Date", "Largest Nodule",
			"Lymph Nodes Examined", "Positive Lymph Nodes",
			"Special Stain Profile", "Status of vascular invasion by tumor",
			"UltrastructuralFindings", "Immunohistochemical Profile",
			"Procedure Type" };

	private static final String[] partLevelColumns = { "Part Label @",
			"Site of Tumor @", "Histological Type @", "Tumor Type @",
			"Invasive Tumor @", "Tumor Configuration @",
			"Tumor Differentiation or Grade @", "Surgical Margins @",
			"Tumor size Max Dimension in cm 1 @", "TumorExtension @" };

	private static final String[] nonCodingAttributes = { "Project", "Patient",
			"Patient Number", "ReportID", "Report_fk", "Report Number",
			"Report Date", "not required" };

	private static final String[] caseLevelCodingAccessors = {
			"getLargestNodule", "getLymphNodesExamined",
			"getPositiveLymphNodes", "getSpecialStainProfile",
			"getStatusOfVascularInvasionByTumor", "getUltrastructuralFindings",
			"getImmunohistochemicalProfile", "getProcedureType" };

	private static final String[] caseLevelCodingMutators = {
			"setLargestNodule", "setLymphNodesExamined",
			"setPositiveLymphNodes", "setSpecialStainProfile",
			"setStatusOfVascularInvasionByTumor", "setUltrastructuralFindings",
			"setImmunohistochemicalProfile", "setProcedureType" };

	private static final String[] partLevelCodingAccessors = {
			"getSiteOfTumor", "getHistologicalType", "getTumorType",
			"getInvasiveTumor", "getTumorConfiguration",
			"getTumorDifferentiationOrGrade", "getSurgicalMargins",
			"getTumorSizeMaxDimensionInCm", "getTumorExtension" };

	private static final String[] partLevelCodingMutators = { "setSiteOfTumor",
			"setHistologicalType", "setTumorType", "setInvasiveTumor",
			"setTumorConfiguration", "setTumorDifferentiationOrGrade",
			"setSurgicalMargins", "setTumorSizeMaxDimensionInCm",
			"setTumorExtension" };

	private static final String[] caseLevelReportingAccessors = {
			"getUltrastructuralFindings", "getLymphNodesExamined",
			"getSpecialStainProfile" };

	private static final String[] partLevelReportingAccessors = {
			"getHistologicalType", "getTumorConfiguration",
			"getTumorDifferentiationOrGrade" };

	private static final Class<?>[] emptySignature = {};
	private static final Object[] emptyParams = {};

	private static final String snomedCodePattern = "SNOMED:\\d+";

	private final boolean isRegeneratingReportContent = false;

	private I2b2MetaDataSourceManager metaDataMgr;
	private Session metaDataSession;
	private I2b2DemoDataSourceManager demoDataMgr;
	private Session demoDataSession;

	private List<XmesoCase> xmesoGoldCases = new ArrayList<XmesoCase>();
	private List<XmesoCase> xmesoGoldCasesCodified = new ArrayList<XmesoCase>();
	private List<XmesoCase> xmesoMachCases = new ArrayList<XmesoCase>();
	private List<XmesoCase> xmesoMachCasesCodified = new ArrayList<XmesoCase>();

	private XmesoCase xmesoCase;
	private XmesoPart xmesoPart;
	private String attributeName;
	private String camelCaseAttribute;
	private String attributeValue;

	private CodeResolver codeResolver;

	private HashSet<String> legalConceptCds = new HashSet<String>();

	private Map<Integer, Set<String>> currentConceptCodes = new HashMap<Integer, Set<String>>();
	private String currentPatientIdentifier;
	private String currentVisitIdentifier;
	private int currentPartNumber;

	private double correctCount = 0.0d;
	private double totalCount = 0.0d;

	public static void main(String[] args) {
		Qa qa = new Qa();
		qa.execute();
	}

	private void execute() {
		try {
			establishMetaDbConnectivity();
			establishDemoDbConnectivity();
			establishCodeResolution();
			etlGoldSet();
			if (isRegeneratingReportContent) {
				parseDeidOutputAndWriteReports();
			}
			readXmiReports();

			tallyStatistics();

			FileUtils.write(new File(CONST_LOG_FILE_PATH),
					tryToStringOriginal());
			FileUtils.write(new File(CONST_OUTPUT_FILE_PATH), toString());

		} catch (IOException | NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void tallyStatistics() {
		for (int caseIdx = 0; caseIdx < xmesoGoldCasesCodified.size(); caseIdx++) {
			XmesoCase goldCase = xmesoGoldCasesCodified.get(caseIdx);
			XmesoCase machCase = xmesoMachCasesCodified.get(caseIdx);
			if (goldCase.getUltrastructuralFindings().equals(
					machCase.getUltrastructuralFindings())) {
				correctCount = correctCount + 1.0d;
			}
			if (goldCase.getLymphNodesExamined().equals(
					machCase.getLymphNodesExamined())) {
				correctCount = correctCount + 1.0d;
			}
			if (goldCase.getSpecialStainProfile().equals(
					machCase.getSpecialStainProfile())) {
				correctCount = correctCount + 1.0d;
			}
			totalCount = totalCount + 3.0d;
			for (int partIdx = 0; partIdx < goldCase.getXmesoParts().length; partIdx++) {
				XmesoPart goldPart = goldCase.getXmesoParts()[partIdx];
				XmesoPart machPart = machCase.getXmesoParts()[partIdx];
				if (goldPart.getHistologicalType().equals(
						machPart.getHistologicalType())) {
					correctCount = correctCount + 1.0d;
				}
				if (goldPart.getTumorConfiguration().equals(
						machPart.getTumorConfiguration())) {
					correctCount = correctCount + 1.0d;
				}
				if (goldPart.getTumorDifferentiationOrGrade().equals(
						machPart.getTumorDifferentiationOrGrade())) {
					correctCount = correctCount + 1.0d;
				}
				totalCount = totalCount + 3.0d;
			}
		}
	}

	private void readXmiReports() {
		copyCases(xmesoGoldCases, xmesoMachCases);
		copyCases(xmesoGoldCasesCodified, xmesoMachCasesCodified);
		QaXmiReader xmiReader = new QaXmiReader();
		xmiReader.replaceAllCases(xmesoMachCases, xmesoMachCasesCodified);
		xmiReader.execute();
	}

	private void parseDeidOutputAndWriteReports() {
		QaDeidParser deidParser = new QaDeidParser();
		deidParser.replaceAllCases(xmesoGoldCases);
		deidParser.execute();

	}

	private void establishCodeResolution() {
		codeResolver = new CodeResolver();
		codeResolver.setMetaDataMgr(metaDataMgr);
		codeResolver.initialize();
	}

	private void etlGoldSet() throws IOException, NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		String tsvGoldSetPath = CONST_INPUT_FILE_PATH;
		final String tsvGoldSetContent = FileUtils.readFileToString(new File(
				tsvGoldSetPath));
		final String[] tsvGoldSetLines = tsvGoldSetContent.split("\n");
		final String[] attributes = tsvGoldSetLines[0].split("\t");
		final String[] camelCaseAttributes = camelCaseAttributes(attributes);
		beanifySpreadSheet(camelCaseAttributes, tsvGoldSetLines);
		adjustIgnoredCases();
		codifySpreadSheet();
	}

	private void adjustIgnoredCases() {
		for (XmesoCase xmesoCase : xmesoGoldCases) {
			final List<Integer> ignoredReportNumberList = Arrays
					.asList(ignoredReportNumbers);
			if (ignoredReportNumberList.contains(Integer.parseInt(xmesoCase
					.getReportNumber()))) {
				System.out.println("Excluding report #"
						+ xmesoCase.getReportNumber());
				xmesoCase.setUltrastructuralFindings("Unknown Ultrastructural");
				xmesoCase.setLymphNodesExamined("No");
				xmesoCase.setSpecialStainProfile("Unknown");
				for (XmesoPart xmesoPart : xmesoCase.getXmesoParts()) {
					xmesoPart.setHistologicalType("Unknown");
					xmesoPart
							.setTumorConfiguration("Configuration Not specified");
					xmesoPart
							.setTumorDifferentiationOrGrade("Unknown Differentiation");
				}
			}
		}
	}

	private void beanifySpreadSheet(String[] camelCaseAttributes,
			String[] tsvGoldSetLines) {
		for (int lineIdx = 1; lineIdx < tsvGoldSetLines.length; lineIdx++) {
			xmesoCase = new XmesoCase();
			xmesoGoldCases.add(xmesoCase);
			final String[] attributeValues = tsvGoldSetLines[lineIdx]
					.split("\t");
			cleanAttributeValues(attributeValues);
			int attrIdx = 0;
			while (attrIdx < camelCaseAttributes.length) {
				camelCaseAttribute = camelCaseAttributes[attrIdx];
				attributeValue = attributeValues[attrIdx];
				if (!isGoldSheetColumnHeaderForPart(camelCaseAttribute)) {
					populateAttributeValue(xmesoCase, camelCaseAttribute,
							attributeValue);
				} else {
					int partIdx = extractPartIdxFromGoldSpreadSheetColumn(camelCaseAttribute);
					xmesoPart = xmesoCase.getXmesoParts()[partIdx - 1];
					String unNumberedAttr = camelCaseAttribute.substring(0,
							camelCaseAttribute.length() - 1);
					populateAttributeValue(xmesoPart, unNumberedAttr,
							attributeValue);
				}
				attrIdx++;
			}
		}
	}

	private void cleanAttributeValues(String[] attributeValues) {
		for (int idx = 0; idx < attributeValues.length; idx++) {
			attributeValues[idx] = attributeValues[idx].replaceAll(
					"[\\\"\\\\,]", " ");
			attributeValues[idx] = StringUtils.strip(attributeValues[idx]);
		}
	}

	private void codifySpreadSheet() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		copyCases(xmesoGoldCases, xmesoGoldCasesCodified);
		for (XmesoCase xmesoCase : xmesoGoldCasesCodified) {
			for (int idx = 0; idx < caseLevelCodingAccessors.length; idx++) {
				String accessorName = caseLevelCodingAccessors[idx];
				String mutatorName = caseLevelCodingMutators[idx];
				Method accessorMethod = xmesoCase.getClass().getMethod(
						accessorName, emptySignature);
				Method mutatorMethod = xmesoCase.getClass().getMethod(
						mutatorName, String.class);
				String preferredTerm = (String) accessorMethod.invoke(
						xmesoCase, emptyParams);
				String code = deriveCodeForAccessorAndPreferredTerm(
						accessorMethod, preferredTerm);
				mutatorMethod.invoke(xmesoCase, code);
			}
		}
		for (XmesoCase xmesoCase : xmesoGoldCasesCodified) {
			for (XmesoPart xmesoPart : xmesoCase.getXmesoParts()) {
				for (int idx = 0; idx < partLevelCodingAccessors.length; idx++) {
					String accessorName = partLevelCodingAccessors[idx];
					String mutatorName = partLevelCodingMutators[idx];
					Method accessorMethod = xmesoPart.getClass().getMethod(
							accessorName, emptySignature);
					Method mutatorMethod = xmesoPart.getClass().getMethod(
							mutatorName, String.class);
					String preferredTerm = (String) accessorMethod.invoke(
							xmesoPart, emptyParams);
					String code = deriveCodeForAccessorAndPreferredTerm(
							accessorMethod, preferredTerm);
					mutatorMethod.invoke(xmesoPart, code);
				}
			}
		}
	}

	private String deriveCodeForAccessorAndPreferredTerm(Method accessorMethod,
			String preferredTerm) {
		String attributeName = chompAndUnCamelCase(accessorMethod.getName());
		String code = "";
		if (isSnomedCode(preferredTerm)) {
			code = preferredTerm;
		} else {
			code = resolveCodeWithCodeResolver(attributeName, preferredTerm);
			if (StringUtils.isBlank(code))
				code = "FAILED";
		}
		return code;
	}

	private boolean isGoldSheetColumnHeaderForPart(String camelCaseAttribute) {
		return camelCaseAttribute.matches("\\D+[0-8]");
	}

	private int extractPartIdxFromGoldSpreadSheetColumn(
			String camelCaseAttribute) {
		int partIdx = new Integer(camelCaseAttribute.substring(
				camelCaseAttribute.length() - 1, camelCaseAttribute.length()));
		return partIdx;
	}

	public PatientDimension findOrCreatePatient(PatientDimension patientQuery) {
		Criteria c = demoDataSession.createCriteria(PatientDimension.class);
		c.add(Restrictions.like("patientNum", patientQuery.getPatientNum()))
				.uniqueResult();
		return patientQuery;
	}

	private String chompAndUnCamelCase(String src) {
		src = src.replaceAll("^[sg]et", "");
		StringBuffer sb = new StringBuffer();
		char[] srcChrs = src.toCharArray();
		for (char c : srcChrs) {
			if (Character.isLetter(c) && Character.isUpperCase(c)) {
				sb.append(' ');
			}
			sb.append(c);
		}
		return StringUtils.trimToEmpty(sb.toString());
	}

	private void copyCases(List<XmesoCase> srcCases, List<XmesoCase> tgtCases) {
		for (XmesoCase srcCase : srcCases) {
			tgtCases.add(new XmesoCase(srcCase));
		}
	}

	@SuppressWarnings("unused")
	private void populateDemoDataDatabase() {
		Date timeNow = new Date();
		for (Integer partNumber : currentConceptCodes.keySet()) {
			for (String conceptCd : currentConceptCodes.get(partNumber)) {
				ObservationFactId obsFactId = new ObservationFactId();
				obsFactId.setConceptCd(conceptCd);
				obsFactId
						.setEncounterNum(new BigDecimal(currentVisitIdentifier));
				obsFactId.setInstanceNum(partNumber.longValue());
				obsFactId.setModifierCd("NA");
				obsFactId.setPatientNum(new BigDecimal(currentVisitIdentifier));
				obsFactId.setProviderId("NA");
				obsFactId.setStartDate(timeNow);
				ObservationFact obsInstance = new ObservationFact();
				obsInstance.setId(obsFactId);
				obsInstance.setSourcesystemCd("GoldEtl");
				Transaction tx = demoDataMgr.getSession().beginTransaction();
				demoDataSession.saveOrUpdate(obsInstance);
				tx.commit();
			}
		}
	}

	@SuppressWarnings("unused")
	private void codifySpreadSheetLine(String[] attributes,
			String tsvGoldSetLine) {
		currentConceptCodes.clear();
		final String[] attributeValues = tsvGoldSetLine.split("\t");
		int attrIdx = 0;
		while (attrIdx < attributes.length) {
			attributeName = attributes[attrIdx];
			attributeValue = attributeValues[attrIdx];
			if (StringUtils.isEmpty(attributeValue)) {
				attributeValue = "Unknown";
			}
			if (attributeName.equals("Patient")) {
				currentPatientIdentifier = attributeValue;
			} else if (attributeName.equals("ReportID")) {
				currentVisitIdentifier = attributeValue.split(",")[0];
				currentVisitIdentifier = StringUtils.substringAfter(
						currentVisitIdentifier, "\"");
				System.out.println(currentVisitIdentifier);
			} else if (isExtractable(attributeName, attributeValue)) {
				truncateAndCacheCurrentPartSuffix();
				if (isImmunohistochemicalProfile(attributeName)) {
					processImmunohistochemicalProfile(attributeName,
							attributeValue);
				} else {
					processCodifyAttributeValue(attributeName, attributeValue);
				}
			}
			attrIdx++;
		}
	}

	private boolean isImmunohistochemicalProfile(String attributeName) {
		return attributeName.toLowerCase()
				.equals("immunohistochemical profile");
	}

	private void processImmunohistochemicalProfile(String attributeName,
			String attributeValue) {
		final String[] stainTargets = attributeValue.split(",");
		for (String stainTarget : stainTargets) {
			processCodifyAttributeValue(attributeName, stainTarget);
		}
	}

	private void processCodifyAttributeValue(String attributeName,
			String attributeValue) {
		String code = "FAILED";
		if (isSnomedCode(attributeValue)) {
			code = attributeValue;
		} else {
			code = resolveCodeWithCodeResolver(attributeName, attributeValue);
			if (StringUtils.isBlank(code)) {
				code = "FAILED";
			}
		}
		if (code.equals("FAILED")) {
			System.out.println(attributeName + ", " + attributeValue + " => "
					+ code);
		} else {
			addCodeForCurrentPart(code);
		}
	}

	@SuppressWarnings("unused")
	private void displayExcelRowContribution() {
		StringBuilder patientLevelBuilder = new StringBuilder();
		patientLevelBuilder.append("pat: " + currentPatientIdentifier);
		patientLevelBuilder.append(" enc: " + currentVisitIdentifier);
		for (int partNum = 1; partNum < 9; partNum++) {
			StringBuilder partBuilder = new StringBuilder();
			partBuilder.append(patientLevelBuilder.toString());
			partBuilder.append(" part: " + partNum);
			partBuilder.append(" codes: ");
			Set<String> codes = currentConceptCodes.get(new Integer(partNum));
			if (codes != null) {
				for (String code : codes) {
					partBuilder.append(code + ",");
				}
			} else {
				partBuilder.append("- ");
			}
			System.out.println(partBuilder.toString());
		}
	}

	private void addCodeForCurrentPart(String code) {
		Set<String> codeList = currentConceptCodes.get(new Integer(
				currentPartNumber));
		if (codeList == null) {
			codeList = new HashSet<String>();
			currentConceptCodes.put(new Integer(currentPartNumber), codeList);
		}
		currentConceptCodes.get(new Integer(currentPartNumber)).add(code);
	}

	private String resolveCodeWithCodeResolver(String attributeName,
			String attributeValue) {
		codeResolver.clear();
		codeResolver.addQueryTerm(attributeName);
		codeResolver.addQueryTerm(attributeValue);
		return codeResolver.searchForCode();
	}

	private void truncateAndCacheCurrentPartSuffix() {
		Pattern pattern = Pattern.compile("(\\D+)([0-8])");
		Matcher matcher = pattern.matcher(attributeName);
		if (matcher.matches()) {
			attributeName = matcher.group(1).trim();
			currentPartNumber = Integer.parseInt(matcher.group(2));
		} else {
			currentPartNumber = 0;
		}
	}

	private boolean isExtractable(String attributeName, String attributeValue) {
		boolean result = true;
		result = result && StringUtils.isNotBlank(attributeName);
		result = result && StringUtils.isNotBlank(attributeValue);
		result = result
				&& !Arrays.asList(nonCodingAttributes).contains(attributeName);
		result = result && !attributeName.startsWith("not required");
		return result;
	}

	private void populateAttributeValue(Object xmesoObj, String attribute,
			String attributeValue) {
		try {
			if (attributeValue != null && attributeValue.length() > 0) {
				final Class<?>[] mutatorSignature = { String.class };
				Method mutator = xmesoObj.getClass().getMethod(
						"set" + attribute, mutatorSignature);
				final Object[] mutatorCallParams = { attributeValue };
				mutator.invoke(xmesoObj, mutatorCallParams);
			}
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private String[] camelCaseAttributes(String[] attributes) {
		final String[] camelCaseAttributes = new String[attributes.length];
		final CamelCaseConverter camelCaseConverter = new CamelCaseConverter();
		int attrIdx = 0;
		for (String attribute : attributes) {
			camelCaseAttributes[attrIdx++] = camelCaseConverter
					.toCamelCase(attribute);
		}
		return camelCaseAttributes;
	}

	@SuppressWarnings("unused")
	private String lowerCaseFirstChar(String src) {
		String result = src;
		if (src != null && src.length() > 0) {
			result = src.substring(0, 1).toLowerCase();
			if (src.length() > 1) {
				result += src.substring(1, src.length());
			}
		}
		return result;
	}

	private void establishMetaDbConnectivity() {
		metaDataMgr = new I2b2MetaDataSourceManager();
		metaDataMgr
				.setHibernateConnectionUrl("jdbc:oracle:thin:@dbmi-i2b2-dev05.dbmi.pitt.edu:1521:xe");
		metaDataMgr.setHibernateDriver("oracle.jdbc.driver.OracleDriver");
		metaDataMgr.setHibernateShowSql("true");
		metaDataSession = metaDataMgr.getSession();
	}

	private void establishDemoDbConnectivity() {
		demoDataMgr = new I2b2DemoDataSourceManager();
		demoDataMgr
				.setHibernateConnectionUrl("jdbc:oracle:thin:@dbmi-i2b2-dev05.dbmi.pitt.edu:1521:xe");
		demoDataMgr.setHibernateDriver("oracle.jdbc.driver.OracleDriver");
		demoDataMgr.setHibernateShowSql("true");
		demoDataSession = demoDataMgr.getSession();
	}

	@SuppressWarnings("unused")
	private void displayMetaData() {
		displayNcatsDemographics();
		displayNcatsIcd9Diag();
		displayNcatsIcd9Proc();
		displayNcatsVisitDetails();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void gatherValidNmvbMesotheliomaConceptCds() {
		Criteria searchCriteria = metaDataSession
				.createCriteria(NmvbMesothelioma.class);
		searchCriteria.addOrder(Order.asc("CHlevel"));
		searchCriteria.addOrder(Order.asc("CFullname"));
		List<NmvbMesothelioma> recordList = searchCriteria.list();
		for (NmvbMesothelioma record : recordList) {
			String baseCode = record.getCBasecode();
			if (baseCode != null && baseCode.length() > 0) {
				legalConceptCds.add(baseCode);
			}

		}
		List<String> sortedCodes = new ArrayList<String>();
		sortedCodes.addAll(legalConceptCds);
		Collections.sort(sortedCodes);
		for (String code : sortedCodes) {
			System.out.println(code);
		}
	}

	@SuppressWarnings("unchecked")
	private void displayNcatsDemographics() {
		Criteria searchCriteria = metaDataSession
				.createCriteria(NcatsDemographics.class);
		searchCriteria.addOrder(Order.asc("CHlevel"));
		searchCriteria.addOrder(Order.asc("CFullname"));
		searchCriteria.setMaxResults(2);
		List<NcatsDemographics> demographicsList = searchCriteria.list();
		for (NcatsDemographics demographics : demographicsList) {
			String formattedOutput = String.format("%5d) %s%n", demographics
					.getCHlevel().intValue(), demographics.getCFullname());
			System.out.print(formattedOutput);
		}
	}

	@SuppressWarnings("unchecked")
	private void displayNcatsIcd9Diag() {
		Criteria searchCriteria = metaDataSession
				.createCriteria(NcatsIcd9Diag.class);
		searchCriteria.addOrder(Order.asc("CHlevel"));
		searchCriteria.addOrder(Order.asc("CFullname"));
		searchCriteria.setMaxResults(2);
		List<NcatsIcd9Diag> recordList = searchCriteria.list();
		for (NcatsIcd9Diag record : recordList) {
			String formattedOutput = String.format("%5d) %s%n", record
					.getCHlevel().intValue(), record.getCFullname());
			System.out.print(formattedOutput);
		}
	}

	@SuppressWarnings("unchecked")
	private void displayNcatsIcd9Proc() {
		Criteria searchCriteria = metaDataSession
				.createCriteria(NcatsIcd9Proc.class);
		searchCriteria.addOrder(Order.asc("CHlevel"));
		searchCriteria.addOrder(Order.asc("CFullname"));
		searchCriteria.setMaxResults(2);
		List<NcatsIcd9Proc> recordList = searchCriteria.list();
		for (NcatsIcd9Proc record : recordList) {
			String formattedOutput = String.format("%5d) %s%n", record
					.getCHlevel().intValue(), record.getCFullname());
			System.out.print(formattedOutput);
		}
	}

	@SuppressWarnings("unchecked")
	private void displayNcatsVisitDetails() {
		Criteria searchCriteria = metaDataSession
				.createCriteria(NcatsVisitDetails.class);
		searchCriteria.addOrder(Order.asc("CHlevel"));
		searchCriteria.addOrder(Order.asc("CFullname"));
		searchCriteria.setMaxResults(2);
		List<NcatsVisitDetails> recordList = searchCriteria.list();
		for (NcatsVisitDetails record : recordList) {
			String formattedOutput = String.format("%5d) %s%n", record
					.getCHlevel().intValue(), record.getCFullname());
			System.out.print(formattedOutput);
		}
	}

	private boolean isSnomedCode(String codeCandidate) {
		Pattern pattern = Pattern.compile(snomedCodePattern);
		Matcher matcher = pattern.matcher(codeCandidate);
		return matcher.find();
	}

	public String toString() {
		String stringifiedQa = "";
		try {
			stringifiedQa = tryToString();
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			stringifiedQa = e.getMessage();
		}
		return stringifiedQa;
	}

	private String tryToString() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		StringBuilder sb = new StringBuilder();
		appendColumns(sb);
		for (int caseIdx = 0; caseIdx < xmesoGoldCases.size(); caseIdx++) {
			XmesoCase machCaseCodified = xmesoMachCasesCodified.get(caseIdx);
			sb.append("\n");
			failSafeAppendString(sb, machCaseCodified.getProject());
			failSafeAppendString(sb, machCaseCodified.getPatient());
			failSafeAppendString(sb, machCaseCodified.getPatientNumber());
			failSafeAppendString(sb, machCaseCodified.getReportId());
			failSafeAppendString(sb, machCaseCodified.getReportFk());
			failSafeAppendString(sb, machCaseCodified.getReportNumber());
			failSafeAppendString(sb, machCaseCodified.getReportDate());
			failSafeAppendString(sb, machCaseCodified.getLargestNodule());
			failSafeAppendString(sb, machCaseCodified.getLymphNodesExamined());
			failSafeAppendString(sb, machCaseCodified.getPositiveLymphNodes());
			failSafeAppendString(sb, machCaseCodified.getSpecialStainProfile());
			failSafeAppendString(sb,
					machCaseCodified.getStatusOfVascularInvasionByTumor());
			failSafeAppendString(sb,
					machCaseCodified.getUltrastructuralFindings());
			failSafeAppendString(sb,
					machCaseCodified.getImmunohistochemicalProfile());
			failSafeAppendString(sb, machCaseCodified.getProcedureType());
			for (int pdx = 1; pdx < 9; pdx++) {
				failSafeAppendString(sb,
						machCaseCodified.getXmesoParts()[pdx - 1]
								.getPartLabel());
				failSafeAppendString(sb,
						machCaseCodified.getXmesoParts()[pdx - 1]
								.getSiteOfTumor());
				failSafeAppendString(sb,
						machCaseCodified.getXmesoParts()[pdx - 1]
								.getHistologicalType());
				failSafeAppendString(sb,
						machCaseCodified.getXmesoParts()[pdx - 1]
								.getTumorType());
				failSafeAppendString(sb,
						machCaseCodified.getXmesoParts()[pdx - 1]
								.getInvasiveTumor());
				failSafeAppendString(sb,
						machCaseCodified.getXmesoParts()[pdx - 1]
								.getTumorConfiguration());
				failSafeAppendString(sb,
						machCaseCodified.getXmesoParts()[pdx - 1]
								.getTumorDifferentiationOrGrade());
				failSafeAppendString(sb,
						machCaseCodified.getXmesoParts()[pdx - 1]
								.getSurgicalMargins());
				failSafeAppendString(sb,
						machCaseCodified.getXmesoParts()[pdx - 1]
								.getTumorSizeMaxDimensionInCm());
				failSafeAppendString(sb,
						machCaseCodified.getXmesoParts()[pdx - 1]
								.getTumorExtension());
			}
		}
		String result = sb.toString();
		if (result.endsWith(",")) {
			result = StringUtils.substringBeforeLast(result, ",");
		}
		return result;
	}

	private void failSafeAppendString(StringBuilder sb, String src) {
		if (src.equals("FAILED")) {
			sb.append(",");
		} else {
			sb.append(src + ",");
		}
	}

	private void appendColumns(StringBuilder sb) {
		for (String s : encounterLevelColumns) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append(s);
		}
		for (int idx = 1; idx <= 8; idx++) {
			for (String s : partLevelColumns) {
				sb.append(",");
				sb.append(s.replaceAll("@", idx + ""));
			}
		}
	}

	private String tryToStringOriginal() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		StringBuilder sb = new StringBuilder();
		sb.append("Accuracy: " + (correctCount / totalCount) + "\n\n");
		for (int caseIdx = 0; caseIdx < xmesoGoldCases.size(); caseIdx++) {
			XmesoCase goldCase = xmesoGoldCases.get(caseIdx);
			XmesoCase machCase = xmesoMachCases.get(caseIdx);
			XmesoCase goldCaseCodified = xmesoGoldCasesCodified.get(caseIdx);
			XmesoCase machCaseCodified = xmesoMachCasesCodified.get(caseIdx);
			String formattedReportNumber = "report"
					+ StringUtils.leftPad(goldCase.getReportNumber(), 12, "0");
			sb.append(formattedReportNumber + " " + goldCase.getReportId()
					+ "\n");
			for (String accessorName : caseLevelReportingAccessors) {
				Method accessor = XmesoCase.class.getMethod(accessorName,
						emptySignature);
				String label = chompAndUnCamelCase(accessorName);
				String goldTerm = (String) accessor.invoke(goldCase,
						emptyParams);
				String machTerm = (String) accessor.invoke(machCase,
						emptyParams);
				String goldCode = (String) accessor.invoke(
						goldCaseCodified, emptyParams);
				String machCode = (String) accessor.invoke(
						machCaseCodified, emptyParams);
				if (!goldCode.equals(machCode)) {
					sb.append("\t\t"
							+ String.format("%s, %s, %s, %s, %s", label,
									goldTerm, goldCode, machTerm, machCode)
							+ "\n");
				}
			}
			for (int partIdx = 0; partIdx < goldCase.getXmesoParts().length; partIdx++) {
				XmesoPart goldCasePart = goldCase.getXmesoParts()[partIdx];
				XmesoPart machCasePart = machCase.getXmesoParts()[partIdx];
				XmesoPart goldCasePartCodified = goldCaseCodified
						.getXmesoParts()[partIdx];
				XmesoPart machCasePartCodified = machCaseCodified
						.getXmesoParts()[partIdx];
				sb.append("\tPart " + partIdx + "\n");
				for (String accessorName : partLevelReportingAccessors) {
					Method accessor = XmesoPart.class.getMethod(accessorName,
							emptySignature);
					String label = chompAndUnCamelCase(accessorName);
					String goldTerm = (String) accessor.invoke(goldCasePart,
							emptyParams);
					String machTerm = (String) accessor.invoke(machCasePart,
							emptyParams);
					String goldCode = (String) accessor.invoke(
							goldCasePartCodified, emptyParams);
					String machCode = (String) accessor.invoke(
							machCasePartCodified, emptyParams);
					if (!goldCode.equals(machCode)) {
						sb.append("\t\t"
								+ String.format("%s, %s, %s, %s, %s", label,
										goldTerm, goldCode, machTerm, machCode)
								+ "\n");
					}
				}
			}
		}

		return sb.toString();
	}

}
