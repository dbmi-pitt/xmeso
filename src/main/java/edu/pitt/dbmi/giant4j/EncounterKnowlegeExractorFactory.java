package edu.pitt.dbmi.giant4j;

public class EncounterKnowlegeExractorFactory {
	
	private static EncounterKnowledgeExtractorInterface encounterKnowledgeExtractor = new EncounterKnowledgeExtractorStub();
	
	private EncounterKnowlegeExractorFactory() {}
	
	public static EncounterKnowledgeExtractorInterface getEncounterKnowledgeExtractor() {
		return encounterKnowledgeExtractor;
	}
	public static void setEncounterKnowledgeExtractor(EncounterKnowledgeExtractorInterface encounterKnowledgeExtractorImpl) {
		encounterKnowledgeExtractor = encounterKnowledgeExtractorImpl;
	}
}
