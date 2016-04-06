package edu.pitt.dbmi.giant4j.ontology;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;


public class CodeGeneratorDeftemplates {

	private String javaCls;
	private String javaSuperCls;
	private String deftemplatesDirectoryPath;

	private int fileStartOffset = 11;

	private int numberTemplatesPerFile = 1000;
	private int numberFilesCreated = 0;
	private int numberDeftemplatesCreated = 0;

	private StringBuilder deftemplatesBuilder = new StringBuilder();

	public void codeGenerate() {
		if ((numberDeftemplatesCreated > 0)
				&& (numberDeftemplatesCreated % numberTemplatesPerFile == 0)) {
			writeToFile();
			numberFilesCreated++;
		}
		generateAndAppendTemplate();
		numberDeftemplatesCreated++;
	}

	private void generateAndAppendTemplate() {
		final StringBuffer sb = new StringBuffer();
		sb.append("(deftemplate @JavaClass@ extends @JavaSuperClass@\n");
		sb.append("   (declare (from-class @JavaClass@)\n");
		sb.append("     (include-variables TRUE)))\n");
		String contentTemplate = sb.toString();
		contentTemplate = contentTemplate.replaceAll("@JavaClass@", javaCls);
		contentTemplate = contentTemplate.replaceAll("@JavaSuperClass@",
				javaSuperCls);
		deftemplatesBuilder.append(contentTemplate);
	}

	public void writeToFile() {
		try {
			if (deftemplatesBuilder.toString().length() > 0) {
				String formattedTemplateNumber = StringUtils.leftPad(numberFilesCreated
						+ fileStartOffset + "", 3, "0");
				File deftemplatesDirectory = new File(deftemplatesDirectoryPath);
				String deftemplatesFileHandle = formattedTemplateNumber
						+ "_deftemplates.clp";
				File deftemplatesFile = new File(deftemplatesDirectory,
						deftemplatesFileHandle);
				System.out.println("Writing to "
						+ deftemplatesFile.getAbsolutePath());
				if (deftemplatesFile.exists() && deftemplatesFile.isFile()) {
					FileUtils.deleteQuietly(deftemplatesFile);
				}
				FileWriter deftemplatesFileWriter = new FileWriter(deftemplatesFile);
				IOUtils.write(deftemplatesBuilder.toString(),
						deftemplatesFileWriter);
				deftemplatesFileWriter.flush();
				deftemplatesFileWriter.close();
				deftemplatesBuilder = new StringBuilder();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getJavaCls() {
		return javaCls;
	}

	public void setJavaCls(String javaCls) {
		this.javaCls = javaCls;
	}

	public String getJavaSuperCls() {
		return javaSuperCls;
	}

	public void setJavaSuperCls(String javaSuperCls) {
		this.javaSuperCls = javaSuperCls;
	}

	public void setDeftemplatesDirectoryPath(String deftemplatesDirectoryPath) {
		this.deftemplatesDirectoryPath = deftemplatesDirectoryPath;
	}

	public void setFileStartOffset(int fileStartOffset) {
		this.fileStartOffset = fileStartOffset;
	}

}
