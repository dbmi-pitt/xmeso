package edu.pitt.dbmi.xmeso.sentence;

import java.util.Set;

import com.aliasi.sentences.HeuristicSentenceModel;

public class SentenceDetector extends HeuristicSentenceModel{
	public SentenceDetector(Set<String> possibleStops, Set<String> impossiblePenultimate, Set<String> impossibleStarts, boolean forceFinalStop, boolean balanceParens) {
		super(possibleStops, impossiblePenultimate, impossibleStarts);
	}
}