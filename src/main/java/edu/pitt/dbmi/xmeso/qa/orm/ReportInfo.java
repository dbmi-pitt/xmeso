package edu.pitt.dbmi.xmeso.qa.orm;

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

	private String reportId;
	private String reportFilename;
	private Date reportDate;

	public ReportInfo() {
	}

	public ReportInfo(
			String reportId, 
			String reportFilename,
			Date reportDate) {
		this.reportId = reportId;
		this.reportFilename = reportFilename;
		this.reportDate = reportDate;
	}

	@Id
	@Column(name = "REPORT_ID", unique = true, nullable = false, length = 150)
	public String getReportId() {
		return this.reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	@Column(name = "REPORT_FILENAME", nullable = false, length = 2100)
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

}
