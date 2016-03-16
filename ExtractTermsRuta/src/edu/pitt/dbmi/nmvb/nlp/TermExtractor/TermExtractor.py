'''
Created on Feb 29, 2016

@author: chb69
'''
from edu.pitt.dbmi.nmvb.nlp.TermExtractor import dbconf
from Oracle import Oracle
import urllib2
import json


def geti2b2Data(meta_db_obj):
    data_set = []
    con = meta_db_obj.getConnection()
    cursor = con.cursor()
    # just a quick note about the SQL below:
    # I added a select * FROM so I could add an ORDER BY clause
    sql = """
        SELECT * FROM (
        SELECT c_fullname, c_name, c_basecode, 'Immunohistochemical Profile'
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE c_fullname LIKE '\NMVB\MESOTHELIOMA\ANATOMICAL PATHOLOGY\IMMUNOHISTOCHEMICAL PROFILE\%'
        UNION ALL
        SELECT c_fullname, regexp_replace(c_name, '\(|\[|\)|\]',''), c_basecode, 'Immunohistochemical Profile' 
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE regexp_like(c_name, '\(|\[') 
        AND c_fullname LIKE '\NMVB\MESOTHELIOMA\ANATOMICAL PATHOLOGY\IMMUNOHISTOCHEMICAL PROFILE\%'        
        UNION ALL
        SELECT c_fullname, c_name, c_basecode, 'Site of tumor'
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE c_fullname LIKE '\NMVB\MESOTHELIOMA\ANATOMICAL PATHOLOGY\SITE OF TUMOR\%'
        UNION ALL
        SELECT c_fullname, regexp_replace(c_name, '\(|\[|\)|\]',''), c_basecode, 'Site of tumor' 
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE regexp_like(c_name, '\(|\[') 
        AND c_fullname LIKE '\NMVB\MESOTHELIOMA\ANATOMICAL PATHOLOGY\SITE OF TUMOR\%'        
        UNION ALL
        SELECT c_fullname, c_name, c_basecode, 'Surgical Procedure' 
        FROM nmvb_i2b2metadata.NCATS_ICD9_PROC
        UNION ALL
        SELECT c_fullname, regexp_replace(c_name, '\(|\[|\)|\]',''), c_basecode, 'Surgical Procedure' 
        FROM nmvb_i2b2metadata.NCATS_ICD9_PROC
        WHERE regexp_like(c_name, '\(|\[')
        UNION ALL
        SELECT c_fullname, c_name, c_basecode, 'Histological Type'
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE c_fullname LIKE '\HISTOLOGICAL TYPE\%'
        UNION ALL
        SELECT c_fullname, regexp_replace(c_name, '\(|\[|\)|\]',''), c_basecode, 'Histological Type' 
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE regexp_like(c_name, '\(|\[') 
        AND c_fullname LIKE '\HISTOLOGICAL TYPE\%'        
        UNION ALL
        SELECT c_fullname, c_name, c_basecode, 'Tumor Configuration'
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE c_fullname LIKE '\TUMOR CONFIGURATION\%'
        UNION
        SELECT c_fullname, regexp_replace(c_name, '\(|\[|\)|\]',''), c_basecode, 'Tumor Configuration' 
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE regexp_like(c_name, '\(|\[') 
        AND c_fullname LIKE '\TUMOR CONFIGURATION\%'        
        UNION ALL
        SELECT c_fullname, c_name, c_basecode, 'Tumor Differentiation'
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE c_fullname LIKE '\TUMOR DIFFERENTIATION OR GRADE\%'
        AND c_hlevel > 1
        UNION ALL
        SELECT c_fullname, regexp_replace(c_name, '\(|\[|\)|\]',''), c_basecode, 'Tumor Differentiation' 
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE regexp_like(c_name, '\(|\[') 
        AND c_fullname LIKE '\TUMOR DIFFERENTIATION OR GRADE\%'
        AND c_hlevel > 1
        ) ORDER BY 4"""
    
    try:
        cursor.execute(sql)
        rows = cursor.fetchall()
        count = 0
        for row in rows:
            i2b2Data = extracti2b2Concept(row)
            # add a "penalty" to indicate if this is the direct label for the concept (lower score indicates more direct label)
            i2b2Data["penalty"] = 0
            data_set.append(i2b2Data)
            synonymCollection = expandi2b2Concept(i2b2Data)
            count = count + 1
            #if count > 200:
            #    return data_set
            if synonymCollection is not None:
                # add a check here to see if the label matches i2b2Data["label"]
                for syn in synonymCollection:
                    if (syn["label"] != i2b2Data["label"]):
                        syn["basecode"] = str(i2b2Data["basecode"]).strip()
                        syn["path"] = str(i2b2Data["path"])
                        syn["category"] = i2b2Data["category"]
                        #print syn
                        data_set.append(syn)
            
    except Exception as e:
        print "Error retrieving i2b2 concepts: {0}".format(e.message)
    return data_set

def extracti2b2Concept(row):
    new_record = {"path" : row[0], "label": row[1], "basecode": str(row[2]).strip(), "category": row[3]}
    return new_record

def expandi2b2Concept(i2b2_concept):
    returnList = []
    # first, extract the basecode to determine which code is being used 
    # second, use the actual code (ex: 123.45, 23454322, etc.) to search bioportal
    basecode = str(i2b2_concept["basecode"]).strip()
    if basecode is None:
        return []
    idx = str.find(basecode, ':')
    if idx == -1:
        print 'Error, cannot find : in string: ' + basecode
        return []
    codingSystem = basecode[0:idx]
    code = basecode[idx+1:]
    #print codingSystem + " : " + code
    bioportalCode = ""
    if codingSystem == "ICD9":
        bioportalCode = "ICD9CM"
    if codingSystem == "SNOMED":
        bioportalCode= "SNOMEDCT"
    # skip records that we don't want to search in Bioportal
    if bioportalCode == "":
        return
    #print codingSystem + " [" + code + "] " + " { " + bioportalCode + " } "
    returnList = getSynonyms("5ccc9223-d513-4b94-99af-619d2a3eb1c4", bioportalCode, code)
    return returnList

"""
this consists of 2 steps:
1.  find the cui for a given code using the Bioportal code (SNOMEDCT, ICD9CM) and the actual code (123.45, 32454234)
2.  for ICD9 codes using this cui, find the cui record which should contain synonymous terms
"""
def getSynonyms(apikey, bioportalCode, code):
    synonymList = []
    # maintain a list of the labels added for this code
    currentLabelList = []
    #step 1:
    bioportalURL = "http://data.bioontology.org/search?apikey={0}&ontologies={1}&q={2}".format(apikey, bioportalCode, code)
    response = urllib2.urlopen(bioportalURL)
    data = json.load(response)   
    if not data.get("collection"):
        print "can't find collection for: " + code
        return []
    collection = data["collection"][0]
    if collection.get("prefLabel"):
        synonymList.append({"label": str(collection["prefLabel"]), "penalty": 0})
        currentLabelList.append(str(collection["prefLabel"])) 
    if collection.get("synonym"):
        for syn in collection["synonym"]:
            # skip synonyms that are already added
            if str(syn) not in currentLabelList:
                synonymList.append({"label": str(syn), "penalty": 1})
                currentLabelList.append(str(syn)) 
    if collection.get("cui"):
        if bioportalCode == "ICD9CM":
            #step 2:
            cui = str(collection.get("cui")[0])
            bioportalURL = "http://data.bioontology.org/search?apikey={0}&cui={1}".format(apikey, str(cui))
            response = urllib2.urlopen(bioportalURL)
            data = json.load(response)
            if data["collection"] is not None:
                for colItem in data["collection"]:
                    #collection = data["collection"][0]
                    if colItem.get("prefLabel"):
                        # skip labels that are already added
                        if str(colItem["prefLabel"]) not in currentLabelList:
                            synonymList.append({"label": str(colItem["prefLabel"]), "penalty": 1})
                            currentLabelList.append(str(colItem["prefLabel"])) 
                    if colItem.get("synonym"):
                        for syn in colItem["synonym"]:
                            # skip synonyms that are the same as the prefLabel
                            if str(syn) not in currentLabelList:
                                synonymList.append({"label": str(syn), "penalty": 1}) 
                                currentLabelList.append(str(syn)) 
        
    return synonymList

def getFileName(category):
    newName = category.replace (" ", "_")
    newName = 'ruta_' + str(newName).lower() + '.txt'
    newName = '/Users/chb69/borromeocd/nmvb/nlp_work/' + newName
    return newName

def dumpData(dataset):
    currentCategory = ''
    fOutput = ''
    for data in dataset:
        if currentCategory == '':
            currentCategory = str(data["category"])
            newFileName = getFileName(str(data["category"]))
            print "Opening file: " + newFileName
            fOutput = open(newFileName, 'w')
        if currentCategory != str(data["category"]):
            currentCategory = str(data["category"])
            newFileName = getFileName(str(data["category"]))
            print "Closing file"
            fOutput.close()
            print "Opening file: " + newFileName
            fOutput = open(newFileName, 'w')
        delimiter = ";"
        data_list = [str(data["path"]), str(data["label"]), str(data["basecode"]), str(data["category"]), str(data["penalty"])]
        strLine = delimiter.join(data_list)
        fOutput.write(strLine + '\n')
    fOutput.close()
    
if __name__ == "__main__":
    meta_db_obj = Oracle(dbconf.dbHost, dbconf.dbPort, dbconf.dbSid, dbconf.dbUser, dbconf.dbPassword)
    data_set = geti2b2Data(meta_db_obj)
    dumpData(data_set)
    print 'Done.'