'''
Created on Apr 29, 2016

@author: chb69
'''
from edu.pitt.dbmi.nmvb.nlp.TermExtractor import dbconf
from Oracle import Oracle
import urllib2
import json
import csv
import requests
from pyquery import PyQuery as pq
import copy
import re


umlsSemanticTypes = {}
# limit the data analyzed if you are debugging
debug = False

# a helper function to convert unicode strings to UTF-8 strings
def byteify(input):
    if isinstance(input, dict):
        return {byteify(key): byteify(value)
                for key, value in input.iteritems()}
    elif isinstance(input, list):
        return [byteify(element) for element in input]
    elif isinstance(input, unicode):
        return input.encode('utf-8')
    else:
        return input
    
# create a dictionary of semantic types based on the UMLS csv file
def loadUmlsSemanticTypes():
    returnList = {}
    # equivalent to site_of_tumor
    returnList['anatomy'] = ['T017','T018','T023','T029','T030','T031']
    #equivalent to surgical_procedure
    returnList['procedure'] = ['T059','T060','T061']
    #equivalent to histological_type, tumor_differentiation, tumor_configuration
    returnList['tumor_description'] = ['T019','T020', 'T033', 'T190','T191']
    # equivalent to immunohistochemical_profile
    returnList['diagnostic_tools'] = ['T034', 'T129', 'T130']
    return returnList
            

# This method creates an array of the "seed" data to use when searching for the values
# The seed data is assumed to contain a set of labels (used for searching) and terminology codes (SNOMED, ICD9, etc.)  
def getSeedData(meta_db_obj):
    con = meta_db_obj.getConnection()
    cursor = con.cursor()
    # just a quick note about the SQL below:
    # I added a select * FROM so I could add an ORDER BY clause
    sql = """
        SELECT c_fullname, label, c_basecode, category FROM (
        SELECT c_fullname, c_name label, c_basecode, 'Ultrastructural Findings' category
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE c_fullname LIKE '\NMVB\MESOTHELIOMA\ANATOMICAL PATHOLOGY\ULTRASTRUCTURAL FINDINGS\%'
        UNION ALL
        SELECT c_fullname, regexp_replace(c_name, '\(|\[|\)|\]','') label, c_basecode, 'Ultrastructural Findings' category
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE regexp_like(c_name, '\(|\[') 
        AND c_fullname LIKE '\NMVB\MESOTHELIOMA\ANATOMICAL PATHOLOGY\ULTRASTRUCTURAL FINDINGS\%'        
        UNION ALL
        SELECT c_fullname, c_name label, c_basecode, 'Immunohistochemical Profile' category
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE c_fullname LIKE '\NMVB\MESOTHELIOMA\ANATOMICAL PATHOLOGY\IMMUNOHISTOCHEMICAL PROFILE\%'
        UNION ALL
        SELECT c_fullname, regexp_replace(c_name, '\(|\[|\)|\]','') label, c_basecode, 'Immunohistochemical Profile' category
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE regexp_like(c_name, '\(|\[') 
        AND c_fullname LIKE '\NMVB\MESOTHELIOMA\ANATOMICAL PATHOLOGY\IMMUNOHISTOCHEMICAL PROFILE\%'        
        UNION ALL
        SELECT c_fullname, c_name label, c_basecode, 'Site of tumor' category
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE c_fullname LIKE '\NMVB\MESOTHELIOMA\ANATOMICAL PATHOLOGY\SITE OF TUMOR\%'
        UNION ALL
        SELECT c_fullname, regexp_replace(c_name, '\(|\[|\)|\]','') label, c_basecode, 'Site of tumor' category
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE regexp_like(c_name, '\(|\[') 
        AND c_fullname LIKE '\NMVB\MESOTHELIOMA\ANATOMICAL PATHOLOGY\SITE OF TUMOR\%'        
        UNION ALL
        SELECT c_fullname, c_name label, c_basecode, 'Surgical Procedure' category
        FROM nmvb_i2b2metadata.NCATS_ICD9_PROC
        UNION ALL
        SELECT c_fullname, regexp_replace(c_name, '\(|\[|\)|\]','') label, c_basecode, 'Surgical Procedure' category
        FROM nmvb_i2b2metadata.NCATS_ICD9_PROC
        WHERE regexp_like(c_name, '\(|\[')
        UNION ALL
        SELECT c_fullname, c_name label, c_basecode, 'Largest Nodule' category
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE c_fullname = '\NMVB\MESOTHELIOMA\ANATOMICAL PATHOLOGY\LARGEST NODULE\SIZE\'
        UNION ALL
        SELECT c_fullname, c_name label, c_basecode, 'Vascular Invasion' category
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE c_fullname = '\NMVB\MESOTHELIOMA\ANATOMICAL PATHOLOGY\STATUS OF VASCULAR INVASION BY TUMOR\'
        UNION ALL
        /* this substring stuff is kind of complicated.  Basically, it takes the c_fullname and extracts
        the first level of the path.  Then it removes the leading and trailing backslashes (using substr) and puts them into their proper case
        using initcap */
        SELECT c_fullname, c_name label, c_basecode, initcap(substr(regexp_substr(c_fullname, '\\.*?\\'),2,length(regexp_substr(c_fullname, '\\.*?\\'))-2)) category
        FROM nmvb_i2b2metadata.NMVB_MESOTHELIOMA
        WHERE C_FULLNAME NOT LIKE '\NMVB\%'
        AND c_fullname NOT LIKE '\SiteHist%'
        AND c_fullname NOT LIKE '\RELATION TYPE%'
        AND c_fullname NOT LIKE '%RECURRENCE%'
        AND c_fullname NOT LIKE '\AGE%'
        AND c_basecode is NOT null
        ) 
        WHERE C_BASECODE is not null
        and C_BASECODE <> '(null)'
        ORDER BY 4"""    
    cursor.execute(sql)
    rows = cursor.fetchall()

    return rows


def extractSeedConcept(row):
    strCategory = row[3]
    if row[3] == None or row[3] == '':
        # if row[3] (category column) is empty, 
        # try extracting the category from row[0] instead
        m = re.search(r'\\(.*?)\\.*?\\', row[0])
        strCategory = str(m.group(1)).title()
    new_record = {"path" : row[0], "label": row[1], "basecode": str(row[2]).strip(), "category": strCategory}
    return new_record

def getUMLSPrefix(basecode):
    retPrefix = ''
    prefix = basecode[0:str(basecode).find(':')]
    if prefix == 'ICD9':
        retPrefix = 'ICD9CM'
    elif prefix == 'LOINC':
        retPrefix = 'LNC'
    elif prefix == 'SNOMED':
        retPrefix = 'SNOMEDCT_US'
    
    if retPrefix == '':
        print "Could not find prefix for basecode: " + basecode
    return retPrefix

def processSeedData(rows):
    data_set = []
    prefixList = []
    # use the term list to track the overall terms that have been added to the data_set.  This
    # avoids adding duplicate values
    term_list = []
    total_count = 0
    currentCategory = ''
    count = 0
    for row in rows:
        i2b2Data = None
        try:
            # use the count to limit the number of items included in the list per category
            # for debugging.  
            if debug == True:
                count = count + 1
                i2b2Data = extractSeedConcept(row)
                # when debugging, we still need to load the next term.
                # we need to check the category to see if it changed.
                if currentCategory == '':
                    currentCategory = i2b2Data["category"]
                    print "Loading terms for category: " + currentCategory
                if currentCategory != i2b2Data["category"]:
                    currentCategory = i2b2Data["category"]
                    print "Found new category.  Loading terms for category: " + currentCategory
                    count = 0
                if count >= 50:
                    #print "here"
                    continue

    
        except Exception as e:
            print "Error retrieving extractSeedConcept: {0}".format(e.message)
            continue
  
        
        # print a statement telling the user the code is still running
        total_count = total_count + 1
        if total_count % 500 == 0:
            print "Loaded {} records for category {}".format(total_count, currentCategory)
        #if i2b2Data["category"] == None:
            #print "Found something with no category"

        if currentCategory == '':
            currentCategory = i2b2Data["category"]
            print "Loading terms for category: " + currentCategory
        if currentCategory != i2b2Data["category"]:
            currentCategory = i2b2Data["category"]
            print "Found new category.  Loading terms for category: " + currentCategory
            count = 0

        
        # add a "penalty" to indicate if this is the direct label for the concept (lower score indicates more direct label)
        i2b2Data["penalty"] = 0
        # complete this step by adding the original data to the array
        # first check for a duplicate
        if i2b2Data["label"] not in term_list:
            term_list.append(i2b2Data["label"])
            data_set.append(i2b2Data)
            
        if i2b2Data["category"] == "None":
            print "Found none"
        
        try:
            prefix = getUMLSPrefix(i2b2Data["basecode"])
            suffix = str(i2b2Data["basecode"])[str(i2b2Data["basecode"]).find(':')+1:]
            if (prefix == ''):
                jsonData = queryUMLSForString(dbconf.umlsUsername, dbconf.umlsPassword,dbconf.umls_api_key, "current", i2b2Data["label"], None)                
            else:
                jsonData = queryUMLSForTerm(dbconf.umlsUsername, dbconf.umlsPassword,dbconf.umls_api_key, "current", prefix, suffix, None)
            objList = buildUMLSObjects(jsonData)
            tmp = ''
            for currObj in objList:
                if currObj['concept'] is None:
                    tmp = currObj['uri']
                else:
                    tmp = currObj['concept']
                jsonData = queryUMLSConcept(dbconf.umlsUsername, dbconf.umlsPassword,dbconf.umls_api_key, byteify(tmp))
                cui = byteify(tmp)
                idx = cui.rfind('/')
                cui = cui[idx+1:]
                synJsonData = queryUMLSSynonyms(dbconf.umlsUsername, dbconf.umlsPassword,dbconf.umls_api_key,"current", prefix, cui, None)
                synList = buildUMLSObjects(synJsonData)
                # add each synonym to the array
                for item in synList:
                    newi2b2Data = copy.deepcopy(i2b2Data)
                    newi2b2Data["label"] = item["name"]
                    newi2b2Data["penalty"] = 1
                    # add if not a duplicate
                    if newi2b2Data["label"] not in term_list:
                        term_list.append(newi2b2Data["label"])
                        data_set.append(newi2b2Data)
                        
                
                
        except Exception as e:
            print "Error retrieving synonyms for concept [{s}].  Error: {m}".format(s= str(i2b2Data["basecode"]),m =e.message)

        '''
        # set the correct semantic type
        semanticTypes = []
        if i2b2Data["category"] == 'Immunohistochemical Profile':
            semanticTypes = umlsSemanticTypes['diagnostic_tools']
        elif i2b2Data["category"] == 'Site of tumor':
            semanticTypes = umlsSemanticTypes['anatomy']
        elif i2b2Data["category"] == 'Surgical Procedure':
            semanticTypes = umlsSemanticTypes['procedure']
        elif i2b2Data["category"] == 'Histological Type':
            semanticTypes = umlsSemanticTypes['tumor_description']
        elif i2b2Data["category"] == 'Tumor Configuration':
            semanticTypes = umlsSemanticTypes['tumor_description']
        elif i2b2Data["category"] == 'Tumor Differentiation':
            semanticTypes = umlsSemanticTypes['tumor_description']
        # NOTE: still need some categories
        labelList = []
        labelList = getLabelsForConcept(i2b2Data, semanticTypes)
        '''
    return data_set

  
# this method returns a UMLS URL using the input parameters
def queryUMLSForTerm(username, password, apiKey, version, sourceName, searchTerm, semanticTypes):
    tkt = getUMLSServiceTicket(username, password, apiKey)
    #sourceName = "ICD9CM"
    #searchTerm = "382.9"
    uri = "https://uts-ws.nlm.nih.gov"
    version = "current"
    query = {'ticket':tkt}
    content_endpoint = "/rest/content/"+str(version)+"/source/"+str(sourceName)+"/"+str(searchTerm)+"/atoms"
    r = requests.get(uri+content_endpoint,params=query)
    r.encoding = 'utf-8'
    items  = json.loads(r.text)
    jsonData = items["result"]
    #print jsonData
    return jsonData

# this method returns a UMLS URL using the input parameters
def queryUMLSForString(username, password, apiKey, version, searchTerm, semanticTypes):
    tkt = getUMLSServiceTicket(username, password, apiKey)
    #sourceName = "ICD9CM"
    #searchTerm = "382.9"
    uri = "https://uts-ws.nlm.nih.gov"
    version = "current"
    query = {'ticket':tkt, 'string' : searchTerm}
    content_endpoint = "/rest/search/"+str(version)
    r = requests.get(uri+content_endpoint,params=query)
    r.encoding = 'utf-8'
    items  = json.loads(r.text)
    jsonData = items["result"]["results"]
    #print jsonData
    return jsonData

def queryUMLSConcept(username, password, apiKey, url):
    tkt = getUMLSServiceTicket(username, password, apiKey)
    query = {'ticket':tkt}
    #content_endpoint = "/rest/content/"+str(version)+"/source/"+str(sourceName)+"/"+str(searchTerm)+"/atoms"
    r = requests.get(url,params=query)
    r.encoding = 'utf-8'
    items  = json.loads(r.text)
    jsonData = items["result"]
    #print jsonData
    return jsonData

def queryUMLSRelations(username, password, apiKey, version, sourceName, searchTerm, semanticTypes):
    tkt = getUMLSServiceTicket(username, password, apiKey)
    query = {'ticket':tkt}
    uri = "https://uts-ws.nlm.nih.gov"
    content_endpoint = "/rest/content/"+str(version)+"/CUI/"+str(searchTerm)+"/relations"
    r = requests.get(uri+content_endpoint,params=query)
    r.encoding = 'utf-8'
    items  = json.loads(r.text)
    jsonData = items["result"]
    #print jsonData
    return jsonData

def queryUMLSSynonyms(username, password, apiKey, version, sourceName, searchTerm, semanticTypes):
    tkt = getUMLSServiceTicket(username, password, apiKey)
    query = {'ticket':tkt, 'language':'ENG' }
    uri = "https://uts-ws.nlm.nih.gov"
    content_endpoint = "/rest/content/current/CUI/"+str(searchTerm)+"/atoms"
    r = requests.get(uri+content_endpoint,params=query)
    r.encoding = 'utf-8'
    items  = json.loads(r.text)
    jsonData = items["result"]
    synList = []
    for item in jsonData:
        currLabel = item['name']
        if synList.count(currLabel) == 0:
            synList.append(currLabel)
    #print '\n'.join(synList)
    #print jsonData
    return jsonData
"""def getSynonyms(dbconf.umlsUsername, dbconf.umlsPassword,dbconf.umls_api_key, cui):
    retList = []
    return retList
"""

def buildUMLSObjects(jsonData):
    retList = []
    if type(jsonData) is list:
        for item in jsonData:
            newObj = {}
            for key,value in item.iteritems():
                newObj[byteify(key)] = byteify(value)
            retList.append(newObj)
    else:
        newObj = {}
        for key,value in jsonData.iteritems():
            newObj[byteify(key)] = byteify(value)
        retList.append(newObj)

    return retList

def getUMLSServiceTicket(username, password, apiKey):
    service = "http://umlsks.nlm.nih.gov"
    uri="https://utslogin.nlm.nih.gov"
    auth_endpoint = "/cas/v1/tickets/"
    params = {'username': username,'password': password}
    #params = {'apikey': apiKey}
    h = {"Content-type": "application/x-www-form-urlencoded", "Accept": "text/plain", "User-Agent":"python" }
    r = requests.post(uri+auth_endpoint,data=params,headers=h)
    d = pq(r.text)
    ## extract the entire URL needed from the HTML form (action attribute) returned - looks similar to https://utslogin.nlm.nih.gov/cas/v1/tickets/TGT-36471-aYqNLN2rFIJPXKzxwdTNC5ZT7z3B3cTAKfSc5ndHQcUxeaDOLN-cas
    ## we make a POST call to this URL in the getst method
    tgt = d.find('form').attr('action')
    params = {'service': service}
    h = {"Content-type": "application/x-www-form-urlencoded", "Accept": "text/plain", "User-Agent":"python" }
    r = requests.post(tgt,data=params,headers=h)
    st = r.text
    return st


def getFileName(category):
    newName = category.replace (" ", "_")
    newName = 'ruta_' + str(newName).lower() + '.txt'
    newName = dbconf.outputDir + newName
    return newName

def dumpData(dataset):
    print "Opening files for output."
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

def extractConceptFromRow(row):
    new_record = {"path" : row[0], "label": row[1], "basecode": str(row[2]).strip(), "category": row[3]}
    return new_record
    
    
if __name__ == "__main__":
    #tgt = getUMLSServiceTicket(dbconf.umlsUsername, dbconf.umlsPassword,dbconf.umls_api_key)
    #print 'UMLS ticket: ' + tgt
    '''
    jsonData = queryUMLSForTerm(dbconf.umlsUsername, dbconf.umlsPassword,dbconf.umls_api_key, "current", "ICD9CM", "191.1", None)
    obj = buildUMLSObjects(jsonData)
    jsonData = queryUMLSConcept(dbconf.umlsUsername, dbconf.umlsPassword,dbconf.umls_api_key, byteify(obj[0]['concept']))
    cui = byteify(obj[0]['concept'])
    idx = cui.rfind('/')
    cui = cui[idx+1:]
    queryUMLSSynonyms(dbconf.umlsUsername, dbconf.umlsPassword,dbconf.umls_api_key,"current", "ICD9CM", cui, None)
    obj = buildUMLSObjects(jsonData)
    '''
    #(username, password, apiKey, version, sourceName, searchTerm, semanticTypes)
    
    
    debug = True
    umlsSemanticTypes = loadUmlsSemanticTypes()
    meta_db_obj = Oracle(dbconf.dbHost, dbconf.dbPort, dbconf.dbSid, dbconf.dbUser, dbconf.dbPassword)
    seed_data_set = getSeedData(meta_db_obj)
    data_set = processSeedData(seed_data_set)
    dumpData(data_set)
    print 'Done.'
    #testMethod()
