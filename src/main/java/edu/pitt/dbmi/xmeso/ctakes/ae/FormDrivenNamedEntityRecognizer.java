package edu.pitt.dbmi.xmeso.ctakes.ae;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;

import edu.pitt.dbmi.nlp.noble.terminology.Concept;
import edu.pitt.dbmi.nlp.noble.terminology.Definition;
import edu.pitt.dbmi.nlp.noble.terminology.SemanticType;
import edu.pitt.dbmi.nlp.noble.terminology.Source;
import edu.pitt.dbmi.nlp.noble.terminology.Term;
import edu.pitt.dbmi.nlp.noble.terminology.TerminologyException;
import edu.pitt.dbmi.nlp.noble.terminology.impl.NobleCoderTerminology;

public class FormDrivenNamedEntityRecognizer {

	private NobleCoderTerminology terminology;

	public static void main(String[] args) {
		FormDrivenNamedEntityRecognizer ner = new FormDrivenNamedEntityRecognizer();
		ner.execute();
	}
	
	public FormDrivenNamedEntityRecognizer() {
	}

	public void execute() {
		try {
			buildTerminology();
		}
		catch (TerminologyException te) {
			te.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void buildTerminology() throws TerminologyException, IOException {
		final Map<String, Concept> conceptMap = new HashMap<String, Concept>();
		File inputFile = new File("C:\\ws\\ws-xmeso\\xmeso\\data\\xmeso\\input\\form\\nmvb.txt");
		LineIterator lineIterator = IOUtils.lineIterator(new FileInputStream(inputFile), "UTF-8");
		while (lineIterator.hasNext()) {
			String line = lineIterator.next();
			if (line.contains("[SNOMED_CT]")) {
				String[] parts = line.split("\\(");
				if (parts.length == 2) {
					String preferredTerm = formatPreferredTerm(parts[0]);
					String cui = formatCui(parts[1]);
					if (conceptMap.get(cui) == null) {
					
						System.out.println(cui + " ==> " + preferredTerm);
				
						Concept concept = new Concept(cui, preferredTerm);
					
						Source source = new Source();
						source.setCode("SNOMED");
						source.setDescription("Standard Nomenclature of Medinene");						 
						Definition definition = new Definition();
						definition.setDefinition("Pending");
						definition.setSource(source);
						definition.setLanguage("en");					
						SemanticType semanticType = SemanticType.getSemanticType("Acquired Abnormality", "T020");
						
						concept.setCode(cui);
						concept.setName(preferredTerm);					
						concept.addSource(source);
						concept.addDefinition(definition);
						concept.addSemanticType(semanticType);
						
						final Term[] terms = {};
						concept.setTerms(terms);
						concept.initialize();
						
						conceptMap.put(cui, concept);
						
						terminology.addConcept(concept);
					}	
				}		
			}
		}
	}
	
	private String formatPreferredTerm(String rawPreferredTerm) {
		String preferredTerm = rawPreferredTerm.replaceAll("^\\W+", "");
		preferredTerm = preferredTerm.trim();
		preferredTerm = preferredTerm.replaceAll("\\s+", " ");
		preferredTerm = StringUtils.lowerCase(preferredTerm);
		return preferredTerm;
	}
	
	private String formatCui(String rawCuiPart) {
		String cui = StringUtils.substringBeforeLast(rawCuiPart, ")");
		cui = cui.replaceAll("^\\s+", "");
		cui = cui.replaceAll("\\s+$", "");
		cui = cui.replaceAll("\\s+", " ");
		Integer cuiAsInteger = new Integer(cui);
		cui = StringUtils.leftPad(cuiAsInteger.intValue()+"", 10, "0");
		return "SNOWMED_" + cui;
	}
	
	public NobleCoderTerminology getTerminology() {
		return terminology;
	}

	public void setTerminology(NobleCoderTerminology terminology) {
		this.terminology = terminology;
	}

}
