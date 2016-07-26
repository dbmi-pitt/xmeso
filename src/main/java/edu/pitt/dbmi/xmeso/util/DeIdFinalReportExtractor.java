package edu.pitt.dbmi.xmeso.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class DeIdFinalReportExtractor {
	
	private Properties xmesoProperties = new Properties();
	
	private Integer reportNumber;
	private String formattedReportNumber;
	private String currentPatientChecksum;
	private String currentReportChecksum;
	private String currentPrincipleDateAsString;
	private Date currentPrincipleDate;
	private String currentReportContent;

	public static void main(String[] args) {
		DeIdFinalReportExtractor extractor = new DeIdFinalReportExtractor();
		try {
			extractor.execute();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void execute() throws IOException, ParseException {
		String userHome = System.getProperty("user.home");
		String xmesoHome = userHome + File.separator + "xmeso";
		loadXmesoProperties(xmesoHome);
		File outputDirectory = establishOutputDirectory(xmesoHome);
		transferDeidFiles2Reports(outputDirectory);

	}
	
	private void transferDeidFiles2Reports(File outputDirectory) throws IOException {
		File deidDirectory = new File("C:\\Users\\zhy19\\workspace\\xmeso\\data\\final\\deid");
		File[] deidFiles = deidDirectory.listFiles();
		for (File deidFile : deidFiles) {
			String content = FileUtils.readFileToString(deidFile);
			final int patternParams = Pattern.DOTALL | Pattern.MULTILINE;
			final Pattern pattern = Pattern.compile("(\\[Report de-identified.*)E_O_R", patternParams);
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				String reportContent = matcher.group(1);
				final String reportName = deidFile.getName().replaceAll("\\.deid$", ".txt");
				File outputFile =  new File(outputDirectory, reportName);
				FileUtils.writeStringToFile(outputFile, reportContent, "utf8");
			}
		}
	}
	
	private File establishOutputDirectory(String xmesoHome) throws IOException {
		File outputDirectory = new File(xmesoHome + File.separator + "reports");
		if (outputDirectory.exists()) {
			FileUtils.forceDelete(outputDirectory);
		}
		outputDirectory.mkdir();
		return outputDirectory;
	}
	
	
	private void loadXmesoProperties(String xmesoHomeDirectoryPath) {
		InputStream input = null;
		try {
			input = new FileInputStream(xmesoHomeDirectoryPath + File.separator + "xmeso.properties");
			xmesoProperties.load(input);
			System.out.println(xmesoProperties.getProperty("organization"));
			System.out.println(xmesoProperties.getProperty("location_path"));
			System.out.println(xmesoProperties.getProperty("url"));
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
	
	private void processReportContent(String reportContent) {
		currentReportContent = reportContent;
		currentReportContent += "\n\n$";
	}
	
	private void parseDeid(File deidDirectory, File outputDirectory) throws IOException, ParseException {
		final String content = FileUtils.readFileToString(deidDirectory, "utf-8");		
		final int patternParams = Pattern.DOTALL | Pattern.MULTILINE;
		final Pattern pattern = Pattern.compile("([Report de-identified.*)E_O_R", patternParams);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			String reportContent = matcher.group(1);
			final String reportName = "report_" + formattedReportNumber + "_" + currentPatientChecksum + "_" + currentReportChecksum + ".txt";
			File outputFile =  new File(outputDirectory, reportName);
			FileUtils.writeStringToFile(outputFile, currentReportContent, "utf8");
			System.out.println(formatCurrentReportInformation());
		}
	}
	
	private void processHeader(String header) throws ParseException {
		Pattern headerParser = Pattern.compile("(\\d+)\\,([A-Za-z0-9\\+]+)\\s+([A-Za-z0-9\\+]+)\\s+SP\\s+([A-Za-z0-9\\+]+)\\s+(\\d+)");
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
