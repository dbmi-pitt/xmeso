package edu.pitt.dbmi.giant4j;

import org.apache.uima.analysis_engine.AnalysisEngine;

import edu.pitt.dbmi.giant4j.kb.KbEncounter;
import edu.pitt.dbmi.giant4j.kb.KbPatient;

public interface EncounterKnowledgeExtractorInterface {
	public void executePatient(KbPatient kbPatient);
	public void executeEncounter(KbEncounter kbEncounter);
	public void setAnalysisEngine(AnalysisEngine ae);
}
