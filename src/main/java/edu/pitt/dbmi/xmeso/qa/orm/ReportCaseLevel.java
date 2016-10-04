package edu.pitt.dbmi.xmeso.qa.orm;

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
	private String specialStains;
	private String ultrastructuralFindings;

	public ReportCaseLevel() {
	}

	public ReportCaseLevel(
			BigDecimal reportId, 
			String lymphNodesExamed,
			String specialStains,
			String ultrastructuralFindings) {
		this.reportId = reportId;
		this.lymphNodesExamed = lymphNodesExamed;
		this.specialStains = specialStains;
		this.ultrastructuralFindings = ultrastructuralFindings;
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
	
	@Column(name = "SPECIAL_STAINS", nullable = false, length = 150)
	public String getSpecialStains() {
		return this.specialStains;
	}

	public void setSpecialStains(String specialStains) {
		this.specialStains = specialStains;
	}
	
	@Column(name = "ULTRASTRUCTURAL_FINDINGS", nullable = false, length = 150)
	public String getUltrastructuralFindings() {
		return this.ultrastructuralFindings;
	}

	public void setUltrastructuralFindings(String ultrastructuralFindings) {
		this.ultrastructuralFindings = ultrastructuralFindings;
	}

}
