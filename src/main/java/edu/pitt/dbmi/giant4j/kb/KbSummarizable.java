package edu.pitt.dbmi.giant4j.kb;

import java.util.ArrayList;
import java.util.List;

public class KbSummarizable extends KbIdentified {
	
	private static final long serialVersionUID = -7882233828225664692L;
	
	protected final List<KbSummary> summaries = new ArrayList<KbSummary>();

	public void addSummary(KbSummary summary) {
		summaries.add(summary);
	}
	
	public List<KbSummary> getSummaries() {
		return summaries;
	}
	
	public void clearSummaries() {
		summaries.clear();
	}

	public String getUuid() {
		return getClass().getSimpleName() + leftPad(getId()+"",4,"0");
	}

    
}
