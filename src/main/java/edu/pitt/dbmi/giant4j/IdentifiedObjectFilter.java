package edu.pitt.dbmi.giant4j;

import org.drools.runtime.ObjectFilter;

import edu.pitt.dbmi.giant4j.kb.KbIdentifiedInterface;

public class IdentifiedObjectFilter implements ObjectFilter {

	@Override
	public boolean accept(Object obj) {
		return KbIdentifiedInterface.class.isAssignableFrom(obj.getClass());
	}

}
