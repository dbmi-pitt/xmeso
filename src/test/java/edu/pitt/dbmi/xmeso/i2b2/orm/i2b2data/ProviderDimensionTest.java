/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class ProviderDimensionTest {

    private I2b2DemoDataSourceManager i2b2DataDataSourceManager;

    private String sourceSystemCd = "Xmeso";
    
    public ProviderDimensionTest() {
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
    public void testProviderDimensionCRUD() {
        System.out.println("testCreate");
        Session session = i2b2DataDataSourceManager.getSession();
        assumeTrue(session != null);
        final String[] doctors = { "X", "Y", "Z" };
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

        System.out.println(
                "testRead");
        
        providerDimensions = session.createQuery("from ProviderDimension p where sourcesystemCd = 'Xmeso'").setMaxResults(10).list();
        assertEquals(providerDimensions.size(), 3);

        System.out.println(
                "testUpdate");

        query = session.createQuery("from ProviderDimension p where sourcesystemCd = :sourceSystemCd");
        query.setString(
                "sourceSystemCd", sourceSystemCd);
        providerDimensions = query.list();
        for (ProviderDimension providerDimension : providerDimensions) {
            providerDimension.setNameChar("Dr. W");
            session.saveOrUpdate(providerDimension);
        }

        session.flush();
        query = session.createQuery("from ProviderDimension p where nameChar like :nameChar and sourcesystemCd = :sourceSystemCd");

        query.setString(
                "nameChar", "Dr. W");
        query.setString(
                "sourceSystemCd", sourceSystemCd);
        providerDimensions = query.list();

        assertEquals(providerDimensions.size(), 3);

        System.out.println(
                "testDelete");

        query = session.createQuery("from ProviderDimension p where nameChar like :nameChar and sourcesystemCd like :sourceSystemCd");

        query.setString(
                "nameChar", "Dr. W");
        query.setString(
                "sourceSystemCd", sourceSystemCd);
        providerDimensions = query.list();
        for (ProviderDimension providerDimension : providerDimensions) {
            session.delete(providerDimension);
        }

        session.flush();
        query = session.createQuery("from ProviderDimension p where sourcesystemCd like :sourceSystemCd");

        query.setString(
                "sourceSystemCd", sourceSystemCd);
        providerDimensions = query.list();

        assertEquals(providerDimensions.size(), 0);
    }

}

