package edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta;


import org.hibernate.Session;

import edu.pitt.dbmi.xmeso.i2b2.orm.I2b2DataSourceManager;

public class I2b2MetaDataSourceManager extends I2b2DataSourceManager {
	
	protected void addAnnotatedClasses() {
        configuration.addAnnotatedClass(XmesoOntology.class);
        configuration.addAnnotatedClass(OntProcessStatus.class);
        configuration.addAnnotatedClass(TableAccess.class);
    }
	
	public Session getSession() {
		dbPropertiesUrl = getClass().getResource("i2b2metadata.properties");
		return super.getSession();
	}

}
