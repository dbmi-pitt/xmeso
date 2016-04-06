package edu.pitt.dbmi.giant4j.ontology;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

/**
 *
 * @author kjm84
 */
public class I2b2OntologyBuilder {

	public static final String CONST_TOP_LEVEL_ENTRY = "http://slidetutor.upmc.edu/deepPhe/BreastCancer.owl#Breast_Cancer_TNM_Finding_v7";

	private List<String> topLevelClses;
	

	private String ontologyPath;
	private String sourceSystemCode;

	private TreeSet<PartialPath> partialPathTreeSet;
	
	private HashMap<String, PartialPath> partialPathMap;

	public void execute() throws OWLOntologyCreationException, IOException,
			ClassNotFoundException, SQLException {
		for (String topLevelCls : topLevelClses) {
			partialPathTreeSet.addAll(extractOntologyPartialPaths(topLevelCls));
		}	
		addTopLevelNode();
		displayPaths(partialPathTreeSet);
		for (PartialPath partialPath : partialPathTreeSet) {
			partialPathMap.put(partialPath.getBaseCode(), partialPath);
		}
	}
	
	private void addTopLevelNode() {
		PartialPath topLevel = new PartialPath();
		topLevel.setPath("\\"+getSourceSystemCode());
		topLevel.setLevel(0);
		topLevel.setLeaf(false);
		partialPathTreeSet.add(topLevel);
	}

	private TreeSet<PartialPath> extractOntologyPartialPaths(String topLevelCls)
			throws OWLOntologyCreationException, IOException {

		final TreeSet<PartialPath> partialPaths = new TreeSet<PartialPath>();

		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = loadDeepPheOntologyFromUrl(m);
		OWLReasonerFactory reasonerFactory;
		reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createReasoner(o);
		OWLDataFactory fac = m.getOWLDataFactory();
		OWLClass elementConcept = fac.getOWLClass(IRI
				.create(topLevelCls));

		final Queue<PartialPath> partialPathQueue = new LinkedList<PartialPath>();
		NodeSet<OWLClass> subClses = reasoner.getSubClasses(elementConcept,
				true);
		for (Node<OWLClass> subCls : subClses) {
			PartialPath path = new PartialPath();
			path.setPath("\\" + getSourceSystemCode());
			path.setReasoner(reasoner);
			path.setCls(subCls.getRepresentativeElement());
			path.setLevel(1);
			partialPathQueue.add(path);
		}

		while (true) {
			PartialPath path;
			path = partialPathQueue.poll();
			if (path == null) {
				break;
			} else {
				partialPathQueue.addAll(path.expand());
			}
			partialPaths.add(path);
		}

	
		return partialPaths;
	}

	private OWLOntology loadDeepPheOntologyFromFile(OWLOntologyManager manager)
			throws IOException, OWLOntologyCreationException {
		File f = new File(ontologyPath);
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
			ontologyIRI = IRI.create(new URL(ontologyPath) );
			o = manager.loadOntology(ontologyIRI);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
		return o;
	}

	private void displayPaths(TreeSet<PartialPath> paths) {
		for (PartialPath path : paths) {
			System.out.println(path);
		}
	}

	public void setOntologyPath(String ontologyPath) {
		this.ontologyPath = ontologyPath;

	}

	public HashMap<String, PartialPath> getPartialPathMap() {
		return partialPathMap;
	}
	
	public String getSourceSystemCode() {
		return sourceSystemCode;
	}

	public void setSourceSystemCode(String sourceSystemCode) {
		this.sourceSystemCode = sourceSystemCode;
	}
	
	public List<String> getTopLevelClses() {
		return topLevelClses;
	}

	public void setTopLevelClses(List<String> topLevelClses) {
		this.topLevelClses = topLevelClses;
	}
	
	public TreeSet<PartialPath> getPartialPathTreeSet() {
		return partialPathTreeSet;
	}

	public void setPartialPathTreeSet(TreeSet<PartialPath> partialPathTreeSet) {
		this.partialPathTreeSet = partialPathTreeSet;
	}

	public String getOntologyPath() {
		return ontologyPath;
	}

	public void setPartialPathMap(HashMap<String, PartialPath> partialPathMap) {
		this.partialPathMap = partialPathMap;
	}


}

