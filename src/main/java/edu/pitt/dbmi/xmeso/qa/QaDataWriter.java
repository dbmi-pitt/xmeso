package edu.pitt.dbmi.xmeso.qa;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

import edu.pitt.dbmi.xmeso.qa.QaDataSourceManager;
import edu.pitt.dbmi.xmeso.qa.orm.ReportCaseLevel;
import edu.pitt.dbmi.xmeso.qa.orm.ReportInfo;

public class QaDataWriter {

	private QaDataSourceManager dataSourceManager;

	/**
	 * Pass the dataSourceManagre instance
	 * 
	 * @param dataSourceManager
	 */
    public QaDataWriter(QaDataSourceManager dataSourceManager) {
		super();

		this.dataSourceManager = dataSourceManager;
	}

	/**
     * Erase old Xmeso records before inserting new one
     */
    public void cleanOldRecordsIfExist() {
    	eraseOldRecordsIfExist("XMESO_REPORT_INFO");
    	eraseOldRecordsIfExist("XMESO_REPORT_CASE_LEVEL");
	}
    
    /**
     * Erase database table records, if old records exist
     * 
     * @param tableName
     */
	public void eraseOldRecordsIfExist(String tableName) {
		// These are SQL, NOT Hibernate Query Language (HQL) queries
		// Actual table name and field name are used, instead of object and properties
		String sql = String.format("select count(*) from %s", tableName);
		SQLQuery q = dataSourceManager.getSession().createSQLQuery(sql);
		Long count = ((BigDecimal) q.uniqueResult()).longValue();

		if (count > 0) {
			// Let users know that the old records will be erased when they rerun the jar
			String output = String.format("Erasing %d previously added xmeso records from %s table", count, tableName);
			System.out.println(output);
			
			sql = String.format("truncate table %s", tableName);
			q = dataSourceManager.getSession().createSQLQuery(sql);
			q.executeUpdate();
		}
	}

	public void resultsSummary() {
		displayRowsAffected("XMESO_REPORT_INFO");
		displayRowsAffected("XMESO_REPORT_CASE_LEVEL");
	}
	
	public void displayRowsAffected(String tableName) {
		// These are SQL, NOT Hibernate Query Language (HQL) queries
		// Actual table name and field name are used, instead of object and properties
		String sql = String.format("select count(*) from %s", tableName);
		SQLQuery q = dataSourceManager.getSession().createSQLQuery(sql);
		Long count = ((BigDecimal) q.uniqueResult()).longValue();

		if (count > 0) {
			// Let users know how many new rows added
			String output = String.format("Newly added %d xmeso records into %s table", count, tableName);
			System.out.println(output);
		} else {
			String output = String.format("NO xmeso record added into %s table", tableName);
			System.out.println(output);
		}
	}
	
	public void createReportInfo(int reportId, String reportFilename, Date reportDate) {
		// Create new report info record
		ReportInfo reportInfo = new ReportInfo();
		
		reportInfo.setReportId(new BigDecimal(reportId));
		reportInfo.setReportFilename(reportFilename);
		reportInfo.setReportDate(reportDate);

		// Transaction
		Transaction tx = dataSourceManager.getSession().beginTransaction();
		dataSourceManager.getSession().saveOrUpdate(reportInfo);
		dataSourceManager.getSession().flush();
		tx.commit();
	}
	
	public void createReportCaseLevel(int reportId, String lymphNodesExamed, String specialStains, String ultrastructuralFindings) {
		// Create new report case level record
		ReportCaseLevel reportCaseLevel = new ReportCaseLevel();
		
		reportCaseLevel.setReportId(new BigDecimal(reportId));
		reportCaseLevel.setLymphNodesExamed(lymphNodesExamed);
		reportCaseLevel.setSpecialStains(specialStains);
		reportCaseLevel.setUltrastructuralFindings(ultrastructuralFindings);
		
		// Transaction
		Transaction tx = dataSourceManager.getSession().beginTransaction();
		dataSourceManager.getSession().saveOrUpdate(reportCaseLevel);
		dataSourceManager.getSession().flush();
		tx.commit();
	}

}