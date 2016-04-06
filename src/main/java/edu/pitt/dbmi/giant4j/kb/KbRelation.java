package edu.pitt.dbmi.giant4j.kb;

public class KbRelation extends KbSummary implements KbRelationInterface {

	private static final long serialVersionUID = 1L;
	
	private int domainId;
	private int rangeId;
	
	public int getDomainId() {
		return this.domainId;
	}
	
	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}
	
	public int getRangeId() {
		return this.rangeId;
	}
	
	public void setRangeId(int rangeId) {
		this.rangeId = rangeId;
	}

}
