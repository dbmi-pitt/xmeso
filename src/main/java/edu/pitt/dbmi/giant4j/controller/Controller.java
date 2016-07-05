package edu.pitt.dbmi.giant4j.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.pitt.dbmi.giant4j.form.XmesoFormPartSet;
import edu.pitt.dbmi.giant4j.kb.KbEncounter;
import edu.pitt.dbmi.giant4j.kb.KbPatient;
import edu.pitt.dbmi.giant4j.kb.KbSummary;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ConceptDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.I2b2DemoDataSourceManager;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ObservationFact;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ObservationFactId;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.PatientDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ProviderDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ProviderDimensionId;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.VisitDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.VisitDimensionId;

public class Controller {

	public static final int patientOffset = 3000000;
	public static final int visitOffset = 4000000;

	private Session i2b2DataSession;

	private String sourcesystemCd = "Xmeso";

	private Date timeNow = new Date();
	private String parsedPatientLimsId;
	private String parsedVisitLimsId;
	private String parsedNoteKind;
	private long idCounter = 1000000L;

	// Roving window of the star
	private PatientDimension currentDbPatient;
	private VisitDimension currentDbVisit;
	private ProviderDimension currentDbProvider;
	private ConceptDimension currentDbConcept;
	private String currentDbModifier;
	private String currentTextToStore;
	private ObservationFact currentDbObservation;

	// Stored for quick access
	private ProviderDimension cTakesDbProvider;
	private ConceptDimension codedDbConcept;

	private List<KbPatient> kbPatients;

	private I2b2DemoDataSourceManager i2b2DataDataSourceManager;

	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.cleanRdbmsData();
		// controller.uploadFsDirectoryToRdbms();
//		controller.constructFastDagFromRdbms();
		controller.constructFastPatientEncountersFromRdbms();
		controller.closeUp();
	}

	public Controller() {
		i2b2DataDataSourceManager = new I2b2DemoDataSourceManager();
		i2b2DataSession = i2b2DataDataSourceManager.getSession();
		// findMaxIdFromObservationFact();
		// findMaxIdFromProviderDimension();
		findOrCreateProvider("XmesoProvider:xmeso");
		findOrCreateConcept("XmesoConcept:coded");
		cTakesDbProvider = currentDbProvider;
		codedDbConcept = currentDbConcept;
	}

	public void closeUp() {
		i2b2DataDataSourceManager.destroy();
	}

	public void cleanRdbmsData() {
		i2b2DataSession = i2b2DataDataSourceManager.getSession();
		I2b2DemoDataCleaner cleaner = new I2b2DemoDataCleaner();
		cleaner.setDataSourceMgr(i2b2DataDataSourceManager);
		cleaner.setSourceSystemCd(sourcesystemCd);
		cleaner.execute();
	}

	private String serializeFormPartSet(XmesoFormPartSet xmesoFormPartSet) {
		byte[] formDataBeanByteArray = SerializationUtils
				.serialize(xmesoFormPartSet);
		return Base64.encodeBase64String(formDataBeanByteArray);
	}

	private XmesoFormPartSet deSerializeFormPartSet(String utfFormPartSetString) {
		XmesoFormPartSet formPartSet = null;
		byte[] objectAsBytes = Base64.decodeBase64(utfFormPartSetString);
		ByteArrayInputStream bais = new ByteArrayInputStream(objectAsBytes);
		formPartSet = (XmesoFormPartSet) SerializationUtils.deserialize(bais);
		return formPartSet;
	}

	public void uploadFsDirectoryRawTexts(KbPatient kbPatient) {
		try {
			for (KbEncounter kbEncounter : kbPatient.getEncounters()) {
				cachePatientParametersFromEncounter(kbEncounter);
				findOrCreatePatient(parsedPatientLimsId);
				findOrCreateVisit(parsedVisitLimsId);
				findOrCreateProvider("XmesoProvider:clinic");
				findOrCreateConcept("XmesoConcept:unprocessed");
				currentDbModifier = parsedNoteKind;
				currentTextToStore = kbEncounter.getContent();
				findOrCreateObservation();

				// Stores the expert summarization for the encounter
				findOrCreateProvider("XmesoProvider:expert");
				currentTextToStore = serializeFormPartSet(new XmesoFormPartSet());
				findOrCreateObservation();

				// Stores the summarization for the encounter
				findOrCreateProvider("XmesoProvider:xmeso");
				currentTextToStore = serializeFormPartSet(new XmesoFormPartSet());
				findOrCreateObservation();

				break;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void cachePatientParametersFromEncounter(KbEncounter kbEncounter) {
		parsedPatientLimsId = padId(kbEncounter.getPatientId());
		parsedVisitLimsId = parsedPatientLimsId + ":"
				+ padId(kbEncounter.getId());
		parsedNoteKind = "pathology";
	}
	
	@SuppressWarnings("unchecked")
	public void constructFastPatientEncountersFromRdbms() {

		findOrCreateProvider("XmesoProvider:clinic");
		ProviderDimension providerClinic = currentDbProvider;
		findOrCreateProvider("XmesoProvider:expert");
		ProviderDimension providerExpert = currentDbProvider;
		findOrCreateProvider("XmesoProvider:xmeso");
		ProviderDimension providerXmeso = currentDbProvider;
	
		findOrCreateConcept("XmesoConcept:unprocessed");
		ConceptDimension conceptUnprocessed = currentDbConcept;
		findOrCreateConcept("XmesoConcept:xmi");
		ConceptDimension conceptXmi = currentDbConcept;
		findOrCreateConcept("XmesoConcept:synoptic");
		ConceptDimension conceptSynoptic = currentDbConcept;
		
		String hql = "from ObservationFact o where ";
		hql += " o.sourcesystemCd like :sourceSystemCd ";
		hql += " order by o.id.patientNum, o.id.encounterNum";
		Query query = i2b2DataSession.createQuery(hql);
		query.setString("sourceSystemCd", sourcesystemCd);
		List<ObservationFact> observationFacts = query.list();
		BigDecimal lastPatientNum = new BigDecimal(-1.0d);
		KbPatient kbPatient = null;
		KbEncounter enc = null;
		int patientSeq = 0;
		for (ObservationFact observationFact : observationFacts) {
			if (observationFact.getId().getPatientNum().intValue() > lastPatientNum
					.intValue()) {
				kbPatient = new KbPatient();
				kbPatient.setId(observationFact.getId().getPatientNum()
						.intValue());
				kbPatient.setSequence(patientSeq++);
				kbPatients.add(kbPatient);
				lastPatientNum = observationFact.getId().getPatientNum();			
				enc = new KbEncounter();
				enc.setId((int) observationFact.getId().getEncounterNum().intValue());
				enc.setSequence(0);
				kbPatient.getEncounters().add(enc);
			}
			boolean isContent = true;
			isContent = isContent &&
					observationFact.getId().getProviderId().equals(providerClinic.getId());
			isContent = isContent &&
					observationFact.getId().getConceptCd().equals(conceptUnprocessed.getConceptCd());
			if (isContent) {
				enc.setContent(observationFact.getObservationBlob());
			}
			
			boolean isXmi = true;
			isXmi = isXmi &&
					observationFact.getId().getProviderId().equals(providerXmeso.getId());
			isXmi = isXmi &&
					observationFact.getId().getConceptCd().equals(conceptXmi.getConceptCd());
			if (isXmi) {
				enc.setXmi(observationFact.getObservationBlob());
			}
			
			boolean isExpert = true;
			isExpert = isExpert &&
					observationFact.getId().getProviderId().equals(providerExpert.getId());
			isExpert = isExpert &&
					observationFact.getId().getConceptCd().equals(conceptSynoptic.getConceptCd());
			if (isExpert) {
				enc.setExpertForm(observationFact.getObservationBlob());
			}
			
			boolean isMachine = true;
			isMachine = isMachine &&
					observationFact.getId().getProviderId().equals(providerXmeso.getId());
			isMachine = isMachine &&
					observationFact.getId().getConceptCd().equals(conceptSynoptic.getConceptCd());
			if (isMachine) {
				enc.setXmesoForm(observationFact.getObservationBlob());
			}
			
			
		}
	}

	@SuppressWarnings("unchecked")
	public void constructFastDagFromRdbms() {
		findOrCreateProvider("XmesoProvider:clinic");
		findOrCreateConcept("XmesoConcept:unprocessed");
		String hql = "from ObservationFact o where ";
		hql += " o.id.providerId = :providerId and ";
		hql += " o.id.conceptCd = :conceptCd and ";
		hql += " o.sourcesystemCd like :sourceSystemCd ";
		hql += " order by o.id.patientNum, o.id.encounterNum, o.id.modifierCd ";
		Query query = i2b2DataSession.createQuery(hql);
		query.setString("providerId", currentDbProvider.getId().getProviderId());
		query.setString("conceptCd", currentDbConcept.getConceptCd());
		query.setString("sourceSystemCd", sourcesystemCd);
		List<ObservationFact> observationFacts = query.list();
		BigDecimal lastPatientNum = new BigDecimal(-1.0d);
		KbPatient kbPatient = null;
		int patientSeq = 0;
		int encounterSeq = 0;
		for (ObservationFact observationFact : observationFacts) {
			if (observationFact.getId().getPatientNum().intValue() > lastPatientNum
					.intValue()) {
				kbPatient = new KbPatient();
				kbPatient.setId(observationFact.getId().getPatientNum()
						.intValue());
				kbPatient.setSequence(patientSeq++);
				kbPatients.add(kbPatient);
				lastPatientNum = observationFact.getId().getPatientNum();
				encounterSeq = 0;
			}
			KbEncounter enc = new KbEncounter();
			enc.setId((int) observationFact.getId().getInstanceNum());
			enc.setKind(observationFact.getId().getModifierCd());
			enc.setSequence(encounterSeq++);
			enc.setContent(observationFact.getObservationBlob());
			kbPatient.getEncounters().add(enc);
		}
	}

	@SuppressWarnings("unchecked")
	public void constructPatientDagFromRdbms() {

		String hql = "from PatientDimension p where ";
		hql += " p.sourcesystemCd like :sourceSystemCd ";
		hql += " order by p.patientNum ";
		Query query = i2b2DataSession.createQuery(hql);
		query.setString("sourceSystemCd", sourcesystemCd);
		List<PatientDimension> sessionBoundPatients = (List<PatientDimension>) query
				.list();
		int patientSeq = 0;
		kbPatients.clear();
		for (PatientDimension sessionBoundPatient : sessionBoundPatients) {
			KbPatient kbPatient = new KbPatient();
			kbPatient.setId(sessionBoundPatient.getPatientNum().intValue());
			kbPatient.setSequence(patientSeq++);
			kbPatients.add(kbPatient);
			constructEncounterDagFromPatient(sessionBoundPatient, kbPatient);
		}
		i2b2DataDataSourceManager.getSession().clear();
	}

	@SuppressWarnings("unchecked")
	private void constructEncounterDagFromPatient(
			PatientDimension rdbmsPatient, KbPatient kbPatient) {
		String hql = "from VisitDimension v where ";
		hql += " v.id.patientNum = :patientNum and ";
		hql += " v.sourcesystemCd like :sourceSystemCd ";
		hql += " order by v.id.encounterNum ";
		Query query = i2b2DataSession.createQuery(hql);
		query.setBigDecimal("patientNum", rdbmsPatient.getPatientNum());
		query.setString("sourceSystemCd", sourcesystemCd);
		List<VisitDimension> rdbmsVisits = (List<VisitDimension>) query.list();
		int encounterSeq = 0;
		for (VisitDimension rdbmsVisit : rdbmsVisits) {
			currentDbPatient = rdbmsPatient;
			currentDbVisit = rdbmsVisit;
			findOrCreateProvider("XmesoProvider:clinic");
			findOrCreateConcept("XmesoConcept:unprocessed");
			List<ObservationFact> observationFacts = pullEncounterContent();
			for (ObservationFact observationFact : observationFacts) {
				KbEncounter enc = new KbEncounter();
				enc.setId((int) observationFact.getId().getInstanceNum());
				enc.setKind(observationFact.getId().getModifierCd());
				enc.setSequence(encounterSeq++);
				enc.setContent(observationFact.getObservationBlob());
				kbPatient.getEncounters().add(enc);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void findMaxIdFromObservationFact() {
		String hql = "from ObservationFact o where";
		hql += " o.sourcesystemCd like :sourceSystemCd";
		hql += " order by o.id.instanceNum desc ";
		Query query = i2b2DataSession.createQuery(hql);
		query.setString("sourceSystemCd", sourcesystemCd);
		List<ObservationFact> observations = (List<ObservationFact>) query
				.list();
		if (!observations.isEmpty()) {
			long observationIdAsLong = observations.iterator().next().getId()
					.getInstanceNum();
			observationIdAsLong++;
			idCounter = Math.max(idCounter, observationIdAsLong);
			System.out
					.println("findMaxIdFromObservationFact resetting idCounter to "
							+ idCounter);
		}
		i2b2DataSession.clear();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void findMaxIdFromProviderDimension() {
		String hql = "from ProviderDimension o where";
		hql += " o.sourcesystemCd like :sourceSystemCd";
		hql += " order by o.id.providerId desc ";
		Query query = i2b2DataSession.createQuery(hql);
		query.setString("sourceSystemCd", sourcesystemCd);
		List<ProviderDimension> observations = (List<ProviderDimension>) query
				.list();
		if (!observations.isEmpty()) {
			long providerIdAsLong = Long.parseLong(observations.iterator()
					.next().getId().getProviderId());
			providerIdAsLong++;
			idCounter = Math.max(idCounter, providerIdAsLong);
			System.out
					.println("findMaxIdFromProviderDimension resetting idCounter to "
							+ idCounter);
		}
		i2b2DataSession.clear();
	}

	@SuppressWarnings("unchecked")
	private List<ObservationFact> pullEncounterContent() {
		String hql = "";
		hql = "from ObservationFact o where ";
		hql += " o.id.patientNum = :patientNum and ";
		hql += " o.id.encounterNum = :encounterNum and ";
		hql += " o.id.providerId = :providerId and ";
		hql += " o.id.conceptCd = :conceptCd and ";
		hql += " o.sourcesystemCd like :sourceSystemCd";
		Query query = i2b2DataSession.createQuery(hql);
		query.setBigDecimal("patientNum", currentDbPatient.getPatientNum());
		query.setBigDecimal("encounterNum", currentDbVisit.getId()
				.getEncounterNum());
		query.setString("providerId", currentDbProvider.getId().getProviderId());
		query.setString("conceptCd", currentDbConcept.getConceptCd());
		query.setString("sourceSystemCd", sourcesystemCd);
		List<ObservationFact> observationFacts = query.list();
		return observationFacts;
	}

	private void findOrCreateObservation() throws IOException {
		findObservationByDimensions();
		if (currentDbObservation == null) {
			createObservation();
		}
	}

	private void createObservation() {
		ObservationFactId observationFactId = new ObservationFactId();
		observationFactId.setInstanceNum(idCounter++);
		observationFactId.setConceptCd(currentDbConcept.getConceptCd());
		observationFactId.setEncounterNum(currentDbVisit.getId()
				.getEncounterNum());
		observationFactId.setModifierCd(currentDbModifier);
		observationFactId.setPatientNum(currentDbPatient.getPatientNum());
		observationFactId.setProviderId(currentDbProvider.getId()
				.getProviderId());
		observationFactId.setStartDate(timeNow);
		currentDbObservation = new ObservationFact();
		currentDbObservation.setId(observationFactId);
		currentDbObservation.setObservationBlob(currentTextToStore);
		currentDbObservation.setConfidenceNum(BigDecimal.ONE);
		currentDbObservation.setDownloadDate(timeNow);
		currentDbObservation.setEndDate(timeNow);
		currentDbObservation.setImportDate(timeNow);
		currentDbObservation.setLocationCd("PGH");
		currentDbObservation.setNvalNum(BigDecimal.ONE);
		currentDbObservation.setQuantityNum(BigDecimal.ONE);
		currentDbObservation.setTvalChar("NA");
		currentDbObservation.setUnitsCd("ug");
		currentDbObservation.setUpdateDate(timeNow);
		currentDbObservation.setUploadId(BigDecimal.ZERO);
		currentDbObservation.setValtypeCd("NA");
		currentDbObservation.setValtypeCd("NA");
		currentDbObservation.setSourcesystemCd(sourcesystemCd);
		i2b2DataSession.saveOrUpdate(currentDbObservation);
		Transaction tx = i2b2DataSession.beginTransaction();
		i2b2DataSession.flush();
		tx.commit();
		i2b2DataSession.clear();
	}

	private void findObservationByDimensions() {
		findObservationByDimensions(currentDbPatient.getPatientNum(),
				currentDbVisit.getId().getEncounterNum(), currentDbProvider
						.getId().getProviderId(),
				currentDbConcept.getConceptCd(), currentDbModifier);
	}

	private void findObservationByDimensions(BigDecimal patientNum,
			BigDecimal encounterNum, String providerId, String conceptCd,
			String modifier) {
		String hql = "from ObservationFact o where ";
		hql += " o.id.patientNum = :patientNum and ";
		hql += " o.id.encounterNum = :encounterNum and ";
		hql += " o.id.providerId = :providerId and ";
		hql += " o.id.conceptCd = :conceptCd and ";
		hql += " o.id.modifierCd = :modifier and ";
		hql += " o.sourcesystemCd like :sourceSystemCd";
		Query query = i2b2DataSession.createQuery(hql);
		query.setBigDecimal("patientNum", patientNum);
		query.setBigDecimal("encounterNum", encounterNum);
		query.setString("providerId", providerId);
		query.setString("conceptCd", conceptCd);
		query.setString("modifier", modifier);
		query.setString("sourceSystemCd", sourcesystemCd);
		currentDbObservation = (ObservationFact) query.uniqueResult();
		nullSafeEvict(currentDbObservation);
	}

	private void findObservationByInstanceNum(long instanceNum) {
		String hql = "from ObservationFact o where ";
		hql += " o.id.instanceNum = :instanceNum and ";
		hql += " o.sourcesystemCd like :sourceSystemCd";
		Query query = i2b2DataSession.createQuery(hql);
		query.setLong("instanceNum", instanceNum);
		query.setString("sourceSystemCd", sourcesystemCd);
		currentDbObservation = (ObservationFact) query.uniqueResult();
		nullSafeEvict(currentDbObservation);
	}

	public String findObservationTextByInstanceNum(long instanceNum) {
		findObservationByInstanceNum(instanceNum);
		String result = (currentDbObservation != null) ? currentDbObservation
				.getObservationBlob() : null;
		return result;
	}

	public XmesoFormPartSet findFormDataByInstanceNum(long instanceNum) {
		findObservationByInstanceNum(instanceNum);
		String blobAsString = (currentDbObservation != null) ? currentDbObservation
				.getObservationBlob() : null;
		return deSerializeFormPartSet(blobAsString);
	}

	public void saveObservationFormData(long observationInstanceNumber,
			XmesoFormPartSet xmesoFormPartSet) {
		findObservationByInstanceNum(observationInstanceNumber);
		String utfFormDataBeanString = serializeFormPartSet(xmesoFormPartSet);
		currentDbObservation.setObservationBlob(utfFormDataBeanString);
		Transaction tx = i2b2DataSession.beginTransaction();
		i2b2DataSession.saveOrUpdate(currentDbObservation);
		i2b2DataSession.flush();
		tx.commit();
		i2b2DataSession.clear();
	}

	private void findOrCreateProvider(String providerNameChar) {
		currentDbProvider = findProviderByNameChar(providerNameChar);
		if (currentDbProvider == null) {
			currentDbProvider = new ProviderDimension();
			ProviderDimensionId providerId = new ProviderDimensionId();
			providerId.setProviderId(padId(idCounter++));
			providerId.setProviderPath(providerNameChar);
			currentDbProvider.setId(providerId);
			currentDbProvider.setImportDate(timeNow);
			currentDbProvider.setDownloadDate(timeNow);
			currentDbProvider.setNameChar(providerNameChar);
			currentDbProvider.setProviderBlob(providerNameChar);
			currentDbProvider.setUpdateDate(timeNow);
			currentDbProvider.setUploadId(new BigDecimal(1.0d));
			currentDbProvider.setSourcesystemCd(sourcesystemCd);
			i2b2DataSession.saveOrUpdate(currentDbProvider);
			Transaction tx = i2b2DataSession.beginTransaction();
			i2b2DataSession.flush();
			tx.commit();
			nullSafeEvict(currentDbProvider);
		}
	}

	private ProviderDimension findProviderByNameChar(String providerNameChar) {
		Query query = i2b2DataSession
				.createQuery("from ProviderDimension p where nameChar like :providerNameChar and sourcesystemCd like :sourceSystemCd");
		query.setString("providerNameChar", providerNameChar);
		query.setString("sourceSystemCd", sourcesystemCd);
		ProviderDimension provider = (ProviderDimension) query.uniqueResult();
		if (provider != null) {
			i2b2DataSession.evict(provider);
		}
		return provider;
	}

	private void findOrCreateConcept(String conceptCd) {
		findConceptByConceptCd(conceptCd);
		if (currentDbConcept == null) {
			currentDbConcept = new ConceptDimension();
			currentDbConcept.setConceptCd(conceptCd);
			currentDbConcept.setConceptPath(conceptCd);
			currentDbConcept.setDownloadDate(timeNow);
			currentDbConcept.setImportDate(timeNow);
			currentDbConcept.setNameChar(conceptCd);
			currentDbConcept.setUpdateDate(timeNow);
			currentDbConcept.setUploadId(new BigDecimal(1.0d));
			currentDbConcept.setSourcesystemCd(sourcesystemCd);
			i2b2DataSession.saveOrUpdate(currentDbConcept);
			Transaction tx = i2b2DataSession.beginTransaction();
			i2b2DataSession.flush();
			tx.commit();
		}
		nullSafeEvict(currentDbConcept);
	}

	private void findConceptByConceptCd(String conceptCd) {
		Query query = i2b2DataSession
				.createQuery("from ConceptDimension p where conceptCd like :conceptCd and sourcesystemCd like :sourceSystemCd");
		query.setString("conceptCd", conceptCd);
		query.setString("sourceSystemCd", sourcesystemCd);
		currentDbConcept = (ConceptDimension) query.uniqueResult();
		nullSafeEvict(currentDbConcept);
	}

	private void findOrCreatePatient(String limsAccession) {
		currentDbPatient = findPatientByLimsAccession(limsAccession);
		if (currentDbPatient == null) {
			currentDbPatient = new PatientDimension();
			currentDbPatient.setPatientNum(new BigDecimal(padId(idCounter++)));
			currentDbPatient.setPatientBlob(limsAccession);
			currentDbPatient.setAgeInYearsNum(new BigDecimal(0.0d));
			currentDbPatient.setBirthDate(timeNow);
			currentDbPatient.setDeathDate(timeNow);
			currentDbPatient.setDownloadDate(timeNow);
			currentDbPatient.setImportDate(timeNow);
			currentDbPatient.setIncomeCd("NA");
			currentDbPatient.setLanguageCd("NA");
			currentDbPatient.setMaritalStatusCd("NA");
			currentDbPatient.setRaceCd("NA");
			currentDbPatient.setReligionCd("NA");
			currentDbPatient.setSexCd("NA");
			currentDbPatient.setStatecityzipPath("NA");
			currentDbPatient.setUpdateDate(timeNow);
			currentDbPatient.setUploadId(new BigDecimal(0.0d));
			currentDbPatient.setVitalStatusCd("NA");
			currentDbPatient.setZipCd("NA");
			currentDbPatient.setSourcesystemCd(sourcesystemCd);
			i2b2DataSession.saveOrUpdate(currentDbPatient);
			Transaction tx = i2b2DataSession.beginTransaction();
			i2b2DataSession.flush();
			tx.commit();
			i2b2DataSession.clear();
		}
	}

	private void findPatientById(int id) {
		Query query = i2b2DataSession
				.createQuery("from PatientDimension p where p.id like :id and sourcesystemCd like :sourceSystemCd");
		query.setBigDecimal("id", new BigDecimal(id));
		query.setString("sourceSystemCd", sourcesystemCd);
		currentDbPatient = (PatientDimension) query.uniqueResult();
		nullSafeEvict(currentDbPatient);

	}

	private void findVisitById(int id) {
		Query query = i2b2DataSession
				.createQuery("from VisitDimension v where v.id.encounterNum = :id and sourcesystemCd like :sourceSystemCd");
		query.setBigDecimal("id", new BigDecimal(id));
		query.setString("sourceSystemCd", sourcesystemCd);
		currentDbVisit = (VisitDimension) query.uniqueResult();
		nullSafeEvict(currentDbVisit);
	}

	private PatientDimension findPatientByLimsAccession(String accession) {
		Query query = i2b2DataSession
				.createQuery("from PatientDimension p where patientBlob like :accessionPrefix and sourcesystemCd like :sourceSystemCd");
		query.setString("accessionPrefix", accession);
		query.setString("sourceSystemCd", sourcesystemCd);
		PatientDimension patient = (PatientDimension) query.uniqueResult();
		if (patient != null) {
			i2b2DataSession.evict(patient);
		}
		return patient;
	}

	private void findOrCreateVisit(String limsAccession) {
		currentDbVisit = findVisitByLimsAccession(limsAccession);
		if (currentDbVisit == null) {
			currentDbVisit = new VisitDimension();
			VisitDimensionId visitId = new VisitDimensionId();
			visitId.setEncounterNum(new BigDecimal(padId(idCounter++)));
			visitId.setPatientNum(currentDbPatient.getPatientNum());
			currentDbVisit.setId(visitId);
			currentDbVisit.setActiveStatusCd("0");
			currentDbVisit.setDownloadDate(timeNow);
			currentDbVisit.setEndDate(timeNow);
			currentDbVisit.setImportDate(timeNow);
			currentDbVisit.setInoutCd("NA");
			currentDbVisit.setLengthOfStay(new BigDecimal(0.0d));
			currentDbVisit.setLocationCd("NA");
			currentDbVisit.setLocationPath("NA");
			currentDbVisit.setStartDate(timeNow);
			currentDbVisit.setUpdateDate(timeNow);
			currentDbVisit.setUploadId(new BigDecimal(0.0d));
			currentDbVisit.setVisitBlob(limsAccession);
			currentDbVisit.setSourcesystemCd(sourcesystemCd);
			i2b2DataSession.saveOrUpdate(currentDbVisit);
			Transaction tx = i2b2DataSession.beginTransaction();
			i2b2DataSession.flush();
			tx.commit();
			i2b2DataSession.clear();
		}
	}

	private VisitDimension findVisitByLimsAccession(String accession) {
		Query query = i2b2DataSession
				.createQuery("from VisitDimension v where visitBlob like :accessionPrefix and sourcesystemCd like :sourceSystemCd");
		query.setString("accessionPrefix", accession);
		query.setString("sourceSystemCd", sourcesystemCd);
		VisitDimension visit = (VisitDimension) query.uniqueResult();
		if (visit != null) {
			i2b2DataSession.evict(visit);
		}
		return visit;
	}

	public void saveOrUpdateEncounterXmi(KbPatient kbPatient,
			KbEncounter kbEncounter, KbSummary xmiSummary) {
		i2b2DataSession = i2b2DataDataSourceManager.getSession();
		findObservationByInstanceNum((long) kbEncounter.getId());
		findPatientById(currentDbObservation.getId().getPatientNum().intValue());
		findVisitById(currentDbObservation.getId().getEncounterNum().intValue());
		findOrCreateProvider("XmesoProvider:xmeso");
		findOrCreateConcept("XmesoConcept:coded");
		currentDbModifier = currentDbObservation.getId().getModifierCd();
		findObservationByDimensions();
		currentDbObservation.setObservationBlob(xmiSummary.getPath());
		Transaction tx = i2b2DataSession.beginTransaction();
		i2b2DataSession.saveOrUpdate(currentDbObservation);
		i2b2DataSession.flush();
		tx.commit();
	}

	public long findCtakesIdForEncounter(KbEncounter kbEncounter) {
		i2b2DataSession = i2b2DataDataSourceManager.getSession();
		findObservationByInstanceNum((long) kbEncounter.getId());
		currentDbModifier = currentDbObservation.getId().getModifierCd();
		findObservationByDimensions(currentDbObservation.getId()
				.getPatientNum(), currentDbObservation.getId()
				.getEncounterNum(), cTakesDbProvider.getId().getProviderId(),
				codedDbConcept.getConceptCd(), currentDbObservation.getId()
						.getModifierCd());
		return currentDbObservation.getId().getInstanceNum();
	}

	public long findObservationInstanceNumberForForm(KbPatient patient,
			String provider) {
		i2b2DataSession = i2b2DataDataSourceManager.getSession();
		currentDbProvider = findProviderByNameChar(provider);
		String hql = "from ObservationFact o where ";
		hql += " o.id.patientNum = :patientNum and ";
		hql += " o.id.providerId = :providerId and ";
		hql += " o.sourcesystemCd = :sourceSystemCd ";
		Query query = i2b2DataSession.createQuery(hql);
		query.setBigDecimal("patientNum", new BigDecimal(patient.getId()));
		query.setString("providerId", currentDbProvider.getId().getProviderId());
		query.setString("sourceSystemCd", sourcesystemCd);
		ObservationFact observation = (ObservationFact) query.uniqueResult();
		long result = (observation != null) ? observation.getId()
				.getInstanceNum() : -1L;
		return result;
	}

	private void nullSafeEvict(Object dbObject) {
		if (dbObject != null) {
			i2b2DataSession.evict(dbObject);
		}
	}

	private String padId(long idAsLong) {
		return StringUtils.leftPad(idAsLong + "", 12, "0");
	}

	public List<KbPatient> getKbPatients() {
		return kbPatients;
	}

	public void setKbPatients(List<KbPatient> kbPatients) {
		this.kbPatients = kbPatients;
	}

}
