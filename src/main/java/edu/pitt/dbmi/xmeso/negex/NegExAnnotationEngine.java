package edu.pitt.dbmi.xmeso.negex;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import edu.pitt.dbmi.xmeso.typesystem.Typesystem.Part;
import edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoNamedEntity;

public class NegExAnnotationEngine extends
		org.apache.uima.fit.component.JCasAnnotator_ImplBase {

/**
 * Vamsi, 
 *    Here is a place to implement the NegEx algorithm.
 *  See below examples of pulling the Types you can work with.	
 */
	
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {	
		System.out.println(getClass().getName() + " being called via a Ruta script..");
		
		Collection<Part> parts = JCasUtil.select(jCas, Part.class);
		for (Part part : parts) {
			System.out.println("Section: " + part.getSectionName() + " Part: " + part.getPartNumber());
			Collection<XmesoNamedEntity> nes = JCasUtil.selectCovered(XmesoNamedEntity.class, part);
			for (XmesoNamedEntity ne : nes) {
				String snomedCode = ne.getSnomedCode();
				if (snomedCode != null) {
					System.out.println("\tCODE: " + snomedCode);
				}
				
			}
		}
		
		System.out.println("Done...");
		
		
	}
}
