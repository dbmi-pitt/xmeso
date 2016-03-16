package edu.pitt.dbmi.xmeso.ctakes.ae;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ConceptDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.I2b2DemoDataSourceManager;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ObservationFact;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ObservationFactId;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.PatientDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.VisitDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.VisitDimensionId;
import edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoEncounterForm;
import edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm;

public class I2B2DataDataWriter extends
org.apache.uima.fit.component.JCasAnnotator_ImplBase  {

	private Date timeNow = new Date();
	private I2b2DemoDataSourceManager dataSourceMgr;

	private int uploadId = 0;

	private String sourceSystemCd = "Xmeso";
	
	private String surgicalProcedureCode = "Xmeso:NS";
	private String histologicTypeCode = "Xmeso:NS";
	
	private int patientNum = 3000000;
	private int visitNum = 4000000;

	public static void main(String[] args) {
		
		try {
			I2b2DemoDataSourceManager i2b2DataDataSourceManager = new I2b2DemoDataSourceManager();
			I2B2DataDataWriter i2b2DataDataWriter = new I2B2DataDataWriter();
			i2b2DataDataWriter.setDataSourceMgr(i2b2DataDataSourceManager);
			i2b2DataDataWriter.setSourceSystemCd("Xmeso");
			i2b2DataDataWriter.execute();
			i2b2DataDataSourceManager.destroy();
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		PatientDimension patientDimension = fetchOrCreatePatient();
		VisitDimension visitDimension = fetchOrCreateVisit();
		
		for (XmesoEncounterForm encounterForm : JCasUtil.select(aJCas,
				XmesoEncounterForm.class)) {
			System.out.println("Found encounter form: "
					+ encounterForm.getCoveredText());
			surgicalProcedureCode = encounterForm
					.getSurgicalProcedureCode();
			histologicTypeCode = encounterForm.getHistologicTypeCode();
			System.out.println("surgicalProcedureCode = "
					+ surgicalProcedureCode);
			System.out.println("histologicTypeCode = " + histologicTypeCode);
			break;
		}
		
		for (XmesoTumorForm tumorForm : JCasUtil.select(aJCas,
				XmesoTumorForm.class)) {
			String tumorSiteCode = tumorForm.getTumorSiteCode();
			String tumorConfigurationCode = tumorForm
					.getTumorConfigurationCode();
			String tumorDifferentiationCode = tumorForm
					.getTumorDifferentiationCode();
			System.out.println("tumorSiteCode = " + tumorSiteCode);
			System.out.println("tumorConfigurationCode = "
					+ tumorConfigurationCode);
			System.out.println("tumorDifferentiationCode = "
					+ tumorDifferentiationCode);
			
			ConceptDimension conceptDimension = fetchOrCreateConcept(surgicalProcedureCode);
			writeObservation(patientDimension.getPatientNum().intValue(), 
					visitDimension.getId().getEncounterNum().intValue(),
					surgicalProcedureCode);
			
		    conceptDimension = fetchOrCreateConcept(histologicTypeCode);
			writeObservation(patientDimension.getPatientNum().intValue(), 
					visitDimension.getId().getEncounterNum().intValue(),
					histologicTypeCode);
			
			conceptDimension = fetchOrCreateConcept(tumorSiteCode);
			writeObservation(patientDimension.getPatientNum().intValue(), 
					visitDimension.getId().getEncounterNum().intValue(),
					tumorSiteCode);
			
			conceptDimension = fetchOrCreateConcept(tumorConfigurationCode);
			writeObservation(patientDimension.getPatientNum().intValue(), 
					visitDimension.getId().getEncounterNum().intValue(),
					conceptDimension.getConceptCd());
			
			conceptDimension = fetchOrCreateConcept(tumorDifferentiationCode);
			writeObservation(patientDimension.getPatientNum().intValue(), 
					visitDimension.getId().getEncounterNum().intValue(),
					conceptDimension.getConceptCd());
			
			
			
		}	
		
		patientNum++;
		visitNum++;
	}

	/****
	 * 
	 *
	 * Patient
	 *
	 */
	public void clearExistingPatientObservations() throws SQLException {
		String sql = "delete from OBSERVATION_FACT where PATIENT_NUM = :patientNum AND SOURCESYSTEM_CD = :sourceSystemCd";
		SQLQuery sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setInteger("patientNum", patientNum);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		sqlUpdate.executeUpdate();
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
		visitDimension.setEndDate(timeNow);
		visitDimension.setImportDate(timeNow);
		visitDimension.setInoutCd("in");
		visitDimension.setLengthOfStay(new BigDecimal(4.0d));
		visitDimension.setLocationCd("Pennsylvania");
		visitDimension.setLocationPath("Pittsburgh/Pennsylvania");
		visitDimension.setSourcesystemCd(getSourceSystemCd());
		visitDimension.setStartDate(timeNow);
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

	public void execute() throws ClassNotFoundException, SQLException {
		cleanOldRecords();
//		writePatients();
//		writeEncounters();
//		writeConcepts();
//		writeObservations();
	}

	public void cleanOldRecords() throws SQLException {

		dataSourceMgr.getSession().clear();

		String sql = "delete from OBSERVATION_FACT where SOURCESYSTEM_CD = :sourceSystemCd";
		SQLQuery sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		sqlUpdate.executeUpdate();

		sql = "delete from CONCEPT_DIMENSION where SOURCESYSTEM_CD = :sourceSystemCd";
		sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		sqlUpdate.executeUpdate();

		sql = "delete from VISIT_DIMENSION where SOURCESYSTEM_CD = :sourceSystemCd";
		sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		sqlUpdate.executeUpdate();

		sql = "delete from PATIENT_DIMENSION where SOURCESYSTEM_CD = :sourceSystemCd";
		sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		sqlUpdate.executeUpdate();
	}

//	@SuppressWarnings("unused")
//	private void writeEncounters() {
//		for (KbPatient kbPatient : patients) {
//			for (KbEncounter kbEncounter : kbPatient.getEncounters()) {
//				Date timeNow = new Date();
//				VisitDimension visitDimension = new VisitDimension();
//				VisitDimensionId visitId = new VisitDimensionId();
//				visitId.setPatientNum(new BigDecimal(patientNum()));
//				visitId.setEncounterNum(new BigDecimal(kbEncounter.getId()));
//				visitDimension.setId(visitId);
//				visitDimension.setActiveStatusCd("Active");
//				visitDimension.setDownloadDate(timeNow);
//				visitDimension.setEndDate(timeNow);
//				visitDimension.setImportDate(timeNow);
//				visitDimension.setInoutCd("in");
//				visitDimension.setLengthOfStay(new BigDecimal(4.0d));
//				visitDimension.setLocationCd("Pennsylvania");
//				visitDimension.setLocationPath("Pittsburgh/Pennsylvania");
//				visitDimension.setSourcesystemCd(getSourceSystemCd());
//				visitDimension.setStartDate(timeNow);
//				visitDimension.setUpdateDate(timeNow);
//				visitDimension.setVisitBlob(null);
//				visitDimension.setUploadId(new BigDecimal(99));
//				dataSourceMgr.getSession().saveOrUpdate(visitDimension);
//			}
//		}
//
//		Transaction tx = dataSourceMgr.getSession().beginTransaction();
//		dataSourceMgr.getSession().flush();
//		tx.commit();
//	}

//	@SuppressWarnings("unused")
//	private void writeConcepts() throws SQLException {
//		activeSummaries.clear();
//		for (KbPatient kbPatient : patients) {
//			for (KbSummary kbSummary : kbPatient.getSummaries()) {
//				if (kbSummary.getIsActive() == 1) {
//					activeSummaries.add(kbSummary);
//				}
//			}
//			for (KbEncounter kbEncounter : kbPatient.getEncounters()) {
//				for (KbSummary kbSummary : kbEncounter.getSummaries()) {
//					if (kbSummary.getIsActive() == 1) {
//						activeSummaries.add(kbSummary);
//					}
//				}
//			}
//		}
//		for (KbSummary kbSummary : activeSummaries) {
//			ConceptDimension conceptDimension = new ConceptDimension();
//			conceptDimension.setConceptPath(kbSummary.getPath() + "\\");
//			conceptDimension.setConceptCd(kbSummary.getBaseCode());
//			conceptDimension.setNameChar(kbSummary.getNameChar());
//			conceptDimension.setConceptBlob(null);
//			conceptDimension.setUpdateDate(timeNow);
//			conceptDimension.setDownloadDate(timeNow);
//			conceptDimension.setImportDate(timeNow);
//			conceptDimension.setSourcesystemCd(getSourceSystemCd());
//			conceptDimension.setUploadId(new BigDecimal(uploadId++));
//			dataSourceMgr.getSession().saveOrUpdate(conceptDimension);
//		}
//		Transaction tx = dataSourceMgr.getSession().beginTransaction();
//		dataSourceMgr.getSession().flush();
//		tx.commit();
//	}

//	@SuppressWarnings("unused")
//	private void writeObservations() throws SQLException {
//		for (KbPatient kbPatient : patients) {
//			for (KbSummary kbSummary : kbPatient.getSummaries()) {
//				if (kbSummary.getIsActive() == 1) {
//					writeObservation(kbPatient, null, kbSummary);
//				}
//			}
//			for (KbEncounter kbEncounter : kbPatient.getEncounters()) {
//				for (KbSummary kbSummary : kbEncounter.getSummaries()) {
//					if (kbSummary.getIsActive() == 1) {
//						writeObservation(kbPatient, kbEncounter, kbSummary);
//					}
//				}
//			}
//		}
//
//		Transaction tx = dataSourceMgr.getSession().beginTransaction();
//		dataSourceMgr.getSession().flush();
//		tx.commit();
//	}
	
	private ObservationFact fetchObservationFact(ObservationFactId observationFactId) {
		Query q = dataSourceMgr
				.getSession()
				.createQuery(
						"from ObservationFact as o where o.id=:id and o.sourcesystemCd=:sourcesystemCd");
		q.setParameter("id", observationFactId);
		q.setParameter("sourcesystemCd", getSourceSystemCd());
		ObservationFact result = (ObservationFact) q.uniqueResult();
		return result;
	}

	private void writeObservation(int patientNum, int visitNum,
			String conceptCd) {

		ObservationFactId observationFactId = new ObservationFactId();
		if (visitNum >= 0) {
			observationFactId.setEncounterNum(new BigDecimal(visitNum));
		} else {
			observationFactId
					.setEncounterNum(new BigDecimal(patientNum));
		}
		
		observationFactId.setPatientNum(new BigDecimal(patientNum));
		observationFactId.setConceptCd(conceptCd);
		observationFactId.setProviderId(getSourceSystemCd());
		observationFactId.setInstanceNum(1L);
		observationFactId.setModifierCd("@");
		observationFactId.setStartDate(timeNow);

		ObservationFact observationFact = fetchObservationFact(observationFactId);
		 
		if (observationFact == null) {
			
			System.out.println("\n\n\nWriting obs (" + observationFactId + ")\n\n");

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

			dataSourceMgr.getSession().saveOrUpdate(observationFact);
		}

	}

	public void setPatientNum(int patientNum) {
	}

	public String getSourceSystemCd() {
		return sourceSystemCd;
	}

	public void setSourceSystemCd(String sourceSystemCd) {
		this.sourceSystemCd = sourceSystemCd;
	}

	public I2b2DemoDataSourceManager getDataSourceMgr() {
		return dataSourceMgr;
	}

	public void setDataSourceMgr(I2b2DemoDataSourceManager dataSourceMgr) {
		this.dataSourceMgr = dataSourceMgr;
	}


//	public void writeObservationsForPatient() {
//		fetchOrCreatePatient();
//		for (KbSummary summary : kbPatient.getSummaries()) {
//			kbSummary = summary;
//			fetchOrCreateConcept();
//			writeObservation(kbPatient, null, kbSummary);
//		}
//		for (KbEncounter encounter : kbPatient.getEncounters()) {
//			kbEncounter = encounter;
//			fetchOrCreateVisit();
//			for (KbSummary summary : kbEncounter.getSummaries()) {
//				kbSummary = summary;
//				fetchOrCreateConcept();
//				writeObservation(kbPatient, kbEncounter, kbSummary);
//			}
//		}
//	}

	

}
