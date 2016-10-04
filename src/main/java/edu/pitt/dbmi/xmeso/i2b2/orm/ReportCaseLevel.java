package edu.pitt.dbmi.xmeso.i2b2.orm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "XMESO_REPORT_CASE_LEVEL")
public class ReportCaseLevel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal reportId;
	private String lymphNodesExamed;
	private String specialStain;
	private String ultrastructuralFindings;
	private String sourcesystemCd;

	public ReportCaseLevel() {
	}

	public ReportCaseLevel(
			BigDecimal reportId, 
			String lymphNodesExamed,
			String specialStain,
			String ultrastructuralFindings,
			String sourcesystemCd) {
		this.reportId = reportId;
		this.lymphNodesExamed = lymphNodesExamed;
		this.specialStain = specialStain;
		this.ultrastructuralFindings = ultrastructuralFindings;
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

	@Column(name = "LYMPH_NODES_EXAMINED", nullable = false, length = 150)
	public String getLymphNodesExamed() {
		return this.lymphNodesExamed;
	}

	public void setLymphNodesExamed(String lymphNodesExamed) {
		this.lymphNodesExamed = lymphNodesExamed;
	}
	
	@Column(name = "SPECIAL_STAIN", nullable = false, length = 150)
	public String getSpecialStain() {
		return this.specialStain;
	}

	public void setSpecialStain(String specialStain) {
		this.specialStain = specialStain;
	}
	
	@Column(name = "ULTRASTRUCTURAL_FINDINGS", nullable = false, length = 150)
	public String getUltrastructuralFindings() {
		return this.ultrastructuralFindings;
	}

	public void setUltrastructuralFindings(String ultrastructuralFindings) {
		this.ultrastructuralFindings = ultrastructuralFindings;
	}
	
	@Column(name = "SOURCESYSTEM_CD", length = 50)
	public String getSourcesystemCd() {
		return this.sourcesystemCd;
	}

	public void setSourcesystemCd(String sourcesystemCd) {
		this.sourcesystemCd = sourcesystemCd;
	}

}
