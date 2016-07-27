# About Xmeso

Xmeso is an Information Extraction Program designed to eXtract information predominantly from
the MESOthelioma Surgical Pathology Reports. It has been written to bolster traditional Extract
Translate Load (ETL) approaches for populating Mesothelioma data for the National
Mesothelioma Virtual Bank (NMVB) project. NMVB consists of a federated network of four
cooperating institutions PITT, NYU, RPCI, and UPENN. The underlying architecture for
federated query is based on Shrine connectivity between i2b2 instances housed within each
participating covered site. This architecture is similar to that used for ACT and PCORI networks
designed by PITT Bioinformatics.

## Configuration

There is a `application.properties` file in the root directory of this project. In order to run the final uber jar file, users will need to specify the input data directory:

````
xmeso.home=C:\\Users\\zhy19\\XMESO_NYU
````

Note: The `\\` must be used when working on Windows system.

The Xmeso input data directory can be anywhere on the file system accessible from the executable
jar. And the input data folder should have the sub-folder/file structure that is similar to the following example:

````
XMESO_NYU/
|-- nmvb_path_report_event_date.csv
|-- reports/
|-- |-- MVB0001_00001.txt
|-- |-- MVB0002_00002.txt
|-- |-- MVB0003_00003.txt
|-- |-- MVB0004_00004.txt
|-- |-- MVB0005_00005.txt
|-- xmeso.properties
nmvb_path_report_event_date.csv
reports/
xmeso.properties
````

The `nmvb_path_report_event_date.csv` contains linkage from the patient report to visit number and visit date:

````
REPORT_ID,NMVB_ID,PATIENT_NUM,EVENT_DATE
00001,MVB0001,0001,2016-07-07
00002,MVB0002,0002,2016-07-07
00003,MVB0003,0003,2016-07-07
00004,MVB0004,0004,2016-07-07
00005,MVB0005,0005,2016-07-07
````

The `reports/` folder contains all the surgical pathology reports. When executing the jar file,  these free text report files will be piped through the UIMA Ruta annotators. Resulting synoptics will populate i2b2 `observation_fact` table as well as appropriate dimension tables.

And the `xmeso.properties` contains the dataset name (usually PITT, RPCI, UPENN, or NYU) and the i2b2 database connection parameters and i2b2 location ontology path and code":

````
organization=NYU
location_path=New York/New York
location_cd=New York
driver = oracle.jdbc.OracleDriver
dialect = org.hibernate.dialect.Oracle10gDialect
url = jdbc:oracle:thin:@dbmi-i2b2-dev05.dbmi.pitt.edu:1521:XE
user = i2b2demodata
password = demouser
show_sql = false
````

## Running the Uber Jar

Open your command line terminal, and go to the directory where this project resides, and type the following coomand:

````
java -jar xmeso-1.0.0.jar
````

And this command will have the following sample outputs in the terminal when everything works successfully:

````
Input data folder path: C:\Users\zhy19\XMESO_NYU
Jul 27, 2016 3:31:13 PM org.hibernate.annotations.common.Version <clinit>
INFO: HCANN000001: Hibernate Commons Annotations {4.0.1.Final}
Jul 27, 2016 3:31:13 PM org.hibernate.Version logVersion
INFO: HHH000412: Hibernate Core {4.0.0.Final}
Jul 27, 2016 3:31:13 PM org.hibernate.cfg.Environment <clinit>
INFO: HHH000206: hibernate.properties not found
Jul 27, 2016 3:31:13 PM org.hibernate.cfg.Environment buildBytecodeProvider
INFO: HHH000021: Bytecode provider name : javassist
Jul 27, 2016 3:31:13 PM org.hibernate.service.jdbc.connections.internal.DriverManagerConnectionProviderImpl configure
INFO: HHH000402: Using Hibernate built-in connection pool (not for production use!)
Jul 27, 2016 3:31:13 PM org.hibernate.service.jdbc.connections.internal.DriverManagerConnectionProviderImpl configure
INFO: HHH000115: Hibernate connection pool size: 20
Jul 27, 2016 3:31:13 PM org.hibernate.service.jdbc.connections.internal.DriverManagerConnectionProviderImpl configure
INFO: HHH000006: Autocommit mode: false
Jul 27, 2016 3:31:13 PM org.hibernate.service.jdbc.connections.internal.DriverManagerConnectionProviderImpl configure
INFO: HHH000401: using driver [oracle.jdbc.OracleDriver] at URL [jdbc:oracle:thin:@dbmi-i2b2-dev05.dbmi.pitt.edu:1521:XE]
Jul 27, 2016 3:31:13 PM org.hibernate.service.jdbc.connections.internal.DriverManagerConnectionProviderImpl configure
INFO: HHH000046: Connection properties: {user=i2b2demodata, password=****}
Jul 27, 2016 3:31:14 PM org.hibernate.dialect.Dialect <init>
INFO: HHH000400: Using dialect: org.hibernate.dialect.Oracle10gDialect
Jul 27, 2016 3:31:14 PM org.hibernate.engine.transaction.internal.TransactionFactoryInitiator initiateService
INFO: HHH000399: Using default transaction strategy (direct JDBC transactions)
Jul 27, 2016 3:31:14 PM org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory <init>
INFO: HHH000397: Using ASTQueryTranslatorFactory
Successfully processed report #00001
Successfully processed report #00002
Successfully processed report #00003
Successfully processed report #00004
Successfully processed report #00005
Jul 27, 2016 3:31:20 PM org.hibernate.service.jdbc.connections.internal.DriverManagerConnectionProviderImpl stop
INFO: HHH000030: Cleaning up connection pool [jdbc:oracle:thin:@dbmi-i2b2-dev05.dbmi.pitt.edu:1521:XE]
````

