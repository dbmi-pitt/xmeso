package edu.pitt.dbmi.xmeso.ctakes.pipeline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;

import edu.pitt.dbmi.xmeso.ctakes.ae.RawWriter;
import edu.pitt.dbmi.xmeso.ctakes.ae.I2B2DataDataWriter;
import edu.pitt.dbmi.xmeso.ctakes.cr.DeIdReader;

public class XmesoPipeline {

	public static void main(String[] args) {
		final String inputFilePath = args[0];
		final String outputDirectory = args[1];
		CollectionReader collectionReader;
		try {
			collectionReader = CollectionReaderFactory
					.createReader(DeIdReader.class,
							DeIdReader.PARAM_INPUT_FILE_PATH, inputFilePath);
			final AnalysisEngine i2B2DataDataWriter = AnalysisEngineFactory
					.createEngine(I2B2DataDataWriter.class);
			final AnalysisEngine xmesoEngine = AnalysisEngineFactory
					.createEngine("edu.pitt.dbmi.xmeso.xmesoEngine");
			TypeSystem typeSystem = null;
			List<AnalysisEngine> engines = new ArrayList<>();
			engines.add(xmesoEngine);
			engines.add(i2B2DataDataWriter);
			
//			 AnalysisEngineFactory.create.createAggregate(engines, typeSystem);
//			
			final AggregateBuilder aggregateBuilder = new AggregateBuilder();
			AnalysisEngine aae = aggregateBuilder.createAggregate();
			final AnalysisEngine rawWriter = AnalysisEngineFactory.createEngine(
					RawWriter.class, "outputDirectory", outputDirectory);
//			aggregateBuilder.add(xmesoEngine.getAnalysisEngineMetaData().);
			SimplePipeline.runPipeline(collectionReader, aae, rawWriter);
		} catch (ResourceInitializationException e) {
			e.printStackTrace();
		} catch (UIMAException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
