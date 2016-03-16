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
public class XmesoOntologyTest {
    
    private I2b2MetaDataSourceManager i2b2MetaDataSourceManager;
    
    public XmesoOntologyTest() {
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
    public void testXmesoOntologyCRUD() {
      
    	System.out.println("testCreate");
      
        Session session = i2b2MetaDataSourceManager.getSession();
        assumeTrue(session != null);
        
        for (int rowIdx = 4002000; rowIdx < 4003000; rowIdx++) {
            Date timeNow = new Date();
            String pathPrefix = "/some/arbitrary/path1/";
            if (rowIdx % 2 == 1) {
                pathPrefix = "/some/arbitrary/path2/";
            }
            XmesoOntology ontology = new XmesoOntology();
            ontology.setCBasecode("NA");
            ontology.setCColumndatatype("NA");
            ontology.setCColumnname(pathPrefix);
            ontology.setCComment(null);
            ontology.setCDimcode("NA");
            ontology.setCFacttablecolumn("NA");
            ontology.setCFullname(pathPrefix + "Xmeso" + rowIdx);
            ontology.setCHlevel(BigDecimal.ZERO);
            ontology.setCMetadataxml(null);
            ontology.setCName("NA");
            ontology.setCOperator("NA");
            ontology.setCPath("NA");
            ontology.setCSymbol("NA");
            ontology.setCSynonymCd("NA");
            ontology.setCTablename("NA");
            ontology.setCTooltip("NA");
            ontology.setCTotalnum(BigDecimal.TEN);
            ontology.setCVisualattributes("NA");
            ontology.setDownloadDate(timeNow);
            ontology.setImportDate(timeNow);
            ontology.setMAppliedPath("NA");
            ontology.setMExclusionCd("NA");
            ontology.setSourcesystemCd("Xmeso");
            ontology.setUpdateDate(timeNow);
            ontology.setValuetypeCd("NA");          
            session.saveOrUpdate(ontology);
        }
        session.flush();
        
        System.out.println("After creation flush....");
        
        Query query;
        query = session.createQuery("from XmesoOntology o where CFullname like :cFullName AND sourcesystemCd like :sourceSystemCd");
        query.setString("cFullName", "%path2%");
        query.setString("sourceSystemCd", "Xmeso");
        List<XmesoOntology> ontologies = query.list();
        assertEquals(ontologies.size(), 500);

        System.out.println("testRead");
        ontologies = session.createQuery("from XmesoOntology o").setMaxResults(10).list();
        assertEquals(ontologies.size(), 10);

        System.out.println("testUpdate");

        query = session.createQuery("from XmesoOntology o where CFullname like :cFullName and sourcesystemCd like :sourceSystemCd");
        query.setString("cFullName", "%path2%");
        query.setString("sourceSystemCd", "Xmeso");
        ontologies = query.list();
        for (XmesoOntology ontology : ontologies) {
            ontology.setCColumnname(ontology.getCColumnname().replaceAll("path2", "path1"));
            session.saveOrUpdate(ontology);
        }
        session.flush();
        query = session.createQuery("from XmesoOntology c where CColumnname like :cColumnName and sourcesystemCd like :sourceSystemCd");
        query.setString("cColumnName", "%path1%");
        query.setString("sourceSystemCd", "Xmeso");
        ontologies = query.list();
        assertEquals(ontologies.size(), 1000);

        System.out.println("testDelete");

        query = session.createQuery("from XmesoOntology o where sourcesystemCd like :sourceSystemCd");
        query.setString("sourceSystemCd", "Xmeso");
        ontologies = query.list();
        for (XmesoOntology ontology : ontologies) {
            session.delete(ontology);
        }
        session.flush();
        query = session.createQuery("from XmesoOntology o where sourcesystemCd like :sourceSystemCd");
        query.setString("sourceSystemCd", "Xmeso");
        ontologies = query.list();
        assertEquals(ontologies.size(), 0);

    }

}
