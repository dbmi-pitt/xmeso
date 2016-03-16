/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta;

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
public class OntProcessStatusTest {

    private I2b2MetaDataSourceManager i2b2MetaDataSourceManager;

    public OntProcessStatusTest() {
    }

    @Before
    public void setUp() {
        i2b2MetaDataSourceManager = new I2b2MetaDataSourceManager();
    }

    @After
    public void tearDown() {
        i2b2MetaDataSourceManager.destroy();
        i2b2MetaDataSourceManager = null;
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testOntProcessStatusCRUD() {
    	
        System.out.println("testCreate");
        
        Session session = i2b2MetaDataSourceManager.getSession();
        assumeTrue(session != null);
        
        for (int deepPheNum = 2000; deepPheNum < 2010; deepPheNum++) {
            Date timeNow = new Date();
            OntProcessStatus ontProcessStatus = new OntProcessStatus();
            ontProcessStatus.setChangeDate(timeNow);
            ontProcessStatus.setChangedbyChar("NA");
            ontProcessStatus.setCrcUploadId(BigDecimal.ZERO);
            ontProcessStatus.setEndDate(timeNow);
            ontProcessStatus.setEntryDate(timeNow);
            ontProcessStatus.setMessage(null);
            ontProcessStatus.setProcessId(deepPheNum);
            ontProcessStatus.setProcessStatusCd("NA");
            ontProcessStatus.setProcessStepCd("NA");
            ontProcessStatus.setProcessTypeCd("NA");
            ontProcessStatus.setStartDate(timeNow);
            ontProcessStatus.setStatusCd("NA");
            session.saveOrUpdate(ontProcessStatus);
        }
        session.flush();

        System.out.println("After creation flush....");

        Query query;
        query = session.createQuery("from OntProcessStatus o where processId >= :pidLower AND processId < :pidUpper");
        query.setInteger("pidLower", 2000);
        query.setInteger("pidUpper", 2010);
        List<OntProcessStatus> ontProcessStatuses = query.list();
        assertEquals(ontProcessStatuses.size(), 10);

        System.out.println("testRead");
        ontProcessStatuses = session.createQuery("from OntProcessStatus o").setMaxResults(10).list();
        assertEquals(ontProcessStatuses.size(), 10);

        System.out.println("testUpdate");

        query = session.createQuery("from OntProcessStatus o where processId >= :pidLower AND processId < :pidUpper");
        query.setInteger("pidLower", 2000);
        query.setInteger("pidUpper", 2005);
        ontProcessStatuses = query.list();
        for (OntProcessStatus ontProcessStatus : ontProcessStatuses) {
            ontProcessStatus.setProcessStatusCd("FINISHED");
            session.saveOrUpdate(ontProcessStatus);
        }
        session.flush();
        query = session.createQuery("from OntProcessStatus o where processStatusCd = :processStatusCd");
        query.setString("processStatusCd", "FINISHED");
        ontProcessStatuses = query.list();
        assertEquals(ontProcessStatuses.size(), 5);

        System.out.println("testDelete");

        query = session.createQuery("from OntProcessStatus o where processId >= :pidLower AND processId < :pidUpper");
        query.setInteger("pidLower", 2000);
        query.setInteger("pidUpper", 2010);
        ontProcessStatuses = query.list();
        for (OntProcessStatus ontProcessStatus : ontProcessStatuses) {
            session.delete(ontProcessStatus);
        }
        session.flush();
        query = session.createQuery("from OntProcessStatus o where processId >= :pidLower AND processId < :pidUpper");
        query.setInteger("pidLower", 2000);
        query.setInteger("pidUpper", 2010);
        ontProcessStatuses = query.list();
        assertEquals(ontProcessStatuses.size(), 0);

    }

}
