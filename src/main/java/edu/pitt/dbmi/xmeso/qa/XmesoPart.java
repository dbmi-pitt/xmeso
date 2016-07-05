package edu.pitt.dbmi.xmeso.qa;

public class XmesoPart {

	private String partLabel = "Unknown";
	private String siteOfTumor = "Unknown";
	private String histologicalType = "Unknown";
	private String tumorType = "Unknown";
	private String invasiveTumor = "Unknown";
	private String tumorConfiguration = "Unknown";
	private String tumorDifferentiationOrGrade = "Unknown";
	private String surgicalMargins = "Unknown";
	private String tumorSizeMaxDimensionInCm = "Unknown";
	private String tumorExtension = "Unknown";
	
	public XmesoPart() {
	}

	public XmesoPart(XmesoPart o) {
		setPartLabel(o.getPartLabel());
		setSiteOfTumor(o.getSiteOfTumor());
		setHistologicalType(o.getHistologicalType());
		setTumorType(o.getTumorType());
		setInvasiveTumor(o.getInvasiveTumor());
		setTumorConfiguration(o.getTumorConfiguration());
		setTumorDifferentiationOrGrade(o.getTumorDifferentiationOrGrade());
		setSurgicalMargins(o.getSurgicalMargins());
		setTumorSizeMaxDimensionInCm(o.getTumorSizeMaxDimensionInCm());
		setTumorExtension(o.getTumorExtension());
	}

	public String getPartLabel() {
		return partLabel;
	}

	public void setPartLabel(String partLabel) {
		this.partLabel = partLabel;
	}

	public String getSiteOfTumor() {
		return siteOfTumor;
	}

	public void setSiteOfTumor(String siteOfTumor) {
		this.siteOfTumor = siteOfTumor;
	}

	public String getHistologicalType() {
		return histologicalType;
	}

	public void setHistologicalType(String histologicalType) {
		this.histologicalType = histologicalType;
	}

	public String getTumorType() {
		return tumorType;
	}

	public void setTumorType(String tumorType) {
		this.tumorType = tumorType;
	}

	public String getInvasiveTumor() {
		return invasiveTumor;
	}

	public void setInvasiveTumor(String invasiveTumor) {
		this.invasiveTumor = invasiveTumor;
	}

	public String getTumorConfiguration() {
		return tumorConfiguration;
	}

	public void setTumorConfiguration(String tumorConfiguration) {
		this.tumorConfiguration = tumorConfiguration;
	}

	public String getTumorDifferentiationOrGrade() {
		return tumorDifferentiationOrGrade;
	}

	public void setTumorDifferentiationOrGrade(
			String tumorDifferentiationOrGrade) {
		this.tumorDifferentiationOrGrade = tumorDifferentiationOrGrade;
	}

	public String getSurgicalMargins() {
		return surgicalMargins;
	}

	public void setSurgicalMargins(String surgicalMargins) {
		this.surgicalMargins = surgicalMargins;
	}

	public String getTumorSizeMaxDimensionInCm() {
		return tumorSizeMaxDimensionInCm;
	}

	public void setTumorSizeMaxDimensionInCm(String tumorSizeMaxDimensionInCm) {
		this.tumorSizeMaxDimensionInCm = tumorSizeMaxDimensionInCm;
	}

	public String getTumorExtension() {
		return tumorExtension;
	}

	public void setTumorExtension(String tumorExtension) {
		this.tumorExtension = tumorExtension;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\t\tPart Label = " + getPartLabel()+ "\n");
		sb.append("\t\tSite of Tumor = " + getSiteOfTumor()+ "\n");
		sb.append("\t\tHistological Type = " + getHistologicalType()+ "\n");
		sb.append("\t\tTumor Type = " + getTumorType()+ "\n");
		sb.append("\t\tInvasive Tumor = " + getInvasiveTumor()+ "\n");
		sb.append("\t\tTumor Configuration = " + getTumorConfiguration()+ "\n");
		sb.append("\t\tTumor Differentiation or Grade = " + getTumorDifferentiationOrGrade() + "\n");
		sb.append("\t\tSurgical Margins = " + getSurgicalMargins()+ "\n");
		sb.append("\t\tTumor size Max Dimension in cm = " + getTumorSizeMaxDimensionInCm()+ "\n");
		sb.append("\t\tTumor Extension = " + getTumorExtension()+ "\n");
		return sb.toString();
	}
	
}
