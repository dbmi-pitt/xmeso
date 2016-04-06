package edu.pitt.dbmi.giant4j.ontology;

import com.lexicalscope.jewel.cli.Option;

public interface KbClassGeneratorOptions {

	@Option(shortName = "o", description = "specify the path to ontology", defaultValue = "../../DeepPhe/ontologies/breastCancer.owl")
	public String getOntologyPath();

	@Option(shortName = "j", description = "specify the path to java source", defaultValue = "../deepphe-uima/src/main/java/org/healthnlp/deepphe/summarization/jess/kb")
	public String getJavaSourceDirectory();

	@Option(shortName = "d", description = "specify the path to the jess clips files", defaultValue = "../deepphe-uima/src/main/jess/autoload")
	public String getDefTemplatesDirectoryPath();

}
