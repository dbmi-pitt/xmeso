package edu.pitt.dbmi.xmeso.qa;

public class XmesoCase {
	
	private static final int CONST_MAX_PARTS = 8;
	
	private String project;
	private String patient;
	private String patientNumber;
	private String reportid;
	private String reportFk;
	private String reportNumber;
	private String reportDate;
	private String largestNodule;
	private String lymphNodesExamined;
	private String positiveLymphNodes;
	private String specialStainProfile;
	private String statusOfVascularInvasionByTumor;
	private String ultrastructuralFindings;
	private String immunohistochemicalProfile;
	private String procedureType;
	private String notRequired;
	
	private XmesoPart[] xmesoParts = new XmesoPart[CONST_MAX_PARTS];
	
	public XmesoCase() {
		for (int idx = 0; idx < xmesoParts.length; idx++) {
			xmesoParts[idx] = new XmesoPart();
			xmesoParts[idx].setPartLabel(idx + "");
		}
	}
	
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getPatientNumber() {
		return patientNumber;
	}

	public void setPatientNumber(String patientNumber) {
		this.patientNumber = patientNumber;
	}

	public String getReportid() {
		return reportid;
	}

	public void setReportid(String reportid) {
		this.reportid = reportid;
	}

	public String getReportFk() {
		return reportFk;
	}

	public void setReportFk(String reportFk) {
		this.reportFk = reportFk;
	}

	public String getReportNumber() {
		return reportNumber;
	}

	public void setReportNumber(String reportNumber) {
		this.reportNumber = reportNumber;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getLargestNodule() {
		return largestNodule;
	}

	public void setLargestNodule(String largestNodule) {
		this.largestNodule = largestNodule;
	}

	public String getLymphNodesExamined() {
		return lymphNodesExamined;
	}

	public void setLymphNodesExamined(String lymphNodesExamined) {
		this.lymphNodesExamined = lymphNodesExamined;
	}

	public String getPositiveLymphNodes() {
		return positiveLymphNodes;
	}

	public void setPositiveLymphNodes(String positiveLymphNodes) {
		this.positiveLymphNodes = positiveLymphNodes;
	}

	public String getSpecialStainProfile() {
		return specialStainProfile;
	}

	public void setSpecialStainProfile(String specialStainProfile) {
		this.specialStainProfile = specialStainProfile;
	}

	public String getStatusOfVascularInvasionByTumor() {
		return statusOfVascularInvasionByTumor;
	}

	public void setStatusOfVascularInvasionByTumor(
			String statusOfVascularInvasionByTumor) {
		this.statusOfVascularInvasionByTumor = statusOfVascularInvasionByTumor;
	}

	public String getUltrastructuralFindings() {
		return ultrastructuralFindings;
	}

	public void setUltrastructuralFindings(String ultrastructuralFindings) {
		this.ultrastructuralFindings = ultrastructuralFindings;
	}

	public String getImmunohistochemicalProfile() {
		return immunohistochemicalProfile;
	}

	public void setImmunohistochemicalProfile(String immunohistochemicalProfile) {
		this.immunohistochemicalProfile = immunohistochemicalProfile;
	}

	public String getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(String procedureType) {
		this.procedureType = procedureType;
	}

	public String getNotRequired() {
		return notRequired;
	}

	public void setNotRequired(String notRequired) {
		this.notRequired = notRequired;
	}
	
	public XmesoPart[] getXmesoParts() {
		return xmesoParts;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Report " + getReportid() + "\n");
		int partIdx = 0;
		for (XmesoPart xmesoPart : getXmesoParts()) {
			if (xmesoPart.getSiteOfTumor() != null && xmesoPart.getSiteOfTumor().length() > 0) {
				sb.append("\tPart " + partIdx + ") " + xmesoPart.getSiteOfTumor() + "\n");
			}
			partIdx++;
		}
		return sb.toString();
	}
}
