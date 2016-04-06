
package edu.pitt.dbmi.giant4j.ontology;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.CaseFormat;

public class CamelCaseConverter {

	public static void main(String[] args) {

		CamelCaseConverter camelCaseConverter = new CamelCaseConverter();
		
		String str = "Hello, there, everyone?";
		System.out.println(str);
		System.out.println(camelCaseConverter.toCamelCase(str));
		
		str = "3-Dimensional Ultrasound-Guided Radiation Therapy";
		System.out.println(str);
		System.out.println(camelCaseConverter.toCamelCase(str));

		str = "Breast Cancer TNM Finding v7";
		System.out.println(str);
		System.out.println(camelCaseConverter.toCamelCase(str));

		str = "Breast Cancer Pathologic TNM Finding v7";
		System.out.println(str);
		System.out.println(camelCaseConverter.toCamelCase(str));

		str = "Abdominal (Mesenteric) Fibromatosis";
		System.out.println(str);
		System.out.println(camelCaseConverter.toCamelCase(str));

		str = "CancerStage";
		System.out.println(str);
		System.out.println(camelCaseConverter.toCamelCase(str));

	}

	public String toCamelCase(String inputString) {
		inputString = splitExistingCamelCaseFragments(inputString);
		inputString = inputString.replaceAll("[\\s\\(\\)\\,\\?\\-]", "_");
		inputString = camelCaseConstituents(inputString);
		inputString = inputString.toLowerCase();
		inputString = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
				inputString);
		inputString = expressNumberPrefixInEnglish(inputString);
		return inputString;
	}
	
	private String camelCaseConstituents(String inputString) {
		String[] constituents = inputString.split("_+");
		StringBuffer sb = new StringBuffer();
		for (String constituent : constituents) {
			if (sb.length() > 0) { sb.append("_"); }
			sb.append(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
					constituent.toLowerCase()));
		}
		return sb.toString();
	}

	private String splitExistingCamelCaseFragments(String inputString) {
		Pattern pattern = Pattern.compile("([A-Z][a-z]+)");
		Matcher matcher = pattern.matcher(inputString);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1) + "_");
		}
		if (sb.length() > 0) sb.setLength(sb.length() - 1);
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	private String expressNumberPrefixInEnglish(String inputString) {
		inputString = inputString.replaceAll("^0", "Zero");
		inputString = inputString.replaceAll("^1", "One");
		inputString = inputString.replaceAll("^2", "Two");
		inputString = inputString.replaceAll("^3", "Three");
		inputString = inputString.replaceAll("^4", "Four");
		inputString = inputString.replaceAll("^5", "Five");
		inputString = inputString.replaceAll("^6", "Six");
		inputString = inputString.replaceAll("^7", "Seven");
		inputString = inputString.replaceAll("^8", "Eight");
		inputString = inputString.replaceAll("^9", "Nine");
		return inputString;
	}

	
}

