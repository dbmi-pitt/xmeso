package edu.pitt.dbmi.giant4j.ontology;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class CodeGeneratorJava {
	
	private String javaPackage;
	private String javaCls;
	private String javaSuperCls;
	private String javaCui;
	private String javaPreferredTerm;
	private String javaFilesParentDirectoryPath;
	private String javaFilesDirectoryPath;
	private String javaCode;
	
	private int numberClassesPerDirectory = 1000;
	private int numberDirectoriesCreated = 0;
	private int numberClassesCreated = 0;

	public void codeGenerate() {
		try {
			if (numberClassesCreated % numberClassesPerDirectory == 0) {
				System.out.println("Writing " + javaCls + "....");
				createNewOutputDirectory();
				numberDirectoriesCreated++;
			}
			generateJavaCode();
			writeToFile();
			numberClassesCreated++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createNewOutputDirectory() throws IOException {
		final String formattedDirectory = "src" + StringUtils.leftPad(numberDirectoriesCreated+"", 3, "0");
		javaFilesDirectoryPath = javaFilesParentDirectoryPath + File.separator + formattedDirectory;
		File javaFilesDirectory = new File(javaFilesDirectoryPath);
		if (javaFilesDirectory.exists()) {
			FileUtils.deleteDirectory(javaFilesDirectory);
		}
		javaFilesDirectory.mkdir();
		extendDirectoryForPackage();
	}

	private void generateJavaCode() {
		final StringBuffer sb = new StringBuffer();
		sb.append("package org.healthnlp.deepphe.summarization.jess.kb;\n");
		sb.append("\n");
		sb.append("public class @JavaClass@ extends @JavaSuperClass@ {\n");
		sb.append("\n");
		sb.append("	private static final long serialVersionUID = 1L;\n");
		sb.append("	\n");
		sb.append("        public @JavaClass@() {\n");
		sb.append("                 setCode(\"@JavaCui@\");\n");
		sb.append("                 setPreferredTerm(\"@JavaPreferredTerm@\");\n");
		sb.append("        }\n");
		sb.append("}\n");
		javaCode = sb.toString();
		javaCode = javaCode.replaceAll("@JavaClass@", javaCls);
		javaCode = javaCode.replaceAll("@JavaSuperClass@",
				javaSuperCls);
		javaCode = javaCode.replaceAll("@JavaCui@", javaCui);
		javaCode = javaCode.replaceAll("@JavaPreferredTerm@",
				javaPreferredTerm);
	}

	private void writeToFile() throws IOException {
		File javaFile = new File(javaFilesDirectoryPath, javaCls + ".java");
		if (javaFile.exists() && javaFile.isFile()) {
			System.err.println("Overwriting existing java file " + javaFile.getAbsolutePath());
		}
		else {
			FileWriter javaFileWriter = new FileWriter(javaFile);
			IOUtils.write(javaCode, javaFileWriter);
			javaFileWriter.flush();
			javaFileWriter.close();
		}
	}
	
	private void extendDirectoryForPackage() {
		String directoryTarget = javaFilesDirectoryPath;
		File directoryTargetFile = new File(directoryTarget);
		String[] packageParts = getJavaPackage().split("\\.");
		for (String packagePart : packageParts) {
			directoryTargetFile = new File(directoryTargetFile, packagePart);
			directoryTargetFile.mkdir();
		}
		javaFilesDirectoryPath = directoryTargetFile.getAbsolutePath();
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

	public String getJavaSuperCls() {
		return javaSuperCls;
	}

	public void setJavaSuperCls(String javaSuperCls) {
		this.javaSuperCls = javaSuperCls;
	}

	public String getJavaCui() {
		return javaCui;
	}

	public void setJavaCui(String javaCui) {
		this.javaCui = javaCui;
	}

	public String getJavaPreferredTerm() {
		return javaPreferredTerm;
	}

	public void setJavaPreferredTerm(String javaPreferredTerm) {
		this.javaPreferredTerm = javaPreferredTerm;
	}

	public void setJavaFilesParentDirectoryPath(String javaFilesParentDirectoryPath) {
		this.javaFilesParentDirectoryPath = javaFilesParentDirectoryPath;
	}

}
