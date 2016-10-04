package edu.pitt.dbmi.xmeso.i2b2.orm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "XMESO_REPORT_PART_LEVEL")
public class ReportPartLevel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal reportId;
	private long partNum;
	private String siteOfTumor;
	private String histologicalType;
	private String tumorConfiguration;
	private String tumorDifferentiationOrGrade;
	private String sourcesystemCd;

	public ReportPartLevel() {
	}

	public ReportPartLevel(
			BigDecimal reportId, 
			long partNum,
			String siteOfTumor,
			String histologicalType,
			String tumorConfiguration,
			String tumorDifferentiationOrGrade,
			String sourcesystemCd) {
		this.reportId = reportId;
		this.partNum = partNum;
		this.siteOfTumor = siteOfTumor;
		this.histologicalType = histologicalType;
		this.tumorConfiguration = tumorConfiguration;
		this.tumorDifferentiationOrGrade = tumorDifferentiationOrGrade;
		this.sourcesystemCd = sourcesystemCd;
	}

	@Column(name = "REPORT_ID", nullable = false, precision = 38, scale = 0)
	public BigDecimal getReportId() {
		return reportId;
	}

	public void setReportId(BigDecimal reportId) {
		this.reportId = reportId;
	}

	@Column(name = "PART_NUM", nullable = false, precision = 18, scale = 0)
	public long getPartNum() {
		return partNum;
	}

	public void setPartNum(long partNum) {
		this.partNum = partNum;
	}

	@Column(name = "SITE_OF_TUMOR", nullable = false, length = 150)
	public String getSiteOfTumor() {
		return siteOfTumor;
	}

	public void setSiteOfTumor(String siteOfTumor) {
		this.siteOfTumor = siteOfTumor;
	}

	@Column(name = "HISTOLOGICAL_TYPE", nullable = false, length = 150)
	public String getHistologicalType() {
		return histologicalType;
	}

	public void setHistologicalType(String histologicalType) {
		this.histologicalType = histologicalType;
	}

	@Column(name = "TUMOR_CONFIGURATION", nullable = false, length = 150)
	public String getTumorConfiguration() {
		return tumorConfiguration;
	}

	public void setTumorConfiguration(String tumorConfiguration) {
		this.tumorConfiguration = tumorConfiguration;
	}

	@Column(name = "TUMOR_DIFFERENTIATION_OR_GRADE", nullable = false, length = 150)
	public String getTumorDifferentiationOrGrade() {
		return tumorDifferentiationOrGrade;
	}

	public void setTumorDifferentiationOrGrade(String tumorDifferentiationOrGrade) {
		this.tumorDifferentiationOrGrade = tumorDifferentiationOrGrade;
	}

	@Column(name = "SOURCESYSTEM_CD", length = 50)
	public String getSourcesystemCd() {
		return this.sourcesystemCd;
	}

	public void setSourcesystemCd(String sourcesystemCd) {
		this.sourcesystemCd = sourcesystemCd;
	}
	
}
