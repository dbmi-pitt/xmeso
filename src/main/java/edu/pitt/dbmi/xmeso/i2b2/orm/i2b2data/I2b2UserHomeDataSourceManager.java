package edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data;

import java.io.File;
import java.net.MalformedURLException;

import org.hibernate.Session;

import edu.pitt.dbmi.xmeso.i2b2.orm.I2b2DataSourceManager;

public class I2b2UserHomeDataSourceManager extends I2b2DataSourceManager {

	@Override
	protected void addAnnotatedClasses() {
		configuration.addAnnotatedClass(ObservationFact.class);
		configuration.addAnnotatedClass(ProviderDimension.class);
		configuration.addAnnotatedClass(VisitDimension.class);
		configuration.addAnnotatedClass(PatientDimension.class);
		configuration.addAnnotatedClass(ConceptDimension.class);
	}
	
	public Session getSession() {
		String userHome = System.getProperty("user.home");
		String xmesoHome = userHome + File.separator + "xmeso";
		File xmesoPropertiesFile = new File(xmesoHome + File.separator + "xmeso.properties");
		try {
			dbPropertiesUrl = xmesoPropertiesFile.toURI().toURL();
		} catch (MalformedURLException e) {
			dbPropertiesUrl = null;
		}
		return super.getSession();
	}

}
