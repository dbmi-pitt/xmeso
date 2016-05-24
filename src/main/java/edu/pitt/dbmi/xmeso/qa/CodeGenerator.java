package edu.pitt.dbmi.xmeso.qa;

public class CodeGenerator {

	private void generateXmesoPartJava(final String[] camelCaseAttributes) {
		StringBuffer sb = new StringBuffer();
		sb.append("package edu.pitt.dbmi.xmeso.qa;\n\n");
		sb.append("public class XmesoPart {\n");
		for (String attribute : camelCaseAttributes) {		
			if (attribute.matches("\\D+1")) {
				final String unNumberedAttr = attribute.substring(0, attribute.length()-1);
				sb.append("private String " + lowerCaseFirstChar(unNumberedAttr) + ";\n");
			}
		}
		for (String attribute : camelCaseAttributes) {
			if (attribute.matches("\\D+1")) {
				final String unNumberedAttr = attribute.substring(0, attribute.length()-1);
				String varName = lowerCaseFirstChar(unNumberedAttr);
				sb.append("public String get" + unNumberedAttr + "() { return " + lowerCaseFirstChar(unNumberedAttr) + ";}\n");
				sb.append("public void set" + unNumberedAttr + "(String " + lowerCaseFirstChar(unNumberedAttr) + ") { this." + varName + " = " + varName + ";}\n");
			}
		}
		sb.append("}");
		System.out.println(sb.toString());
	}
	
	@SuppressWarnings("unused")
	private void generateXmesoCaseReportJava(final String[] camelCaseAttributes) {
		StringBuffer sb = new StringBuffer();
		sb.append("public class NmvbCase {\n");
		for (String attribute : camelCaseAttributes) {
			if (!attribute.matches("\\D+[0-8]")) {
				sb.append("private String " + lowerCaseFirstChar(attribute) + ";\n");
			}
		}
		for (String attribute : camelCaseAttributes) {
			if (!attribute.matches("\\D+[0-8]")) {
				String varName = lowerCaseFirstChar(attribute);
				sb.append("public String get" + attribute + "() { return " + lowerCaseFirstChar(attribute) + ";}\n");
				sb.append("public void set" + attribute + "(String " + lowerCaseFirstChar(attribute) + ") { this." + varName + " = " + varName + ";}\n");
			}
		}
		sb.append("}");
		System.out.println(sb.toString());
	}
	
	private String lowerCaseFirstChar(String src) {
		String result = src;
		if (src != null && src.length() > 0) {
			result = src.substring(0, 1).toLowerCase();
			if (src.length() > 1) {
				result += src.substring(1, src.length());
			}
		}
		return result;
	}
	
}
