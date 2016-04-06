package edu.pitt.dbmi.xmeso.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class RandomCohortSelector {
	
	public static void main(String[] args) {
		RandomCohortSelector selector = new RandomCohortSelector();
		try {
			selector.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void execute() throws IOException {
		File reportDirectory = new File("C:\\ws\\ws-xmeso\\xmeso\\data\\reports");
		File randomizedResultsDirectory = new File("C:\\ws\\ws-xmeso\\xmeso\\data\\cohort");
		if (randomizedResultsDirectory.exists() && randomizedResultsDirectory.isDirectory()) {
			FileUtils.deleteDirectory(randomizedResultsDirectory);
		}
		FileUtils.forceMkdir(randomizedResultsDirectory);
		int numberOfFilesRead = 0;
		int numberOfFilesWritten = 0;
		File[] files = reportDirectory.listFiles();
		for (File srcFile : files) {
			 if (numberOfFilesRead % 7 == 0) {
				File tgtFile = new File(randomizedResultsDirectory, srcFile.getName());
				boolean preserveFileDate = true;
				FileUtils.copyFile(srcFile, tgtFile, preserveFileDate);
				numberOfFilesWritten++;
				if (numberOfFilesWritten == 50) {
					break;
				}
			}
			numberOfFilesRead++;
		}
	}

}
