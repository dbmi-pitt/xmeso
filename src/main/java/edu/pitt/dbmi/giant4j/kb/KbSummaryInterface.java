package edu.pitt.dbmi.giant4j.kb;

public interface KbSummaryInterface extends KbIdentifiedInterface {
	
	public int getSummarizableId();
	public void setSummarizableId(int summarizableId);
	public String getCode();
	public void setCode(String code);
	public String getPreferredTerm();
	public void setPreferredTerm(String preferredTerm);
	public String getValue();
	public void setValue(String value);
	public String getUnitOfMeasure();
	public void setUnitOfMeasure(String unitOfMeasure);
	public String getPath();
	public void setPath(String path);
	public String getBaseCode();
	public void setBaseCode(String baseCode);
	public String getNameChar();
	public void setNameChar(String nameChar);
	public int getSpos();
	public void setSpos(int sPos);
	public int getEpos();
	public void setEpos(int ePos);
	
}
