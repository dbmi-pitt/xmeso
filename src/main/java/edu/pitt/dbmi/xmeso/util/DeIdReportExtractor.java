package edu.pitt.dbmi.xmeso.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.uima.util.FileUtils;

public class DeIdReportExtractor {
	
	private Integer reportNumber;
	private String formattedReportNumber;
	private String currentPatientChecksum;
	private String currentReportChecksum;
	private String currentPrincipleDateAsString;
	private Date currentPrincipleDate;
	private String currentReportContent;

	public static void main(String[] args) {
		DeIdReportExtractor extractor = new DeIdReportExtractor();
		try {
			extractor.execute();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void execute() throws IOException, ParseException {
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
			
			final String reportName = "report_" + formattedReportNumber + "_" + currentPatientChecksum + "_" + currentReportChecksum + ".txt";
			File outputFile =  new File(outputDirectory, reportName);
			FileUtils.saveString2File( currentReportContent, outputFile, "utf8");
			System.out.println(formatCurrentReportInformation());
		}
	}
	
	private void processReportContent(String reportContent) {
		currentReportContent = reportContent;
		currentReportContent += "\n\n$";
	}

	private void processHeader(String header) throws ParseException {
		Pattern headerParser = Pattern.compile("(\\d+)\\,(\\w+)\\s+(\\w+)\\s+SP\\s+(\\w+)\\s+(\\d+)");
		Matcher matcher = headerParser.matcher(header);
		if (matcher.find()) {
			reportNumber = new Integer(matcher.group(1));
			formattedReportNumber = StringUtils.leftPad(reportNumber+"", 5, "0");
			currentPatientChecksum = matcher.group(2);
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
