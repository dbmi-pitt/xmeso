package edu.pitt.dbmi.xmeso.negex;

import java.util.HashSet;
import java.util.Set;

public class SentenceDetectorConfigurator {

	private Set<String> POSSIBLE_STOPS = new HashSet<String>();
	private Set<String> IMPOSSIBLE_PENULTIMATES = new HashSet<String>();
	private Set<String> IMPOSSIBLE_SENTENCE_STARTS = new HashSet<String>();

	private boolean forceFinalStop;
	private boolean balanceParentheses;

	public SentenceDetectorConfigurator() {
		initializePossibleStops();
		initializeImpossiblePenultimates();
		initializeImpossibleSentenceStarts();
	}
	
	public SentenceDetectorConfigurator(boolean forceFinalStop, boolean balanceParentheses) {
		initializePossibleStops();
		initializeImpossiblePenultimates();
		initializeImpossibleSentenceStarts();
		setForceFinalStopsFlag(forceFinalStop);
		setBalanceParenthesesFlag(balanceParentheses);
	}

	public Set<String> getPossibleStops() {
		return POSSIBLE_STOPS;
	}

	public Set<String> getImpossiblePenUltimates() {
		return IMPOSSIBLE_PENULTIMATES;
	}

	public Set<String> getImpossibleSentenceStarts() {
		return IMPOSSIBLE_SENTENCE_STARTS;
	}


	public void initializePossibleStops() {
		POSSIBLE_STOPS.add(".");
	}

	public void initializeImpossiblePenultimates() {
		IMPOSSIBLE_PENULTIMATES.add("Mme");
		IMPOSSIBLE_PENULTIMATES.add("Dr");
		IMPOSSIBLE_PENULTIMATES.add("Mr");
		IMPOSSIBLE_PENULTIMATES.add("Mrs");
	}

	public void initializeImpossibleSentenceStarts() {
		IMPOSSIBLE_SENTENCE_STARTS.add(")");
		IMPOSSIBLE_SENTENCE_STARTS.add("]");
		IMPOSSIBLE_SENTENCE_STARTS.add("}");
		IMPOSSIBLE_SENTENCE_STARTS.add(">");
		IMPOSSIBLE_SENTENCE_STARTS.add("<");
		IMPOSSIBLE_SENTENCE_STARTS.add(".");
		IMPOSSIBLE_SENTENCE_STARTS.add("!");
		IMPOSSIBLE_SENTENCE_STARTS.add("?");
		IMPOSSIBLE_SENTENCE_STARTS.add(":");
		IMPOSSIBLE_SENTENCE_STARTS.add(";");
		IMPOSSIBLE_SENTENCE_STARTS.add("-");
		IMPOSSIBLE_SENTENCE_STARTS.add("--");
		IMPOSSIBLE_SENTENCE_STARTS.add("---");
		IMPOSSIBLE_SENTENCE_STARTS.add("%");
	}

	public void addPossibleStop(String stop) {
		POSSIBLE_STOPS.add(stop);
	}

	public void removePossibleStop(String stop) {
		POSSIBLE_STOPS.remove(stop);
	}

	public void addImpossiblePenUltimate(String impossiblePenUltimate) {
		IMPOSSIBLE_PENULTIMATES.add(impossiblePenUltimate);
	}

	public void removeImpossiblePenUltimate(String impossiblePenUltimate) {
		IMPOSSIBLE_PENULTIMATES.remove(impossiblePenUltimate);
	}

	public void addImpossibleSentenceStart(String impossibleSentenceStart) {
		IMPOSSIBLE_SENTENCE_STARTS.add(impossibleSentenceStart);
	}

	public void removeImpossibleSentenceStart(String impossibleSentenceStart) {
		IMPOSSIBLE_SENTENCE_STARTS.remove(impossibleSentenceStart);
	}

	public void setForceFinalStopsFlag(boolean flag){
		forceFinalStop = flag;
	}

	public boolean getForceFinalStopsFlag(){
		return forceFinalStop;
	}

	public void setBalanceParenthesesFlag(boolean flag) {
		balanceParentheses = flag;
	}

	public boolean getBalanceParenthesesFlag() {
		return balanceParentheses;
	}


}
