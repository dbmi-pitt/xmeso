package edu.pitt.dbmi.xmeso.ctakes.pipeline;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;

import edu.pitt.dbmi.xmeso.ctakes.ae.PassThruAnnotationEngine;
import edu.pitt.dbmi.xmeso.ctakes.ae.RawWriter;
import edu.pitt.dbmi.xmeso.ctakes.cr.DeIdReader;

public class XmesoDeidUnpacker {

	public static void main(String[] args) {
		final String inputFilePath = args[0];
		final String outputDirectory = args[1];
		CollectionReader collectionReader;
		try {
			collectionReader = CollectionReaderFactory
					.createReader(DeIdReader.class,
							DeIdReader.PARAM_INPUT_FILE_PATH, inputFilePath);
			final AnalysisEngine passThruEngine = AnalysisEngineFactory
					.createEngine(PassThruAnnotationEngine.class);
			final AnalysisEngine rawWriter = AnalysisEngineFactory.createEngine(
					RawWriter.class, "outputDirectory", outputDirectory);
			SimplePipeline.runPipeline(collectionReader, passThruEngine, rawWriter);
		} catch (ResourceInitializationException e) {
			e.printStackTrace();
		} catch (UIMAException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
