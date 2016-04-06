package edu.pitt.dbmi.giant4j.treeview.artifact;

import org.apache.commons.lang3.StringUtils;

import edu.pitt.dbmi.giant4j.kb.KbPatient;

public class AnnotationsSummaryUserObject {
	
	private KbPatient patient;
	private String provider;
	private long observationInstanceNumber = -1L;

	public KbPatient getPatient() {
		return patient;
	}

	public void setPatient(KbPatient patient) {
		this.patient = patient;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public long getObservationInstanceNumber() {
		return observationInstanceNumber;
	}

	public void setObservationInstanceNumber(long observationInstanceNumber) {
		this.observationInstanceNumber = observationInstanceNumber;
	}

	public String toString() {
		String providerPart = StringUtils.substringAfterLast(provider, ":");
		return providerPart.toUpperCase() + " Summary";
	}
}
