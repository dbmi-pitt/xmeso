/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package edu.pitt.dbmi.xmeso.ctakes.ae;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ctakes.core.util.DocumentIDAnnotationUtil;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.analysis_engine.metadata.AnalysisEngineMetaData;
import org.apache.uima.analysis_engine.metadata.FlowControllerDeclaration;
import org.apache.uima.analysis_engine.metadata.SofaMapping;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.collection.CasConsumerDescription;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ExternalResourceDependency;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceManager;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.resource.metadata.MetaDataObject;
import org.apache.uima.resource.metadata.ResourceManagerConfiguration;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.NameClassPair;
import org.apache.uima.util.UriUtils;
import org.apache.uima.util.XMLInputSource;
import org.apache.uima.util.XMLParser;
import org.apache.uima.util.XMLParser.ParsingOptions;
import org.w3c.dom.Element;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * A simple CAS consumer that writes the CAS to XMI format.
 * <p/>
 * This CAS Consumer takes one parameter:
 * <ul>
 * <li><code>OutputDirectory</code> - path to directory into which output files
 * will be written</li>
 * </ul>
 */
public class XMIWriter extends CasConsumer_ImplBase  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5559390242141272034L;
	/**
	 * Name of configuration parameter that must be set to the path of a
	 * directory into which the output files will be written.
	 */
	public static final String PARAM_OUTPUTDIR = "OutputDirectory";
	@ConfigurationParameter(name = PARAM_OUTPUTDIR, description = "Output directory to write xmi files", mandatory = true)
	private File mOutputDir;

	private int mDocNum;

	public void initialize() throws ResourceInitializationException {
		super.initialize();
		mDocNum = 0;
		String outputDirectoryPath = (String) getConfigParameterValue(PARAM_OUTPUTDIR);
		mOutputDir = new File(outputDirectoryPath);
		if (!mOutputDir.exists()) {
			mOutputDir.mkdirs();
		}
	}

	/**
	 * Processes the CAS which was populated by the TextAnalysisEngines. <br>
	 * In this case, the CAS is converted to XMI and written into the output
	 * file .
	 *
	 * @param aCAS
	 *            a CAS which has been populated by the TAEs
	 * @throws AnalysisEngineProcessException
	 * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(org.apache.uima.cas.CAS)
	 */

	public void processCas(CAS aCAS) throws ResourceProcessException {
		String modelFileName = null;

		JCas jcas;
		try {
			jcas = aCAS.getJCas();
		} catch (CASException e1) {
			e1.printStackTrace();
			throw new AnalysisEngineProcessException(e1);
		}

		String originalFileName = DocumentIDAnnotationUtil.getDocumentID(jcas);
		File outFile = null;
		if (originalFileName != null && !originalFileName.isEmpty()) {
			File inFile;
			try {
				String outFileName = null;
				if (originalFileName.contains("/")) {
					URI uri = UriUtils.quote(originalFileName);
					inFile = new File(uri);
					outFileName = inFile.getName();
				} else {
					outFileName = originalFileName;
				}
				outFileName += ".xmi";
				outFile = new File(mOutputDir, outFileName);

			} catch (URISyntaxException e) {
				// bad URI, use default processing below
			}

		}
		if (outFile == null) {
			outFile = new File(mOutputDir, "doc" + mDocNum++ + ".xmi"); // JiraUIMA-629
		}
		// serialize XCAS and write to output file
		try {
			writeXmi(jcas.getCas(), outFile, modelFileName);
		} catch (IOException e) {
			throw new AnalysisEngineProcessException(e);
		} catch (SAXException e) {
			throw new AnalysisEngineProcessException(e);
		}

	}

	/**
	 * Serialize a CAS to a file in XMI format
	 *
	 * @param aCas
	 *            CAS to serialize
	 * @param name
	 *            output file
	 * @throws SAXException
	 * @throws Exception
	 * @throws ResourceProcessException
	 */
	public static void writeXmi(CAS aCas, File name) throws IOException,
			SAXException {
		FileOutputStream out = null;

		try {
			// write XMI
			out = new FileOutputStream(name);
			XmiCasSerializer ser = new XmiCasSerializer(aCas.getTypeSystem());
			XMISerializer xmlSer = new XMISerializer(out, true);
			ser.serialize(aCas, xmlSer.getContentHandler());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * Serialize a CAS to a file in XMI format
	 *
	 * @param aCas
	 *            CAS to serialize
	 * @param name
	 *            output file
	 * @throws SAXException
	 * @throws Exception
	 * @throws ResourceProcessException
	 */
	public static void writeXmi(CAS aCas, File name, String modelFileName)
			throws IOException, SAXException {
		FileOutputStream out = null;

		try {
			// write XMI
			out = new FileOutputStream(name);
			XmiCasSerializer ser = new XmiCasSerializer(aCas.getTypeSystem());
			XMISerializer xmlSer = new XMISerializer(out, true);
			ser.serialize(aCas, xmlSer.getContentHandler());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * Parses and returns the descriptor for this collection reader. The
	 * descriptor is stored in the uima.jar file and located using the
	 * ClassLoader.
	 *
	 * @return an object containing all of the information parsed from the
	 *         descriptor.
	 * @throws InvalidXMLException
	 *             if the descriptor is invalid or missing
	 */
	public static CasConsumerDescription getDescription()
			throws InvalidXMLException {
		InputStream descStream = XMIWriter.class
				.getResourceAsStream("XmiWriter.xml");
		if (descStream == null) {
			String packageForCls =  XMIWriter.class.getPackage().toString();
			System.err.println("Couldn't find " + XMIWriter.class.getPackage() + "/XmiWriter.xml");
		}
		return UIMAFramework.getXMLParser().parseCasConsumerDescription(
				new XMLInputSource(descStream, null));
	}

	public static URL getDescriptorURL() {
		return XMIWriter.class.getResource("XmiWriter.xml");
	}

	
}