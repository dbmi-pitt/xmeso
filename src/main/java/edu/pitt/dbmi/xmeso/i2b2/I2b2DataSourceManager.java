/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.dbmi.xmeso.i2b2;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

public abstract class I2b2DataSourceManager {

	private static final Logger logger = LoggerFactory.getLogger(I2b2DataSourceManager.class);

	protected String hibernateDialect;
	protected String hibernateDriver;
	protected String hibernateConnectionUrl;
	protected String hibernateUserName;
	protected String hibernateUserPassword;

	// Very dangerous to have this set to create or update. Only available for
	// explicit use by the installer or other utility methods. Must always
	// default to null to avoid accidental database modifications.
	protected String hibernateHbm2ddlAuto = "none";

	protected String hibernateShowSql = "false";
	protected String hibernateDefaultSchema = "false";
	protected String hibernateCacheUseSecondLevelCache = "false";

	protected Configuration configuration;

	protected SessionFactory sessionFactory;

	protected Session session;
	
	protected URL dbPropertiesUrl;

	public I2b2DataSourceManager() {
	}

	protected void buildConfiguration() {

		try {
			Properties dbProperties = new Properties();
			FileReader reader = new FileReader(dbPropertiesUrl.getPath());
			dbProperties.load(reader);

			/* Sample i2b2 connection settings
			 *  
			 *  hibernate.driver = oracle.jdbc.OracleDriver
				hibernate.dialect = org.hibernate.dialect.Oracle10gDialect
				hibernate.url = jdbc:oracle:thin:@dbmi-i2b2-dev05.dbmi.pitt.edu:1521:XE
				hibernate.user = 
				hibernate.password = 
				hibernate.show_sql = false
			 */
			setHibernateDriver(dbProperties.getProperty("hibernate.driver", oracle.jdbc.OracleDriver.class.getName()));
			setHibernateDialect(dbProperties.getProperty("hibernate.dialect", org.hibernate.dialect.Oracle10gDialect.class.getName()));
			// No need to specify the default values
			setHibernateConnectionUrl(dbProperties.getProperty("hibernate.url"));
			setHibernateUserName(dbProperties.getProperty("hibernate.user"));
			setHibernateUserPassword(dbProperties.getProperty("hibernate.password"));
			// Set default as true
			setHibernateShowSql(dbProperties.getProperty("hibernate.show_sql", "true"));
			
			// Set default schema
			setHibernateDefaultSchema(dbProperties.getProperty("hibernate.default_schema"));

			configuration = new Configuration();
			configuration.setProperty("hibernate.dialect", getHibernateDialect());
			configuration.setProperty("hibernate.connection.driver_class", getHibernateDriver());
			configuration.setProperty("hibernate.show_sql", getHibernateShowSql());
			configuration.setProperty("hibernate.connection.url", getHibernateConnectionUrl());
			configuration.setProperty("hibernate.connection.username", getHibernateUserName());
			configuration.setProperty("hibernate.connection.password", getHibernateUserPassword());
			configuration.setProperty("hibernate.cache.use_second_level_cache", getHibernateCacheUseSecondLevelCache());

			if (getHibernateHbm2ddlAuto() != null) {
				configuration.setProperty("hibernate.hbm2ddl.auto", getHibernateHbm2ddlAuto());
			}
			addAnnotatedClasses();

		} catch (IOException x) {
			System.err.println("Error connecting to I2B2 database.");
		}

	}

	/**
	 * get or create configuration object to further customize it before calling
	 * getSession()
	 *
	 * @return
	 */
	public Configuration getConfiguration() {
		if (configuration == null) {
			buildConfiguration();
		}
		return configuration;
	}

	protected abstract void addAnnotatedClasses(); 

	public void addAnnotatedClsesForPackage() {
		logger.debug("Entered addAnnotatedClsesForPackage for " + getClass().getName());
		
		try {
			String pkgName = getClass().getPackage().getName();
			ClassPath classPath = ClassPath.from(getClass().getClassLoader());
			final ArrayList<Class<?>> clses = new ArrayList<Class<?>>();
			Set<ClassInfo> clsInfos = classPath.getTopLevelClassesRecursive(pkgName);
			for (ClassInfo clsInfo : clsInfos) {
				if (!clsInfo.getName().equals(getClass().getName())) {
					clses.add(clsInfo.load());
				}
			}
			if (clses.isEmpty()) {
				logger.error("Failed to load hibernate classes");
			} else {
				for (Class<?> cls : clses) {
//					System.err.println("Configuring " + cls.getName()
//							+ " into Hibernate");
//					logger.info("Configuring " + cls.getName()
//							+ " into Hibernate");
					configuration.addAnnotatedClass(cls);
				}
			}
		} catch (IOException e) {
		}
		logger.debug("Exited addAnnotatedClsesForPackage for " + getClass().getName());
	}

	/*
	 * SessionFactory
	 */
	protected boolean buildSessionFactory(Configuration configuration) {
		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(ssrb.build());
		return !sessionFactory.isClosed();
	}

	/*
	 * Session
	 */
	public Session getSession() {
		try {
			if (session == null) {
				if (configuration == null) {
					buildConfiguration();
				}
				if (sessionFactory == null) {
					buildSessionFactory(configuration);
				}

				session = sessionFactory.openSession();
			}
		} catch (Exception x) {
			System.err.println("Failed to get database session.");
			session = null;
		}
		return session;
	}

	/*
	 * Clean up
	 */
	protected void closeSession() throws HibernateException {
		if (session != null && session.isOpen()) {
			session.close();
			session = null;
		}
	}

	protected void closeSessionFactory() throws HibernateException {
		if (sessionFactory != null && !sessionFactory.isClosed()) {
			sessionFactory.close();
		}
		sessionFactory = null;
	}

	public void destroy() {
		closeSession();
		closeSessionFactory();
		logger.debug("destroyed " + getClass().getName() + " : session and sessionFactory removed.");
	}

	@Override
	protected void finalize() throws Throwable {
		destroy();
		super.finalize();
	}

	public String getHibernateDialect() {
		return hibernateDialect;
	}

	public void setHibernateDialect(String paramHibernateDialect) {
		this.hibernateDialect = paramHibernateDialect;
	}

	public String getHibernateDriver() {
		return hibernateDriver;
	}

	public void setHibernateDriver(String hibernateDriver) {
		this.hibernateDriver = hibernateDriver;
	}

	public String getHibernateConnectionUrl() {
		return hibernateConnectionUrl;
	}

	public void setHibernateConnectionUrl(String hibernateConnectionUrl) {
		this.hibernateConnectionUrl = hibernateConnectionUrl;
	}

	public String getHibernateUserName() {
		return hibernateUserName;
	}

	public void setHibernateUserName(String hibernateUserName) {
		this.hibernateUserName = hibernateUserName;
	}

	public String getHibernateUserPassword() {
		return hibernateUserPassword;
	}

	public void setHibernateUserPassword(String hibernateUserPassword) {
		this.hibernateUserPassword = hibernateUserPassword;
	}

	public String getHibernateHbm2ddlAuto() {
		return hibernateHbm2ddlAuto;
	}

	public void setHibernateHbm2ddlAuto(String hibernateHbm2ddlAuto) {
		this.hibernateHbm2ddlAuto = hibernateHbm2ddlAuto;
	}

	public String getHibernateShowSql() {
		return hibernateShowSql;
	}

	public void setHibernateShowSql(String hibernateShowSql) {
		this.hibernateShowSql = hibernateShowSql;
	}

	public String getHibernateDefaultSchema() {
		return hibernateDefaultSchema;
	}

	public void setHibernateDefaultSchema(String hibernateDefaultSchema) {
		this.hibernateDefaultSchema = hibernateDefaultSchema;
	}
	
	public String getHibernateCacheUseSecondLevelCache() {
		return hibernateCacheUseSecondLevelCache;
	}

	public void setHibernateCacheUseSecondLevelCache(String hibernateCacheUseSecondLevelCache) {
		this.hibernateCacheUseSecondLevelCache = hibernateCacheUseSecondLevelCache;
	}

}
