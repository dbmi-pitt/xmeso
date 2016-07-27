package edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

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
		Properties properties = new Properties();
		
		// Get the xmeso.home path from application.properties
		try {
			File file = new File("application.properties");
			FileInputStream fileInput = new FileInputStream(file);
			
			properties.load(fileInput);
			fileInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String xmesoHome = properties.getProperty("xmeso.home");

		//System.out.println("Input data folder path: " + xmesoHome);
		
		File xmesoPropertiesFile = new File(xmesoHome + File.separator + "xmeso.properties");
		try {
			dbPropertiesUrl = xmesoPropertiesFile.toURI().toURL();
		} catch (MalformedURLException e) {
			dbPropertiesUrl = null;
		}
		return super.getSession();
	}

}
