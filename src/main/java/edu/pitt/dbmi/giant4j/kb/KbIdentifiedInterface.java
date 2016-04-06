package edu.pitt.dbmi.giant4j.kb;

import java.io.Serializable;

public interface KbIdentifiedInterface extends Serializable {
	public int getId();
	public void setId(int id);
	public int getIsActive();
	public void setIsActive(int isActive);
}
