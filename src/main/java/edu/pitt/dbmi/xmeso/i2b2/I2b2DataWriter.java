package edu.pitt.dbmi.xmeso.i2b2;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

import edu.pitt.dbmi.xmeso.i2b2.I2b2DataSourceManager;
import edu.pitt.dbmi.xmeso.i2b2.orm.ConceptDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.ObservationFact;
import edu.pitt.dbmi.xmeso.i2b2.orm.ObservationFactId;
import edu.pitt.dbmi.xmeso.i2b2.orm.PatientDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.ProviderDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.ProviderDimensionId;
import edu.pitt.dbmi.xmeso.i2b2.orm.VisitDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.VisitDimensionId;
import edu.pitt.dbmi.xmeso.i2b2.orm.ReportCaseLevel;
import edu.pitt.dbmi.xmeso.i2b2.orm.ReportInfo;
import edu.pitt.dbmi.xmeso.i2b2.orm.ReportPartLevel;

public class I2b2DataWriter {
	
	private Date timeNow = new Date();
	private I2b2DataSourceManager dataSourceManager;
	private String sourcesystemCd;
	private String providerId;

	/**
	 * Pass the `sourcesystem_cd` value and dataSourceManagre instance
	 * 
	 * @param dataSourceManager
	 * @param sourcesystemCd
	 */
    public I2b2DataWriter(I2b2DataSourceManager dataSourceManager, String sourcesystemCd, String providerId) {
		super();

		this.dataSourceManager = dataSourceManager;
		this.sourcesystemCd = sourcesystemCd;
		this.providerId = providerId;
	}

	/**
     * Erase old Xmeso records before inserting new one
     * We don't touch the XMESO_PATIENT_DIMENSION table, 
     * since it should have already been filled with patient records.
     * Same for XMESO_CONCEPT_DIMENSION table
     */
    public void cleanOldRecordsIfExist() {
    	eraseOldRecordsIfExist("XMESO_PROVIDER_DIMENSION");
    	eraseOldRecordsIfExist("XMESO_VISIT_DIMENSION");
    	eraseOldRecordsIfExist("XMESO_OBSERVATION_FACT");
    	
    	// QA tables
    	eraseOldRecordsIfExist("XMESO_REPORT_INFO");
    	eraseOldRecordsIfExist("XMESO_REPORT_CASE_LEVEL");
    	eraseOldRecordsIfExist("XMESO_REPORT_PART_LEVEL");
	}
    
    /**
     * Erase database table records based on sourcesystem_cd, if old records exist
     * 
     * @param tableName
     */
	public void eraseOldRecordsIfExist(String tableName) {
		// These are SQL, NOT Hibernate Query Language (HQL) queries
		// Actual table name and field name are used, instead of object and properties
		String sql = String.format("select count(*) from %s where SOURCESYSTEM_CD = :sourcesystemCd", tableName);
		SQLQuery q = dataSourceManager.getSession().createSQLQuery(sql);
		q.setString("sourcesystemCd", sourcesystemCd);
		Long count = ((BigDecimal) q.uniqueResult()).longValue();

		if (count > 0) {
			// Let users know that the old records will be erased when they rerun the jar
			String output = String.format("Erasing %d previously added xmeso records from %s table", count, tableName);
			System.out.println(output);
			
			sql = String.format("delete from %s where SOURCESYSTEM_CD = :sourcesystemCd", tableName);
			q = dataSourceManager.getSession().createSQLQuery(sql);
			q.setString("sourcesystemCd", sourcesystemCd);
			q.executeUpdate();
		}
	}

	public void resultsSummary() {
		displayRowsAffected("XMESO_PROVIDER_DIMENSION");
		displayRowsAffected("XMESO_VISIT_DIMENSION");
		displayRowsAffected("XMESO_OBSERVATION_FACT");
		
		// QA tables
		displayRowsAffected("XMESO_REPORT_INFO");
		displayRowsAffected("XMESO_REPORT_CASE_LEVEL");
		displayRowsAffected("XMESO_REPORT_PART_LEVEL");
	}
	
	public void displayRowsAffected(String tableName) {
		// These are SQL, NOT Hibernate Query Language (HQL) queries
		// Actual table name and field name are used, instead of object and properties
		String sql = String.format("select count(*) from %s where SOURCESYSTEM_CD = :sourcesystemCd", tableName);
		SQLQuery q = dataSourceManager.getSession().createSQLQuery(sql);
		q.setString("sourcesystemCd", sourcesystemCd);
		Long count = ((BigDecimal) q.uniqueResult()).longValue();

		if (count > 0) {
			// Let users know how many new rows added
			String output = String.format("Newly added %d xmeso records into %s table", count, tableName);
			System.out.println(output);
		} else {
			String output = String.format("NO xmeso record added into %s table", tableName);
			System.out.println(output);
		}
	}
	
	/**
	 * Create a fake patient record if no existing patients in the XMESO_PATIENT_DIMENSION table
	 * We won't need to do this in real use case
	 * 
	 * @return
	 */
	public PatientDimension fetchOrCreatePatient(int patientNum) {
		PatientDimension existingPatient = fetchPatient(patientNum);
		if (existingPatient == null) {
			System.out.println("Patient #" + patientNum + " doesn't exist in XMESO_PATIENT_DIMENSION table");
			
			PatientDimension newPatient = newPatient(patientNum);
			dataSourceManager.getSession().saveOrUpdate(newPatient);
			// Transaction
			Transaction tx = dataSourceManager.getSession().beginTransaction();
			dataSourceManager.getSession().flush();
			tx.commit();
			
			System.out.println("Created fake patient information for patient #" + patientNum + " in XMESO_PATIENT_DIMENSION table");
			
			existingPatient = fetchPatient(patientNum);
		}
		return existingPatient;
	}

	/**
	 * Fetch info of individual patient from the XMESO_PATIENT_DIMENSION table
	 * 
	 * @return
	 */
	private PatientDimension fetchPatient(int patientNum) {
		PatientDimension patientDimension = new PatientDimension();
		patientDimension.setPatientNum(new BigDecimal(patientNum));
		patientDimension.setSourcesystemCd(sourcesystemCd);
		// Hibernate Query Language (HQL)
		String hql = "from PatientDimension as p where p.patientNum=:patientNum and p.sourcesystemCd=:sourcesystemCd";
		Query q = dataSourceManager.getSession().createQuery(hql);
		q.setProperties(patientDimension);
		PatientDimension result = (PatientDimension) q.uniqueResult();
		return result;
	}

	/**
	 * Create fake patient record for testing
	 * 
	 * The patient ID will be the PATIENT_NUM from the nmvb_path_report_event_date.csv
	 * everything else will be fake and repeated for each patient
	 * 
	 * @return
	 */
	private PatientDimension newPatient(int patientNum) {
		PatientDimension patientDimension = new PatientDimension();

		patientDimension.setPatientNum(new BigDecimal(patientNum));
		patientDimension.setVitalStatusCd((String) null);
		// Fake a birthday date
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(1967, 3, 20);
		patientDimension.setBirthDate(calendar.getTime());
		patientDimension.setDeathDate(null);
		patientDimension.setSexCd("M");
		patientDimension.setAgeInYearsNum(new BigDecimal(78));
		patientDimension.setLanguageCd("english");
		patientDimension.setRaceCd("white");
		patientDimension.setMaritalStatusCd("married");
		patientDimension.setReligionCd("roman catholic");
		patientDimension.setZipCd("15232");
		patientDimension.setStatecityzipPath("Zip codes\\Massachusetts\\Boston\\02115\\");
		patientDimension.setIncomeCd("Medium");
		patientDimension.setPatientBlob(null);
		// Use today's date as the `UPDATE_DATE`, `DOWNLOAD_DATE` and `IMPORT_DATE` in the PATIENT_DIMENSION table
		patientDimension.setUpdateDate(timeNow);
		patientDimension.setDownloadDate(timeNow);
		patientDimension.setImportDate(timeNow);
		patientDimension.setSourcesystemCd(sourcesystemCd);
		patientDimension.setUploadId(null);
		
		return patientDimension;
	}

	/**
	 * Create provider info based on configuration
	 * 
	 * @param providerId
	 * @param providerPath
	 * @param providerNameChar
	 */
	public void createProvider(String providerId, String providerPath, String providerNameChar) {
		ProviderDimension providerDimension = new ProviderDimension();
		ProviderDimensionId providerDimensionId = new ProviderDimensionId();

		providerDimensionId.setProviderId(providerId);
		providerDimensionId.setProviderPath(providerPath);
		
		providerDimension.setId(providerDimensionId);
		providerDimension.setNameChar(providerNameChar);
		providerDimension.setProviderBlob(null);
		// Use today's date as the `UPDATE_DATE`, `DOWNLOAD_DATE` and `IMPORT_DATE` in the XMESO_PATIENT_DIMENSION table
		providerDimension.setUpdateDate(timeNow);
		providerDimension.setDownloadDate(timeNow);
		providerDimension.setImportDate(timeNow);
		providerDimension.setSourcesystemCd(sourcesystemCd);
		providerDimension.setUploadId(null);

		// Transaction
		Transaction tx = dataSourceManager.getSession().beginTransaction();
		// Insert into XMESO_PROVIDER_DIMENSION table
		dataSourceManager.getSession().saveOrUpdate(providerDimension);
		dataSourceManager.getSession().flush();
		tx.commit();
		
		System.out.println(System.lineSeparator());
		System.out.println("Created provider record in XMESO_PROVIDER_DIMENSION table");
	}

	/**
	 * Add new visit info to the XMESO_VISIT_DIMENSION table
	 * 
	 * @param patientNum
	 * @param visitNum
	 * @param visitDate
	 * @param locationCd
	 * @param locationPath
	 */
	public void createVisit(int patientNum, int visitNum, Date visitDate) {
		VisitDimension visitDimension = new VisitDimension();
		VisitDimensionId visitId = new VisitDimensionId();
		
		visitId.setEncounterNum(new BigDecimal(visitNum));
		visitId.setPatientNum(new BigDecimal(patientNum));
		
		visitDimension.setId(visitId);
		// Just hard code the value for now
		visitDimension.setActiveStatusCd("Active");
		visitDimension.setStartDate(visitDate);
		visitDimension.setEndDate(visitDate);
		visitDimension.setInoutCd("in");
		// location_cd and location_path are supposed to be per visit related
		// Use null for now
		visitDimension.setLocationCd(null);
		visitDimension.setLocationPath(null);
		visitDimension.setLengthOfStay(new BigDecimal(1.0d));
		visitDimension.setVisitBlob(null);
		// Use today's date as the `UPDATE_DATE`, `DOWNLOAD_DATE` and `IMPORT_DATE` in the XMESO_VISIT_DIMENSION table
		visitDimension.setUpdateDate(timeNow);
		visitDimension.setDownloadDate(timeNow);
		visitDimension.setImportDate(timeNow);
		visitDimension.setSourcesystemCd(sourcesystemCd);
		visitDimension.setUploadId(null);

		// Transaction
		Transaction tx = dataSourceManager.getSession().beginTransaction();
		// Insert into XMESO_VISIT_DIMENSION table
		dataSourceManager.getSession().saveOrUpdate(visitDimension);
		dataSourceManager.getSession().flush();
		tx.commit();
		
		System.out.println("Created visit record for patient #" + patientNum + " in XMESO_VISIT_DIMENSION table");
	}

	/**
	 * Fetch existing concept info or create new record(with incomplete information) otherwise
	 * 
	 * A concept found in one report may also appear in another report
	 * That's why we "fetch or create" to reuse previously added concepts
	 * 
	 * @param code
	 * @return
	 */
	public ConceptDimension fetchOrCreateConcept(String code) {
		ConceptDimension existingConcept = fetchConcept(code);
		if (existingConcept == null) {
			System.out.println("Concept code " + code + " doesn't exist in XMESO_CONCEPT_DIMENSION table");
			
			ConceptDimension newConcept = newConcept(code);
			
			// Transaction
			Transaction tx = dataSourceManager.getSession().beginTransaction();
			dataSourceManager.getSession().saveOrUpdate(newConcept);
			dataSourceManager.getSession().flush();
			tx.commit();
			
			System.out.println("Created new concept code " + code + " in XMESO_CONCEPT_DIMENSION table");
			
			existingConcept = fetchConcept(code);
		}

		return existingConcept;
	}

	/**
	 * Fetch info of individual visit from the XMESO_CONCEPT_DIMENSION table
	 * 
	 * @param code
	 * @return
	 */
	private ConceptDimension fetchConcept(String code) {
		ConceptDimension conceptDimension = new ConceptDimension();
		conceptDimension.setConceptCd(code);
		conceptDimension.setSourcesystemCd(sourcesystemCd);
		// Hibernate Query Language (HQL)
		String hql = "from ConceptDimension as c where c.conceptCd=:conceptCd and c.sourcesystemCd=:sourcesystemCd";
		Query q = dataSourceManager.getSession().createQuery(hql);
		q.setProperties(conceptDimension);
		ConceptDimension result = (ConceptDimension) q.uniqueResult();
		return result;
	}

	/**
	 * Add incomplete concept info to the XMESO_CONCEPT_DIMENSION table
	 * 
	 * @param code
	 * @return
	 */
	private ConceptDimension newConcept(String code) {
		ConceptDimension conceptDimension = new ConceptDimension();
		// Only for testing purpose since concept_path can't be null
		// and the records added to this XMESO_CONCEPT_DIMENSION table is only for maintaining constraints between other tables
		conceptDimension.setConceptPath("\\" + code);
		conceptDimension.setConceptCd(code);
		conceptDimension.setNameChar(null);
		conceptDimension.setConceptBlob(null);
		// Use today's date as the `UPDATE_DATE`, `DOWNLOAD_DATE` and `IMPORT_DATE` in the XMESO_CONCEPT_DIMENSION table
		conceptDimension.setUpdateDate(timeNow);
		conceptDimension.setDownloadDate(timeNow);
		conceptDimension.setImportDate(timeNow);
		conceptDimension.setSourcesystemCd(sourcesystemCd);
		conceptDimension.setUploadId(null);
		
		return conceptDimension;
	}

	/**
	 * Add new observation fact info to the XMESO_OBSERVATION_FACT table
	 * 
	 * @param patientNum
	 * @param visitNum
	 * @param conceptCd
	 * @param instanceNum
	 */
	public void createObservation(int patientNum, int visitNum, String conceptCd, long instanceNum) {
		// Compose the observation fact ID (multiple fields as primary key)
		ObservationFactId observationFactId = new ObservationFactId();
		
		// User visit number (report ID) as the `ENCOUNTER_NUM` if it's valid
		// Otherwise use the patient number
		if (visitNum >= 0) {
			observationFactId.setEncounterNum(new BigDecimal(visitNum));
		} else {
			observationFactId.setEncounterNum(new BigDecimal(patientNum));
		}
		observationFactId.setPatientNum(new BigDecimal(patientNum));
		observationFactId.setConceptCd(conceptCd);
		// We use the provider_id specified in the properties file
		observationFactId.setProviderId(providerId);
		observationFactId.setInstanceNum(instanceNum);
		// Cannot insert NULL into XMESO_OBSERVATION_FACT "MODIFIER_CD" field
		observationFactId.setModifierCd("@");
		// Use today's date as the `START_DATE` in the XMESO_OBSERVATION_FACT table
		observationFactId.setStartDate(timeNow);

		// Create new observation fact
		ObservationFact observationFact = new ObservationFact();
		
		observationFact.setId(observationFactId);
		observationFact.setValtypeCd(null);
		observationFact.setTvalChar(null);
		observationFact.setNvalNum(null);
		observationFact.setValueflagCd(null);
		observationFact.setQuantityNum(new BigDecimal(1));
		observationFact.setUnitsCd(null);
		// Use today's date as the `END_DATE` in the XMESO_OBSERVATION_FACT table
		observationFact.setEndDate(timeNow);
		observationFact.setLocationCd(null);
		observationFact.setObservationBlob(null);
		observationFact.setConfidenceNum(new BigDecimal(1.0));
		// Use today's date as the `UPDATE_DATE`, `DOWNLOAD_DATE` and `IMPORT_DATE` in the XMESO_OBSERVATION_FACT table
		observationFact.setUpdateDate(timeNow);
		observationFact.setDownloadDate(timeNow);
		observationFact.setImportDate(timeNow);
		observationFact.setSourcesystemCd(sourcesystemCd);
		observationFact.setUploadId(null);

		// Transaction
		Transaction tx = dataSourceManager.getSession().beginTransaction();
		dataSourceManager.getSession().saveOrUpdate(observationFact);
		dataSourceManager.getSession().flush();
		tx.commit();
	}
	
	public void createReportInfo(int reportId, String reportFilename, Date reportDate) {
		// Create new report info record
		ReportInfo reportInfo = new ReportInfo();
		
		reportInfo.setReportId(new BigDecimal(reportId));
		reportInfo.setReportFilename(reportFilename);
		reportInfo.setReportDate(reportDate);
		reportInfo.setSourcesystemCd(sourcesystemCd);

		// Transaction
		Transaction tx = dataSourceManager.getSession().beginTransaction();
		dataSourceManager.getSession().saveOrUpdate(reportInfo);
		dataSourceManager.getSession().flush();
		tx.commit();
	}
	
	public void createReportCaseLevel(int reportId, String lymphNodesExamed, String specialStains, String ultrastructuralFindings) {
		// Create new report case level record
		ReportCaseLevel reportCaseLevel = new ReportCaseLevel();
		
		reportCaseLevel.setReportId(new BigDecimal(reportId));
		reportCaseLevel.setLymphNodesExamed(lymphNodesExamed);
		reportCaseLevel.setSpecialStains(specialStains);
		reportCaseLevel.setUltrastructuralFindings(ultrastructuralFindings);
		reportCaseLevel.setSourcesystemCd(sourcesystemCd);
		
		// Transaction
		Transaction tx = dataSourceManager.getSession().beginTransaction();
		dataSourceManager.getSession().saveOrUpdate(reportCaseLevel);
		dataSourceManager.getSession().flush();
		tx.commit();
	}

	public void createReportPartLevel(int reportId, long partLabel, String siteOfTumor, String histologicalType, String tumorConfiguration, String tumorDifferentiationOrGrade) {
		// Create new report case level record
		ReportPartLevel reportPartLevel = new ReportPartLevel();
		
		reportPartLevel.setReportId(new BigDecimal(reportId));
		reportPartLevel.setPartLabel(partLabel);
		reportPartLevel.setSiteOfTumor(siteOfTumor);
		reportPartLevel.setHistologicalType(histologicalType);
		reportPartLevel.setTumorConfiguration(tumorConfiguration);
		reportPartLevel.setTumorDifferentiationOrGrade(tumorDifferentiationOrGrade);
		reportPartLevel.setSourcesystemCd(sourcesystemCd);
		
		// Transaction
		Transaction tx = dataSourceManager.getSession().beginTransaction();
		dataSourceManager.getSession().saveOrUpdate(reportPartLevel);
		dataSourceManager.getSession().flush();
		tx.commit();
	}

}