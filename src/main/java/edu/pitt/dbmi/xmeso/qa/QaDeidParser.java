package edu.pitt.dbmi.xmeso.qa;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.uima.util.FileUtils;

public class QaDeidParser {
	
	private Integer reportNumber;
	private String formattedReportNumber;
	private String currentPatientChecksum;
	private String currentReportChecksum;
	private String currentPrincipleDateAsString;
	private Date currentPrincipleDate;
	private String currentReportContent;
	
	private List<XmesoCase> xmesoCases = new ArrayList<XmesoCase>();
	private Map<String, XmesoCase> xmesoCaseMap = new HashMap<String, XmesoCase>();
	
	public static void main(String[] args) {
		QaDeidParser extractor = new QaDeidParser();
		extractor.execute();
	}
	
	public void replaceAllCases(List<XmesoCase> xmesoCases) {
		this.xmesoCases.clear();
		this.xmesoCases.addAll(xmesoCases);
		for (XmesoCase xmesoCase : xmesoCases) {
			String key = xmesoCase.getReportId();
			xmesoCaseMap.put(key, xmesoCase);
		}
	}

	public void execute()  {
		try {
			tryExecute();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void tryExecute() throws IOException, ParseException {
		File deIdFile = new File("C:\\ws\\ws-xmeso\\xmeso\\data\\xmeso\\input\\nmvb160520\\nmvb_train.deid");
		File outputDirectory = new File("C:\\ws\\ws-xmeso\\xmeso\\data\\xmeso\\output\\nmvb160520");
		if (outputDirectory.exists()) {
			FileUtils.deleteRecursive(outputDirectory);
		}
		outputDirectory.mkdir();
		final String content = FileUtils.file2String(deIdFile, "utf-8");		
		final int patternParams = Pattern.DOTALL | Pattern.MULTILINE;
		final Pattern pattern = Pattern.compile("S_O_H(.+?)E_O_H(.+?)E_O_R", patternParams);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			String header = matcher.group(1);
			String reportContent = matcher.group(2);
			processHeader(header);
			processReportContent(reportContent);
			
			final String reportName = "report_" + formattedReportNumber + ".txt";
			File outputFile =  new File(outputDirectory, reportName);
			FileUtils.saveString2File( currentReportContent, outputFile, "utf8");
			System.out.println(formatCurrentReportInformation());
		}
	}
	
	private void processReportContent(String reportContent) {
		currentReportContent = reportContent;
		currentReportContent += "\n\n$";
	}

//	1,BohWEAHrwxRB	BohWEAHrwxRB	SP	A12345BohWEAHrwxRB	20160504
	private void processHeader(String header) throws ParseException {
		Pattern headerParser = Pattern.compile("(\\d+)\\,(\\S+)\\s+(\\S+)\\s+SP\\s+(\\S+)\\s+(\\d+)");
		Matcher matcher = headerParser.matcher(header);
		if (matcher.find()) {
			reportNumber = new Integer(matcher.group(1));
			currentPatientChecksum = matcher.group(2);
			String key = reportNumber + " " + currentPatientChecksum;
			XmesoCase xmesoCase = xmesoCaseMap.get(key);
			if (xmesoCase != null) {
				formattedReportNumber = StringUtils.leftPad(xmesoCase.getReportNumber(), 5, "0");
				String currentPatientChecksumVerifier = matcher.group(3);
				if (currentPatientChecksum.equals(currentPatientChecksumVerifier)) {
					currentReportChecksum = matcher.group(4);
					currentPrincipleDateAsString = matcher.group(5);
					DateFormat df = new SimpleDateFormat("yyyyMMdd");
					currentPrincipleDate = df.parse(currentPrincipleDateAsString);
				}
				else {
					System.err.println("ERROR in header: " + header);
				}
			}
		}
	}
	
	private String formatCurrentReportInformation() {
		StringBuilder sb = new StringBuilder();
		sb.append("reportNumber = " + reportNumber + "\n");
		sb.append("patient = " + currentPatientChecksum + "\n");
		sb.append("report = " + currentReportChecksum + "\n");
		sb.append("report = " + currentPrincipleDate + "\n");
		sb.append("chars of content = " + currentReportContent.length());
		return sb.toString();
	}

}
