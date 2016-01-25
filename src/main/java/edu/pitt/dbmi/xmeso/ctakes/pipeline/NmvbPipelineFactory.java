package edu.pitt.dbmi.xmeso.ctakes.pipeline;

import javax.annotation.concurrent.Immutable;

import org.apache.ctakes.chunker.ae.Chunker;
import org.apache.ctakes.clinicalpipeline.ClinicalPipelineFactory;
import org.apache.ctakes.contexttokenizer.ae.ContextDependentTokenizerAnnotator;
import org.apache.ctakes.core.ae.SentenceDetector;
import org.apache.ctakes.core.ae.SimpleSegmentAnnotator;
import org.apache.ctakes.core.ae.TokenizerAnnotatorPTB;
import org.apache.ctakes.core.cr.FilesInDirectoryCollectionReader;
import org.apache.ctakes.postagger.POSTagger;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.resource.ResourceInitializationException;

import edu.pitt.dbmi.xmeso.ctakes.ae.XMIWriter;
import edu.pitt.dbmi.xmeso.ctakes.cr.DeIdReader;

@Immutable
final public class NmvbPipelineFactory {

	private NmvbPipelineFactory() {
	}

	public static AnalysisEngine createPipelineEngine()
			throws ResourceInitializationException {
		return createPipelineEngine(null);
	}

	public static AnalysisEngine createPipelineEngine(
			final NmvbPipelineOptions options)
			throws ResourceInitializationException {
		final AggregateBuilder aggregateBuilder = createAggregateBuilder();
		return aggregateBuilder.createAggregate();
	}

	public static AnalysisEngineDescription createPipelineDescription()
			throws ResourceInitializationException {
		return createPipelineDescription(null);
	}

	public static AnalysisEngineDescription createPipelineDescription(
			final NmvbPipelineOptions options)
			throws ResourceInitializationException {
		final AggregateBuilder aggregateBuilder = createAggregateBuilder();
		return aggregateBuilder.createAggregateDescription();
	}

	private static AggregateBuilder createAggregateBuilder()
			throws ResourceInitializationException {
		final AggregateBuilder aggregateBuilder = new AggregateBuilder();

		addCoreEngines(aggregateBuilder);

		return aggregateBuilder;
	}

	public static CollectionReader createFilesInDirectoryReader(
			final String inputDirectory) throws ResourceInitializationException {
		return CollectionReaderFactory.createReader(
				FilesInDirectoryCollectionReader.class,
				FilesInDirectoryCollectionReader.PARAM_INPUTDIR,
				inputDirectory, FilesInDirectoryCollectionReader.PARAM_RECURSE,
				true);
	}

	public static CollectionReader createDeIdFileReader(
			final String inputFilePath) throws ResourceInitializationException {
		return CollectionReaderFactory.createReader(DeIdReader.class,
				DeIdReader.PARAM_INPUT_FILE_PATH, inputFilePath);
	}

	public static AnalysisEngine createXMIWriter(final String outputDirectory)
			throws ResourceInitializationException {
		return AnalysisEngineFactory.createEngine(XMIWriter.class,
				XMIWriter.PARAM_OUTPUTDIR, outputDirectory);
	}

	static private void addCoreEngines(final AggregateBuilder aggregateBuilder)
			throws ResourceInitializationException {
		aggregateBuilder.add(SimpleSegmentAnnotator
				.createAnnotatorDescription());
		aggregateBuilder.add(SentenceDetector.createAnnotatorDescription());
		aggregateBuilder
				.add(TokenizerAnnotatorPTB.createAnnotatorDescription());
		aggregateBuilder.add(ContextDependentTokenizerAnnotator
				.createAnnotatorDescription());
		aggregateBuilder.add(POSTagger.createAnnotatorDescription());
		aggregateBuilder.add(Chunker.createAnnotatorDescription());
		aggregateBuilder.add(ClinicalPipelineFactory
				.getStandardChunkAdjusterAnnotator());
	}

}