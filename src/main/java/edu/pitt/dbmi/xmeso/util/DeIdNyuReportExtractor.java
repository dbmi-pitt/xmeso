package edu.pitt.dbmi.xmeso.util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class DeIdNyuReportExtractor {
	
	public static void main(String[] args) {
		DeIdNyuReportExtractor extractor = new DeIdNyuReportExtractor();
		try {
			extractor.execute();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void execute() throws IOException, ParseException {
		final String xmesoHome = "C:\\ws\\ws-xmeso\\xmeso\\data\\nyu";
		File nyuDeidFile = new File(xmesoHome + File.separator + "path_reports_v2_deid.txt");
		File outputDirectory = establishOutputDirectory(xmesoHome);
		transferDeidFiles2Reports(nyuDeidFile, outputDirectory);
	}
	
	private void transferDeidFiles2Reports(File deidFile, File outputDirectory) throws IOException {
			String content = FileUtils.readFileToString(deidFile);
			final int patternParams = Pattern.DOTALL | Pattern.MULTILINE;
			final Pattern pattern = Pattern.compile("(\\[\\*\\*Location.*?SURGICAL PATHOLOGY REPORT)", patternParams);
			int reportNumber = 1;
			Matcher matcher = pattern.matcher(content);
			int sPos = -1;
			int ePos = -1;
			if (matcher.find()) {
				sPos = matcher.start(1);
			}
			while (matcher.find()) {
				ePos = matcher.start(1);
				extractToFile(outputDirectory, reportNumber, content, sPos, ePos);
				reportNumber++;
				sPos = ePos;
			}
			ePos = content.length();
			extractToFile(outputDirectory, reportNumber, content, sPos, ePos);
	}
	
	private void extractToFile(File outputDirectory, int reportNumber, String content, int sPos, int ePos) throws IOException {
		String reportContent = content.substring(sPos, ePos);
		reportContent +="\n\n$";
		String patientNum = StringUtils.leftPad(reportNumber+"", 4, "0");
		String visitNum = StringUtils.leftPad(reportNumber+"", 5, "0");
		final String reportName = "MVB" + patientNum + "_" + visitNum + ".txt";
		File outputFile =  new File(outputDirectory, reportName);
		FileUtils.writeStringToFile(outputFile, reportContent, "utf8");
	}
	
	private File establishOutputDirectory(String xmesoHome) throws IOException {
		File outputDirectory = new File(xmesoHome + File.separator + "reports");
		if (outputDirectory.exists()) {
			FileUtils.forceDelete(outputDirectory);
		}
		outputDirectory.mkdir();
		return outputDirectory;
	}

}
