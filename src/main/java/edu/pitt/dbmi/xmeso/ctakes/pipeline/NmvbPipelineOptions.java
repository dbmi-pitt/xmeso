package edu.pitt.dbmi.xmeso.ctakes.pipeline;

import com.lexicalscope.jewel.cli.Option;

public interface NmvbPipelineOptions {
   @Option(
         shortName = "e",
         description = "specify the path to the directory where the trained event model is located",
         defaultValue = "org/apache/ctakes/temporal/ae/eventannotator/")
   public String getEventModelDirectory();

   @Option(
         shortName = "t",
         description = "specify the path to the directory where the trained event model is located",
         defaultValue = "/org/apache/ctakes/temporal/ae/timeannotator/")
   public String getTimeModelDirectory();

   @Option(
         shortName = "d",
         description = "specify the path to the directory where the trained event-doctime relation model is located",
         defaultValue = "/org/apache/ctakes/temporal/ae/doctimerel")
   public String getDoctimerelModelDirectory();

   @Option(
         shortName = "r",
         description = "Specify the path to the directory where the trained event-time relation model is located",
         defaultValue = "target/eval/thyme/train_and_test/event-time/")
   public String getEventTimeRelationModelDirectory();

   @Option(
         shortName = "s",
         description = "Specify the path to the directory where the trained event-event relation model is located",
         defaultValue = "target/eval/thyme/train_and_test/event-event/")
   // add in default value once we have a satisfying trained model
   public String getEventEventRelationModelDirectory();

   @Option(
         shortName = "c",
         description = "Specify the path to the directory where the trained coreference model is located",
         defaultToNull = true)
   public String getCoreferenceModelDirectory();

}
