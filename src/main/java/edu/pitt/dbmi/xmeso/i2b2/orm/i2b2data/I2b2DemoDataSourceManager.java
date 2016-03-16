package edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data;

import org.hibernate.Session;

import edu.pitt.dbmi.xmeso.i2b2.orm.I2b2DataSourceManager;

public class I2b2DemoDataSourceManager extends I2b2DataSourceManager {
	
	protected void addAnnotatedClasses() {
		configuration.addAnnotatedClass(ObservationFact.class);
		configuration.addAnnotatedClass(ProviderDimension.class);
		configuration.addAnnotatedClass(VisitDimension.class);
		configuration.addAnnotatedClass(PatientDimension.class);
		configuration.addAnnotatedClass(ConceptDimension.class);

	}
	
	public Session getSession() {
		dbPropertiesUrl = getClass().getResource("i2b2demodata.properties");
		return super.getSession();
	}

}
