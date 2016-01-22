package edu.pitt.dbmi.xmeso.ctakes.cr;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.FileUtils;
import org.apache.uima.util.Progress;

public class DeIdReader extends CollectionReader_ImplBase {
	
	public static final String PARAM_INPUT_FILE_PATH = "InputDeIdFilePath";
	
	private String content;
	private Matcher matcher;
	private int numberReportsExtracted = 0;
	
	/**
	 * @see org.apache.uima.collection.CollectionReader_ImplBase#initialize()
	 */
	public void initialize() throws ResourceInitializationException {
		File deIdFile = new File(
				(String) getConfigParameterValue(PARAM_INPUT_FILE_PATH));
		if (!deIdFile.exists() || !deIdFile.isFile()) {
			throw new ResourceInitializationException(
					ResourceConfigurationException.CONFIG_SETTING_ABSENT,
					new Object[] { PARAM_INPUT_FILE_PATH,
							getMetaData().getName(), deIdFile.getPath() });
		}

		try {
			String content = FileUtils.file2String(deIdFile, "utf-8");
			
			int patternParams = Pattern.DOTALL | Pattern.MULTILINE;
			Pattern pattern = Pattern.compile("\\b[SE]_O_H\\b", patternParams);
			matcher = pattern.matcher(content);
			
		} catch (IOException e) {
			throw new ResourceInitializationException(
					ResourceConfigurationException.CONFIG_SETTING_ABSENT,
					new Object[] { PARAM_INPUT_FILE_PATH,
							getMetaData().getName(), deIdFile.getPath() });
		}
		
	}


	public void getNext(CAS aCAS) throws IOException, CollectionException {
		JCas jCas;
		try {
			jCas = aCAS.getJCas();
			int beginIndex = matcher.end();
			matcher.find();
			int endIndex = matcher.start();
			String reportContent = content.substring(beginIndex, endIndex);
			jCas.setDocumentText(reportContent);
		} catch (CASException e) {
			throw new IOException(e);
		}
	}

	public boolean hasNext() throws IOException, CollectionException {
		return matcher.find();
	}

	public Progress[] getProgress() {
		// TODO Auto-generated method stub
		return null;
	}

	public void close() throws IOException {	
	}

}
