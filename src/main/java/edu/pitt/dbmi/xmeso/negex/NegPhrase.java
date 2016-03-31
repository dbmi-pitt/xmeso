package edu.pitt.dbmi.xmeso.negex;


public class NegPhrase {
	
	private String negationPhrase;
	private String negationType;
	
	private int startIndex;
	private int endIndex;
	
	private String coveredText;
	
	public String getNegationPhrase() {
		return negationPhrase;
	}
	public void setNegationPhrase(String negationPhrase) {
		this.negationPhrase = negationPhrase;
	}
	public String getNegationType() {
		return negationType;
	}
	public void setNegationType(String negationType) {
		this.negationType = negationType;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	public void setCoveredText(String coveredText) {
		this.coveredText = coveredText;
	}
	public String getCoveredText() {
		return this.coveredText;
	}

}
