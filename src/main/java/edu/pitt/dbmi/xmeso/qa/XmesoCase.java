package edu.pitt.dbmi.xmeso.qa;

public class XmesoCase {
	
	private static final int CONST_MAX_PARTS = 8;
	
	private String project = "Unknown";
	private String patient = "Unknown";
	private String patientNumber = "Unknown";
	private String reportId = "Unknown";
	private String reportFk = "Unknown";
	private String reportNumber = "Unknown";
	private String reportDate = "Unknown";
	private String largestNodule = "Unknown";
	private String positiveLymphNodes = "Unknown";
	private String statusOfVascularInvasionByTumor = "Unknown";
	private String immunohistochemicalProfile = "Unknown";
	private String notRequired = "Unknown";
	
	private String procedureType = "Unknown";
	private String ultrastructuralFindings = "Unknown";
	private String lymphNodesExamined = "Unknown";
	private String specialStainProfile = "Unknown";
	
	private XmesoPart[] xmesoParts = new XmesoPart[CONST_MAX_PARTS];
	
	public XmesoCase() {
		for (int idx = 0; idx < xmesoParts.length; idx++) {
			xmesoParts[idx] = new XmesoPart();
			xmesoParts[idx].setPartLabel(idx + "");
		}
	}
	
	public XmesoCase(XmesoCase o) {
		setProject(o.getProject());
		setPatient(o.getPatient());
		setPatientNumber(o.getPatientNumber());
		setReportId(o.getReportId());
		setReportFk(o.getReportFk());
		setReportNumber(o.getReportNumber());
		setReportDate(o.getReportDate());
		setLargestNodule(o.getLargestNodule());		
		setPositiveLymphNodes(o.getPositiveLymphNodes());
		setSpecialStainProfile(o.getSpecialStainProfile());
		setStatusOfVascularInvasionByTumor(o.getStatusOfVascularInvasionByTumor());
		setImmunohistochemicalProfile(o.getImmunohistochemicalProfile());	
		setNotRequired(o.getNotRequired());
		
		setProcedureType(o.getProcedureType());
		setUltrastructuralFindings(o.getUltrastructuralFindings());
		setLymphNodesExamined(o.getLymphNodesExamined());
		setSpecialStainProfile(o.getSpecialStainProfile());
		
		for (int partIdx = 0; partIdx < o.getXmesoParts().length; partIdx++) {
			XmesoPart myXmesoPart = new XmesoPart(o.getXmesoParts()[partIdx]);
			xmesoParts[partIdx] = myXmesoPart;
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

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
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
		sb.append("Project = " + getProject()+ "\n");
		sb.append("Patient = " + getPatient()+ "\n");
		sb.append("Patient Number = " + getPatientNumber()+ "\n");
		sb.append("ReportID = " + getReportId() + "\n");
		sb.append("Report_fk = " + getReportFk() + "\n");
		sb.append("Report Number = " + getReportNumber() + "\n");
		sb.append("Report Date = " + getReportDate() + "\n");
//		sb.append("Largest Nodule = " + getLargestNodule() + "\n");
//		sb.append("Positive Lymph Nodes = " + getPositiveLymphNodes() + "\n");	
//		sb.append("Status of vascular invasion by tumor = " + getStatusOfVascularInvasionByTumor() + "\n");
//		sb.append("Immunohistochemical Profile = " + getImmunohistochemicalProfile() + "\n");
	
		sb.append("Procedure Type = " + getProcedureType() + "\n");
		sb.append("Ultrastructural Findings = " + getUltrastructuralFindings() + "\n");
		sb.append("Lymph Nodes Examined = " + getLymphNodesExamined() + "\n");
		sb.append("Special Stain Profile = " + getSpecialStainProfile() + "\n");
		
		int partIdx = 0;
		for (XmesoPart xmesoPart : getXmesoParts()) {
			if (xmesoPart.getSiteOfTumor() != null && xmesoPart.getSiteOfTumor().length() > 0) {
				sb.append("\tPart " + partIdx + ")\n" + xmesoPart.toString() + "\n");
			}
			partIdx++;
		}
		return sb.toString();
	}

	
}
