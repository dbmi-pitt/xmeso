package edu.pitt.dbmi.giant4j.treeview.artifact;

import edu.pitt.dbmi.giant4j.kb.KbEncounter;

public class AnnotationsAnaforaUserObject {
	private KbEncounter kbEncounter;
	private long annotationIndex = -1L;
	public long getAnnotationIndex() {
		return annotationIndex;
	}
	public void setAnnotationIndex(long annotationIndex) {
		this.annotationIndex = annotationIndex;
	}
	public KbEncounter getKbEncounter() {
		return kbEncounter;
	}
	public void setKbEncounter(KbEncounter kbEncounter) {
		this.kbEncounter = kbEncounter;
	}
	public String toString() {
		return "Anafora";
	}
}
