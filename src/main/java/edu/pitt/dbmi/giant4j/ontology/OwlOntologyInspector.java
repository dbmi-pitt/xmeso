package edu.pitt.dbmi.giant4j.ontology;

import static org.semanticweb.owlapi.search.Searcher.annotations;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

public class OwlOntologyInspector {

	public static final String CONST_DEEPPHE_ONT_PATH = "http://localhost:8080/deepphe/ontologies/modelBreastCancer.owl";
	public static final String CONST_NAMESPACE_CANCER = "http://localhost:8080/deepphe/ontologies/modelCancer.owl";
	public static final String CONST_NAMESPACE_BREAST_CANCER = "http://localhost:8080/deepphe/ontologies/modelBreastCancer.owl#";

	public static void main(String[] args) {
		OwlOntologyInspector inspector = new OwlOntologyInspector();
		try {
			inspector.conductTrials();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void conductTrials() throws OWLOntologyCreationException,
			OWLOntologyStorageException, IOException {
		// examineDeepPheOntology();
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = loadDeepPheOntologyFromUrl(m);
		System.out.println("\n\nInterpretation subclasses...");
		traceSubClses(
				o,
				IRI.create(CONST_NAMESPACE_BREAST_CANCER
						+ "TumorSize"));
		traceSubClses(
				o,
				IRI.create(CONST_NAMESPACE_BREAST_CANCER
						+ "BreastCancerPrimaryTumorClassification"));
		traceSubClses(
				o,
				IRI.create(CONST_NAMESPACE_BREAST_CANCER
						+ "ClinicalRegionalLymphNodeClassification"));
		traceSubClses(
				o,
				IRI.create(CONST_NAMESPACE_BREAST_CANCER
						+ "BreastCancerDistantMetastasisClassification"));
		traceSubClses(
				o,
				IRI.create(CONST_NAMESPACE_BREAST_CANCER
						+ "BreastCancerStage"));
		traceSubClses(
				o,
				IRI.create(CONST_NAMESPACE_CANCER
						+ "#Interpretation"));
		traceSubClses(
				o,
				IRI.create("http://ontologies.dbmi.pitt.edu/deepphe/modelCancer.owl#Interpretation"));
//		System.out.println("\n\nTrace Medication sub classes...");
//		traceSubClses(
//				o,
//				IRI.create("http://blulab.chpc.utah.edu/ontologies/SchemaOntology.owl#Medication"));
//		System.out.println("\n\nTrace I_03074 sub classes...");
//		traceSubClses(
//				o,
//				IRI.create("http://blulab.chpc.utah.edu/ontologies/SchemaOntology.owl#I_03074"));
//		System.out.println("\n\nTrace Entity sub classes...");
//		traceSubClses(
//				o,
//				IRI.create("http://blulab.chpc.utah.edu/ontologies/SchemaOntology.owl#Entity"));
//		System.out.println("\n\nTrace Patient super classes...");
//		traceSubClses(
//				o,
//				IRI.create("http://blulab.chpc.utah.edu/ontologies/SchemaOntology.owl#Patient"));
//		traceSubClses(
//		o,
//		IRI.create("http://slidetutor.upmc.edu/deepPhe/BreastCancer.owl#Breast_Cancer_TNM_Finding_v7"));
	
	}

	private OWLOntology loadDeepPheOntology(OWLOntologyManager manager)
			throws IOException, OWLOntologyCreationException {
		File f = new File(CONST_DEEPPHE_ONT_PATH);
		String fText = FileUtils.readFileToString(f, "UTF-8");
		OWLOntology o = manager
				.loadOntologyFromOntologyDocument(new StringDocumentSource(
						fText));
		return o;
	}
	
	private OWLOntology loadDeepPheOntologyFromUrl(OWLOntologyManager manager) {
		IRI ontologyIRI;
		OWLOntology o = null;
		try {
			ontologyIRI = IRI.create(new URL(CONST_DEEPPHE_ONT_PATH) );
			o = manager.loadOntology(ontologyIRI);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
		return o;
	}

	private void traceSubClses(OWLOntology o, IRI iri) throws IOException,
			OWLOntologyCreationException {
		OWLReasonerFactory reasonerFactory;
		reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createReasoner(o);
		OWLDataFactory fac = o.getOWLOntologyManager().getOWLDataFactory();
		OWLClass tgtCls = fac.getOWLClass(iri);
		Queue<OWLClass> clsQueue = new LinkedList<>();
		clsQueue.add(tgtCls);
		while (true) {
			OWLClass cls = clsQueue.poll();
			if (cls == null) {
				break;
			} else {
				System.out.println("Class: " + cls.getIRI().toString());
				System.out.println("rdfs:Label " + labelFor(o, cls));
				System.out.println(("prefCui " + prefCuiFor(o, cls)));
				Set<OWLClass> subClses = reasoner.getSubClasses(cls, true).getFlattened();
				for (OWLClass subCls : subClses) {
					clsQueue.add(subCls);
				}
			}
		}
	}

	private void traceSuperClses(OWLOntology o, IRI iri) throws IOException,
			OWLOntologyCreationException {
		OWLReasonerFactory reasonerFactory;
		reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createReasoner(o);
		OWLDataFactory fac = o.getOWLOntologyManager().getOWLDataFactory();
		OWLClass tgtCls = fac.getOWLClass(iri);
		Queue<OWLClass> clsQueue = new LinkedList<OWLClass>();
		clsQueue.add(tgtCls);
		while (true) {
			OWLClass cls = clsQueue.poll();
			if (cls == null) {
				break;
			} else {
				System.out.println("Class: " + cls.getIRI().toString());
				System.out.println("rdfs:Label " + labelFor(o, cls));
				Set<OWLClass> superClses = reasoner.getSuperClasses(cls, true).getFlattened();
				for (OWLClass superCls : superClses) {
					clsQueue.add(superCls);
				}
			}
		}
	}
	
	private String prefCuiFor(OWLOntology ontology, OWLClass cls) {
		/*
		 * Use a visitor to extract label annotations
		 */
		PreferredCuiExtractor pce = new PreferredCuiExtractor();
		for (OWLAnnotation anno : annotations(ontology
				.getAnnotationAssertionAxioms(cls.getIRI()))) {
			anno.accept(pce);
		}
		/* Print out the label if there is one. If not, just use the class URI */
		if (pce.getResult() != null) {
			return pce.getResult();
		} else {
			return cls.getIRI().toString();
		}
	}

	private String labelFor(OWLOntology ontology, OWLClass cls) {
		/*
		 * Use a visitor to extract label annotations
		 */
		LabelExtractor le = new LabelExtractor();
		for (OWLAnnotation anno : annotations(ontology
				.getAnnotationAssertionAxioms(cls.getIRI()))) {
			anno.accept(le);
		}
		/* Print out the label if there is one. If not, just use the class URI */
		if (le.getResult() != null) {
			return le.getResult();
		} else {
			return cls.getIRI().toString();
		}
	}
}
