package edu.pitt.dbmi.xmeso.i2b2;

import java.io.File;
import java.net.MalformedURLException;

import org.hibernate.Session;

import edu.pitt.dbmi.xmeso.i2b2.I2b2DataSourceManager;
import edu.pitt.dbmi.xmeso.i2b2.orm.ConceptDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.ObservationFact;
import edu.pitt.dbmi.xmeso.i2b2.orm.PatientDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.ProviderDimension;
import edu.pitt.dbmi.xmeso.i2b2.orm.VisitDimension;

public class I2b2DemoDataSourceManager extends I2b2DataSourceManager {
	
	protected void addAnnotatedClasses() {
		configuration.addAnnotatedClass(ObservationFact.class);
		configuration.addAnnotatedClass(ProviderDimension.class);
		configuration.addAnnotatedClass(VisitDimension.class);
		configuration.addAnnotatedClass(PatientDimension.class);
		configuration.addAnnotatedClass(ConceptDimension.class);

	}
	
	public Session getSession() {
		// Get the i2b2 related info from the application.properties
		File xmesoPropertiesFile = new File("application.properties");
		try {
			dbPropertiesUrl = xmesoPropertiesFile.toURI().toURL();
		} catch (MalformedURLException e) {
			dbPropertiesUrl = null;
		}
		return super.getSession();
	}

}
