package edu.pitt.dbmi.giant4j.ontology;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.TreeSet;

public class DataDbManager {

	private String CONST_DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private String CONST_DB_URL = "jdbc:oracle:thin:@dbmi-i2b2-dev05.dbmi.pitt.edu:1521:xe";
	private String CONST_DB_USER = "i2b2demodata";
	private String CONST_DB_PASSWORD = "demouser";

	private TreeSet<PartialPath> ontologyConcepts;

	private int patientNum = -1;

	private Connection connection;

	public static void main(String[] args) {
		DataDbManager dataDbManager = new DataDbManager();
		try {
			dataDbManager.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void execute() throws ClassNotFoundException, SQLException {
		Class.forName(CONST_DB_DRIVER);
		connection = openConnection();
		cleanOldRecords();
		insertPatient();
		insertConcepts();
		insertObservations();
		closeConnection();
	}

	private void cleanOldRecords() throws SQLException {
		String sql = null;
		PreparedStatement pStmt = null;

		sql = "delete from OBSERVATION_FACT where SOURCESYSTEM_CD = ?";
		pStmt = connection.prepareStatement(sql);
		pStmt.setString(1, "DEEPPHE");
		pStmt.executeUpdate();
		pStmt.close();

		sql = "delete from CONCEPT_DIMENSION where SOURCESYSTEM_CD = ?";
		pStmt = connection.prepareStatement(sql);
		pStmt.setString(1, "DEEPPHE");
		pStmt.executeUpdate();
		pStmt.close();

		sql = "delete from PATIENT_DIMENSION where SOURCESYSTEM_CD = ?";
		pStmt = connection.prepareStatement(sql);
		pStmt.setString(1, "DEEPPHE");
		pStmt.executeUpdate();
		pStmt.close();
	}

	private void insertPatient() throws SQLException {
		String sql = "";
		sql += "insert into PATIENT_DIMENSION (";
		sql += "PATIENT_NUM,";
		sql += "VITAL_STATUS_CD,";
		sql += "BIRTH_DATE,";
		sql += "DEATH_DATE,";
		sql += "SEX_CD,";
		sql += "AGE_IN_YEARS_NUM,";
		sql += "LANGUAGE_CD,";
		sql += "RACE_CD,";
		sql += "MARITAL_STATUS_CD,";
		sql += "RELIGION_CD,";
		sql += "ZIP_CD,";
		sql += "STATECITYZIP_PATH,";
		sql += "INCOME_CD,";
		sql += "PATIENT_BLOB,";
		sql += "UPDATE_DATE,";
		sql += "DOWNLOAD_DATE,";
		sql += "IMPORT_DATE,";
		sql += "SOURCESYSTEM_CD,";
		sql += "UPLOAD_ID";
		sql += ") values (?";
		for (int cdx = 0; cdx < 18; cdx++) {
			sql += ",?";
		}
		sql += ")";
		PreparedStatement pStmt = connection.prepareStatement(sql);

		pStmt.setInt(1, patientNum); // PATIENT_NUM
		pStmt.setString(2, null); // VITAL_STATUS_CD
		pStmt.setTimestamp(3, new Timestamp((new Date()).getTime())); // BIRTH_DATE
		pStmt.setTimestamp(4, new Timestamp((new Date()).getTime())); // DEATH_DATE
		pStmt.setString(5, "F"); // SEX_CD
		pStmt.setInt(6, 78); // AGE_IN_YEARS_NUM
		pStmt.setString(7, "english"); // LANGUAGE_CD
		pStmt.setString(8, "white"); // RACE_CD
		pStmt.setString(9, "married"); // MARITAL_STATUS_CD
		pStmt.setString(10, "roman catholic"); // RELIGION_CD
		pStmt.setString(11, "02115"); // ZIP_CD
		pStmt.setString(12, "Zip codes\\Massachusetts\\Boston\\02115\\"); // STATECITYZIP_PATH
		pStmt.setString(13, "Medium"); // INCOME_CD
		pStmt.setNull(14, Types.CLOB); // PATIENT_BLOB
		pStmt.setTimestamp(15, new Timestamp((new Date()).getTime())); // UPDATE_DATE
		pStmt.setTimestamp(16, new Timestamp((new Date()).getTime())); // DOWNLOAD_DATE
		pStmt.setTimestamp(17, new Timestamp((new Date()).getTime())); // IMPORT_DATE
		pStmt.setString(18, "DEEPPHE"); // SOURCESYSTEM_CD
		pStmt.setInt(19, patientNum); // UPLOAD_ID
		pStmt.executeUpdate();

		pStmt.close();
	}

	private void insertConcepts() throws SQLException {
		String sql = "";
		sql += "insert into CONCEPT_DIMENSION (";
		sql += "CONCEPT_PATH,";
		sql += "CONCEPT_CD,";
		sql += "NAME_CHAR,";
		sql += "CONCEPT_BLOB,";
		sql += "UPDATE_DATE,";
		sql += "DOWNLOAD_DATE,";
		sql += "IMPORT_DATE,";
		sql += "SOURCESYSTEM_CD,";
		sql += "UPLOAD_ID";
		sql += ") values (?";
		for (int cdx = 0; cdx < 8; cdx++) {
			sql += ",?";
		}
		sql += ")";
		PreparedStatement pStmt = connection.prepareStatement(sql);
		int uploadId = 0;
		for (PartialPath concept : ontologyConcepts) {
			if (concept.getBaseCode() == null) {
				continue;
			}
			pStmt.setString(1, concept.getPath() + "\\"); // CONCEPT_PATH
			pStmt.setString(2, concept.getBaseCode()); // CONCEPT_CD
			pStmt.setString(3, Utilities.extractCname(concept)); // NAME_CHAR
			pStmt.setNull(4, Types.CLOB); // CONCEPT_BLOB
			pStmt.setTimestamp(5, new Timestamp((new Date()).getTime())); // UPDATE_DATE
			pStmt.setTimestamp(6, new Timestamp((new Date()).getTime())); // DOWNLOAD_DATE
			pStmt.setTimestamp(7, new Timestamp((new Date()).getTime())); // IMPORT_DATE
			pStmt.setString(8, "DEEPPHE"); // SOURCESYSTEM_CD
			pStmt.setInt(9, uploadId++); // UPLOAD_ID
			pStmt.executeUpdate();
		}
	}

	private void insertObservations() throws SQLException {
		String sql = "";
		sql += "insert into OBSERVATION_FACT (";
		sql += "ENCOUNTER_NUM,";
		sql += "PATIENT_NUM,";
		sql += "CONCEPT_CD,";
		sql += "PROVIDER_ID,";
		sql += "START_DATE,";
		sql += "MODIFIER_CD,";
		sql += "INSTANCE_NUM,";
		sql += "VALTYPE_CD,";
		sql += "TVAL_CHAR,";
		sql += "NVAL_NUM,"; // 10
		sql += "VALUEFLAG_CD,";
		sql += "QUANTITY_NUM,";
		sql += "UNITS_CD,";
		sql += "END_DATE,";
		sql += "LOCATION_CD,";
		sql += "OBSERVATION_BLOB,";
		sql += "CONFIDENCE_NUM,";
		sql += "UPDATE_DATE,";
		sql += "DOWNLOAD_DATE,";
		sql += "IMPORT_DATE,"; // 20
		sql += "SOURCESYSTEM_CD,";
		sql += "UPLOAD_ID";
		sql += ") values (?";
		for (int cdx = 0; cdx < 21; cdx++) {
			sql += ",?";
		}
		sql += ")";
		System.out.println(sql);
		PreparedStatement pStmt = connection.prepareStatement(sql);
		
		for (PartialPath concept : getOntologyConcepts()) {
			if (concept.getBaseCode() == null) {
				continue;
			}
			pStmt.setInt(1, 1); // ENCOUNTER_NUM
			pStmt.setInt(2, patientNum); // PATIENT_NUM
			pStmt.setString(3, concept.getBaseCode()); // CONCEPT_CD
			pStmt.setString(4, "DEEPPHE"); // PROVIDER_ID
			pStmt.setTimestamp(5, new Timestamp((new Date()).getTime())); // START_DATE
			pStmt.setString(6, "@"); // MODIFIER_CD
			pStmt.setInt(7, 1); // INSTANCE_NUM
			pStmt.setString(8, "@"); // VALTYPE_CD
			pStmt.setString(9, "@"); // TVAL_CHAR
			pStmt.setInt(10, 0); // NVAL_NUM
			pStmt.setString(11, "@"); // VALUEFLAG_CD
			pStmt.setInt(12, 1); // QUANTITY_NUM
			pStmt.setString(13, "@"); // UNITS_CD
			pStmt.setTimestamp(14, new Timestamp((new Date()).getTime())); // END_DATE
			pStmt.setString(15, "@"); // LOCATION_CD
			pStmt.setNull(16, Types.CLOB); // OBSERVATION_BLOB
			pStmt.setFloat(17, 1.0f); // CONFIDENCE_NUM
			pStmt.setTimestamp(18, new Timestamp((new Date()).getTime())); // UPDATE_DATE
			pStmt.setTimestamp(19, new Timestamp((new Date()).getTime())); // DOWNLOAD_DATE
			pStmt.setTimestamp(20, new Timestamp((new Date()).getTime())); // IMPORT_DATE
			pStmt.setString(21, "DEEPPHE"); // SOURCESYSTEM_CD
			pStmt.setNull(22, Types.NUMERIC); // UPLOAD_ID
			pStmt.executeUpdate();
		}

		pStmt.close();
	}

	private Connection openConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(CONST_DB_URL,
				CONST_DB_USER, CONST_DB_PASSWORD);
		return connection;
	}

	private void closeConnection() throws SQLException {
		connection.close();
	}

	public TreeSet<PartialPath> getOntologyConcepts() {
		return ontologyConcepts;
	}

	public void setOntologyConcepts(TreeSet<PartialPath> ontologyConcepts) {
		this.ontologyConcepts = ontologyConcepts;
	}

	public void setPatientNum(int patientNum) {
		this.patientNum = patientNum;

	}

}
