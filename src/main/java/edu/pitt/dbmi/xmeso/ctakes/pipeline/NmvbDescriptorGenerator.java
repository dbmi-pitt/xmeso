package edu.pitt.dbmi.xmeso.ctakes.pipeline;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.xml.sax.SAXException;

import javax.annotation.concurrent.Immutable;
import java.io.FileOutputStream;
import java.io.IOException;

@Immutable
final public class NmvbDescriptorGenerator {
   private NmvbDescriptorGenerator() {
   }

   static interface DescriptorOptions extends NmvbPipelineOptions {
      @Option(
            shortName = "x",
            description = "File to write xml descriptor to",
            defaultToNull = true)
      public String getXmlOutput();

   }

   public static void writeDescriptor( final String descriptorPath )
         throws ResourceInitializationException, InvalidXMLException, IOException, SAXException {
      final AnalysisEngineDescription aed = NmvbPipelineFactory.createPipelineDescription();
      aed.toXML( new FileOutputStream( descriptorPath ) );
   }

   public static void main( final String... args )
         throws ResourceInitializationException, InvalidXMLException, IOException, SAXException {
      final DescriptorOptions options = CliFactory.parseArguments( DescriptorOptions.class, args );
      final AnalysisEngineDescription aed = NmvbPipelineFactory.createPipelineDescription( options );
      if ( options.getXmlOutput() != null ) {
         aed.toXML( new FileOutputStream( options.getXmlOutput() ) );
      }
   }


}
