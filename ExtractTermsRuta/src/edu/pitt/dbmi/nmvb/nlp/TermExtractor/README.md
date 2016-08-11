The ExtractTermsRuta code builds a dictionary of terms for use by Ruta from i2b2's ontology.
The code interfaces with the Unified Medical Language System (UMLS) API

https://documentation.uts.nlm.nih.gov/

** Pre-requisites **

-- dbconf.py --
The code contains a dbconf_example.py file.  This file is an example.  Please copy it
and rename it dbconf.py.  In dbconf.py, you need to set the following values:

- Oracle connection information (username, password, etc.) to connect to your i2b2 metadata schema
- UMLS api key, username and password.  To apply for a UMLS account, please see this link:

https://documentation.uts.nlm.nih.gov/rest/authentication.html

- outputDir the directory where the completed  


-- Python pre-requisites --
You must install the following python modules:
PyQuery
cx_Oracle 
urllib2
requests


-- Running the code --
Use python to execute the __main__ method in TermExtractor.py
The output will contain different messages depending on the codes extracted from i2b2.

"Found new category.  Loading terms for category: XXXX" this indicates the code found a new category of i2b2 terms to load.
"Could not find prefix for basecode: XXXX" this message is caused by some i2b2 terms with 
    basecodes not found in UMLS (ex: PATH|IMMUNH:UN)
"Error retrieving synonyms for concept [XXXXX].  Error: concept" this message is caused by
    i2b2 terms with basecodes not found in UMLS (see above).
"Error retrieving extractSeedConcept: 'NoneType' object has no attribute 'group'" this indicates 
    that the code is attempting to process a top-level node (ex: /SURGICAL MARGINS/).  These
    i2b2 items do not need to be searched.

The code generate several lines when it outputs the data to files (where XXXX is the "category"):

Opening files for output.
Opening file: /Users/chb69/borromeocd/nmvb/nlp_work/ruta_XXXX.txt
Closing file
...
Done.



The code performs the following steps:

1.  Execute a SQL query to retrieve the term list from i2b2's ontology (the metadata schema)
2.  For each item in the i2b2 term list, add it to the Ruta dictionary.
3.  For each item in the i2b2 term list, determine its "category".
4.  For each item in the i2b2 term list, find all synonyms from UMLS.  Add the
       UMLS synonyms to the Ruta dictionary.
5.  After all the items in the i2b2 term list are finished, dump the data into separate 
       semi-colon delimited files for Ruta.  Create one file for each "category".
       
