package edu.pitt.dbmi.xmeso.ctakes.pipeline;

import java.io.IOException;

import javax.annotation.concurrent.Immutable;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;

@Immutable
final public class NmvbPipelineRunner {
   private NmvbPipelineRunner() {
   }

   static interface Options extends NmvbPipelineOptions {
      @Option(
            shortName = "i",
            description = "specify the path to the deid output file to be processed")
      public String getInputDirectory();

      @Option(
            shortName = "o",
            description = "specify the path to the directory where the output xmi files are to be saved")
      public String getOutputDirectory();
   }

   public static void runNmvbPipeline( final String inputFilePath,
                                         final String outputDirectory ) throws UIMAException, IOException {
      final CollectionReader collectionReader = NmvbPipelineFactory.createDeIdFileReader(inputFilePath);
      final AnalysisEngineDescription ctakesNmvbDescription = NmvbPipelineFactory.createPipelineDescription();
      final AnalysisEngine ctakesNmvbEngine = AnalysisEngineFactory.createEngine( ctakesNmvbDescription );
      final AnalysisEngine xmiWriter = NmvbPipelineFactory.createXMIWriter( outputDirectory );
      SimplePipeline
            .runPipeline( collectionReader, ctakesNmvbEngine, xmiWriter );
   }


   public static void main( final String... args ) throws UIMAException, IOException {
      final Options options = CliFactory.parseArguments( Options.class, args );
      runNmvbPipeline( options.getInputDirectory(), options.getOutputDirectory() );
   }


}
