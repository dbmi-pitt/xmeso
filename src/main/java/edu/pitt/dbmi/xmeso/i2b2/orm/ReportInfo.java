package edu.pitt.dbmi.xmeso.i2b2.orm;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "XMESO_REPORT_INFO")
public class ReportInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal reportId;
	private String reportFilename;
	private Date reportDate;
	private String sourcesystemCd;

	public ReportInfo() {
	}

	public ReportInfo(
			BigDecimal reportId, 
			String reportFilename,
			Date reportDate,
			String sourcesystemCd) {
		this.reportId = reportId;
		this.reportFilename = reportFilename;
		this.reportDate = reportDate;
		this.sourcesystemCd = sourcesystemCd;
	}

	@Id
	@Column(name = "REPORT_ID", unique = true, nullable = false, precision = 38, scale = 0)
	public BigDecimal getReportId() {
		return this.reportId;
	}

	public void setReportId(BigDecimal reportId) {
		this.reportId = reportId;
	}

	@Column(name = "REPORT_FILENAME", nullable = false, length = 150)
	public String getReportFilename() {
		return this.reportFilename;
	}

	public void setReportFilename(String reportFilename) {
		this.reportFilename = reportFilename;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REPORT_DATE", length = 7)
	public Date getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	@Column(name = "SOURCESYSTEM_CD", length = 50)
	public String getSourcesystemCd() {
		return this.sourcesystemCd;
	}

	public void setSourcesystemCd(String sourcesystemCd) {
		this.sourcesystemCd = sourcesystemCd;
	}
	
}
