package edu.pitt.dbmi.giant4j.kb;

public class KbEncounter extends KbSummarizable {
	
	private static final long serialVersionUID = 995528454712965512L;
	
	private int patientId = -1;
	private int sequence = -1;
	private String kind = "NA";
	private String uri;
	private String content;
	private String expertForm;
	private String xmesoForm;
	private String xmi;
	
	
	public KbEncounter() {
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getExpertForm() {
		return expertForm;
	}

	public void setExpertForm(String expertForm) {
		this.expertForm = expertForm;
	}

	public String getXmesoForm() {
		return xmesoForm;
	}

	public void setXmesoForm(String xmesoForm) {
		this.xmesoForm = xmesoForm;
	}

	public String getXmi() {
		return xmi;
	}

	public void setXmi(String xmi) {
		this.xmi = xmi;
	}
	
	public String fetchInfo() {
		String delimitedId = "<" + getId() + ">";
		String paddedSequence = leftPad(getSequence() + "", 4, "0");
		StringBuilder sb = new StringBuilder();
		sb.append("Encounter" + paddedSequence + delimitedId + "\n");
		sb.append("\n\n\n" + getContent() + "\n\n\n");
		sb.append("\n\n=====================================================================\n\n");
		if (getSummaries().size() == 0) {
			sb.append("\n\n\nYet to be processed by cTAKES and FHIR");
		}
		else {
			sb.append("\n\n\nEncounter Summary Information: \n\n");
			for (KbSummary summary : getSummaries()) {
				sb.append(summary.toString() + "\n");
			}
		}
		return sb.toString();
	}
	
	public String toString() {
		return "Encounter" + leftPad(getSequence() + "", 4, "0");
	}

}
