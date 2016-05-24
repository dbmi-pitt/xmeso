package edu.pitt.dbmi.xmeso.qa;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.I2b2MetaDataSourceManager;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.NcatsDemographics;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.NcatsIcd9Diag;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.NcatsIcd9Proc;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.NcatsVisitDetails;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.NmvbMesothelioma;

public class Qa {

	private I2b2MetaDataSourceManager metaDataMgr;
	private Session metaDataSession;

	private List<XmesoCase> xmesoCases = new ArrayList<XmesoCase>();

	private XmesoCase xmesoCase;
	private XmesoPart xmesoPart;
	private String attributeName;
	private String camelCaseAttribute;
	private String attributeValue;

	private CodeResolver codeResolver;

	private HashSet<String> legalConceptCds = new HashSet<String>();
	
	private static final String[] nonCodingAttributes = {
		"Project", "Patient", "ReportID", "Report_fk", "Report Number", "Report Date"};
	
	public static void main(String[] args) {
		Qa qa = new Qa();
		qa.execute();
	}

	private void execute() {
		try {
			establishDbConnectivity();
			establishCodeResolution();
			etlGoldSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void establishCodeResolution() {
		codeResolver = new CodeResolver();
		codeResolver.setMetaDataMgr(metaDataMgr);
		codeResolver.initialize();
	}

	private void etlGoldSet() throws IOException {
		final String tsvGoldSetPath = "C:\\ws\\ws-xmeso\\xmeso\\data\\xmeso\\input\\nmvb160520\\ExampleAnnotationTable.tsv";
		final String tsvGoldSetContent = FileUtils.readFileToString(new File(
				tsvGoldSetPath));
		final String[] tsvGoldSetLines = tsvGoldSetContent.split("\n");
		final String[] attributes = tsvGoldSetLines[0].split("\t");
		final String[] camelCaseAttributes = camelCaseAttributes(attributes);
		codifySpreadSheet(attributes, camelCaseAttributes, tsvGoldSetLines);
		// beanifySpreadSheet(camelCaseAttributes, tsvGoldSetLines);
		// for (XmesoCase xmesoCase : xmesoCases) {
		// System.out.println(xmesoCase);
		// }
	}

	private void codifySpreadSheet(String[] attributes,
			String[] camelCaseAttributes, String[] tsvGoldSetLines) {
		for (int lineIdx = 1; lineIdx < tsvGoldSetLines.length; lineIdx++) {
			final String[] attributeValues = tsvGoldSetLines[lineIdx]
					.split("\t");
			int attrIdx = 0;
			while (attrIdx < attributes.length) {
				attributeName = attributes[attrIdx];
				attributeValue = attributeValues[attrIdx];
				if (StringUtils.isNotBlank(attributeName)
						&& StringUtils.isNotBlank(attributeValue)
						&& !Arrays.asList(nonCodingAttributes).contains(attributeName)) {
					if (attributeName.matches("\\D+[0-8]")) {
						attributeName = attributeName.replaceAll("\\s+[0-8]",
								"");
					}
					codeResolver.clear();
					codeResolver.addQueryTerm(attributeName);
					codeResolver.addQueryTerm(attributeValue);
					String code = codeResolver.searchForCode();
					if (StringUtils.isBlank(code)) {
						code = "FAILED";
					}
					System.out.println(attributeName + ", " + attributeValue
							+ " => " + code);
				}
				attrIdx++;
			}
		}
	}

	private void beanifySpreadSheet(String[] camelCaseAttributes,
			String[] tsvGoldSetLines) {
		for (int lineIdx = 1; lineIdx < tsvGoldSetLines.length; lineIdx++) {
			xmesoCase = new XmesoCase();
			xmesoCases.add(xmesoCase);
			final String[] attributeValues = tsvGoldSetLines[lineIdx]
					.split("\t");
			int attrIdx = 0;
			while (attrIdx < camelCaseAttributes.length) {
				camelCaseAttribute = camelCaseAttributes[attrIdx];
				attributeValue = attributeValues[attrIdx];
				if (!camelCaseAttribute.matches("\\D+[0-8]")) {
					populateAttributeValue(xmesoCase, camelCaseAttribute,
							attributeValue);
				} else {
					int partIdx = new Integer(camelCaseAttribute.substring(
							camelCaseAttribute.length() - 1,
							camelCaseAttribute.length()));
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

	private void establishDbConnectivity() {
		metaDataMgr = new I2b2MetaDataSourceManager();
		metaDataMgr
				.setHibernateConnectionUrl("jdbc:oracle:thin:@dbmi-i2b2-dev05.dbmi.pitt.edu:1521:xe");
		metaDataMgr.setHibernateDriver("oracle.jdbc.driver.OracleDriver");
		metaDataMgr.setHibernateShowSql("true");
		metaDataSession = metaDataMgr.getSession();
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

}
