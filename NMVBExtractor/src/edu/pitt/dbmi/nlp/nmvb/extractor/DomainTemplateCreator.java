package edu.pitt.dbmi.nlp.nmvb.extractor;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import edu.pitt.dbmi.nlp.noble.extract.model.Template;
import edu.pitt.dbmi.nlp.noble.extract.model.TemplateCreator;
import edu.pitt.dbmi.nlp.noble.extract.model.TemplateFactory;
import edu.pitt.dbmi.nlp.noble.ontology.IClass;
import edu.pitt.dbmi.nlp.noble.ontology.ILogicExpression;
import edu.pitt.dbmi.nlp.noble.ontology.IOntology;
import edu.pitt.dbmi.nlp.noble.ontology.IOntologyException;
import edu.pitt.dbmi.nlp.noble.ontology.IProperty;
import edu.pitt.dbmi.nlp.noble.ontology.IRestriction;
import edu.pitt.dbmi.nlp.noble.ontology.LogicExpression;
import edu.pitt.dbmi.nlp.noble.ontology.owl.OOntology;
import edu.pitt.dbmi.nlp.noble.terminology.TerminologyException;

public class DomainTemplateCreator {

	/**
	 * create extraction model for a domain in OWL format 
	 * @param inputFile
	 * @param outputFile
	 * @throws TerminologyException 
	 * @throws IOException 
	 * @throws IOntologyException 
	 * @throws FileNotFoundException 
	 */
	public static IOntology createDomainOntology(File inputFile,File outputFile) throws FileNotFoundException, IOntologyException, IOException, TerminologyException{
		// create ontology and save the template
		TemplateCreator termCreator = new TemplateCreator();
		System.out.println("initializing ..");
		IOntology ontology = termCreator.createOntology(inputFile);
		System.out.println("saving ontology "+outputFile.getAbsolutePath()+" ...");
		ontology.write(new FileOutputStream(outputFile),IOntology.OWL_FORMAT);
		return ontology;
	}
	
	/**
	 * create domain template file
	 * @param ontologyFile
	 * @param templateFile
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOntologyException
	 * @throws IOException
	 * @throws TerminologyException
	 * @throws Exception
	 */
	public static Template createDomainTemplate(File ontologyFile, File templateFile) throws FileNotFoundException, IOntologyException, IOException, TerminologyException, Exception{
		System.out.println("Creating template ..");
		TemplateFactory tf = TemplateFactory.getInstance();
		for(Template template : TemplateFactory.importOntologyTemplate(ontologyFile.getAbsolutePath())){
			tf.exportTemplate(template,new FileOutputStream(templateFile));
			return template;
		}
		return null;
	}
	
	

	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		File dataDirectory = new File("/home/tseytlin/Work/NMVB/data");
		File seedTerminology = new File(dataDirectory,"NMVB.txt");
		File outputOntology = new File(dataDirectory,"NMVB.owl");
		File templateFile = new File(dataDirectory,"NMVB.template");
		
		// create domain
		createDomainOntology(seedTerminology, outputOntology);
		
		// augment ontology to add ranges
		//augmentDomainOntology(outputOntology);
		
		// create template
		createDomainTemplate(outputOntology, templateFile);
		
	}

	
}
