/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author kjm84
 */
public class PatientDimensionTest {

    private I2b2DemoDataSourceManager i2b2DataDataSourceManager;

    private String sourceSystemCd = "Xmeso";
    
    public PatientDimensionTest() {
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
    public void testPatientDimensionCRUD() {
        System.out.println("testCreate");
        Session session = i2b2DataDataSourceManager.getSession();
        assumeTrue(session != null);
        for (int patNum = 4002000; patNum < 4003000; patNum++) {
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
        assertEquals(patientDimensions.size(), 1000);

        System.out.println("testRead");
        patientDimensions = session.createQuery("from PatientDimension p").setMaxResults(10).list();
        assertEquals(patientDimensions.size(), 10);

        System.out.println("testUpdate");

        query = session.createQuery("from PatientDimension p where ageInYearsNum like :ageInYearsNum and sourcesystemCd = :sourceSystemCd");
        query.setBigDecimal("ageInYearsNum", new BigDecimal(78));
        query.setString("sourceSystemCd", sourceSystemCd);
        patientDimensions = query.list();
        for (PatientDimension patientDimension : patientDimensions) {
            patientDimension.setAgeInYearsNum(new BigDecimal(patientDimension.getAgeInYearsNum().doubleValue() + 1.0d));
            session.saveOrUpdate(patientDimension);
        }
        session.flush();
        query = session.createQuery("from PatientDimension p where ageInYearsNum like :ageInYearsNum and sourcesystemCd = :sourceSystemCd");
        query.setBigDecimal("ageInYearsNum", new BigDecimal(79));
        query.setString("sourceSystemCd", sourceSystemCd);
        patientDimensions = query.list();
        assertEquals(patientDimensions.size(), 1000);

        System.out.println("testDelete");

        query = session.createQuery("from PatientDimension p where ageInYearsNum like :ageInYearsNum and sourcesystemCd like :sourceSystemCd");
        query.setBigDecimal("ageInYearsNum", new BigDecimal(79));
        query.setString("sourceSystemCd", sourceSystemCd);
        patientDimensions = query.list();
        for (PatientDimension patientDimension : patientDimensions) {
            session.delete(patientDimension);
        }
        session.flush();
        query = session.createQuery("from PatientDimension p where sourcesystemCd like :sourceSystemCd");
        query.setString("sourceSystemCd", sourceSystemCd);
        patientDimensions = query.list();
        assertEquals(patientDimensions.size(), 0);
    }

}
