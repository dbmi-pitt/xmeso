package edu.pitt.dbmi.nlp.nmvb.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.pitt.dbmi.nlp.noble.extract.InformationExtractor;
import edu.pitt.dbmi.nlp.noble.extract.model.Template;
import edu.pitt.dbmi.nlp.noble.extract.model.TemplateFactory;


public class NMVBExtractorMain {

	public static final String TEMPLATE_FILE = "/NMVB.template";
	public static final String REPORTS_DIR = "/reports";
	public static final String OUTPUT_DIR = "/output";
	public static final String RESULTS_CSV = "/RESULTS.csv";
	public static final String DATA_CSV = "/data.csv";
	
	//			System.err.println("Usage: java InformationExtractor [template] [input directory] [output directory]");

	String inputDirectory;
	private ArrayList<Template> templates;
	private InformationExtractor extractor;
	private String templateFilePath;
	private String reportsDirPath;
	private String outputDirPath;
	private String dataFilePath;
	private String resultsFilePath;
	
	public NMVBExtractorMain(String inputDir) {
		this.inputDirectory = inputDir;
		this.templateFilePath = inputDirectory + TEMPLATE_FILE;
		this.reportsDirPath = inputDirectory + REPORTS_DIR;
		this.outputDirPath = inputDirectory + OUTPUT_DIR;
		this.dataFilePath = inputDirectory + DATA_CSV;
		this.resultsFilePath = outputDirPath + RESULTS_CSV;
	}
	public static void main(String[] args) {
		if(args.length<1){
			System.out.println("Usage: NMVBExtractorMain <inputDir>");
			return;
		}
		
		NMVBExtractorMain e = new NMVBExtractorMain(args[0]);
		
		try {
			e.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	private void start() throws Exception {
		if(!checkRequiredResources()){
			throw new Exception("Required Resources not present. Exiting...");
		}
		
		initInformationExtractor();
		processDocuments();
		crateOutputCSVs();
	}
	

	private boolean checkRequiredResources() {
		boolean status = true;
		if(!(new File(templateFilePath).exists())){
			System.err.println("Template file not found at: " + templateFilePath);
			status = false;
		}

		if(!(new File(reportsDirPath).exists())){
			System.err.println("Reports directory not found at: " + reportsDirPath);
			status = false;
		}
		
		return status;
		
	}
	private void initInformationExtractor() throws Exception {
		this.templates = new ArrayList<Template>();
		this.templates.add(importTemplates(templateFilePath));
		
		this.extractor = new NMVB_InformationExtractor();
	}
	
	private Template importTemplates(String url) throws Exception{
        InputStream is = null;
        if(url.startsWith("http://"))
            is = new URL(url).openStream();
        else
            is = new FileInputStream(new File(url));
        if(is != null){
            Template t =  TemplateFactory.getInstance().importTemplate(is);
            is.close();
            return t;
        }
        return null;
    }
	
	private void processDocuments() {
        // do information extraction
        extractor.process(templates,reportsDirPath, outputDirPath);
        
	}
	

	private void crateOutputCSVs() throws Exception {
		List<List<String>> encMapping = new ArrayList<List<String>>();
		List<List<String>> patientMapping = new ArrayList<List<String>>();
		List<List<String>> patientDimension = new ArrayList<List<String>>();
		
		
		List<List<String>> visitDimension = new ArrayList<List<String>>();
		List<List<String>> obsFact = new ArrayList<List<String>>();
		
		List<String> patientIDsProcessed = new ArrayList<String>();
		List<String> reportIDsProcessed = new ArrayList<String>();
		
		File dataFile = new File(dataFilePath);
		
		if(!dataFile.exists()){
			throw new Exception("Could not find data file at: " + dataFilePath);
		}
		
		File resultsFile = new File(resultsFilePath);
		if(!resultsFile.exists()){
			throw new Exception("Could not find results file at: " + resultsFilePath);
		}
		
		
		try {
			 List<List<String>> data = FileUtils.parseCSVFile(dataFile,",");
			 List<List<String>> results = FileUtils.parseCSVFile(resultsFile,"\t");
			 HashMap<String,List<String>> resultsMap = buildResultsHashMap(results);
			 boolean first = true;
			 for(int rowIdx=0;rowIdx<data.size();rowIdx++){
//					if(first){
//					first = false;
//					continue;
//				}
				 List<String> row = data.get(rowIdx);
				 int colIdx = 0;
				 String UUID = null;
				 String nmvbID = null;
				 String recordID = null;
				 String patientID = null;
				 String reportID = null;
				 String eventYear = null;
				 String name = null;
				 String MRN = null;
				 String SSN = null;
				 String gender = null;
				 String race = null;
				 String ethnicity = null;
				 String age = null;
				 String dateofBirth = null;

				 
				 if(row.size()>colIdx+1)
					  UUID = row.get(colIdx++).trim();
				 
				if(row.size()>colIdx+1)
					  nmvbID = row.get(colIdx++).trim();
				 
				if(row.size()>colIdx+1)
					  recordID = row.get(colIdx++).trim();

				if(row.size()>colIdx+1)
					  patientID = row.get(colIdx++).trim();
				 
				if(row.size()>colIdx+1)
					  reportID = row.get(colIdx++).trim();
				 
				if(row.size()>colIdx+1)
					  eventYear = row.get(colIdx++).trim();
				 
				if(row.size()>colIdx+1)
					  name = row.get(colIdx++).trim();
				 
				if(row.size()>colIdx+1)
					  MRN = row.get(colIdx++).trim();
				 
				if(row.size()>colIdx+1)
					  SSN = row.get(colIdx++).trim();
				if(row.size()>colIdx+1)
					  gender = row.get(colIdx++).trim();
				 
				if(row.size()>colIdx+1)
					  race = row.get(colIdx++).trim();
				 
				if(row.size()>colIdx+1)
					  ethnicity = row.get(colIdx++).trim();
				 
				if(row.size()>colIdx+1)
					  age = row.get(colIdx++).trim();
				 
				if(row.size()>colIdx+1)
					  dateofBirth = row.get(colIdx++).trim();
				 
				 UUID = unQuote(UUID);
				 nmvbID = unQuote(nmvbID);
				 recordID = unQuote(recordID);
				 patientID = unQuote(patientID);
				 reportID = unQuote(reportID);
				 eventYear = unQuote(eventYear);
				 name = unQuote(name);
				 MRN = unQuote(MRN);
				 SSN = unQuote(SSN);
				 gender = unQuote(gender);
				 race = unQuote(race);
				 ethnicity = unQuote(ethnicity);
				 age = unQuote(age);
				 dateofBirth = unQuote(dateofBirth);
				 
				 if(!patientIDsProcessed.contains(nmvbID)){
					 patientIDsProcessed.add(nmvbID);
					 
					 ////////////// Patient Mapping
					 List<String> patientMappingRow = new ArrayList<String>();
					 
					 patientMappingRow.add(nmvbID);
					 patientMappingRow.add("MVB");
					 patientMappingRow.add(""+patientIDsProcessed.indexOf(nmvbID)+1);
					 patientMappingRow.add("status");
					 patientMappingRow.add("updatedate");
					 patientMappingRow.add("downdate");
					 patientMappingRow.add("importdate");
					 patientMappingRow.add("sourcesystemcd");
					 patientMappingRow.add("uploadid");
					 
					 patientMapping.add(patientMappingRow);
					 
					 //////////// Patient Dimension
					 List<String> patientDimRow = new ArrayList<String>();
					 
					 patientDimRow.add(""+patientIDsProcessed.indexOf(nmvbID)+1);
					 patientDimRow.add("vital status");
					 patientDimRow.add(dateofBirth);
					 patientDimRow.add("deathdate");
					 patientDimRow.add(gender);
					 patientDimRow.add(age);
					 patientDimRow.add("lang");
					 patientDimRow.add(race);
					 patientDimRow.add("marital");
					 patientDimRow.add("religion");
					 patientDimRow.add("zip");
					 patientDimRow.add("state");
					 patientDimRow.add("blob");
					 patientDimRow.add("updatedate");
					 patientDimRow.add("downdate");
					 patientDimRow.add("importdate");
					 patientDimRow.add("sourcesystemcd");
					 patientDimRow.add("uploadid");
					 
					 patientDimension.add(patientDimRow);
				 }
				 
				 if(!reportIDsProcessed.contains(reportID)){
					 reportIDsProcessed.add(reportID);
					 
					 
					 ///////////// Encounter Mapping
					 List<String> encMappingRow = new ArrayList<String>();
					 
					 encMappingRow.add(reportID);
					 encMappingRow.add("TIESID");
					 encMappingRow.add(""+reportIDsProcessed.indexOf(reportID)+1);
					 encMappingRow.add(nmvbID);
					 encMappingRow.add("MVB");
					 encMappingRow.add("status");
					 encMappingRow.add("updatedate");
					 encMappingRow.add("downdate");
					 encMappingRow.add("importdate");
					 encMappingRow.add("sourcesystemcd");
					 encMappingRow.add("uploadid");
					 
					 encMapping.add(encMappingRow);
					 
					 
					 ///////////// Visit Dimension
					 List<String> visitDimRow = new ArrayList<String>();
					 
					 visitDimRow.add(""+reportIDsProcessed.indexOf(reportID)+1);
					 visitDimRow.add(""+patientIDsProcessed.indexOf(nmvbID)+1);
					 visitDimRow.add("status");
					 visitDimRow.add("startdate");
					 visitDimRow.add("enddate");
					 visitDimRow.add("inoutcd");
					 visitDimRow.add("visitblob");
					 visitDimRow.add("updatedate");
					 visitDimRow.add("downdate");
					 visitDimRow.add("importdate");
					 visitDimRow.add("sourcesystemcd");
					 visitDimRow.add("uploadid");
					 
					 visitDimension.add(visitDimRow);
					 
					 ///////////////// Observation Fact
					 List<String> resultsRow = resultsMap.get(UUID);
					 if(resultsRow!=null)
						 addObservationFacts(obsFact, patientIDsProcessed.indexOf(nmvbID)+1, reportIDsProcessed.indexOf(reportID)+1, resultsRow);
					 
					 
					 
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		File file = new File(outputDirPath + "/obsfact.csv");
		outputCSV(obsFact, file);
		file = new File(outputDirPath + "/encMapping.csv");
		outputCSV(encMapping, file);
		file = new File(outputDirPath + "/patMapping.csv");
		outputCSV(patientMapping, file);
		file = new File(outputDirPath + "/visitdim.csv");
		outputCSV(visitDimension, file);
		file = new File(outputDirPath + "/patientdim.csv");
		outputCSV(patientDimension, file);
	}

	private String unQuote(String str) {
		if(str==null)
			return str;
		if(str.indexOf("\"") == 0 && str.lastIndexOf("\"") > 1){
			return str.substring(str.indexOf("\"")+1, str.lastIndexOf("\""));
		}
		return str;
	}
	private void outputCSV(List<List<String>> data, File f) {
		
		String output = "";
		for(List<String> row:data){
			for(String s:row){
				output += s + "\t";
			}
			output+= "\n";
		}
		
		try {
			FileUtils.writeToFile(f, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private HashMap<String, List<String>> buildResultsHashMap(
			List<List<String>> results) {
		HashMap<String,List<String>> map = new HashMap<String, List<String>>();
		
		boolean first = true;
		for(List<String> row:results){
//			if(first){
//				first = false;
//				continue;
//			}
			String uuid = unQuote(row.get(0));
			if(uuid.indexOf(".txt")>1)
				uuid = uuid.substring(0, uuid.indexOf(".txt"));
			
			map.put(uuid, row);
		}
		
		return map;
		
	}
	
	private void addObservationFacts(List<List<String>> obsFact, int patientNum, int encNum,
			List<String> resultsRow) {
		
		
		for(int colNo = 0;colNo<resultsRow.size();colNo++){
			 if(colNo==0)
				 continue;
			 
			 String modifierCD = "";
			 switch(colNo){
			 case 1:
				 modifierCD = "Tumor Site";
				 break;
			 case 2:
				 modifierCD = "Number of Positive Lymph Nodes";
				 break;
			 case 3:
				 modifierCD = "Number of Lymph Nodes Examined";
				 break;
			 case 4:
				 modifierCD = "Additional Dimensions";
				 break;
			 case 5:
				 modifierCD = "Additional Pathologic Findings";
				 break;
			 case 6:
				 modifierCD = "Surgical Margins";
				 break;
			 case 7:
				 modifierCD = "Surgical Procedure Type";
				 break;
			 case 8:
				 modifierCD = "Maximum Thickness";
				 break;
			 case 9:
				 modifierCD = "Histological Type";
				 break;
			 case 10:
				 modifierCD = "Tumor Differentiation or Grade";
				 break;
			 case 11:
				 modifierCD = "Greatest Dimension";
				 break;
			 case 12:
				 modifierCD = "Tumor Configuration";
				 break;
			 }
				 
			 String value = resultsRow.get(colNo);
			 value = unQuote(value);
			 String[] values =  value.split(";");
			 
			 for(String val:values){
				 if(val.trim().length()==0)
					 continue;
					 
				 List<String> obsFactRow = new ArrayList<String>();
				 
				 obsFactRow.add(""+encNum);
				 obsFactRow.add(""+patientNum);
				 obsFactRow.add(val.trim());
				 obsFactRow.add("providerID");
				 obsFactRow.add("startdate");
				 obsFactRow.add(modifierCD);
				 obsFactRow.add("tval");
				 obsFactRow.add("nval");
				 obsFactRow.add("valflag");
				 obsFactRow.add("quantity");
				 obsFactRow.add("units");
				 obsFactRow.add("enddate");
				 obsFactRow.add("location");
				 obsFactRow.add("confidence");
				 obsFactRow.add("obsblob");
				 obsFactRow.add("updatedate");
				 obsFactRow.add("downdate");
				 obsFactRow.add("importdate");
				 obsFactRow.add("sourcesystemcd");
				 obsFactRow.add("uploadid");
				 
				 obsFact.add(obsFactRow);
			 }
		}
	}
}
