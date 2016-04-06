package edu.pitt.dbmi.giant4j.kb;

import java.util.ArrayList;
import java.util.List;

public class KbPatient extends KbSummarizable {
	
	private static final long serialVersionUID = 1L;

	private int sequence = -1;
	
	protected final List<KbEncounter> encounters = new ArrayList<KbEncounter>();

	public void addEncounter(KbEncounter encounter) {
		encounters.add(encounter);
	}
	
	public List<KbEncounter> getEncounters() {
		return encounters;
	}
	
	public void clearEncounters() {
		encounters.clear();
	}

	public Object getGender() {
		return "Female";
	}
	
	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String fetchInfo() {
		String delimitedId = "<" + getId() + ">";
		String paddedSequence = leftPad(getSequence() + "", 4, "0");
		StringBuilder sb = new StringBuilder();
		sb.append("Patient" + paddedSequence + delimitedId + "\n");
		if (getSummaries().size() == 0) {
			sb.append("\n\n\nYet to be summarized");
		}
		else {
			sb.append("\n\n\nPatient Summary Information: \n\n");
			for (KbSummary summary : getSummaries()) {
				sb.append(summary.toString() + "\n");
			}
		}
		return sb.toString();
	}

	public String toString() {
		return "Patient" + leftPad(getSequence() + "", 4, "0");
	}

}
