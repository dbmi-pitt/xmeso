package edu.pitt.dbmi.xmeso.util;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.uima.util.FileUtils;

public class DeIdReportExtractor {

	public static void main(String[] args) {
		DeIdReportExtractor extractor = new DeIdReportExtractor();
		try {
			extractor.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void execute() throws IOException {
		File deIdFile = new File("C:\\ws\\ws-xmeso\\xmeso\\data\\xmeso\\input\\upmc\\nmvb.deid");
		File outputDirectory = new File("C:\\ws\\ws-xmeso\\xmeso\\data\\nmvb-extractor\\reports");
		if (outputDirectory.exists()) {
			FileUtils.deleteRecursive(outputDirectory);
		}
		outputDirectory.mkdir();
		final String content = FileUtils.file2String(deIdFile, "utf-8");		
		final int patternParams = Pattern.DOTALL | Pattern.MULTILINE;
		final Pattern pattern = Pattern.compile("E_O_H|E_O_R", patternParams);
		Matcher matcher = pattern.matcher(content);
		int numberReportsExtracted = 0;
		while (matcher.find()) {
			int beginIndex = matcher.end();
		    matcher.find();
			int endIndex = matcher.start();
			String reportName = "report" + StringUtils.leftPad(numberReportsExtracted + "", 5, "0") + ".txt";
			String reportContent = content.substring(beginIndex, endIndex);
			File outputFile = new File(outputDirectory, reportName);
			FileUtils.saveString2File(reportContent, outputFile);
			numberReportsExtracted++;
		}
	}

}
