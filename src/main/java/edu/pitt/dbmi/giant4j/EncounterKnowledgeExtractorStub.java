package edu.pitt.dbmi.giant4j;

import org.apache.uima.analysis_engine.AnalysisEngine;

import edu.pitt.dbmi.giant4j.kb.KbEncounter;
import edu.pitt.dbmi.giant4j.kb.KbPatient;

public class EncounterKnowledgeExtractorStub implements EncounterKnowledgeExtractorInterface {
	
	@Override
	public void executePatient(KbPatient patient) {
		if (patient.getSequence() == 0) {
			initializePatientZeroEncounters(patient);
		}
		else if (patient.getSequence() == 1) {
			initializePatientOneEncounters(patient);
		}
	}
	
	@Override
	public void setAnalysisEngine(AnalysisEngine ae) {
	}
	
	@Override
	public void executeEncounter(KbEncounter kbEncounter) {
	}
	
	private void initializePatientZeroEncounters(KbPatient patient) {
		
		for (KbEncounter encounter : patient.getEncounters()) {
			
			if (encounter.getSequence() == 0) {
				encounter.setKind("Pathology Report");
				encounter.setPatientId(patient.getId());
				encounter.setSequence(0);
				
				
				
			}
			else if (encounter.getSequence() == 1) {
				
				encounter.setKind("Progress Note");
				encounter.setPatientId(patient.getId());
				encounter.setSequence(1);				
				
				
			
			}
			else if (encounter.getSequence() == 2) {
				
				encounter.setKind("Progress Note");
				encounter.setPatientId(patient.getId());
				encounter.setSequence(2);
				
			
			}
		}
	}
	
	private void initializePatientOneEncounters(KbPatient patient) {
		for (KbEncounter encounter : patient.getEncounters()) {
			
			if (encounter.getSequence() == 0) {
				encounter.setKind("Pathology Report");
				encounter.setPatientId(patient.getId());
				
				
			}
			else if (encounter.getSequence() == 1) {
				
				encounter.setKind("Pathology Report");
				encounter.setPatientId(patient.getId());
			
			}
			else if (encounter.getSequence() == 2) {
				
				encounter.setKind("Pathology Report");
				encounter.setPatientId(patient.getId());
			
				
			}
		}
		
	}

	



}
