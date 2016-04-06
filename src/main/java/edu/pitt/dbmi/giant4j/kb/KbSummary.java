package edu.pitt.dbmi.giant4j.kb;


public class KbSummary extends KbIdentified implements KbSummaryInterface {

	private static final long serialVersionUID = 932761016714436847L;
	
	private int summarizableId = -1;
	private String code = "NA";
	private String preferredTerm = "NA";
	private String value = "NA";
	private String unitOfMeasure = "NA";
	
	private String path = "NA";
	private String baseCode = "NA";
	private String nameChar  = "NA";
	
	private int sPos = -1;
	private int ePos = -1;

	public int getSummarizableId() {
		return summarizableId;
	}

	public void setSummarizableId(int summarizableId) {
		this.summarizableId = summarizableId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPreferredTerm() {
		return preferredTerm;
	}

	public void setPreferredTerm(String preferredTerm) {
		this.preferredTerm = preferredTerm;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getBaseCode() {
		return baseCode;
	}

	public void setBaseCode(String baseCode) {
		this.baseCode = baseCode;
	}

	public String getNameChar() {
		return nameChar;
	}

	public void setNameChar(String nameChar) {
		this.nameChar = nameChar;
	}
	
	public int getSpos() {
		return sPos;
	}
	public void setSpos(int sPos) {
		this.sPos = sPos;
	}

	public int getEpos() {
		return ePos;
	}
	public void setEpos(int ePos) {
		this.ePos = ePos;
	}
	

	public String toString() {
		return "<" + getId() + "> " + getCode() + " " + getPreferredTerm();
	}

}
