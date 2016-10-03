--------------------------------------------------------
--  DDL for Table XMESO_REPORT_INFO
--------------------------------------------------------

  CREATE TABLE "XMESO_REPORT_INFO" 
   (	"REPORT_ID" VARCHAR2(150),
	"REPORT_FILENAME" VARCHAR2(150), 
	"REPORT_DATE" DATE
   ) ;
--------------------------------------------------------
--  DDL for Table XMESO_REPORT_CASE_LEVEL
--------------------------------------------------------

  CREATE TABLE "XMESO_REPORT_CASE_LEVEL" 
   (	"REPORT_ID" VARCHAR2(150),
	"LYMPH_NODES_EXAMINED" VARCHAR2(150), 
	"SPECIAL_STAINS" VARCHAR2(150), 
	"ULTRASTRUCTURAL_FINDINGS" VARCHAR2(150)
   ) ;
   
--------------------------------------------------------
--  DDL for Table XMESO_REPORT_PART_LEVEL
--------------------------------------------------------

  CREATE TABLE "XMESO_REPORT_PART_LEVEL" 
   (	"REPORT_ID" VARCHAR2(150),
	"PART_LABEL" NUMBER(38,0), 
	"SITE_OF_TUMOR" VARCHAR2(150), 
	"HISTOLOGICAL_TYPE" VARCHAR2(150),
	"TUMOR_CONFIGURATION" VARCHAR2(150),
	"TUMOR_DIFFERENTIATION_OR_GRADE" VARCHAR2(150)
   ) ;
   
--------------------------------------------------------
--  Constraints for Table XMESO_REPORT_INFO
--------------------------------------------------------

  ALTER TABLE "XMESO_REPORT_INFO" MODIFY ("REPORT_ID" NOT NULL ENABLE);
  
--------------------------------------------------------
--  Constraints for Table XMESO_REPORT_CASE_LEVEL
--------------------------------------------------------

  ALTER TABLE "XMESO_REPORT_CASE_LEVEL" MODIFY ("REPORT_ID" NOT NULL ENABLE);
  
--------------------------------------------------------
--  Constraints for Table XMESO_REPORT_PART_LEVEL
--------------------------------------------------------

  ALTER TABLE "XMESO_REPORT_PART_LEVEL" MODIFY ("REPORT_ID" NOT NULL ENABLE);
  ALTER TABLE "XMESO_REPORT_PART_LEVEL" MODIFY ("PART_LABEL" NOT NULL ENABLE);