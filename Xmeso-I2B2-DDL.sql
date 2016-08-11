--------------------------------------------------------
--  File created - Thursday-August-11-2016   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table XMESO_CONCEPT_DIMENSION
--------------------------------------------------------

  CREATE TABLE "XMESO_CONCEPT_DIMENSION" 
   (	"CONCEPT_PATH" VARCHAR2(2100), 
	"CONCEPT_CD" VARCHAR2(150), 
	"NAME_CHAR" VARCHAR2(4000), 
	"CONCEPT_BLOB" CLOB, 
	"UPDATE_DATE" DATE, 
	"DOWNLOAD_DATE" DATE, 
	"IMPORT_DATE" DATE, 
	"SOURCESYSTEM_CD" VARCHAR2(150), 
	"UPLOAD_ID" NUMBER(38,0)
   ) ;
--------------------------------------------------------
--  DDL for Table XMESO_OBSERVATION_FACT
--------------------------------------------------------

  CREATE TABLE "XMESO_OBSERVATION_FACT" 
   (	"ENCOUNTER_NUM" NUMBER(38,0), 
	"PATIENT_NUM" NUMBER(38,0), 
	"CONCEPT_CD" VARCHAR2(50), 
	"PROVIDER_ID" VARCHAR2(50), 
	"START_DATE" DATE, 
	"MODIFIER_CD" VARCHAR2(100), 
	"INSTANCE_NUM" NUMBER(18,0), 
	"VALTYPE_CD" VARCHAR2(50), 
	"TVAL_CHAR" VARCHAR2(255), 
	"NVAL_NUM" NUMBER(18,5), 
	"VALUEFLAG_CD" VARCHAR2(50), 
	"QUANTITY_NUM" NUMBER(18,5), 
	"UNITS_CD" VARCHAR2(50), 
	"END_DATE" DATE, 
	"LOCATION_CD" VARCHAR2(50), 
	"OBSERVATION_BLOB" CLOB, 
	"CONFIDENCE_NUM" NUMBER(18,5), 
	"UPDATE_DATE" DATE, 
	"DOWNLOAD_DATE" DATE, 
	"IMPORT_DATE" DATE, 
	"SOURCESYSTEM_CD" VARCHAR2(50), 
	"UPLOAD_ID" NUMBER(38,0)
   ) ;
--------------------------------------------------------
--  DDL for Table XMESO_PATIENT_DIMENSION
--------------------------------------------------------

  CREATE TABLE "XMESO_PATIENT_DIMENSION" 
   (	"PATIENT_NUM" NUMBER(38,0), 
	"VITAL_STATUS_CD" VARCHAR2(50), 
	"BIRTH_DATE" DATE, 
	"DEATH_DATE" DATE, 
	"SEX_CD" VARCHAR2(50), 
	"AGE_IN_YEARS_NUM" NUMBER(38,0), 
	"LANGUAGE_CD" VARCHAR2(50), 
	"RACE_CD" VARCHAR2(50), 
	"MARITAL_STATUS_CD" VARCHAR2(50), 
	"RELIGION_CD" VARCHAR2(50), 
	"ZIP_CD" VARCHAR2(10), 
	"STATECITYZIP_PATH" VARCHAR2(700), 
	"INCOME_CD" VARCHAR2(50), 
	"PATIENT_BLOB" CLOB, 
	"UPDATE_DATE" DATE, 
	"DOWNLOAD_DATE" DATE, 
	"IMPORT_DATE" DATE, 
	"SOURCESYSTEM_CD" VARCHAR2(50), 
	"UPLOAD_ID" NUMBER(38,0)
   ) ;
--------------------------------------------------------
--  DDL for Table XMESO_PROVIDER_DIMENSION
--------------------------------------------------------

  CREATE TABLE "XMESO_PROVIDER_DIMENSION" 
   (	"PROVIDER_ID" VARCHAR2(50), 
	"PROVIDER_PATH" VARCHAR2(700), 
	"NAME_CHAR" VARCHAR2(850), 
	"PROVIDER_BLOB" CLOB, 
	"UPDATE_DATE" DATE, 
	"DOWNLOAD_DATE" DATE, 
	"IMPORT_DATE" DATE, 
	"SOURCESYSTEM_CD" VARCHAR2(50), 
	"UPLOAD_ID" NUMBER(38,0)
   ) ;
--------------------------------------------------------
--  DDL for Table XMESO_VISIT_DIMENSION
--------------------------------------------------------

  CREATE TABLE "XMESO_VISIT_DIMENSION" 
   (	"ENCOUNTER_NUM" NUMBER(38,0), 
	"PATIENT_NUM" NUMBER(38,0), 
	"ACTIVE_STATUS_CD" VARCHAR2(50), 
	"START_DATE" DATE, 
	"END_DATE" DATE, 
	"INOUT_CD" VARCHAR2(50), 
	"LOCATION_CD" VARCHAR2(50), 
	"LOCATION_PATH" VARCHAR2(900), 
	"LENGTH_OF_STAY" NUMBER(38,0), 
	"VISIT_BLOB" CLOB, 
	"UPDATE_DATE" DATE, 
	"DOWNLOAD_DATE" DATE, 
	"IMPORT_DATE" DATE, 
	"SOURCESYSTEM_CD" VARCHAR2(50), 
	"UPLOAD_ID" NUMBER(38,0)
   ) ;
--------------------------------------------------------
--  Constraints for Table XMESO_CONCEPT_DIMENSION
--------------------------------------------------------

  ALTER TABLE "XMESO_CONCEPT_DIMENSION" MODIFY ("CONCEPT_PATH" NOT NULL ENABLE);
 
  ALTER TABLE "XMESO_CONCEPT_DIMENSION" MODIFY ("CONCEPT_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table XMESO_OBSERVATION_FACT
--------------------------------------------------------

  ALTER TABLE "XMESO_OBSERVATION_FACT" MODIFY ("ENCOUNTER_NUM" NOT NULL ENABLE);
 
  ALTER TABLE "XMESO_OBSERVATION_FACT" MODIFY ("PATIENT_NUM" NOT NULL ENABLE);
 
  ALTER TABLE "XMESO_OBSERVATION_FACT" MODIFY ("CONCEPT_CD" NOT NULL ENABLE);
 
  ALTER TABLE "XMESO_OBSERVATION_FACT" MODIFY ("PROVIDER_ID" NOT NULL ENABLE);
 
  ALTER TABLE "XMESO_OBSERVATION_FACT" MODIFY ("START_DATE" NOT NULL ENABLE);
 
  ALTER TABLE "XMESO_OBSERVATION_FACT" MODIFY ("MODIFIER_CD" NOT NULL ENABLE);
 
  ALTER TABLE "XMESO_OBSERVATION_FACT" MODIFY ("INSTANCE_NUM" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table XMESO_PATIENT_DIMENSION
--------------------------------------------------------

  ALTER TABLE "XMESO_PATIENT_DIMENSION" MODIFY ("PATIENT_NUM" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table XMESO_PROVIDER_DIMENSION
--------------------------------------------------------

  ALTER TABLE "XMESO_PROVIDER_DIMENSION" MODIFY ("PROVIDER_ID" NOT NULL ENABLE);
 
  ALTER TABLE "XMESO_PROVIDER_DIMENSION" MODIFY ("PROVIDER_PATH" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table XMESO_VISIT_DIMENSION
--------------------------------------------------------

  ALTER TABLE "XMESO_VISIT_DIMENSION" MODIFY ("ENCOUNTER_NUM" NOT NULL ENABLE);
 
  ALTER TABLE "XMESO_VISIT_DIMENSION" MODIFY ("PATIENT_NUM" NOT NULL ENABLE);
