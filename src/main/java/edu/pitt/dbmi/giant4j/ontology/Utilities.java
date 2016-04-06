package edu.pitt.dbmi.giant4j.ontology;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

public class Utilities {
	
	  public static void displaySignatureClses(OWLOntology o) {
	        Set<OWLClass> clses = o.getClassesInSignature();
	        for (OWLClass cls : clses) {
	        	System.out.println(cls.getIRI().toString());
	        }
	    }
	  
	   public static void examineDeepPheOntology(OWLOntology o) throws IOException, OWLOntologyCreationException {
	        OWLReasonerFactory reasonerFactory;
	        reasonerFactory = new StructuralReasonerFactory();
	        OWLReasoner reasoner = reasonerFactory.createReasoner(o);
	        Node<OWLClass> topNode = reasoner.getTopClassNode();
	        Queue<OWLClass> clsQueue = new LinkedList<OWLClass>();
	        clsQueue.add(topNode.getRepresentativeElement());
	        while (true) {
	            OWLClass cls = clsQueue.poll();
	            if (cls == null) {
	                break;
	            } else {
	                System.out.println(cls.getIRI().toString());
	                for (Node<OWLClass> subClsNode : reasoner.getSubClasses(cls, true)) {
	                    clsQueue.add(subClsNode.getRepresentativeElement());
	                }
	            }
	        }
	    }
	   
	   public static String extractCname(PartialPath partialPath) {
			int lastSlashPos;
			lastSlashPos = partialPath.getPath().lastIndexOf("\\");
			return partialPath.getPath().substring(lastSlashPos + 1);
		}

}
