package edu.pitt.dbmi.giant4j.kb;

public interface KbRelationInterface extends KbSummaryInterface {
	public int getDomainId();
	public void setDomainId(int domainId);
	public int getRangeId();
	public void setRangeId(int rangeId);
}
