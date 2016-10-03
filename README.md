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
| Special Stains (FISH) | Tumor Differentiation |
| | Site of Tumor |

## Creating Database Schema and Tables

Before jumping to the configuration section, we'll first need to have the database schema and required i2b2 tables created. In your Oracle database server, create a schema first (you can decide the schema name), then run the [DDL](Xmeso-I2B2-DDL.sql) to create the required tables as follow:

- `XMESO_PROVIDER_DIMENSION`
- `XMESO_OBSERVATION_FACT`
- `XMESO_CONCEPT_DIMENSION`
- `XMESO_VISIT_DIMENSION`
- `XMESO_PATIENT_DIMENSION`

These tables have the same structure as the formal i2b2 tables without the `XMESO_` prefix. But you won't see all the constraints since they are used for data staging purpose. 

Among these five tables, leave `XMESO_OBSERVATION_FACT` and `XMESO_VISIT_DIMENSION` blank, because these are the two tables we'll actually add the extracted information into. Also don't import any data into the `XMESO_PROVIDER_DIMENSION` table, this program will add One record into it, and we'll cover this in the configuration section. 

You SHOULD load data from your existing `CONCEPT_DIMENSION` table and `PATIENT_DIMENSION` table into the two corresponding `XMESO_` tables. If there's any patient that doesn't exist, this program will create fake patient record into the `XMESO_PATIENT_DIMENSION` table. Similarly, if there's new concept code found during the information extraction process but this concept code is not already in the `CONCEPT_DIMENSION` table, this new record will be added to the `XMESO_CONCEPT_DIMENSION` table (with incomplete information).

Note: once you have the data loaded into `XMESO_PATIENT_DIMENSION` table and `XMESO_CONCEPT_DIMENSION` table, you'll also need to change the `sourcesystem_cd` value to the one that you configured in the configuration section. It's mainly used as an data source identifier, so we know all the related table records are used for this specific program.

Finally, your DBA will need to migrate the resulting data from these staging tables to the corresponding i2b2 production tables.

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

In addition, users will also need to specify their i2b2 related settings. It's required to use Oracle 10g or later to run the i2b2 database. When connecting to the i2b2 database from this Xmeso, you'll need to specify the Oracle hostname, port number, as well as the service name. The suggested url format is `jdbc:oracle:thin:@//[HOST][:PORT]/SERVICE`

Talk to your DBA if don't know the actual Oracle database settings.

````
# It's required to use Oracle 10g and later
# Format: jdbc:oracle:thin:@//[HOST][:PORT]/SERVICE
i2b2.url=jdbc:oracle:thin:@//dbmi-i2b2-dev05.dbmi.pitt.edu:1521/XE
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
|-- |-- Report_file_1.txt
|-- |-- Report_file_2.txt
|-- |-- Report_file_3.txt
|-- |-- Report_file_4.txt
````

The `linkage.csv` (stick with this file name) contains linkage information about the patient report, NMVB_ID, patient number,visit date, as well as the actual report file name. The NMVB_ID column is only used for easy reference, it's totally fine if we don't have this column. And the report file name is used to find the actual report file in the `reports` folder. The file name can be anything, it's only used to link the report file with corresponding row in this linkage file.

Note: you have to stick with this header names and their order exactly as this:

````
REPORT_ID,NMVB_ID,PATIENT_NUM,EVENT_DATE,REPORT_FILE
````

Here is the sample linkage content:

````
REPORT_ID,NMVB_ID,PATIENT_NUM,EVENT_DATE,REPORT_FILE
15869,MVB0002,1000000083,1991-12-31,Report_file_1.txt
15887,MVB0003,1000000084,1984-05-10,Report_file_2.txt
17555,MVB0004,1000000085,1987-08-08,Report_file_3.txt
15979,MVB0006,1000000086,1979-02-28,Report_file_4.txt
````

Note: the `EVENT_DATE` must be in the format of "YYYY-MM-DD".

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
Input data folder path: C:\Users\joe\XMESO_PITT

Created provider record in XMESO_PROVIDER_DIMENSION table

Total 4 report files will be processed

[1/4] Processing report file Report_file_1.txt
Created visit record for patient #1000000083 in XMESO_VISIT_DIMENSION table
Successfully processed report file Report_file_1.txt and added extracted information to XMESO_OBSERVATION_FACT table

[2/4] Processing report file Report_file_2.txt
Created visit record for patient #1000000084 in XMESO_VISIT_DIMENSION table
Successfully processed report file Report_file_2.txt and added extracted information to XMESO_OBSERVATION_FACT table

[3/4] Processing report file Report_file_3.txt
Created visit record for patient #1000000085 in XMESO_VISIT_DIMENSION table
Successfully processed report file Report_file_3.txt and added extracted information to XMESO_OBSERVATION_FACT table

[4/4] Processing report file Report_file_4.txt
Created visit record for patient #1000000086 in XMESO_VISIT_DIMENSION table
Successfully processed report file Report_file_4.txt and added extracted information to XMESO_OBSERVATION_FACT table

Finished processing all reports.
Newly added 1 xmeso records into XMESO_PROVIDER_DIMENSION table
Newly added 4 xmeso records into XMESO_VISIT_DIMENSION table
Newly added 44 xmeso records into XMESO_OBSERVATION_FACT table
````
Once finish running, new records will be added to the following I2B2 database staging tables:

- `XMESO_PROVIDER_DIMENSION` - only one record of the specified provider information
- `XMESO_VISIT_DIMENSION` - all visit information, basically one visit per report
- `XMESO_OBSERVATION_FACT` - all the found observation facts

As we have mentioned earlier, it's possible that new patient record will be added if that patient doesn't exist in the loaded records. Similarly new concept code will be added as well if it doesn't exist in the pre-loaded table.

Note: this Jar is supposed to be executed for only once. If you rerun it, make sure to repeat all the previous steps.

## For Developers

You must have the following installed to build/install xmeso:

* [Oracle Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3.x](https://maven.apache.org/download.cgi)
* [Eclipse 4.4 (Luna)](https://eclipse.org/downloads/packages/eclipse-ide-java-developers/lunasr2)

You'll also need to install the [UIMA Ruta component](https://uima.apache.org/ruta.html) to assist development. UIMA Ruta component consists of two major parts: An Analysis Engine, which interprets and executes the rule-based scripting language, and the Eclipse-based tooling (Workbench), which provides various support for developing rules.
