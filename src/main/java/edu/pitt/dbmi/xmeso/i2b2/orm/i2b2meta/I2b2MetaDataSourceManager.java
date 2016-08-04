package edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta;


import java.io.File;
import java.net.MalformedURLException;

import org.hibernate.Session;

import edu.pitt.dbmi.xmeso.i2b2.orm.I2b2DataSourceManager;

public class I2b2MetaDataSourceManager extends I2b2DataSourceManager {
	
	protected void addAnnotatedClasses() {
        configuration.addAnnotatedClass(XmesoOntology.class);
        configuration.addAnnotatedClass(NcatsDemographics.class);
        configuration.addAnnotatedClass(NcatsIcd9Diag.class);
        configuration.addAnnotatedClass(NcatsIcd9Proc.class);
        configuration.addAnnotatedClass(NcatsVisitDetails.class);
        configuration.addAnnotatedClass(NmvbMesothelioma.class);      
        configuration.addAnnotatedClass(OntProcessStatus.class);
        configuration.addAnnotatedClass(TableAccess.class);
    }

	public Session getSession() {
		// Get the i2b2 related info from the application.properties
		File xmesoPropertiesFile = new File("i2b2metadata.properties");
		try {
			dbPropertiesUrl = xmesoPropertiesFile.toURI().toURL();
		} catch (MalformedURLException e) {
			dbPropertiesUrl = null;
		}
		return super.getSession();
	}
}
