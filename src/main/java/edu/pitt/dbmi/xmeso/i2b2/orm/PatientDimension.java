package edu.pitt.dbmi.xmeso.i2b2.orm;

// Generated Apr 15, 2015 4:30:25 PM by Hibernate Tools hbm2java 4.3.1
//Modified Aug 10, 2016 by Zhou Yuan

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "XMESO_PATIENT_DIMENSION")
public class PatientDimension implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal patientNum;
	private String vitalStatusCd;
	private Date birthDate;
	private Date deathDate;
	private String sexCd;
	private BigDecimal ageInYearsNum;
	private String languageCd;
	private String raceCd;
	private String maritalStatusCd;
	private String religionCd;
	private String zipCd;
	private String statecityzipPath;
	private String incomeCd;
	private String patientBlob;
	private Date updateDate;
	private Date downloadDate;
	private Date importDate;
	private String sourcesystemCd;
	private BigDecimal uploadId;

	public PatientDimension() {
	}

	public PatientDimension(BigDecimal patientNum) {
		this.patientNum = patientNum;
	}

	public PatientDimension(
			BigDecimal patientNum, 
			String vitalStatusCd,
			Date birthDate, 
			Date deathDate, 
			String sexCd,
			BigDecimal ageInYearsNum, 
			String languageCd, 
			String raceCd,
			String maritalStatusCd, 
			String religionCd, 
			String zipCd,
			String statecityzipPath, 
			String incomeCd, 
			String patientBlob,
			Date updateDate, 
			Date downloadDate, 
			Date importDate,
			String sourcesystemCd, 
			BigDecimal uploadId) {
		this.patientNum = patientNum;
		this.vitalStatusCd = vitalStatusCd;
		this.birthDate = birthDate;
		this.deathDate = deathDate;
		this.sexCd = sexCd;
		this.ageInYearsNum = ageInYearsNum;
		this.languageCd = languageCd;
		this.raceCd = raceCd;
		this.maritalStatusCd = maritalStatusCd;
		this.religionCd = religionCd;
		this.zipCd = zipCd;
		this.statecityzipPath = statecityzipPath;
		this.incomeCd = incomeCd;
		this.patientBlob = patientBlob;
		this.updateDate = updateDate;
		this.downloadDate = downloadDate;
		this.importDate = importDate;
		this.sourcesystemCd = sourcesystemCd;
		this.uploadId = uploadId;
	}

	@Id
	@Column(name = "PATIENT_NUM", unique = true, nullable = false, precision = 38, scale = 0)
	public BigDecimal getPatientNum() {
		return this.patientNum;
	}

	public void setPatientNum(BigDecimal patientNum) {
		this.patientNum = patientNum;
	}

	@Column(name = "VITAL_STATUS_CD", length = 50)
	public String getVitalStatusCd() {
		return this.vitalStatusCd;
	}

	public void setVitalStatusCd(String vitalStatusCd) {
		this.vitalStatusCd = vitalStatusCd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTH_DATE", length = 7)
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DEATH_DATE", length = 7)
	public Date getDeathDate() {
		return this.deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	@Column(name = "SEX_CD", length = 50)
	public String getSexCd() {
		return this.sexCd;
	}

	public void setSexCd(String sexCd) {
		this.sexCd = sexCd;
	}

	@Column(name = "AGE_IN_YEARS_NUM", precision = 38, scale = 0)
	public BigDecimal getAgeInYearsNum() {
		return this.ageInYearsNum;
	}

	public void setAgeInYearsNum(BigDecimal ageInYearsNum) {
		this.ageInYearsNum = ageInYearsNum;
	}

	@Column(name = "LANGUAGE_CD", length = 50)
	public String getLanguageCd() {
		return this.languageCd;
	}

	public void setLanguageCd(String languageCd) {
		this.languageCd = languageCd;
	}

	@Column(name = "RACE_CD", length = 50)
	public String getRaceCd() {
		return this.raceCd;
	}

	public void setRaceCd(String raceCd) {
		this.raceCd = raceCd;
	}

	@Column(name = "MARITAL_STATUS_CD", length = 50)
	public String getMaritalStatusCd() {
		return this.maritalStatusCd;
	}

	public void setMaritalStatusCd(String maritalStatusCd) {
		this.maritalStatusCd = maritalStatusCd;
	}

	@Column(name = "RELIGION_CD", length = 50)
	public String getReligionCd() {
		return this.religionCd;
	}

	public void setReligionCd(String religionCd) {
		this.religionCd = religionCd;
	}

	@Column(name = "ZIP_CD", length = 10)
	public String getZipCd() {
		return this.zipCd;
	}

	public void setZipCd(String zipCd) {
		this.zipCd = zipCd;
	}

	@Column(name = "STATECITYZIP_PATH", length = 700)
	public String getStatecityzipPath() {
		return this.statecityzipPath;
	}

	public void setStatecityzipPath(String statecityzipPath) {
		this.statecityzipPath = statecityzipPath;
	}

	@Column(name = "INCOME_CD", length = 50)
	public String getIncomeCd() {
		return this.incomeCd;
	}

	public void setIncomeCd(String incomeCd) {
		this.incomeCd = incomeCd;
	}

	@Column(name = "PATIENT_BLOB")
	@Type(type = "org.hibernate.type.MaterializedClobType")
	public String getPatientBlob() {
		return this.patientBlob;
	}

	public void setPatientBlob(String patientBlob) {
		this.patientBlob = patientBlob;
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
