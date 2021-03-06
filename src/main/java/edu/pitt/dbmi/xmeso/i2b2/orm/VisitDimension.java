package edu.pitt.dbmi.xmeso.i2b2.orm;

// Generated Apr 15, 2015 4:30:25 PM by Hibernate Tools hbm2java 4.3.1
//Modified Aug 10, 2016 by Zhou Yuan

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "XMESO_VISIT_DIMENSION")
public class VisitDimension implements Serializable {

	private static final long serialVersionUID = 1L;

	private VisitDimensionId id;
	private String activeStatusCd;
	private Date startDate;
	private Date endDate;
	private String inoutCd;
	private String locationCd;
	private String locationPath;
	private BigDecimal lengthOfStay;
	private String visitBlob;
	private Date updateDate;
	private Date downloadDate;
	private Date importDate;
	private String sourcesystemCd;
	private BigDecimal uploadId;

	public VisitDimension() {
	}

	public VisitDimension(VisitDimensionId id) {
		this.id = id;
	}

	public VisitDimension(
			VisitDimensionId id, 
			String activeStatusCd,
			Date startDate, 
			Date endDate, 
			String inoutCd, 
			String locationCd,
			String locationPath, 
			BigDecimal lengthOfStay, 
			String visitBlob,
			Date updateDate, 
			Date downloadDate, 
			Date importDate,
			String sourcesystemCd, 
			BigDecimal uploadId) {
		this.id = id;
		this.activeStatusCd = activeStatusCd;
		this.startDate = startDate;
		this.endDate = endDate;
		this.inoutCd = inoutCd;
		this.locationCd = locationCd;
		this.locationPath = locationPath;
		this.lengthOfStay = lengthOfStay;
		this.visitBlob = visitBlob;
		this.updateDate = updateDate;
		this.downloadDate = downloadDate;
		this.importDate = importDate;
		this.sourcesystemCd = sourcesystemCd;
		this.uploadId = uploadId;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "encounterNum", column = @Column(name = "ENCOUNTER_NUM", nullable = false, precision = 38, scale = 0)),
			@AttributeOverride(name = "patientNum", column = @Column(name = "PATIENT_NUM", nullable = false, precision = 38, scale = 0)) })
	public VisitDimensionId getId() {
		return this.id;
	}

	public void setId(VisitDimensionId id) {
		this.id = id;
	}

	@Column(name = "ACTIVE_STATUS_CD", length = 50)
	public String getActiveStatusCd() {
		return this.activeStatusCd;
	}

	public void setActiveStatusCd(String activeStatusCd) {
		this.activeStatusCd = activeStatusCd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "INOUT_CD", length = 50)
	public String getInoutCd() {
		return this.inoutCd;
	}

	public void setInoutCd(String inoutCd) {
		this.inoutCd = inoutCd;
	}

	@Column(name = "LOCATION_CD", length = 50)
	public String getLocationCd() {
		return this.locationCd;
	}

	public void setLocationCd(String locationCd) {
		this.locationCd = locationCd;
	}

	@Column(name = "LOCATION_PATH", length = 900)
	public String getLocationPath() {
		return this.locationPath;
	}

	public void setLocationPath(String locationPath) {
		this.locationPath = locationPath;
	}

	@Column(name = "LENGTH_OF_STAY", precision = 38, scale = 0)
	public BigDecimal getLengthOfStay() {
		return this.lengthOfStay;
	}

	public void setLengthOfStay(BigDecimal lengthOfStay) {
		this.lengthOfStay = lengthOfStay;
	}

	@Column(name = "VISIT_BLOB")
	@Type(type = "org.hibernate.type.MaterializedClobType")
	public String getVisitBlob() {
		return this.visitBlob;
	}

	public void setVisitBlob(String visitBlob) {
		this.visitBlob = visitBlob;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_DATE", length = 7)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DOWNLOAD_DATE", length = 7)
	public Date getDownloadDate() {
		return this.downloadDate;
	}

	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "IMPORT_DATE", length = 7)
	public Date getImportDate() {
		return this.importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	@Column(name = "SOURCESYSTEM_CD", length = 50)
	public String getSourcesystemCd() {
		return this.sourcesystemCd;
	}

	public void setSourcesystemCd(String sourcesystemCd) {
		this.sourcesystemCd = sourcesystemCd;
	}

	@Column(name = "UPLOAD_ID", precision = 38, scale = 0)
	public BigDecimal getUploadId() {
		return this.uploadId;
	}

	public void setUploadId(BigDecimal uploadId) {
		this.uploadId = uploadId;
	}

}
