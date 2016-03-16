/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author kjm84
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ObservationFactTest.class,
    edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ProviderDimensionTest.class,
    edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.VisitDimensionTest.class,
    edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.PatientDimensionTest.class,
    edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ConceptDimensionTest.class})
public class OrmDataTestSuite {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
