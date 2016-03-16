/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author kjm84
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.XmesoOntologyTest.class,
    edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.OntProcessStatusTest.class,
    edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.TableAccessTest.class})
public class OrmMetaTestSuite {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
