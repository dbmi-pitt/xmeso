package edu.pitt.dbmi.xmeso.ctakes.ae;

import edu.pitt.dbmi.nlp.noble.terminology.Annotation;
import edu.pitt.dbmi.nlp.noble.terminology.Concept;
import edu.pitt.dbmi.nlp.noble.terminology.Definition;
import edu.pitt.dbmi.nlp.noble.terminology.Relation;
import edu.pitt.dbmi.nlp.noble.terminology.SemanticType;
import edu.pitt.dbmi.nlp.noble.terminology.Source;
import edu.pitt.dbmi.nlp.noble.terminology.Terminology;
import edu.pitt.dbmi.nlp.noble.terminology.TerminologyException;

public class ConceptDiagnosticsDisplay {
	
	public void displayConceptDiagnostics(Concept c)
			throws TerminologyException {
		StringBuffer sb = new StringBuffer();

		// Done
		sb.append("cui: " + c.getCode() + "\n");
		sb.append("name: " + c.getName() + "\n");
		sb.append("definition: " + c.getDefinition() + "\n");
		sb.append("cn: " + c.getName() + "\n");
		SemanticType[] semTypes = c.getSemanticTypes();
		for (SemanticType sType : semTypes) {
			String tui = sType.getCode();
			String sty = sType.getName();
			sb.append("tui: " + tui + "sty: " + sty + "\n");
		}

		// Skipping
		sb.append("offset: " + c.getOffset() + "\n");
		sb.append("conceptClass: " + c.getConceptClass() + "\n");
		sb.append("content: " + c.getContent() + "\n");
		sb.append("matchedTerm: " + c.getMatchedTerm() + "\n");
		sb.append("score: " + c.getScore() + "\n");
		String conceptCode = c.getCode(new Source("NCI"));
		sb.append("conceptCode (NCI): " + conceptCode + "\n");
		conceptCode = c.getCode(new Source("LNC"));
		sb.append("conceptCode (LNC): " + conceptCode + "\n");
		conceptCode = c.getCode(new Source("MSH"));
		sb.append("conceptCode (MSH): " + conceptCode + "\n");

		// Todo

		sb.append("preferredTerm: " + c.getPreferredTerm() + "\n");
		String[] synonyms = c.getSynonyms();
		for (String synonym : synonyms) {
			sb.append("synonym: " + synonym + "\n");
		}

		sb.append("searchString: " + c.getSearchString() + "\n");
		c.getProperties();

		Terminology term = c.getTerminology();
		sb.append("terminology name: " + term.getName() + "\n");
		c.getTerms();
		c.getText();
		c.getWordMap();

		c.getRelatedConcepts();
		Relation relation = new Relation("isa");
		c.getRelatedConcepts(relation);
		c.getRelationMap();
		c.getRelations();

		Annotation annots[] = c.getAnnotations();
		c.getParentConcepts();
		edu.pitt.dbmi.nlp.noble.terminology.Concept[] children = c
				.getChildrenConcepts();
		Definition[] definitions = c.getDefinitions();
		String[] matchedTerms = c.getMatchedTerms();

		c.getCodes();

		System.out.println(sb.toString());

	}

}
