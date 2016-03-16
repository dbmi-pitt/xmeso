package edu.pitt.dbmi.xmeso.ctakes.cr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.ctakes.typesystem.type.structured.DocumentID;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.FileUtils;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.cleartk.util.ViewUriUtil;

public class DeIdReader extends CollectionReader_ImplBase {
	
	public static final String PARAM_INPUT_FILE_PATH = "InputDeIdFilePath";
	
	final private int patternParams = Pattern.DOTALL | Pattern.MULTILINE;
	final private Pattern pattern = Pattern.compile("S_O_H(.+?)E_O_H(.+?)E_O_R", patternParams);
	
	private String content;
	private Matcher matcher;
	
	private int totalNumberToExtract = 0;
	private int numberReportsExtracted = 0;
	
	private File temporaryDirectory = null;
	
	/**
	 * @see org.apache.uima.collection.CollectionReader_ImplBase#initialize()
	 */
	public void initialize() throws ResourceInitializationException {
		String deIdFilePath = null;
		Object deIdFilePathObj =  getConfigParameterValue(PARAM_INPUT_FILE_PATH);
		if (deIdFilePathObj instanceof String[]) {
			deIdFilePath = ((String[])deIdFilePathObj)[0];
		}
		else if (deIdFilePathObj instanceof String) {
			deIdFilePath = (String) deIdFilePathObj;
		}
		File deIdFile = new File(deIdFilePath);
		if (!deIdFile.exists() || !deIdFile.isFile()) {
			throw new ResourceInitializationException(
					ResourceConfigurationException.CONFIG_SETTING_ABSENT,
					new Object[] { PARAM_INPUT_FILE_PATH,
							getMetaData().getName(), deIdFile.getPath() });
		}

		try {
			content = FileUtils.file2String(deIdFile, "utf-8");
			matcher = pattern.matcher(content);
			while (matcher.find()) {
				totalNumberToExtract++;
			}
			matcher = pattern.matcher(content);
		} catch (IOException e) {
			throw new ResourceInitializationException(
					ResourceConfigurationException.CONFIG_SETTING_ABSENT,
					new Object[] { PARAM_INPUT_FILE_PATH,
							getMetaData().getName(), deIdFile.getPath() });
		}
		
		try {
			File parentTempDirectory = new File("C:\\ws\\ws-xmeso\\xmeso\\data");	
			temporaryDirectory = FileUtils.createTempDir(parentTempDirectory, "deid");
		}
		catch (Exception x) {
			x.printStackTrace();
		}
	}

	public void getNext(CAS aCAS) throws IOException, CollectionException {
		JCas jCas;
		try {
			jCas = aCAS.getJCas();
			String header = matcher.group(1);
			String reportContent = matcher.group(2);
//			System.out.println(header);
			reportContent += globXmesoPatientAndEncounterSections(header);
			String prefix = "report";
			String suffix = StringUtils.leftPad(numberReportsExtracted + "", 5, "0");
//			File temp = FileUtils.createTempFile(prefix, suffix, temporaryDirectory);
//			IOUtils.write(reportContent, new FileWriter(temp));
//			ViewUriUtil.setURI(jCas, temp.toURI());
			jCas.setDocumentText(reportContent);
			DocumentID token = new DocumentID(jCas);
			token.setDocumentID(prefix + suffix);
			token.addToIndexes(jCas);
			numberReportsExtracted++;
		} catch (CASException e) {
			throw new IOException(e);
		}
	}
	
	  private String globXmesoPatientAndEncounterSections(String deIdHeader) {
	    	StringBuilder sb = new StringBuilder();
	    	String patternString = "^\\s*(\\d+),(\\S+)\\s+(\\S+)\\s+SP";
	    	int patternFlags = Pattern.DOTALL | Pattern.MULTILINE;
	    	Pattern pattern = Pattern.compile(patternString, patternFlags);
	    	Matcher matcher = pattern.matcher(deIdHeader);
	    	if (matcher.find()) {
	    		String encounterNumAsString = matcher.group(1);
	    		String patientHash = matcher.group(2);
	    		String verifyPatientHash = matcher.group(3);
	    		if (patientHash.equals(verifyPatientHash)) {
	    			sb.append("\nXMESO PATIENT:\n");
	        		sb.append(patientHash);
	        		sb.append("\n");
	        		sb.append("\nXMESO ENCOUNTER:\n");
	        		sb.append(encounterNumAsString);
	        		sb.append("\n");
	    		}
	    		else {
	    			System.err.println("No patient or report hash found.");
	    			
	    		}
	    		sb.append("\n$"); // End of report delimiter	
	    	}	
	    	return sb.toString();
	    }

	public boolean hasNext() throws IOException, CollectionException {
		return matcher.find();
	}

	public Progress[] getProgress() {
		return new Progress[] { new ProgressImpl(numberReportsExtracted,
				totalNumberToExtract, Progress.ENTITIES) };
	}

	public void close() throws IOException {	
	}

}
