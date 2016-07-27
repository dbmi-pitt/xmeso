package edu.pitt.dbmi.giant4j.kb;


public class KbIdentified implements KbIdentifiedInterface {
	
	private static final long serialVersionUID = 3569891592557068875L;

	public static int idGenerator = 0;
	
	private int id;
	private int isActive = 0;

	public KbIdentified() {
		id = idGenerator++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	
	protected String leftPad(String s, int n, String padChr) {
		StringBuilder sb = new StringBuilder();
		int padSize = Math.max(0, s.length() - n);
		for (int idx = 0; idx < padSize; idx++) {
			sb.append(padChr);
		}
		sb.append(s); 
	    return sb.toString();
	}
	
}
