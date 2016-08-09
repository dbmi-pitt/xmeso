# About Xmeso

Xmeso is an Information Extraction Program designed to eXtract information predominantly from
the MESOthelioma Surgical Pathology Reports. It has been written to bolster traditional Extract
Translate Load (ETL) approaches for populating Mesothelioma data for the National
Mesothelioma Virtual Bank (NMVB) project. NMVB consists of a federated network of four
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

## Configuration

There is a `application.properties` file in the root directory of this project. In order to run the final jar file, users will need to specify the input data directory. The Xmeso input data directory can be anywhere on the file system accessible from the executable jar. 

````
xmeso_data=C:/Users/zhy19/XMESO_PITT
````

Note: Always use the Unix file separator "/" when working on Windows system. Otherwise you'll have to specify the path as 

````
xmeso_data=C:\\Users\\zhy19\\XMESO_PITT
````

In addition, users will also need to specify their i2b2 related settings.

````
# i2b2 location ontology path and code
location_path=Pittsburgh/Pennsylvania
location_cd=Pennsylvania

# i2b2 source system code
sourcesystem_cd=Xmeso

# i2b2 database connection settings
driver = oracle.jdbc.OracleDriver
dialect = org.hibernate.dialect.Oracle10gDialect
url = jdbc:oracle:thin:@dbmi-i2b2-dev05.dbmi.pitt.edu:1521:XE
user = i2b2demodata
password = demouser
show_sql = false
````

And the input data folder should have the sub-folder/file structure that is similar to the following example:

````
XMESO_PITT/
|-- nmvb_path_report_event_date.csv
|-- reports/
|-- |-- MVB0002_15869.txt
|-- |-- MVB0003_15887.txt
|-- |-- MVB0004_17555.txt
|-- |-- MVB0005_15979.txt
|-- |-- MVB0006_15891.txt
````

The `nmvb_path_report_event_date.csv` contains linkage from the patient report to visit number and visit date:

````
REPORT_ID,NMVB_ID,PATIENT_NUM,EVENT_DATE
15869,MVB0002,0002,1991-12-31
15887,MVB0003,0003,1984-05-10
17555,MVB0004,0004,1987-08-08
15979,MVB0006,0006,1979-02-28
15891,MVB0010,0010,1979-01-10
````

The `reports/` folder contains all the surgical pathology reports. When executing the jar file,  these free text report files will be piped through the UIMA Ruta annotators. Resulting synoptics will populate i2b2 `observation_fact` table as well as appropriate dimension tables.

## Executing the Jar

Open your command line terminal, and go to the directory where this project resides, and type the following command:

````
java -jar xmeso-1.0.0-jar-with-dependencies.jar
````

And this command will have the following sample outputs in the terminal when everything works successfully:

````
Input data folder path: C:\Users\zhy19\XMESO_PITT
Erasing previously added xmeso records (36) from XMESO_OBSERVATION_FACT table
Erasing previously added xmeso records (12) from XMESO_CONCEPT_DIMENSION table
Erasing previously added xmeso records (4) from XMESO_VISIT_DIMENSION table
Successfully processed report #15869
Successfully processed report #15887
Successfully processed report #17555
Successfully processed report #15979
Finished processing all reports.
````
Once finish running, this tool will have all the found information added to the following I2B2 database tables:

- `OBSERVATION_FACT`
- `CONCEPT_DIMENSION`
- `VISIT_DIMENSION`

Then you can do further analysis by referencing the added records with the existing patients.

Note: when rerunning this jar file, all the previously added database records will be erased before adding new records.

## For Developers

You must have the following installed to build/install xmeso:

* Oracle Java 1.8 (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Maven 3.x (https://maven.apache.org/download.cgi)

You'll also need to install the UIMA Ruta component (https://uima.apache.org/ruta.html) to assist development. UIMA Ruta component consists of two major parts: An Analysis Engine, which interprets and executes the rule-based scripting language, and the Eclipse-based tooling (Workbench), which provides various support for developing rules.
