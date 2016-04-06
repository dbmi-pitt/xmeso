package edu.pitt.dbmi.giant4j.ontology;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetaDataDbManager {

	private String CONST_DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private String CONST_DB_URL = "jdbc:oracle:thin:@dbmi-i2b2-dev05.dbmi.pitt.edu:1521:xe";
	private String CONST_DB_USER = "i2b2metadata";
	private String CONST_DB_PASSWORD = "demouser";

	private Connection connection;

	private String ontologyTableName = "DEEPPHE2_ONTOLOGY";
	private String sourceSystemCode = "DEEPPHE2";
	
	private TreeSet<PartialPath> partialPaths;

	public MetaDataDbManager() {
	}

	public void execute() {
		try {
			Class.forName(CONST_DB_DRIVER);
			connection = openConnection();
			createOrReplaceOntologyTable();
			populateOntologyTableFromPartialPaths();
			deleteOldTableAccessRecords();
			populateTableAccessTable();
			closeConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(I2b2OntologyBuilder.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}
	
	private void createOrReplaceOntologyTable() throws SQLException {
		String sql;
		PreparedStatement pStmt;

		if (existsOntologyTable()) {
			sql = "drop table " + getOntologyTableName();
			pStmt = connection.prepareStatement(sql);
			pStmt.executeUpdate();
			pStmt.close();
		}

		sql = "create table " + getOntologyTableName() +  " as select * from COHORT_ONTOLOGY";
		pStmt = connection.prepareStatement(sql);
		pStmt.executeUpdate();
		pStmt.close();

		sql = "delete from " + getOntologyTableName();
		pStmt = connection.prepareStatement(sql);
		pStmt.executeUpdate();
		pStmt.close();

	}

	private boolean existsOntologyTable() throws SQLException {
		String sql = "SELECT count(*) FROM all_tables where owner = ? and table_name = ?";
		PreparedStatement pStmt;
		pStmt = connection.prepareStatement(sql);
		pStmt.setString(1, "I2B2METADATA");
		pStmt.setString(2, getOntologyTableName());
		int count;
		try (ResultSet rs = pStmt.executeQuery()) {
			count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
		}
		pStmt.close();
		return count > 0;
	}

	private void populateOntologyTableFromPartialPaths() throws SQLException {
		String sql;
		sql = "insert into " + getOntologyTableName() + " (";
		sql += "C_HLEVEL,";
		sql += "C_FULLNAME,";
		sql += "C_NAME,";
		sql += "C_SYNONYM_CD,";
		sql += "C_VISUALATTRIBUTES,\n";
		sql += "C_TOTALNUM,";
		sql += "C_BASECODE,";
		sql += "C_METADATAXML,";
		sql += "C_FACTTABLECOLUMN,";
		sql += "C_TABLENAME,\n";
		sql += "C_COLUMNNAME,";
		sql += "C_COLUMNDATATYPE,";
		sql += "C_OPERATOR,";
		sql += "C_DIMCODE,";
		sql += "C_COMMENT,\n";
		sql += "C_TOOLTIP,";
		sql += "M_APPLIED_PATH,";
		sql += "UPDATE_DATE,";
		sql += "DOWNLOAD_DATE,";
		sql += "IMPORT_DATE,\n";
		sql += "SOURCESYSTEM_CD,";
		sql += "VALUETYPE_CD,";
		sql += "M_EXCLUSION_CD,";
		sql += "C_PATH,";
		sql += "C_SYMBOL";
		sql += ") values (";
		for (int idx = 0; idx < 24; idx++) {
			sql += "?,";
		}
		sql += "?)";	
		System.out.println(sql);
		
		PreparedStatement pStmt = connection.prepareStatement(sql);
		for (PartialPath partialPath : partialPaths) {
			pStmt.setInt(1, partialPath.getLevel()); // C_HLEVEL
			pStmt.setString(2, partialPath.getPath() + "\\"); // C_FULLNAME
			pStmt.setString(3, extractCname(partialPath)); // C_NAME
			pStmt.setString(4, "N"); // C_SYNONYM_CD
			if (partialPath.isLeaf()) {
				pStmt.setString(5, "LA "); // C_VISUALATTRIBUTES
			}
			else {
				pStmt.setString(5, "FA "); // C_VISUALATTRIBUTES
			}
			
			pStmt.setInt(6, 0); // C_TOTALNUM
			pStmt.setString(7, partialPath.getBaseCode()); // C_BASECODE
			pStmt.setString(8, null); // C_METADATAXML
			pStmt.setString(9, "concept_cd"); // C_FACTTABLECOLUMN
			pStmt.setString(10, "concept_dimension"); // C_TABLENAME
			pStmt.setString(11, "concept_path"); // C_COLUMNNAME
			pStmt.setString(12, "T"); // C_COLUMNDATATYPE
			pStmt.setString(13, "LIKE"); // C_OPERATOR
			pStmt.setString(14, partialPath.getPath() + "\\"); // C_DIMCODE
			pStmt.setString(15, null); // C_COMMENT
			pStmt.setString(16,
					partialPath.getPath().replaceAll("\\\\", " \\\\ ")
							.substring(1)
							+ "\\"); // C_TOOLTIP
			pStmt.setString(17, "@"); // M_APPLIED_PATH
			java.util.Date timeNow = new java.util.Date();
			pStmt.setDate(18, new Date(timeNow.getTime())); // UPDATE_DATE
			pStmt.setDate(19, new Date(timeNow.getTime())); // DOWNLOAD_DATE
			pStmt.setDate(20, new Date(timeNow.getTime())); // IMPORT_DATE
			pStmt.setString(21, getSourceSystemCode()); // SOURCESYSTEM_CD
			pStmt.setString(22, null); // VALUETYPE_CD
			pStmt.setString(23, null); // M_EXCLUSION_CD
			pStmt.setString(24, null); // C_PATH
			pStmt.setString(25, null); // C_SYMBOL
			pStmt.executeUpdate();
		}
		pStmt.close();
	}


	
	private void deleteOldTableAccessRecords() throws SQLException {
		String sql = "delete from TABLE_ACCESS where C_TABLE_CD = ?";
		PreparedStatement pStmt = connection.prepareStatement(sql);
		pStmt.setString(1, getSourceSystemCode()); // C_TABLE_CD
		pStmt.executeUpdate();
		pStmt.close();
	}

	private void populateTableAccessTable() throws SQLException {
		// Fill in the TABLE_ACCESS table
		
		PreparedStatement pStmt;
		String sql = "insert into TABLE_ACCESS (";
		sql += "C_TABLE_CD,";
		sql += "C_TABLE_NAME,";
		sql += "C_PROTECTED_ACCESS,";
		sql += "C_HLEVEL,";
		sql += "C_FULLNAME,";
		sql += "C_NAME,";
		sql += "C_SYNONYM_CD,";
		sql += "C_VISUALATTRIBUTES,";
		sql += "C_TOTALNUM,";
		sql += "C_BASECODE,";
		sql += "C_METADATAXML,";
		sql += "C_FACTTABLECOLUMN,";
		sql += "C_DIMTABLENAME,";
		sql += "C_COLUMNNAME,";
		sql += "C_COLUMNDATATYPE,";
		sql += "C_OPERATOR,";
		sql += "C_DIMCODE,";
		sql += "C_COMMENT,";
		sql += "C_TOOLTIP,";
		sql += "C_ENTRY_DATE,";
		sql += "C_CHANGE_DATE,";
		sql += "C_STATUS_CD,";
		sql += "VALUETYPE_CD";
		sql += ") values (";
		for (int idx = 0; idx < 22; idx++) {
			sql += "?,";
		}
		sql += "?)";

		pStmt = connection.prepareStatement(sql);
		pStmt.setString(1, getSourceSystemCode()); // C_TABLE_CD
		pStmt.setString(2, getOntologyTableName()); // C_TABLE_NAME
		pStmt.setString(3, "N"); // C_PROTECTED_ACCESS
		pStmt.setInt(4, 1); // C_HLEVEL
		pStmt.setString(5, "\\DEEPPHE2\\"); // C_FULLNAME
		pStmt.setString(6, "DeepPhe2 Ontology"); // C_NAME
		pStmt.setString(7, "N"); // C_SYNONYM_CD
		pStmt.setString(8, "FA "); // C_VISUALATTRIBUTES
		pStmt.setString(9, null); // C_TOTALNUM
		pStmt.setString(10, null); // C_BASECODE
		pStmt.setString(11, null); // C_METADATAXML
		pStmt.setString(12, "concept_cd"); // C_FACTTABLECOLUMN
		pStmt.setString(13, "concept_dimension"); // C_DIMTABLENAME
		pStmt.setString(14, "concept_path"); // C_COLUMNNAME
		pStmt.setString(15, "T"); // C_COLUMNDATATYPE
		pStmt.setString(16, "LIKE"); // C_OPERATOR
		pStmt.setString(17, "\\DEEPPHE2\\"); // C_DIMCODE
		pStmt.setString(18, null); // C_COMMENT
		pStmt.setString(19, "DeepPhe2 Ontology"); // C_TOOLTIP
		pStmt.setString(20, null); // C_ENTRY_DATE
		pStmt.setString(21, null); // C_CHANGE_DATE
		pStmt.setString(22, null); // C_STATUS_CD
		pStmt.setString(23, null); // VALUETYPE_CD
		pStmt.executeUpdate();
		pStmt.close();
		
	}

	private String extractCname(PartialPath partialPath) {
		int lastSlashPos;
		lastSlashPos = partialPath.getPath().lastIndexOf("\\");
		return partialPath.getPath().substring(lastSlashPos + 1);
	}

	private Connection openConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(CONST_DB_URL,
				CONST_DB_USER, CONST_DB_PASSWORD);
		return connection;
	}

	private void closeConnection() throws SQLException {
		connection.close();
	}

	public TreeSet<PartialPath> getPartialPaths() {
		return partialPaths;
	}

	public void setPartialPaths(TreeSet<PartialPath> partialPaths) {
		this.partialPaths = partialPaths;
	}

	public String getOntologyTableName() {
		return ontologyTableName;
	}

	public void setOntologyTableName(String ontologyTableName) {
		this.ontologyTableName = ontologyTableName;
	}

	public String getSourceSystemCode() {
		return sourceSystemCode;
	}

	public void setSourceSystemCode(String sourceSystemCode) {
		this.sourceSystemCode = sourceSystemCode;
	}

}
