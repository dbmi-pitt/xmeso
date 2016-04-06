package edu.pitt.dbmi.giant4j.ontology;

import static org.semanticweb.owlapi.search.Searcher.annotations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import com.lexicalscope.jewel.cli.CliFactory;

public class KnowledgebaseClassGenerator {

	private final CamelCaseConverter camelCaseConverter = new CamelCaseConverter();

	private KbClassGeneratorOptions options;

	private OWLOntologyManager currentOntologyManager;
	private OWLOntology currentOntology;
	private OWLReasonerFactory reasonerFactory;
	private OWLReasoner reasoner;
	private OWLDataFactory owlDataFactory;

	private final String javaSrcDirectoryRoot = "C:\\ws\\ws-deepphe-6\\DeepPhe\\deepphe-workbench\\modgen";
	private File javaFilesParentDirectoryPath;
	private File deftemplatesDirectoryPath;

	private final CodeGeneratorJava codeGeneratorJava = new CodeGeneratorJava();
	private final CodeGeneratorDeftemplates codeGeneratorDeftemplates = new CodeGeneratorDeftemplates();

	private final String topCls = "http://www.w3.org/2002/07/owl#Thing";
	private final Queue<OWLClass> clsQueue = new LinkedList<>();
	private final HashMap<String, IRI> iriMap = new HashMap<String, IRI>();
	private final HashMap<String, String> parentMap = new HashMap<String, String>();
	private final TreeSet<String> sortedClsNames = new TreeSet<String>();

	public static void main(String[] args) {
		try {
			KnowledgebaseClassGenerator generator = new KnowledgebaseClassGenerator();
			generator.setOptions(CliFactory.parseArguments(
					KbClassGeneratorOptions.class, args));
			generator.execute();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void execute() throws OWLOntologyCreationException, IOException {
		initializeOntologyAndJavaFootPrint();
		generateDefTemplates();
		generateJavaClasses();
	}

	private void generateDefTemplates() throws IOException,
			OWLOntologyCreationException {
		codeGeneratorDeftemplates.setFileStartOffset(11);
		codeGeneratorDeftemplates
				.setDeftemplatesDirectoryPath(deftemplatesDirectoryPath
						.getAbsolutePath());
		clsQueue.add(getClassForIri(topCls));
		while (true) {
			OWLClass cls = clsQueue.poll();
			if (cls == null) {
				codeGeneratorDeftemplates.writeToFile();
				break;
			} else {
				if (isPittClass(cls.getIRI().toString())) {
					processCls(cls);
				}
				queueSubClasses(cls);
			}
		}
	}

	private void generateJavaClasses() throws IOException,
			OWLOntologyCreationException {
		gatherSortedClses();
		codeGeneratorJava
				.setJavaPackage("org.healthnlp.deepphe.summarization.jess.kb");
		codeGeneratorJava.setJavaFilesParentDirectoryPath(javaSrcDirectoryRoot);
		for (String javaCls : sortedClsNames) {
			IRI iri = iriMap.get(javaCls);
			OWLClass cls = getClassForIri(iri.toString());
			String javaCui = deriveCodeFromCuiAnnotation(cls);
			String javaPreferredTerm = derivePreferredTermFromLabelAnnotation(cls);
			javaCui = (javaCui == null) ? javaCls : javaCui;
			javaPreferredTerm = (javaPreferredTerm == null) ? javaCls
					: javaPreferredTerm;
			String javaSuperCls = deriveJavaParentClassFromOwlClass(cls);
			codeGeneratorJava.setJavaCls(javaCls);
			codeGeneratorJava.setJavaSuperCls(javaSuperCls);
			codeGeneratorJava.setJavaCui(javaCui);
			codeGeneratorJava.setJavaPreferredTerm(javaPreferredTerm);
			codeGeneratorJava.codeGenerate();
		}
	}

	private void processCls(OWLClass cls) throws IOException {
		String iriAsString = cls.getIRI().toString();
		if (iriMap.get(iriAsString) == null) {
			iriMap.put(iriAsString, cls.getIRI());
			String javaCls = getCamelCasedClassName(cls);
			String javaSuperCls = deriveJavaParentClassFromOwlClass(cls);
			codeGeneratorDeftemplates.setJavaCls(javaCls);
			codeGeneratorDeftemplates.setJavaSuperCls(javaSuperCls);
			codeGeneratorDeftemplates.codeGenerate();
		}
	}

	private String deriveJavaParentClassFromOwlClass(OWLClass cls) {
		// First check if this class has already been assigned a parent
		String javaParentClass = null;
		String javaClass = getCamelCasedClassName(cls);
		javaParentClass = parentMap.get(javaClass);
		if (javaParentClass == null) {
			OWLClass parentClass = getFirstTouchedSuperClass(cls);
			if (parentClass != null) {
				javaParentClass = getCamelCasedClassName(parentClass);
			}
			else {
				javaParentClass = "Summary";
			}
			parentMap.put(javaClass, javaParentClass);
		}
		return javaParentClass;
	}

	private void queueSubClasses(OWLClass cls) {
		boolean isDirectSubclasses = true;
		Set<OWLClass> subClses = reasoner
				.getSubClasses(cls, isDirectSubclasses).getFlattened();
		for (OWLClass subCls : subClses) {
			clsQueue.add(subCls);
		}
	}

	private void initializeOntologyAndJavaFootPrint()
			throws OWLOntologyCreationException, IOException {
		initializeOntology();
		initializeJavaFootPrint();
	}

	private void initializeOntology() throws OWLOntologyCreationException,
			IOException {
		currentOntologyManager = OWLManager.createOWLOntologyManager();
		currentOntology = loadDeepPheOntologyFromUri(currentOntologyManager);
		reasonerFactory = new StructuralReasonerFactory();
		reasoner = reasonerFactory.createReasoner(currentOntology);
		owlDataFactory = currentOntology.getOWLOntologyManager()
				.getOWLDataFactory();
	}

	private OWLOntology loadDeepPheOntologyFromUri(OWLOntologyManager manager)
			throws IOException, OWLOntologyCreationException {
		IRI iri = IRI.create(options.getOntologyPath());
		OWLOntology o = manager.loadOntology(iri);
		return o;
	}

	@SuppressWarnings("unused")
	private OWLOntology loadDeepPheOntologyFromFile(OWLOntologyManager manager)
			throws IOException, OWLOntologyCreationException {
		File f = new File(options.getOntologyPath());
		OWLOntology o = manager.loadOntologyFromOntologyDocument(f);
		return o;
	}

	private void initializeJavaFootPrint() throws FileNotFoundException {
		javaFilesParentDirectoryPath = new File(
				options.getJavaSourceDirectory());
		if (!javaFilesParentDirectoryPath.exists()
				|| !javaFilesParentDirectoryPath.isDirectory()) {
			throw new FileNotFoundException(
					javaFilesParentDirectoryPath.getAbsolutePath()
							+ " is not found.");
		}
		deftemplatesDirectoryPath = new File(
				options.getDefTemplatesDirectoryPath());
		if (!deftemplatesDirectoryPath.exists()
				|| !deftemplatesDirectoryPath.isDirectory()) {
			throw new FileNotFoundException(
					deftemplatesDirectoryPath.getAbsolutePath()
							+ " is not found.");
		}
	}

	private OWLClass getClassForIri(String iri) {
		return owlDataFactory.getOWLClass(IRI.create(iri));
	}

	private void gatherSortedClses() {
		final HashMap<String, IRI> clsNameToIriMap = new HashMap<String, IRI>();
		for (IRI clsIri : iriMap.values()) {
			String clsIriAsString = clsIri.toString();
			String clsName = StringUtils
					.substringAfterLast(clsIriAsString, "#");
			String javaClsName = camelCaseConverter.toCamelCase(clsName);
			if (sortedClsNames.contains(javaClsName)) {
				System.err.println("CLASS COLLISION:" + javaClsName);
			} else {
				sortedClsNames.add(javaClsName);
				clsNameToIriMap.put(javaClsName, clsIri);
			}
		}
		iriMap.clear();
		iriMap.putAll(clsNameToIriMap);
	}

	private String deriveCodeFromCuiAnnotation(OWLClass cls) {
		String javaCode = prefCuiFor(currentOntology, cls);
		return javaCode;
	}

	private String derivePreferredTermFromLabelAnnotation(OWLClass cls) {
		String javaPreferredTerm = labelFor(currentOntology, cls);
		return javaPreferredTerm;
	}
	
	private String getCamelCasedClassName(OWLClass cls) {
		return camelCaseConverter.toCamelCase(getClassName(cls));
	}

	private String getClassName(OWLClass cls) {
		String result = null;
		IRI clsIri = cls.getIRI();
		String clsIriString = (clsIri != null) ? clsIri.toString() : null;
		result = (clsIriString != null) ? StringUtils.substringAfterLast(
				clsIriString, "#") : null;
		return result;
	}

	private OWLClass getFirstTouchedSuperClass(OWLClass cls) {
		Set<OWLClass> superClses = reasoner.getSuperClasses(cls, true)
				.getFlattened();
		OWLClass superCls = null;
		// return the first super class that has been seen so far
		for (Iterator<OWLClass> iter = superClses.iterator(); iter.hasNext();) {
			superCls = iter.next();
			String superClsIriAsString = superCls.getIRI().toString();
			if (iriMap.get(superClsIriAsString) != null) {
				break;
			}
			else {
				superCls = null;
			}
		}
		return superCls;
	}

	private String prefCuiFor(OWLOntology ontology, OWLClass cls) {
		String cui = "NA";
		/*
		 * Use a visitor to extract label annotations
		 */
		boolean isDerivable = true;
		isDerivable = isDerivable && (ontology != null);
		isDerivable = isDerivable && (cls != null);
		isDerivable = isDerivable && (cls.getIRI() != null);
		isDerivable = isDerivable
				&& (annotations(ontology.getAnnotationAssertionAxioms(cls
						.getIRI())) != null);
		if (isDerivable) {
			PreferredCuiExtractor pce = new PreferredCuiExtractor();
			Collection<OWLAnnotation> annots = annotations(ontology
					.getAnnotationAssertionAxioms(cls.getIRI()));
			for (OWLAnnotation anno : annots) {
				anno.accept(pce);
			}
			if (pce.getResult() != null) {
				cui = pce.getResult();
			}
		}
		return cui;
	}

	private String labelFor(OWLOntology ontology, OWLClass cls) {
		String label = "NA";
		/*
		 * Use a visitor to extract label annotations
		 */
		boolean isDerivable = true;
		isDerivable = isDerivable && (ontology != null);
		isDerivable = isDerivable && (cls != null);
		isDerivable = isDerivable && (cls.getIRI() != null);
		isDerivable = isDerivable
				&& (annotations(ontology.getAnnotationAssertionAxioms(cls
						.getIRI())) != null);
		if (isDerivable) {
			LabelExtractor le = new LabelExtractor();
			Collection<OWLAnnotation> annots = annotations(ontology
					.getAnnotationAssertionAxioms(cls.getIRI()));
			for (OWLAnnotation anno : annots) {
				anno.accept(le);
			}
			if (le.getResult() != null) {
				label = le.getResult();
			}
		}

		return label.replaceAll("\"", "");
	}

	private boolean isPittClass(String iri) {
		return iri.contains("pitt");
	}

	public KbClassGeneratorOptions getOptions() {
		return options;
	}

	public void setOptions(KbClassGeneratorOptions options) {
		this.options = options;
	}

}
