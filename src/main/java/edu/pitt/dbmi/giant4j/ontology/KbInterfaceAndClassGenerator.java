package edu.pitt.dbmi.giant4j.ontology;

import static org.semanticweb.owlapi.search.Searcher.annotations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.uima.util.FileUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import com.lexicalscope.jewel.cli.CliFactory;

public class KbInterfaceAndClassGenerator {

	private final CamelCaseConverter camelCaseConverter = new CamelCaseConverter();

	private KbClassGeneratorOptions options;

	private OWLOntologyManager currentOntologyManager;
	private OWLOntology currentOntology;
	private OWLReasonerFactory reasonerFactory;
	private OWLReasoner reasoner;
	private OWLDataFactory owlDataFactory;

	// private final String javaSrcDirectoryRoot =
	// "C:\\ws\\ws-deepphe-8\\DeepPhe\\deepphe-workbench\\modgen";
	private File javaFilesParentDirectoryPath;
//	private final JavaInterfaceGenerator codeGeneratorJava = new JavaInterfaceGenerator();
	private final String topCls = "http://www.w3.org/2002/07/owl#Thing";
	private final HashMap<String, IRI> classIriMap = new HashMap<String, IRI>();
	private final HashMap<String, IRI> objectPropertyIriMap = new HashMap<String, IRI>();
	private final HashMap<String, IRI> nonPittIriMap = new HashMap<String, IRI>();
	private final HashMap<String, List<?>> superClsMap = new HashMap<String, List<?>>();
	private final HashMap<String, List<?>> superObjectPropertyMap = new HashMap<String, List<?>>();
	private final TreeSet<String> sortedClsNames = new TreeSet<String>();
	private final TreeSet<String> sortedObjectPropertyNames = new TreeSet<String>();
	
	private final Queue<OWLClass> clsQueue = new LinkedList<>();
	private final Queue<OWLObjectProperty> objectPropertyQueue = new LinkedList<>();

	public static void main(String[] args) {
		try {
			KbInterfaceAndClassGenerator generator = new KbInterfaceAndClassGenerator();
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
		walkAndCachObjectProperties();
		generateSuperObjectProperties();
		generateJavaInterfacesForObjectProperties();
		
		walkAndCacheClasses();
		generateSuperClasses();
		System.out.println("There are " + nonPittIriMap.size()
				+ " non pitt classes.");
		System.out.println("There are " + classIriMap.size() + " classes.");
		generateJavaInterfacesForClasses();
	}

	private void initializeOntologyAndJavaFootPrint()
			throws OWLOntologyCreationException, IOException {
		initializeOntology();
		initializeJavaFootPrint();
	}

	private void initializeJavaFootPrint() throws FileNotFoundException {
		javaFilesParentDirectoryPath = new File(
				options.getJavaSourceDirectory());
		if (javaFilesParentDirectoryPath.exists()) {
			FileUtils.deleteRecursive(javaFilesParentDirectoryPath);
		}
		javaFilesParentDirectoryPath.mkdir();
		if (!javaFilesParentDirectoryPath.exists()
				|| !javaFilesParentDirectoryPath.isDirectory()) {
			throw new FileNotFoundException(
					javaFilesParentDirectoryPath.getAbsolutePath()
							+ " is not found.");
		}
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

	private void walkAndCacheClasses() {
		OWLClass owlThingCls = getClassForIri(topCls);
		clsQueue.add(owlThingCls);
		while (true) {
			OWLClass cls = clsQueue.poll();
			if (cls == null) {
				break;
			} else if (cls.getIRI().toString().endsWith("Nothing")) {
				continue;
			} else {
				if (!isPittClass(cls.getIRI().toString())) {
					mapNonPittIri(cls.getIRI());
					if (nonPittIriMap.size() % 10 == 0) {
						System.out.println(cls.getIRI().toString());
					}
				}
				mapClassIri(cls.getIRI());
				queueSubClasses(cls);
			}
		}
	}

	private void walkAndCachObjectProperties() {
		objectPropertyQueue.addAll( resolvePropertyExpressions(reasoner
				.getTopObjectPropertyNode()));
		while (true) {
			OWLObjectProperty nextObjProperty = objectPropertyQueue.poll();
			if (nextObjProperty == null) {
				break;
			}
			mapObjectPropertyIri(nextObjProperty.getIRI());
			boolean isDirectSubProperties = true;
			objectPropertyQueue.addAll(resolvePropertyExpressions(reasoner
					.getSubObjectProperties(nextObjProperty,
							isDirectSubProperties).getFlattened()));
		}
	}

	private Set<OWLObjectProperty> resolvePropertyExpressions(
			Node<OWLObjectPropertyExpression> pes) {
		final Set<OWLObjectProperty> result = new HashSet<>();
		for (OWLObjectPropertyExpression pe : pes) {
			Set<OWLObjectProperty> objectProperties = pe
					.getObjectPropertiesInSignature();
			result.addAll(objectProperties);
		}
		return result;
	}
	
	private Set<OWLObjectProperty> resolvePropertyExpressions(
			Set<OWLObjectPropertyExpression> pes) {
		final Set<OWLObjectProperty> result = new HashSet<>();
		for (OWLObjectPropertyExpression pe : pes) {
			Set<OWLObjectProperty> objectProperties = pe
					.getObjectPropertiesInSignature();
			result.addAll(objectProperties);
		}
		return result;
	}

	private void queueSubClasses(OWLClass cls) {
		boolean isDirectSubclasses = true;
		Set<OWLClass> subClses = reasoner
				.getSubClasses(cls, isDirectSubclasses).getFlattened();
		for (OWLClass subCls : subClses) {
			clsQueue.add(subCls);
		}
	}

	private void generateSuperClasses() {
		for (String javaCls : classIriMap.keySet()) {
			OWLClass cls = getClassForIri(classIriMap.get(javaCls).toString());
			superClsMap.put(javaCls, deriveJavaParentClassesFromOwlClass(cls));
		}
	}
	
	private void generateSuperObjectProperties() {
		for (String javaObjectProperty : objectPropertyIriMap.keySet()) {
			OWLObjectProperty cls = getObjectPropertyForIri(objectPropertyIriMap.get(javaObjectProperty).toString());
			superObjectPropertyMap.put(javaObjectProperty, deriveJavaParentObjectPropertiesFromOwlObjectProperty(cls));
		}
	}

	private List<?> deriveJavaParentObjectPropertiesFromOwlObjectProperty(
			OWLObjectProperty objectProperty) {
		Set<OWLObjectProperty> superObjectProperties = resolvePropertyExpressions(reasoner.getSuperObjectProperties(objectProperty, true)
				.getFlattened());
		List<String> superObjectPropertyList = new ArrayList<String>();
		for (OWLObjectProperty superObjectProperty : superObjectProperties) {
			if (superObjectProperty.getIRI().isNothing()) {
				continue;
			} else {
				String javaSuperObjectProperty = getCamelCasedObjectPropertyName(superObjectProperty);
				if (objectPropertyIriMap.keySet().contains(javaSuperObjectProperty)) {
					superObjectPropertyList.add(javaSuperObjectProperty);
				}
			}

		}
		return superObjectPropertyList;
	}

	private OWLObjectProperty getObjectPropertyForIri(String iri) {
		return owlDataFactory.getOWLObjectProperty(IRI.create(iri));
	}

	private List<String> deriveJavaParentClassesFromOwlClass(OWLClass cls) {
		Set<OWLClass> superClses = reasoner.getSuperClasses(cls, true)
				.getFlattened();
		List<String> superClsList = new ArrayList<String>();
		for (OWLClass superCls : superClses) {
			if (superCls.getIRI().isNothing()) {
				continue;
			} else {
				String javaSuperClass = getCamelCasedClassName(superCls);
				if (classIriMap.keySet().contains(javaSuperClass)) {
					superClsList.add(javaSuperClass);
				}
			}

		}
		return superClsList;
	}

//	private void generateJavaInterfacesForObjectProperties() {
	@SuppressWarnings("unchecked")
	private void generateJavaInterfacesForObjectProperties() throws IOException,
			OWLOntologyCreationException {
		gatherSortedObjectProperties();
		JavaInterfaceAndClassGenerator codeGeneratorJava = new JavaInterfaceAndClassGenerator();
		codeGeneratorJava
				.setJavaPackage("org.healthnlp.deepphe.summarization.drools.kb.impl");
		codeGeneratorJava
				.setJavaFilesParentDirectoryPath(javaFilesParentDirectoryPath
						.getAbsolutePath());
		codeGeneratorJava.setJavaFilesDirectoryPrefix("rel");
		for (String javaObjectProperty : sortedObjectPropertyNames) {
			if (javaObjectProperty.endsWith("BottomobjectProperty")) {
				continue;
			}
			List<String> superObjectProperties = (List<String>) superObjectPropertyMap.get(javaObjectProperty);
			if (superObjectProperties == null) {
				superObjectProperties = new ArrayList<String>();
			}
			if (superObjectProperties.isEmpty()) {
				superObjectProperties.add("KbRelationInterface");
			}
			codeGeneratorJava.setJavaCls(javaObjectProperty);
			codeGeneratorJava.setJavaSuperCls("KbRelation");
			codeGeneratorJava.setJavaSuperInterfaces(superObjectProperties);
			codeGeneratorJava.codeGenerate();
		}
	}
	

	@SuppressWarnings("unchecked")
	private void generateJavaInterfacesForClasses()  {
		gatherSortedClses();
		JavaInterfaceAndClassGenerator codeGeneratorJava = new JavaInterfaceAndClassGenerator();
		codeGeneratorJava
				.setJavaPackage("org.healthnlp.deepphe.summarization.drools.kb.impl");
		codeGeneratorJava
				.setJavaFilesParentDirectoryPath(javaFilesParentDirectoryPath
						.getAbsolutePath());
		codeGeneratorJava.setJavaFilesDirectoryPrefix("cls");
		for (String javaCls : sortedClsNames) {
			List<String> superClasses = (List<String>) superClsMap.get(javaCls);
			if (superClasses == null) {
				superClasses = new ArrayList<String>();
			}
			if (superClasses.isEmpty()) {
				superClasses.add("KbSummaryInterface");
			}
			codeGeneratorJava.setJavaCls(javaCls);
			codeGeneratorJava.setJavaSuperCls("KbSummary");
			codeGeneratorJava.setJavaSuperInterfaces(superClasses);
			codeGeneratorJava.codeGenerate();
		}
		
	}


	private void mapClassIri(IRI iri) {
		OWLClass cls = getClassForIri(iri.toString());
		String javaClass = getCamelCasedClassName(cls);
		if (classIriMap.get(javaClass) != null
				&& !classIriMap.get(javaClass).toString().equals(iri.toString())) {
			System.err.println("Duplicate javaClass to IRI mapping \n\t" + iri
					+ "\n\t" + classIriMap.get(javaClass));
		} else {
			classIriMap.put(javaClass, iri);
		}

	}
	
	private void mapObjectPropertyIri(IRI iri) {
		if (!iri.toString().endsWith("SchemaOntology.owl#hasStage")) {
			OWLObjectProperty cls = getObjectPropertyForIri(iri.toString());
			String javaObjectProperty = getCamelCasedObjectPropertyName(cls);
			if (objectPropertyIriMap.get(javaObjectProperty) != null
					&& !objectPropertyIriMap.get(javaObjectProperty).toString().equals(iri.toString())) {
				System.err.println("Duplicate javaObjectProperty to IRI mapping \n\t" + iri
						+ "\n\t" + objectPropertyIriMap.get(javaObjectProperty));
			} else {
				objectPropertyIriMap.put(javaObjectProperty, iri);
			}
		}
	}

	private void mapNonPittIri(IRI iri) {
		OWLClass cls = getClassForIri(iri.toString());
		String javaClass = getCamelCasedClassName(cls);
		nonPittIriMap.put(javaClass, iri);
	}

	private OWLOntology loadDeepPheOntologyFromUri(OWLOntologyManager manager)
			throws IOException, OWLOntologyCreationException {

		IRI iri = IRI.create(options.getOntologyPath());
		OWLOntology o = manager.loadOntologyFromOntologyDocument(iri);
		// OWLOntology o = manager.loadOntology(iri);

		return o;
	}

	@SuppressWarnings("unused")
	private OWLOntology loadDeepPheOntologyFromFile(OWLOntologyManager manager)
			throws IOException, OWLOntologyCreationException {
		File f = new File(options.getOntologyPath());
		OWLOntology o = manager.loadOntologyFromOntologyDocument(f);
		return o;
	}

	private OWLClass getClassForIri(String iri) {
		return owlDataFactory.getOWLClass(IRI.create(iri));
	}

	private void gatherSortedClses() {
		for (String javaClsName : classIriMap.keySet()) {
			sortedClsNames.add(javaClsName);
		}
	}
	
	private void gatherSortedObjectProperties() {
		for (String javaObjectPropertyName : objectPropertyIriMap.keySet()) {
			sortedObjectPropertyNames.add(javaObjectPropertyName);
		}
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
	
	private String getCamelCasedObjectPropertyName(OWLObjectProperty cls) {
//		return camelCaseConverter.toCamelCase(getObjectPropertyName(cls));
		return getObjectPropertyName(cls);
	}

	private String getObjectPropertyName(OWLObjectProperty cls) {
		String result = null;
		IRI clsIri = cls.getIRI();
		String clsIriString = (clsIri != null) ? clsIri.toString() : null;
		result = (clsIriString != null) ? StringUtils.substringAfterLast(
				clsIriString, "#") : null;
//		result = (result != null) ? "Relation_" + result : null;
		result = (result != null) ? StringUtils.capitalise(result) : null;
		return result;
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

