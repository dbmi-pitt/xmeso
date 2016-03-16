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
public class TableAccessTest {
    
    private I2b2MetaDataSourceManager i2b2MetaDataSourceManager;
    
    public TableAccessTest() {
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
    public void testTableAccessCRUD() {
      
    	System.out.println("testCreate");
        
    	Session session = i2b2MetaDataSourceManager.getSession();
        assumeTrue(session != null);
        
        for (int rowIdx = 4002000; rowIdx < 4002010; rowIdx++) {
            Date timeNow = new Date(); 
            TableAccess tableAccess = new TableAccess();
            tableAccess.setCBasecode("NA");
            tableAccess.setCChangeDate(timeNow);
            tableAccess.setCColumndatatype("NA");
            tableAccess.setCColumnname("NA");
            tableAccess.setCComment(null);
            tableAccess.setCDimcode("NA");
            tableAccess.setCDimtablename("NA");
            tableAccess.setCEntryDate(timeNow);
            tableAccess.setCFacttablecolumn("NA");
            tableAccess.setCFullname("NA");
            tableAccess.setCHlevel(BigDecimal.ZERO);
            tableAccess.setCMetadataxml(null);
            tableAccess.setCName("NA");
            tableAccess.setCOperator("NA");
            tableAccess.setCProtectedAccess(Character.MIN_VALUE);
            tableAccess.setCStatusCd(Character.MIN_VALUE);
            tableAccess.setCSynonymCd('c');
            tableAccess.setCTableCd("Xmeso" + rowIdx);
            tableAccess.setCTableName("NA");
            tableAccess.setCTooltip("NA");
            tableAccess.setCTotalnum(BigDecimal.TEN);
            tableAccess.setCVisualattributes("NA");
            tableAccess.setValuetypeCd("NA");        
            session.saveOrUpdate(tableAccess);
        }
        session.flush();
        
        System.out.println("After creation flush....");
        
        Query query;
        query = session.createQuery("from TableAccess o where CTableCd like :CTableCd");
        query.setString("CTableCd", "Xmeso%");
        List<TableAccess> tableAccesss = query.list();
        assertEquals(tableAccesss.size(), 10);

        System.out.println("testRead");
        tableAccesss = session.createQuery("from TableAccess o").setMaxResults(10).list();
        assertEquals(tableAccesss.size(), 10);

        System.out.println("testUpdate");

        query = session.createQuery("from TableAccess o where CFullname like :CFullname AND CTableCd like :CTableCd");
        query.setString("CFullname", "NA");
        query.setString("CTableCd", "Xmeso%");
        tableAccesss = query.list();
        for (TableAccess tableAccess : tableAccesss) {
            tableAccess.setCFullname(tableAccess.getCTableCd());
            session.saveOrUpdate(tableAccess);
        }
        session.flush();
        query = session.createQuery("from TableAccess c where CFullname = CTableCd");
        tableAccesss = query.list();
        assertEquals(tableAccesss.size(), 10);

        System.out.println("testDelete");

        query = session.createQuery("from TableAccess o where CTableCd like :CTableCd");
        query.setString("CTableCd", "Xmeso%");
        tableAccesss = query.list();
        for (TableAccess tableAccess : tableAccesss) {
            session.delete(tableAccess);
        }
        session.flush();
        query = session.createQuery("from TableAccess o where CTableCd like :CTableCd");
        query.setString("CTableCd", "Xmeso%");
        tableAccesss = query.list();
        assertEquals(tableAccesss.size(), 0);

    }

   
    
}
