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

import java.io.OutputStream;
import java.io.Writer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.uima.UIMARuntimeException;
import org.apache.uima.internal.util.XMLUtils;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/*
 * This class mimic's UIMA's XMLSerilizer, but it explictly uses the internal xalan
 * for xml handling, rather than any potential 3rd party such as Saxon.
 * Code was redudant because UIMA setter is private.
 * We also want to allow both to co-exist in the same environment for use case such as
 * FHIR which depends on SAXON-HE.
 */

public class XMISerializer {

	private SAXTransformerFactory transformerFactory;
	private TransformerHandler mHandler;
	private Transformer mTransformer;

	public XMISerializer(boolean isFormattedOutput) {
		try {
			transformerFactory = (SAXTransformerFactory) SAXTransformerFactory
					.newInstance(
							"com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl",
							this.getClass().getClassLoader());

			mHandler = transformerFactory.newTransformerHandler();
			mTransformer = mHandler.getTransformer();

		} catch (TransformerConfigurationException e) {
			throw new UIMARuntimeException(e);
		}
	}

	private OutputStream mOutputStream;
	private Writer mWriter;

	public void setOutputStream(OutputStream aOutputStream) {
		mWriter = null;
		mOutputStream = aOutputStream;
		mHandler.setResult(createSaxResultObject());
	}

	public void setWriter(Writer aWriter) {
		mOutputStream = null;
		mWriter = aWriter;
		mHandler.setResult(createSaxResultObject());
	}

	public XMISerializer(OutputStream aOutputStream, boolean isFormattedOutput) {
		this(isFormattedOutput);
		setOutputStream(aOutputStream);
	}

	private Result createSaxResultObject() {
		if (mOutputStream != null) {
			return new StreamResult(mOutputStream);
		} else if (mWriter != null) {
			return new StreamResult(mWriter);
		} else {
			return null;
		}
	}

	public XMISerializer(OutputStream aOutputStream) {
		this(true);
		setOutputStream(aOutputStream);
	}

	public ContentHandler getContentHandler() {
		String xmlVer = mTransformer.getOutputProperty(OutputKeys.VERSION);
		boolean xml10 = xmlVer == null || "1.0".equals(xmlVer);
		return new CharacterValidatingContentHandler(!xml10, mHandler);
	}

	static class CharacterValidatingContentHandler implements ContentHandler {
		ContentHandler mHandler;
		boolean mXml11;

		CharacterValidatingContentHandler(boolean xml11,
				ContentHandler serializerHandler) {
			mHandler = serializerHandler;
			mXml11 = xml11;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
		 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		public void startElement(String uri, String localName, String qName,
				Attributes atts) throws SAXException {
			for (int i = 0; i < atts.getLength(); i++) {
				String val = atts.getValue(i);
				checkForInvalidXmlChars(val, mXml11);
			}
			mHandler.startElement(uri, localName, qName, atts);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
		 */
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			checkForInvalidXmlChars(ch, start, length, mXml11);
			mHandler.characters(ch, start, length);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.ContentHandler#endDocument()
		 */
		public void endDocument() throws SAXException {
			mHandler.endDocument();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
		 * java.lang.String, java.lang.String)
		 */
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			mHandler.endElement(uri, localName, qName);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
		 */
		public void endPrefixMapping(String prefix) throws SAXException {
			mHandler.endPrefixMapping(prefix);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
		 */
		public void ignorableWhitespace(char[] ch, int start, int length)
				throws SAXException {
			mHandler.ignorableWhitespace(ch, start, length);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.xml.sax.ContentHandler#processingInstruction(java.lang.String,
		 * java.lang.String)
		 */
		public void processingInstruction(String target, String data)
				throws SAXException {
			mHandler.processingInstruction(target, data);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
		 */
		public void setDocumentLocator(Locator locator) {
			mHandler.setDocumentLocator(locator);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
		 */
		public void skippedEntity(String name) throws SAXException {
			mHandler.skippedEntity(name);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.ContentHandler#startDocument()
		 */
		public void startDocument() throws SAXException {
			mHandler.startDocument();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String,
		 * java.lang.String)
		 */
		public void startPrefixMapping(String prefix, String uri)
				throws SAXException {
			mHandler.startPrefixMapping(prefix, uri);
		}

		private final void checkForInvalidXmlChars(String s, boolean xml11)
				throws SAXParseException {
			final int index = XMLUtils.checkForNonXmlCharacters(s, xml11);
			if (index >= 0) {
				throw new SAXParseException("Trying to serialize non-XML "
						+ (xml11 ? "1.1" : "1.0") + " character: "
						+ s.charAt(index) + ", 0x"
						+ Integer.toHexString(s.charAt(index)), null);
			}
		}

		private final void checkForInvalidXmlChars(char[] ch, int start,
				int length, boolean xml11) throws SAXParseException {
			final int index = XMLUtils.checkForNonXmlCharacters(ch, start,
					length, xml11);
			if (index >= 0) {
				throw new SAXParseException("Trying to serialize non-XML "
						+ (xml11 ? "1.1" : "1.0") + " character: " + ch[index]
						+ ", 0x" + Integer.toHexString(ch[index]), null);
			}
		}
	}
}
