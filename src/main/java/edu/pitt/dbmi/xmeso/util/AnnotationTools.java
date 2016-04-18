package edu.pitt.dbmi.xmeso.util;

import java.util.List;

import org.apache.uima.jcas.tcas.Annotation;

public class AnnotationTools {
	
	public static String annotToText(Annotation annot) {
		StringBuilder sb = new StringBuilder();
		sb.append(annot.getClass().getSimpleName() + ": (");
		sb.append(annot.getBegin() + ",");
		sb.append(annot.getEnd() + ") => ");
		if (annot.getCoveredText().equals("\n")) {
			sb.append("EOLN");
		} else {
			sb.append(annot.getCoveredText());
		}
		return sb.toString();
	}

	public static String annotToTextWithNoCovering(Annotation annot) {
		StringBuilder sb = new StringBuilder();
		sb.append(annot.getClass().getSimpleName() + ": (");
		sb.append(annot.getBegin() + ",");
		sb.append(annot.getEnd() + ") => ");
		return sb.toString();
	}

	@SuppressWarnings("unused")
	private static void displayDimensions(List<Integer> partNumbers,
			List<Integer> sectionLevels, List<Integer> codedPenalties) {
		System.out.println("Displaying dimensions");
		StringBuilder sb = new StringBuilder();
		sb.append("Part Numbers: ");
		for (Integer partNumber : partNumbers) {
			sb.append(partNumber + ", ");
		}
		System.out.println(sb.toString());
		sb = new StringBuilder();
		sb.append("Section Levels: ");
		for (Integer sectionLevel : sectionLevels) {
			sb.append(sectionLevel + ", ");
		}
		System.out.println(sb.toString());
		sb = new StringBuilder();
		sb.append("Coded Penalties:  ");
		for (Integer codedPenalty : codedPenalties) {
			sb.append(codedPenalty + ", ");
		}
		System.out.println(sb.toString());
	}


}
