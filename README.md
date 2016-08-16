# About Xmeso

Xmeso is an Information Extraction Program designed to eXtract information predominantly from
the MESOthelioma Surgical Pathology Reports. It has been written to bolster traditional Extract
Translate Load (ETL) approaches for populating Mesothelioma data for the ]National
Mesothelioma Virtual Bank (NMVB)](https://mesotissue.org/) project. NMVB consists of a federated network of four
cooperating institutions PITT, NYU, RPCI, and UPENN. The underlying architecture for
federated query is based on Shrine connectivity between i2b2 instances housed within each
participating covered site. This architecture is similar to that used for ACT and PCORI networks
designed by PITT Bioinformatics.

Xmeso is implemented as a hybrid system of Apache Ruta Scripts and Apache Uima Java Text
Annotators. Ruta is essentially a Java Annotator under the hood but the Ruta Scripting
language is unique in its expressivity.

At this development stage, this Xmeso tool will extract six Data Elements over the report set:

| Case Level | Part Level |
| --- | --- |
| Ultrastructural Findings | Histopathologic Type |
| Lymph Nodes Examined | Tumor Configuration |
| Special Stain Profile | Tumor Differentiation |

## Creating Database Schema and Tables

Before jumping to the configuration section, we'll first need to have the database schema and required i2b2 tables created. In your Oracle database server, create a schema first (you can decide the schema name), then run the [DDL](Xmeso-I2B2-DDL.sql) to create the required tables as follow:

- `XMESO_PROVIDER_DIMENSION`
- `XMESO_OBSERVATION_FACT`
- `XMESO_CONCEPT_DIMENSION`
- `XMESO_VISIT_DIMENSION`
- `XMESO_PATIENT_DIMENSION`

These tables have the same structure as the formal i2b2 tables without the `XMESO_` prefix. But you won't see all the constraints since they are used for data staging purpose. Your DBA will need to migrate the resulting data from these staging tables to the corresponding i2b2 production tables.

Note: you can either load the patient records directly into the `XMESO_PATIENT_DIMENSION` table, or leave it blank. If you leave it blank, fake patient records (but using the actual patient number found in the linkage file) will be created.

## Configuration

There is a `xmeso.properties` file in the root directory of this project. In order to run the final jar file, users will need to specify the input data directory absolute path. The Xmeso input data directory can be anywhere on the file system accessible from the executable jar. 

On Unix/Linux/Mac machine, you may have something like this:

````
xmeso_data=/home/joe/XMESO_PITT
````

On Windows machine, you should always use the Unix file separator "/" as well, for example:

````
xmeso_data=C:/Users/joe/XMESO_PITT
````

Otherwise you'll have to specify the path as:

````
xmeso_data=C:\\Users\\joe\\XMESO_PITT
````

The next thing that users may need to change is the provider information and the source system code:

````
# provider info
provider_id=XMESO:Pathology
# Must use \\
provider_path=\\i2b2\\Providers\\XMESO\\Pathology
provider_name_char=Pathology

# source system code
sourcesystem_cd=Xmeso
````
The above provider info is used to create ONE (only one) record in the `XMESO_PROVIDER_DIMENSION` table. Nothing fancy. And we assume all the reports are from this same provider.

And the `sourcesystem_cd` value is mainly used as an data source identifier, so we know all the results from running this Xmeso program are different from other existing data records.

In addition, users will also need to specify their i2b2 related settings.

````
# It's required to use Oracle 10g and later
i2b2.hostname=dbmi-i2b2-dev05.dbmi.pitt.edu
i2b2.port=1521
# i2b2 database schema
# this schema contains the following tables (don't ever change the table name): 
# - XMESO_OBSERVATION_FACT
# - XMESO_PATIENT_DIMENSION
# - XMESO_VISIT_DIMENSION
# - XMESO_CONCEPT_DIMENSION
# - XMESO_PROVIDER_DIMENSION
i2b2.schema=I2B2DEMODATA
# i2b2 database username and password
i2b2.user=i2b2demodata
i2b2.password=demouser
````

And the input data folder should have the sub-folder/file structure that is similar to the following example:

````
XMESO_PITT/
|-- linkage.csv
|-- reports/
|-- |-- MVB0002_15869.txt
|-- |-- MVB0003_15887.txt
|-- |-- MVB0004_17555.txt
|-- |-- MVB0006_15891.txt
````

The `linkage.csv` contains linkage from the patient report to visit number and visit date:

````
REPORT_ID,NMVB_ID,PATIENT_NUM,EVENT_DATE
15869,MVB0002,0002,1991-12-31
15887,MVB0003,0003,1984-05-10
17555,MVB0004,0004,1987-08-08
15979,MVB0006,0006,1979-02-28
````

The `reports/` folder contains all the surgical pathology reports. When executing the jar file,  these free text report files will be piped through the UIMA Ruta annotators. Resulting synoptics will populate i2b2 `XMESO_OBSERVATION_FACT` table as well as appropriate dimension tables.

## Executing the Jar

Open your command line terminal, and go to the directory where this project resides, and type `java -version` to verify that you have Java Runtime Environment (JRE) installed on your computer. Otherwise, you'll need to install it.

````
$ java -version
java version "1.8.0_92"
Java(TM) SE Runtime Environment (build 1.8.0_92-b14)
Java HotSpot(TM) 64-Bit Server VM (build 25.92-b14, mixed mode)
````

Then you can type the following command to execute this Jar file:

````
java -jar xmeso-1.0.0-jar-with-dependencies.jar
````

And this command will have the following sample outputs in the terminal when everything works successfully:

````
Input data folder path: C:\Users\zhy19\XMESO_PITT
Created provider record in XMESO_PROVIDER_DIMENSION table
Processing report file MVB0002_15869.txt
Created fake patient information for patient #2 in XMESO_PATIENT_DIMENSION table
Created visit record for patient #2 in XMESO_VISIT_DIMENSION table
Successfully processed report file MVB0002_15869.txt and added extracted information to XMESO_CONCEPT_DIMENSION and XMESO_OBSERVATION_FACT table
Processing report file MVB0003_15887.txt
Created fake patient information for patient #3 in XMESO_PATIENT_DIMENSION table
Created visit record for patient #3 in XMESO_VISIT_DIMENSION table
Successfully processed report file MVB0003_15887.txt and added extracted information to XMESO_CONCEPT_DIMENSION and XMESO_OBSERVATION_FACT table
Processing report file MVB0004_17555.txt
Created fake patient information for patient #4 in XMESO_PATIENT_DIMENSION table
Created visit record for patient #4 in XMESO_VISIT_DIMENSION table
Successfully processed report file MVB0004_17555.txt and added extracted information to XMESO_CONCEPT_DIMENSION and XMESO_OBSERVATION_FACT table
Processing report file MVB0006_15979.txt
Created fake patient information for patient #6 in XMESO_PATIENT_DIMENSION table
Created visit record for patient #6 in XMESO_VISIT_DIMENSION table
Successfully processed report file MVB0006_15979.txt and added extracted information to XMESO_CONCEPT_DIMENSION and XMESO_OBSERVATION_FACT table
Finished processing all reports.
Newly added 1 xmeso records into XMESO_PROVIDER_DIMENSION table
Newly added 36 xmeso records into XMESO_OBSERVATION_FACT table
Newly added 12 xmeso records into XMESO_CONCEPT_DIMENSION table
Newly added 4 xmeso records into XMESO_VISIT_DIMENSION table
````
Once finish running, new records will be added to the following I2B2 database staging tables:

- `XMESO_PROVIDER_DIMENSION` - only one record of the specified provider information
- `XMESO_OBSERVATION_FACT` - all the found observation facts
- `XMESO_CONCEPT_DIMENSION` - all the found concepts
- `XMESO_VISIT_DIMENSION` - all visit information, basically one visit per report

Note: the table records that are added to `XMESO_PATIENT_DIMENSION`, `XMESO_CONCEPT_DIMENSION`, and `XMESO_PROVIDER_DIMENSION` are only for staging purpose to main the constraints across those 5 tables. This program will create fake patient records (but using the actual patient number found in the linkage file) into `XMESO_PATIENT_DIMENSION`. And that one record added to `XMESO_PROVIDER_DIMENSION` is based on the provider information settings that are specified in the `xmeso.properties`. And therecords added to the `XMESO_CONCEPT_DIMENSION` table don't need to be copied to the production table.

Then you can do further analysis by referencing the added records with the existing patients.

Note: when rerunning this jar file, all the previously added database records will be erased (except the patient records) before adding new records. And you will see the following additional message:

````
Input data folder path: C:\Users\zhy19\XMESO_PITT
Erasing 1 previously added xmeso records from XMESO_PROVIDER_DIMENSION table
Erasing 36 previously added xmeso records from XMESO_OBSERVATION_FACT table
Erasing 12 previously added xmeso records from XMESO_CONCEPT_DIMENSION table
Erasing 4 previously added xmeso records from XMESO_VISIT_DIMENSION table
Created provider record in XMESO_PROVIDER_DIMENSION table
Processing report file MVB0002_15869.txt
Created visit record for patient #2 in XMESO_VISIT_DIMENSION table
Successfully processed report file MVB0002_15869.txt and added extracted information to XMESO_CONCEPT_DIMENSION and XMESO_OBSERVATION_FACT table
Processing report file MVB0003_15887.txt
Created visit record for patient #3 in XMESO_VISIT_DIMENSION table
Successfully processed report file MVB0003_15887.txt and added extracted information to XMESO_CONCEPT_DIMENSION and XMESO_OBSERVATION_FACT table
Processing report file MVB0004_17555.txt
Created visit record for patient #4 in XMESO_VISIT_DIMENSION table
Successfully processed report file MVB0004_17555.txt and added extracted information to XMESO_CONCEPT_DIMENSION and XMESO_OBSERVATION_FACT table
Processing report file MVB0006_15979.txt
Created visit record for patient #6 in XMESO_VISIT_DIMENSION table
Successfully processed report file MVB0006_15979.txt and added extracted information to XMESO_CONCEPT_DIMENSION and XMESO_OBSERVATION_FACT table
Finished processing all reports.
Newly added 1 xmeso records into XMESO_PROVIDER_DIMENSION table
Newly added 36 xmeso records into XMESO_OBSERVATION_FACT table
Newly added 12 xmeso records into XMESO_CONCEPT_DIMENSION table
Newly added 4 xmeso records into XMESO_VISIT_DIMENSION table
````

## For Developers

You must have the following installed to build/install xmeso:

* [Oracle Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3.x](https://maven.apache.org/download.cgi)
* [Eclipse 4.4 (Luna)](https://eclipse.org/downloads/packages/eclipse-ide-java-developers/lunasr2)

You'll also need to install the [UIMA Ruta component](https://uima.apache.org/ruta.html) to assist development. UIMA Ruta component consists of two major parts: An Analysis Engine, which interprets and executes the rule-based scripting language, and the Eclipse-based tooling (Workbench), which provides various support for developing rules.
