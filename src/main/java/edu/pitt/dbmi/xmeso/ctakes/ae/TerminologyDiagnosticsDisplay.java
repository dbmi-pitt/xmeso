package edu.pitt.dbmi.xmeso.ctakes.ae;

import java.util.Arrays;

import edu.pitt.dbmi.nlp.noble.terminology.impl.NobleCoderTerminology;

public class TerminologyDiagnosticsDisplay {
	
	private NobleCoderTerminology terminology;
	
	public void displayTerminologyDiagnostics() {
		StringBuffer sb = new StringBuffer();
		sb.append("isCachingEnabled " + terminology.isCachingEnabled() + "\n");
		sb.append("isContiguousMode " + terminology.isContiguousMode() + "\n");
		sb.append("defaultSearchMethod " + terminology.getDefaultSearchMethod()
				+ "\n");
		sb.append("languageFilter "
				+ Arrays.toString(terminology.getLanguageFilter()) + "\n");
		sb.append("semanticTypeFilter "
				+ Arrays.toString(terminology.getSemanticTypeFilter()) + "\n");
		sb.append("sourceFilter "
				+ Arrays.toString(terminology.getSourceFilter()) + "\n");
		sb.append("isIgnoreAcronyms " + terminology.isIgnoreAcronyms() + "\n");
		sb.append("isIgnoreSmallWords " + terminology.isIgnoreSmallWords()
				+ "\n");
		sb.append("isIgnoreUsedWords " + terminology.isIgnoreUsedWords() + "\n");
		sb.append("isSubsumptionMode " + terminology.isSubsumptionMode() + "\n");
		sb.append("isOrderedMode " + terminology.isOrderedMode() + "\n");
		sb.append("isOverlapMode " + terminology.isOverlapMode() + "\n");
		sb.append("isPartialMode " + terminology.isPartialMode() + "\n");
		sb.append("isContiguousMode " + terminology.isContiguousMode() + "\n");
		sb.append("maxWordGap " + terminology.getMaximumWordGap() + "\n");

		sb.append("isScoreConcepts " + terminology.isScoreConcepts() + "\n");
		sb.append("isSelectBestCandidate "
				+ terminology.isSelectBestCandidate() + "\n");
		System.out.println(sb.toString());
	}

}
