package edu.pitt.dbmi.xmeso;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

import edu.pitt.dbmi.xmeso.i2b2.orm.I2b2DataSourceManager;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ConceptDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ObservationFact;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ObservationFactId;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.PatientDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.VisitDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.VisitDimensionId;

public class I2B2DemoDataWriter {
	
	private Date timeNow = new Date();
	private I2b2DataSourceManager dataSourceMgr;

	private int uploadId = 0;

	private String sourceSystemCd = "Xmeso";

	private int patientNum = -1;
	private int visitNum = -1;
	private int instanceNum = -1;
	
	private Date visitDate;

	public void cleanOldRecords() {

		String sql = "delete from XMESO_OBSERVATION_FACT where SOURCESYSTEM_CD = :sourceSystemCd";
		SQLQuery sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		sqlUpdate.executeUpdate();

		sql = "delete from XMESO_CONCEPT_DIMENSION where SOURCESYSTEM_CD = :sourceSystemCd";
		sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		sqlUpdate.executeUpdate();

		sql = "delete from XMESO_VISIT_DIMENSION where SOURCESYSTEM_CD = :sourceSystemCd";
		sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		sqlUpdate.executeUpdate();

		sql = "delete from XMESO_PATIENT_DIMENSION where SOURCESYSTEM_CD = :sourceSystemCd";
		sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		sqlUpdate.executeUpdate();
	}
	
	/****
	 * 
	 *
	 * Patient
	 *
	 */
	public void clearExistingPatientObservations() throws SQLException {
		String sql = "delete from XMESO_OBSERVATION_FACT where PATIENT_NUM = :patientNum AND SOURCESYSTEM_CD = :sourceSystemCd";
		SQLQuery sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setInteger("patientNum", patientNum);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		Transaction tx = dataSourceMgr.getSession().beginTransaction();
		sqlUpdate.executeUpdate();
		tx.commit();
	}

	public PatientDimension fetchOrCreatePatient() {
		PatientDimension existingPatient = fetchPatient();
		if (existingPatient == null) {
			PatientDimension newPatient = newPatient();
			dataSourceMgr.getSession().saveOrUpdate(newPatient);
			Transaction tx = dataSourceMgr.getSession().beginTransaction();
			dataSourceMgr.getSession().flush();
			tx.commit();
			existingPatient = fetchPatient();
		}
		return existingPatient;
	}

	private PatientDimension fetchPatient() {
		PatientDimension patientDimension = new PatientDimension();
		patientDimension.setPatientNum(new BigDecimal(patientNum));
		patientDimension.setSourcesystemCd(getSourceSystemCd());
		Query q = dataSourceMgr
				.getSession()
				.createQuery(
						"from PatientDimension as p where p.patientNum=:patientNum and p.sourcesystemCd=:sourcesystemCd");
		q.setProperties(patientDimension);
		PatientDimension result = (PatientDimension) q.uniqueResult();
		return result;
	}

	private PatientDimension newPatient() {
		PatientDimension patientDimension = new PatientDimension();
		Date timeNow = new Date();
		patientDimension.setPatientNum(new BigDecimal(patientNum));
		patientDimension.setVitalStatusCd((String) null);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(1967, 3, 20);
		patientDimension.setBirthDate(calendar.getTime());
		patientDimension.setDeathDate(null);
		patientDimension.setSexCd("F");
		patientDimension.setAgeInYearsNum(new BigDecimal(78));
		patientDimension.setLanguageCd("Chinese");
		patientDimension.setRaceCd("asian");
		patientDimension.setMaritalStatusCd("single");
		patientDimension.setReligionCd("Agnostic");
		patientDimension.setZipCd("15232");
		patientDimension
				.setStatecityzipPath("Zip codes\\Massachusetts\\Boston\\02115\\");
		patientDimension.setIncomeCd("Medium");
		patientDimension.setPatientBlob(null);
		patientDimension.setUpdateDate(timeNow);
		patientDimension.setDownloadDate(timeNow);
		patientDimension.setImportDate(timeNow);
		patientDimension.setSourcesystemCd(getSourceSystemCd());
		patientDimension.setUploadId(new BigDecimal(patientNum));
		return patientDimension;
	}

	/****
	 * 
	 *
	 * Encounter
	 *
	 */
	public VisitDimension fetchOrCreateVisit() {
		VisitDimension existingVisit = fetchVisit();
		if (existingVisit == null) {
			VisitDimension newVisit = newVisit();
			dataSourceMgr.getSession().saveOrUpdate(newVisit);
			Transaction tx = dataSourceMgr.getSession().beginTransaction();
			dataSourceMgr.getSession().flush();
			tx.commit();
			existingVisit = fetchVisit();
		}
		return existingVisit;
	}

	private VisitDimension fetchVisit() {
		VisitDimension visitDimension = new VisitDimension();
		VisitDimensionId visitDimensionId = new VisitDimensionId();
		visitDimensionId.setPatientNum(new BigDecimal(patientNum));
		visitDimensionId.setEncounterNum(new BigDecimal(visitNum));
		visitDimension.setId(visitDimensionId);
		visitDimension.setSourcesystemCd(getSourceSystemCd());
		Query q = dataSourceMgr
				.getSession()
				.createQuery(
						"from VisitDimension as v where v.id=:id and v.sourcesystemCd=:sourcesystemCd");
		q.setProperties(visitDimension);
		VisitDimension result = (VisitDimension) q.uniqueResult();
		return result;
	}

	private VisitDimension newVisit() {
		Date timeNow = new Date();
		VisitDimension visitDimension = new VisitDimension();
		VisitDimensionId visitId = new VisitDimensionId();
		visitId.setPatientNum(new BigDecimal(patientNum));
		visitId.setEncounterNum(new BigDecimal(visitNum));
		visitDimension.setId(visitId);
		visitDimension.setActiveStatusCd("Active");
		visitDimension.setDownloadDate(timeNow);
		visitDimension.setEndDate(visitDate);
		visitDimension.setImportDate(timeNow);
		visitDimension.setInoutCd("in");
		visitDimension.setLengthOfStay(new BigDecimal(1.0d));
		visitDimension.setLocationCd("Pennsylvania");
		visitDimension.setLocationPath("Pittsburgh/Pennsylvania");
		visitDimension.setSourcesystemCd(getSourceSystemCd());
		visitDimension.setStartDate(visitDate);
		visitDimension.setUpdateDate(timeNow);
		visitDimension.setVisitBlob(null);
		visitDimension.setUploadId(new BigDecimal(99));
		return visitDimension;
	}

	/****
	 * 
	 *
	 * Concept (aka Summary)
	 *
	 */
	public ConceptDimension fetchOrCreateConcept(String code) {
		ConceptDimension existingConcept = fetchConcept(code);
		if (existingConcept == null) {
			ConceptDimension newConcept = newConcept(code);
			dataSourceMgr.getSession().saveOrUpdate(newConcept);
			Transaction tx = dataSourceMgr.getSession().beginTransaction();
			dataSourceMgr.getSession().flush();
			tx.commit();
			existingConcept = fetchConcept(code);
		}

		return existingConcept;
	}

	private ConceptDimension fetchConcept(String code) {
		ConceptDimension conceptDimension = new ConceptDimension();
		conceptDimension.setConceptCd(code);
		conceptDimension.setSourcesystemCd(getSourceSystemCd());
		Query q = dataSourceMgr
				.getSession()
				.createQuery(
						"from ConceptDimension as c where c.conceptCd=:conceptCd and c.sourcesystemCd=:sourcesystemCd");
		q.setProperties(conceptDimension);
		ConceptDimension result = (ConceptDimension) q.uniqueResult();
		return result;
	}

	private ConceptDimension newConcept(String code) {
		ConceptDimension conceptDimension = new ConceptDimension();
		conceptDimension.setConceptPath("\\" + code);
		conceptDimension.setConceptCd(code);
		conceptDimension.setNameChar(code);
		conceptDimension.setConceptBlob(null);
		conceptDimension.setUpdateDate(timeNow);
		conceptDimension.setDownloadDate(timeNow);
		conceptDimension.setImportDate(timeNow);
		conceptDimension.setSourcesystemCd(getSourceSystemCd());
		conceptDimension.setUploadId(new BigDecimal(uploadId++));
		return conceptDimension;
	}

	private ObservationFact fetchObservationFact(
			ObservationFactId observationFactId) {
		Query q = dataSourceMgr
				.getSession()
				.createQuery(
						"from ObservationFact as o where o.id=:id and o.sourcesystemCd=:sourcesystemCd");
		q.setParameter("id", observationFactId);
		q.setParameter("sourcesystemCd", getSourceSystemCd());
		ObservationFact result = (ObservationFact) q.uniqueResult();
		return result;
	}

	public void writeObservation(int patientNum, int visitNum,
			String conceptCd, long instanceNum) {

		ObservationFactId observationFactId = new ObservationFactId();
		if (visitNum >= 0) {
			observationFactId.setEncounterNum(new BigDecimal(visitNum));
		} else {
			observationFactId.setEncounterNum(new BigDecimal(patientNum));
		}

		observationFactId.setPatientNum(new BigDecimal(patientNum));
		observationFactId.setConceptCd(conceptCd);
		observationFactId.setProviderId(getSourceSystemCd());
		observationFactId.setInstanceNum(instanceNum);
		observationFactId.setModifierCd("@_"+uploadId);
		observationFactId.setStartDate(timeNow);

		ObservationFact observationFact = fetchObservationFact(observationFactId);

		if (observationFact == null) {

			System.out.println("Writing obs (" + observationFactId + ")");

			observationFact = new ObservationFact();
			observationFact.setId(observationFactId);
			observationFact.setValtypeCd("@");
			observationFact.setTvalChar("@");
			observationFact.setNvalNum(new BigDecimal(-1));
			observationFact.setValueflagCd("@");
			observationFact.setQuantityNum(new BigDecimal(1));
			observationFact.setUnitsCd("@");
			observationFact.setEndDate(timeNow);
			observationFact.setLocationCd("@");
			observationFact.setObservationBlob(null);
			observationFact.setConfidenceNum(new BigDecimal(1.0));
			observationFact.setUpdateDate(timeNow);
			observationFact.setDownloadDate(timeNow);
			observationFact.setImportDate(timeNow);
			observationFact.setSourcesystemCd(getSourceSystemCd());
			observationFact.setUploadId(new BigDecimal(uploadId++));

			Transaction tx = dataSourceMgr.getSession().beginTransaction();
			dataSourceMgr.getSession().saveOrUpdate(observationFact);
			dataSourceMgr.getSession().flush();
			tx.commit();
		}
	}

	public void setPatientNum(int patientNum) {
	}

	public int getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(int visitNum) {
		this.visitNum = visitNum;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public int getInstanceNum() {
		return instanceNum;
	}

	public void setInstanceNum(int instanceNum) {
		this.instanceNum = instanceNum;
	}

	public String getSourceSystemCd() {
		return sourceSystemCd;
	}

	public void setSourceSystemCd(String sourceSystemCd) {
		this.sourceSystemCd = sourceSystemCd;
	}

	public I2b2DataSourceManager getDataSourceMgr() {
		return dataSourceMgr;
	}

	public void setDataSourceMgr(I2b2DataSourceManager dataSourceMgr) {
		this.dataSourceMgr = dataSourceMgr;
	}

}
