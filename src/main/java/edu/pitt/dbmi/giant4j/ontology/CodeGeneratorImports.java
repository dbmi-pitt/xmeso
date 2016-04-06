package edu.pitt.dbmi.giant4j.ontology;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class CodeGeneratorImports {
	
	private String javaPackage;
	private String javaCls;
	private String importsFilePath;
	private File importsFile;
	private int importsFileIndex;
	
	private StringBuilder importsBuilder = new StringBuilder();
	
	public void codeGenerate() {
		StringBuilder sb = new StringBuilder();
		sb.append("(import @JavaPackage@.@JavaClass@)\n");
		String contentTemplate = sb.toString();
		contentTemplate = contentTemplate.replaceAll("@JavaPackage@", javaPackage);
		contentTemplate = contentTemplate.replaceAll("@JavaClass@", javaCls);
		importsBuilder.append(contentTemplate);
	}

	public void writeToFile() throws IOException {
		String formattedIndex = StringUtils.leftPad(importsFileIndex+"", 3, "0");
		File importsDirectory = new File(importsFilePath);
		String importsFileHandle = formattedIndex + "_imports.clp";
		File importsFile = new File(importsDirectory, importsFileHandle);
		System.out.println("Writing to " + importsFile.getAbsolutePath());
		if (importsFile.exists() && importsFile.isFile()) {
			System.err.println("Overwriting existing imports file " + importsFile.getAbsolutePath());
		}
		else {
			FileWriter importsFileWriter = new FileWriter(importsFile);
			IOUtils.write(importsBuilder.toString(), importsFileWriter);
			importsFileWriter.flush();
			importsFileWriter.close();
			importsBuilder = new StringBuilder();
		}
		importsFileIndex++;
	}
	
	public String getJavaPackage() {
		return javaPackage;
	}

	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}

	public String getJavaCls() {
		return javaCls;
	}

	public void setJavaCls(String javaCls) {
		this.javaCls = javaCls;
	}

	public String getImportsFilePath() {
		return importsFilePath;
	}

	public void setImportsFilePath(String importsFilePath) {
		this.importsFilePath = importsFilePath;
	}

	public File getImportsFile() {
		return importsFile;
	}

	public int getImportsFileIndex() {
		return importsFileIndex;
	}

	public void setImportsFileIndex(int importsFileIndex) {
		this.importsFileIndex = importsFileIndex;
	}

	

}
