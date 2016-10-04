package edu.pitt.dbmi.xmeso.i2b2.orm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XMESO_GIANT")
public class Giant implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal reportId;
	private BigDecimal mapId;
	private String answer;
	private String sourcesystemCd;

	public Giant() {
	}

	public Giant(
			BigDecimal reportId, 
			BigDecimal mapId,
			String answer,
			String sourcesystemCd) {
		this.reportId = reportId;
		this.mapId = mapId;
		this.answer = answer;
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

	@Id
	@Column(name = "MAP_ID", unique = true, nullable = false, precision = 38, scale = 0)
	public BigDecimal getMapId() {
		return mapId;
	}

	public void setMapId(BigDecimal mapId) {
		this.mapId = mapId;
	}

	@Column(name = "ANSWER", nullable = false, length = 150)
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name = "SOURCESYSTEM_CD", length = 50)
	public String getSourcesystemCd() {
		return this.sourcesystemCd;
	}

	public void setSourcesystemCd(String sourcesystemCd) {
		this.sourcesystemCd = sourcesystemCd;
	}
	
}
