package edu.pitt.dbmi.xmeso.ctakes.cr;

import java.io.File;
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

public class DeIdReader extends CollectionReader_ImplBase {
	
	public static final String PARAM_INPUT_FILE_PATH = "InputDeIdFilePath";
	
	final private int patternParams = Pattern.DOTALL | Pattern.MULTILINE;
	final Pattern pattern = Pattern.compile("E_O_H|E_O_R", patternParams);
	
	private String content;
	private Matcher matcher;
	
	private int totalNumberToExtract = 0;
	private int numberReportsExtracted = 0;
	
	/**
	 * @see org.apache.uima.collection.CollectionReader_ImplBase#initialize()
	 */
	public void initialize() throws ResourceInitializationException {
		String deIdFilePath = (String) getConfigParameterValue(PARAM_INPUT_FILE_PATH);
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
			totalNumberToExtract /= 2;
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
			DocumentID token = new DocumentID(jCas);
			token.setDocumentID("report" + StringUtils.leftPad(numberReportsExtracted + "", 5, "0"));
			token.addToIndexes(jCas);
			numberReportsExtracted++;
		} catch (CASException e) {
			throw new IOException(e);
		}
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
