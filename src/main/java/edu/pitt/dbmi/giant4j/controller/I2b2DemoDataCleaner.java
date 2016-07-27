package edu.pitt.dbmi.giant4j.controller;

import org.hibernate.SQLQuery;

import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.I2b2DemoDataSourceManager;

public class I2b2DemoDataCleaner {
	
	private I2b2DemoDataSourceManager dataSourceMgr;
	private String sourceSystemCd;


	public static void main(String[] args) {
		I2b2DemoDataCleaner cleaner = new I2b2DemoDataCleaner();
		cleaner.execute();

	}
	
	public void execute() {
		
		dataSourceMgr.getSession().clear();

		String sql = "delete from XMESO_OBSERVATION_FACT where SOURCESYSTEM_CD = :sourceSystemCd";
		SQLQuery sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		sqlUpdate.executeUpdate();

		sql = "delete from CONCEPT_DIMENSION where SOURCESYSTEM_CD = :sourceSystemCd";
		sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		sqlUpdate.executeUpdate();
		
		sql = "delete from PROVIDER_DIMENSION where SOURCESYSTEM_CD = :sourceSystemCd";
		sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		sqlUpdate.executeUpdate();

		sql = "delete from VISIT_DIMENSION where SOURCESYSTEM_CD = :sourceSystemCd";
		sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		sqlUpdate.executeUpdate();

		sql = "delete from PATIENT_DIMENSION where SOURCESYSTEM_CD = :sourceSystemCd";
		sqlUpdate = dataSourceMgr.getSession().createSQLQuery(sql);
		sqlUpdate.setString("sourceSystemCd", getSourceSystemCd());
		sqlUpdate.executeUpdate();
	}

	public I2b2DemoDataSourceManager getDataSourceMgr() {
		return dataSourceMgr;
	}

	public void setDataSourceMgr(I2b2DemoDataSourceManager dataSourceMgr) {
		this.dataSourceMgr = dataSourceMgr;
	}
	
	public String getSourceSystemCd() {
		return sourceSystemCd;
	}

	public void setSourceSystemCd(String sourceSystemCd) {
		this.sourceSystemCd = sourceSystemCd;
	}


}
