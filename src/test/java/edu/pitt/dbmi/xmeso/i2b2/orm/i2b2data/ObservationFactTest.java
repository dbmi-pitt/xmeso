/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

/**
 *
 * @author kjm84
 */
public class ObservationFactTest {

    private I2b2DemoDataSourceManager i2b2DataDataSourceManager;

    private String sourceSystemCd = "Xmeso";
    
    public ObservationFactTest() {
    }

    @Before
    public void setUp() {
        i2b2DataDataSourceManager = new I2b2DemoDataSourceManager();
    }

    @After
    public void tearDown() {
        i2b2DataDataSourceManager.destroy();
        i2b2DataDataSourceManager = null;
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testObservationFactCRUD() {
    	
    	Session session = i2b2DataDataSourceManager.getSession();
    	assumeTrue(session != null);

        final List<PatientDimension> patients = createPatients();
        final List<ConceptDimension> concepts = createConcepts();
        final List<VisitDimension> visits = createVisits(patients);
        final List<ProviderDimension> providers = createProviders();
    
        // ****************************
        // Create
        // ****************************
        Date timeNow = new Date();
        Random generator = new Random(timeNow.getTime());
         
        for (int odx = 0; odx < 100; odx++) {
            PatientDimension patient = patients.get(generator.nextInt(patients.size()));
            Query query = session.createQuery("from VisitDimension v where v.id.patientNum like :patientNum and sourcesystemCd = :sourceSystemCd");
            query.setBigDecimal("patientNum", patient.getPatientNum());
            query.setString("sourceSystemCd", sourceSystemCd);
            List<VisitDimension> patientVisits = query.list();
             
            VisitDimension patientVisit = patientVisits.get(choiceIndex(generator,patientVisits.size()));
            ProviderDimension provider = providers.get(generator.nextInt(providers.size()));
            ConceptDimension concept = concepts.get(generator.nextInt(concepts.size()));
            
            ObservationFactId observationFactId = new ObservationFactId();
            observationFactId.setConceptCd(concept.getConceptCd());
            observationFactId.setEncounterNum(patientVisit.getId().getEncounterNum());
            observationFactId.setInstanceNum(odx);
            observationFactId.setModifierCd("NA");
            observationFactId.setPatientNum(patient.getPatientNum());
            observationFactId.setProviderId(provider.getId().getProviderId());
            observationFactId.setStartDate(timeNow);
            
            ObservationFact observationFact = new ObservationFact();
            observationFact.setId(observationFactId);
            observationFact.setConfidenceNum(BigDecimal.ONE);
            observationFact.setDownloadDate(timeNow);
            observationFact.setEndDate(timeNow);
            observationFact.setImportDate(timeNow);
            observationFact.setLocationCd("PGH");
            observationFact.setNvalNum(BigDecimal.ONE);
            observationFact.setObservationBlob(null);
            observationFact.setQuantityNum(BigDecimal.ONE);
            observationFact.setSourcesystemCd(sourceSystemCd);
            observationFact.setTvalChar("NA");
            observationFact.setUnitsCd("ug");
            observationFact.setUpdateDate(timeNow);
            observationFact.setUploadId(BigDecimal.ZERO);
            observationFact.setValtypeCd("NA");
            observationFact.setValtypeCd("NA");
            session.saveOrUpdate(observationFact);
        }
        session.flush();

        Query query = session.createQuery("from ObservationFact o where sourcesystemCd = :sourceSystemCd");
        query.setString(
                "sourceSystemCd", sourceSystemCd);
        List<ObservationFact> observationFacts = query.list();
        assertEquals(observationFacts.size(), 100);

        
        // ****************************
        // Read
        // ****************************
        
        System.out.println(
                "testRead");
        observationFacts = session.createQuery("from ObservationFact o where sourcesystemCd = 'Xmeso'").setMaxResults(10).list();
        assertEquals(observationFacts.size(), 10);

        
        // ****************************
        // Update
        // ****************************
        
        System.out.println(
                "testUpdate");

        query = session.createQuery("from ObservationFact o where tvalChar = :tvalChar and sourcesystemCd = :sourceSystemCd");
        query.setString(
                "tvalChar", "NA");
        query.setString(
                "sourceSystemCd", sourceSystemCd);
        observationFacts = query.list();
        for (ObservationFact observationFact : observationFacts) {
            observationFact.setTvalChar("APPLICABLE");
            session.saveOrUpdate(observationFact);
        }

        session.flush();
        query = session.createQuery("from ObservationFact o where tvalChar like :tvalChar and sourcesystemCd = :sourceSystemCd");

        query.setString(
                "tvalChar", "APPLICABLE");
        query.setString(
                "sourceSystemCd", sourceSystemCd);
        observationFacts = query.list();

        assertEquals(observationFacts.size(), 100);

       
        // ****************************
        // Deletion
        // ****************************
        
        System.out.println(
                "testDelete");

        query = session.createQuery("from ObservationFact o where tvalChar like :tvalChar and sourcesystemCd like :sourceSystemCd");

        query.setString(
                "tvalChar", "APPLICABLE");
        query.setString(
                "sourceSystemCd", sourceSystemCd);
        observationFacts = query.list();
        for (ObservationFact providerDimension : observationFacts) {
            session.delete(providerDimension);
        }
        
         query = session.createQuery("from ObservationFact o where sourcesystemCd like :sourceSystemCd");
        query.setString(
                "sourceSystemCd", sourceSystemCd);
          observationFacts = query.list();
        for (ObservationFact observationFact : observationFacts) {
            session.delete(observationFact);
        }
        
        for (PatientDimension patientDimension : patients) {
        	session.delete(patientDimension);
        }
        for (VisitDimension visitDimension : visits) {
        	session.delete(visitDimension);
        }
        for (ProviderDimension providerDimension : providers) {
        	session.delete(providerDimension);
        }
        for (ConceptDimension conceptDimension : concepts) {
        	session.delete(conceptDimension);
        }
      
        session.flush();
        query = session.createQuery("from ObservationFact o where sourcesystemCd like :sourceSystemCd");

        query.setString(
                "sourceSystemCd", sourceSystemCd);
        observationFacts = query.list();

        assertEquals(observationFacts.size(), 0);
    }

    @SuppressWarnings("unchecked")
	private List<PatientDimension> createPatients() {
        Session session = i2b2DataDataSourceManager.getSession();
        for (int patNum = 4002000; patNum < 4002020; patNum++) {
            Date timeNow = new Date();
            PatientDimension patientDimension = new PatientDimension();
            patientDimension.setPatientNum(new BigDecimal(patNum));
            patientDimension.setAgeInYearsNum(new BigDecimal(78));
            patientDimension.setBirthDate(timeNow);
            patientDimension.setDeathDate(timeNow);
            patientDimension.setDownloadDate(timeNow);
            patientDimension.setImportDate(timeNow);
            patientDimension.setIncomeCd(null);
            patientDimension.setLanguageCd(null);
            patientDimension.setMaritalStatusCd(null);
            patientDimension.setPatientBlob(null);
            patientDimension.setRaceCd(null);
            patientDimension.setSourcesystemCd(sourceSystemCd);
            session.saveOrUpdate(patientDimension);
        }
        session.flush();
        Query query = session.createQuery("from PatientDimension p where ageInYearsNum like :ageInYearsNum and sourcesystemCd = :sourceSystemCd");
        query.setBigDecimal("ageInYearsNum", new BigDecimal(78));
        query.setString("sourceSystemCd", sourceSystemCd);
        List<PatientDimension> patientDimensions = query.list();
        assertEquals(patientDimensions.size(), 20);
        return patientDimensions;
    }

    @SuppressWarnings("unchecked")
	public List<ConceptDimension> createConcepts() {
        Session session = i2b2DataDataSourceManager.getSession();
        for (int rowIdx = 4002000; rowIdx < 4002020; rowIdx++) {
            String cui = "xmeso:" + rowIdx;
            Date timeNow = new Date();
            String pathPrefix = "/some/arbitrary/path1/";
            if (rowIdx % 2 == 1) {
                pathPrefix = "/some/arbitrary/path2/";
            }
            ConceptDimension conceptDimension = new ConceptDimension();
            conceptDimension.setConceptCd(cui);
            conceptDimension.setConceptPath(pathPrefix + cui);
            conceptDimension.setDownloadDate(timeNow);
            conceptDimension.setImportDate(timeNow);
            conceptDimension.setNameChar(pathPrefix + cui);
            conceptDimension.setSourcesystemCd(sourceSystemCd);
            conceptDimension.setUpdateDate(timeNow);
            conceptDimension.setUploadId(BigDecimal.ZERO);
            session.saveOrUpdate(conceptDimension);
        }
        session.flush();
        Query query = session.createQuery("from ConceptDimension c where conceptCd like :conceptCd and sourcesystemCd = :sourceSystemCd");
        query.setString("conceptCd", "xmeso:%");
        query.setString("sourceSystemCd", sourceSystemCd);
        List<ConceptDimension> conceptDimensions = query.list();
        assertEquals(conceptDimensions.size(), 20);
        return conceptDimensions;
    }

    @SuppressWarnings("unchecked")
	private List<VisitDimension> createVisits(List<PatientDimension> patientDimensions) {
        Session session = i2b2DataDataSourceManager.getSession();
        for (PatientDimension patientDimension : patientDimensions) {
            for (int encounterNum = 0; encounterNum < 5; encounterNum++) {
                Date timeNow = new Date();
                VisitDimension visitDimension = new VisitDimension();
                VisitDimensionId visitId = new VisitDimensionId();
                visitId.setPatientNum(patientDimension.getPatientNum());
                visitId.setEncounterNum(new BigDecimal(encounterNum));
                visitDimension.setId(visitId);
                visitDimension.setActiveStatusCd("Active");
                visitDimension.setDownloadDate(timeNow);
                visitDimension.setEndDate(timeNow);
                visitDimension.setImportDate(timeNow);
                visitDimension.setInoutCd("in");
                visitDimension.setLengthOfStay(new BigDecimal(4.0d));
                visitDimension.setLocationCd("Pennsylvania");
                visitDimension.setLocationPath("Pittsburgh/Pennsylvania");
                visitDimension.setSourcesystemCd(sourceSystemCd);
                visitDimension.setStartDate(timeNow);
                visitDimension.setUpdateDate(timeNow);
                visitDimension.setVisitBlob(null);
                visitDimension.setUploadId(new BigDecimal(99));
                session.saveOrUpdate(visitDimension);
            }
        }
        session.flush();
        Query query = session.createQuery("from VisitDimension v where locationPath like :locationPath and sourcesystemCd = :sourceSystemCd");
        query.setString("locationPath", "Pitt%");
        query.setString("sourceSystemCd", sourceSystemCd);
        List<VisitDimension> visitDimensions = query.list();
        assertEquals(visitDimensions.size(), patientDimensions.size() * 5);
        return visitDimensions;
    }

    @SuppressWarnings("unchecked")
	private List<ProviderDimension> createProviders() {
        Session session = i2b2DataDataSourceManager.getSession();
        final String[] doctors = {"X", "Y", "Z"};
        for (String doctor : doctors) {
            Date timeNow = new Date();
            ProviderDimension providerDimension = new ProviderDimension();
            ProviderDimensionId providerId = new ProviderDimensionId();
            providerId.setProviderId(doctor);
            providerId.setProviderPath("doctor:" + doctor);
            providerDimension.setId(providerId);
            providerDimension.setDownloadDate(timeNow);
            providerDimension.setImportDate(timeNow);
            providerDimension.setNameChar("Dr. " + doctor);
            providerDimension.setProviderBlob(null);
            providerDimension.setSourcesystemCd(sourceSystemCd);
            providerDimension.setUpdateDate(timeNow);
            providerDimension.setUploadId(new BigDecimal(1.0d));
            session.saveOrUpdate(providerDimension);
        }
        session.flush();
        Query query = session.createQuery("from ProviderDimension p where nameChar like :nameChar and sourcesystemCd = :sourceSystemCd");
        query.setString(
                "nameChar", "Dr.%");
        query.setString(
                "sourceSystemCd", sourceSystemCd);
        List<ProviderDimension> providerDimensions = query.list();
        assertEquals(providerDimensions.size(), 3);
        return providerDimensions;
    }
    
    private int choiceIndex(Random generator, int collectionSize) {
          int choiceIndex = generator.nextInt(collectionSize);
          choiceIndex = Math.min(collectionSize - 1, choiceIndex);
          choiceIndex = Math.max(0, choiceIndex);
          return choiceIndex;
    }

}
