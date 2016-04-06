package edu.pitt.dbmi.giant4j.ontology;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class JavaInterfaceAndClassGenerator {
	
	private String javaPackage;
	private String javaCls;
	private String javaSuperCls;
	private List<String> javaSuperInterfaces;
	private String javaFilesDirectoryPrefix;
	private String javaFilesParentDirectoryPath;
	private String javaInterfacesDirectoryPath;
	private String javaClassesDirectoryPath;
	private String javaInterfaceCode;
	private String javaClassCode;
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
			writeToInterfaceFile();
			writeToClassFile();
			numberClassesCreated++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createNewOutputDirectory() throws IOException {
		final String formattedDirectory = javaFilesDirectoryPrefix + StringUtils.leftPad(numberDirectoriesCreated+"", 3, "0");
		javaInterfacesDirectoryPath = javaFilesParentDirectoryPath + File.separator + formattedDirectory;
		File javaInterfacesDirectory = new File(javaInterfacesDirectoryPath);
		if (javaInterfacesDirectory.exists()) {
			FileUtils.deleteDirectory(javaInterfacesDirectory);
		}
		javaInterfacesDirectory.mkdir();
		extendDirectoryForPackage();
	}

	private void generateJavaCode() {
		generateJavaInterfaceCode();
		generateJavaClassCode();
	}
	
	private void generateJavaInterfaceCode() {
		final StringBuffer sb = new StringBuffer();
		sb.append("package org.healthnlp.deepphe.summarization.drools.kb;\n");
		sb.append("\n");
		sb.append("@SerializationImport@");
		sb.append("\n");
		sb.append("public interface @JavaClass@ extends @JavaSuperClasses@ {\n");
		sb.append("\n");
		sb.append("}\n");
		javaInterfaceCode = sb.toString();
		javaInterfaceCode = javaInterfaceCode.replaceAll("@JavaClass@", javaCls);
		javaInterfaceCode = javaInterfaceCode.replaceAll("@JavaSuperClasses@",
				StringUtils.join(javaSuperInterfaces,", "));
		if (javaSuperInterfaces.contains("Serializable")) {
			javaInterfaceCode = javaInterfaceCode.replaceAll("@SerializationImport@", "import java.io.Serializable;");
		}
		else {
			javaInterfaceCode = javaInterfaceCode.replaceAll("@SerializationImport@", "");
		}
	}
	
	private void generateJavaClassCode() {
		final StringBuffer sb = new StringBuffer();
		sb.append("package org.healthnlp.deepphe.summarization.drools.kb.impl;\n");
		sb.append("\n");
		sb.append("import org.healthnlp.deepphe.summarization.drools.kb.@JavaSuperClass@;\n");
		sb.append("import org.healthnlp.deepphe.summarization.drools.kb.@JavaClass@;\n");
		sb.append("public class @JavaClass@Impl extends @JavaSuperClass@ implements @JavaClass@ {\n");
		sb.append("\n");
		sb.append("	private static final long serialVersionUID = 1L;\n");
		sb.append("	\n");
		sb.append("        public @JavaClass@Impl() {\n");
		sb.append("                 setCode(\"@JavaCui@\");\n");
		sb.append("                 setPreferredTerm(\"@JavaPreferredTerm@\");\n");
		sb.append("        }\n");
		sb.append("}\n");
		javaClassCode = sb.toString();
		javaClassCode = javaClassCode.replaceAll("@JavaClass@", javaCls);
		javaClassCode = javaClassCode.replaceAll("@JavaSuperClass@",
				javaSuperCls);
		javaClassCode = javaClassCode.replaceAll("@JavaCui@", javaCls);
		javaClassCode = javaClassCode.replaceAll("@JavaPreferredTerm@",
				javaCls);
	}

	private void writeToInterfaceFile() throws IOException {
		File javaFile = new File(javaInterfacesDirectoryPath, javaCls + ".java");
		if (javaFile.exists() && javaFile.isFile()) {
			System.err.println("Overwriting existing java file " + javaFile.getAbsolutePath());
		}
		else {
			FileWriter javaFileWriter = new FileWriter(javaFile);
			IOUtils.write(javaInterfaceCode, javaFileWriter);
			javaFileWriter.flush();
			javaFileWriter.close();
		}
	}
	
	private void writeToClassFile() throws IOException {
		File javaFile = new File(javaClassesDirectoryPath, javaCls + "Impl.java");
		if (javaFile.exists() && javaFile.isFile()) {
			System.err.println("Overwriting existing java file " + javaFile.getAbsolutePath());
		}
		else {
			FileWriter javaFileWriter = new FileWriter(javaFile);
			IOUtils.write(javaClassCode, javaFileWriter);
			javaFileWriter.flush();
			javaFileWriter.close();
		}
	}
	
	private void extendDirectoryForPackage() {
		String directoryTarget = javaInterfacesDirectoryPath;
		File directoryTargetFile = new File(directoryTarget);
		String[] packageParts = getJavaPackage().split("\\.");
		for (String packagePart : packageParts) {
			directoryTargetFile = new File(directoryTargetFile, packagePart);
			directoryTargetFile.mkdir();
		}
		javaClassesDirectoryPath = directoryTargetFile.getAbsolutePath();
		javaInterfacesDirectoryPath = directoryTargetFile.getParentFile().getAbsolutePath();
	}

	public String getJavaPackage() {
		return javaPackage;
	}

	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}

	public void setJavaCls(String javaCls) {
		this.javaCls = javaCls;
	}
	
	public void setJavaSuperInterfaces(List<String> javaSuperInterfaces) {
		this.javaSuperInterfaces = javaSuperInterfaces;
	}

	public void setJavaFilesParentDirectoryPath(String javaFilesParentDirectoryPath) {
		this.javaFilesParentDirectoryPath = javaFilesParentDirectoryPath;
	}
	
	public void setNumberClassesPerDirectory(int numberClassesPerDirectory) {
		this.numberClassesPerDirectory = numberClassesPerDirectory;
	}

	public void setNumberDirectoriesCreated(int numberDirectoriesCreated) {
		this.numberDirectoriesCreated = numberDirectoriesCreated;
	}

	public String getJavaFilesDirectoryPrefix() {
		return javaFilesDirectoryPrefix;
	}

	public void setJavaFilesDirectoryPrefix(String javaFilesDirectoryPrefix) {
		this.javaFilesDirectoryPrefix = javaFilesDirectoryPrefix;
	}

	public String getJavaSuperCls() {
		return javaSuperCls;
	}

	public void setJavaSuperCls(String javaSuperCls) {
		this.javaSuperCls = javaSuperCls;
	}


}
