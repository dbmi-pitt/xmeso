package edu.pitt.dbmi.giant4j.ontology;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import edu.pitt.dbmi.xmeso.i2b2.I2B2DataDataWriter;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.I2b2DemoDataSourceManager;

public class OntologyCleaner extends JDialog implements Runnable {

	private static final long serialVersionUID = 1L;
	private PropertyChangeSupport pcs;
	private String message;
	private TreeSet<PartialPath> partialPathTreeSet;
	private HashMap<String, PartialPath> partialPathMap;
	
	private I2b2DemoDataSourceManager i2b2DataDataSourceManager;
	private I2b2OntologyBuilder i2b2OntologyBuilder;
	private I2B2DataDataWriter i2b2DataDataWriter;
	private MetaDataDbManager metaDataDbManager;

	private JTextPane messageText;
	private JScrollPane paneScrollPane;

	public OntologyCleaner(Frame parent) {
		super(parent, "DeepPhe Summarization Ontology Loader", false);
		messageText = new JTextPane();
		paneScrollPane = new JScrollPane(messageText);
		getContentPane().add(paneScrollPane, BorderLayout.CENTER);
		pack();
		setSize(new Dimension(400, 200));
		setResizable(false);
		setLocationRelativeTo(parent);
		pcs = new PropertyChangeSupport(this);
	}

	@Override
	public void run() {
		try {
			setMessage("Begin Ontology Slicing");
			i2b2OntologyBuilder
					.setOntologyPath("..\\ontologies\\BreastCancer.owl");
			i2b2OntologyBuilder.setSourceSystemCode("DEEPPHE2");
			final List<String> topLevelClses = new ArrayList<String>();
			topLevelClses
					.add("http://dbmi.pitt.edu/deepphe/ontologies/breastCancer.owl#Receptor_Status");
			topLevelClses
					.add("http://dbmi.pitt.edu/deepphe/ontologies/breastCancer.owl#Breast_Carcinoma_by_Gene_Expression_Profile");
			topLevelClses
					.add("http://dbmi.pitt.edu/deepphe/ontologies/breastCancer.owl#Generic_TNM_Finding");
//			topLevelClses
//					.add("http://dbmi.pitt.edu/deepphe/ontologies/breastCancer.owl#Generic_Primary_Tumor_TNM_Finding");
//			topLevelClses
//					.add("http://dbmi.pitt.edu/deepphe/ontologies/breastCancer.owl#Generic_Regional_Lymph_Nodes_TNM_Finding");
			topLevelClses
					.add("http://dbmi.pitt.edu/deepphe/ontologies/breastCancer.owl#Tumor_Size");
			i2b2OntologyBuilder.setTopLevelClses(topLevelClses);
			i2b2OntologyBuilder.setPartialPathTreeSet(partialPathTreeSet);
			i2b2OntologyBuilder.setPartialPathMap(partialPathMap);
			setMessage("Slicing Ontology");
			i2b2OntologyBuilder.execute();
			setMessage("Done slicing Ontology");

			setMessage("Populating metadata database");
			metaDataDbManager = new MetaDataDbManager();
			metaDataDbManager.setOntologyTableName("DEEPPHE2_ONTOLOGY");
			metaDataDbManager.setSourceSystemCode("DEEPPHE2");
			metaDataDbManager.setPartialPaths(partialPathTreeSet);
			metaDataDbManager.execute();

			setMessage("Finished");

		} catch (OWLOntologyCreationException | ClassNotFoundException
				| IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String newMessage) {
		String oldMessage = message;
		message = newMessage;
		messageText.setText(messageText.getText() + "\n" + newMessage);

		pcs.firePropertyChange("message", oldMessage, newMessage);
	}

	public TreeSet<PartialPath> getPartialPathTreeSet() {
		return partialPathTreeSet;
	}

	public void setPartialPathTreeSet(TreeSet<PartialPath> partialPathTreeSet) {
		this.partialPathTreeSet = partialPathTreeSet;
	}

	public HashMap<String, PartialPath> getPartialPathMap() {
		return partialPathMap;
	}

	public void setPartialPathMap(HashMap<String, PartialPath> partialPathMap) {
		this.partialPathMap = partialPathMap;
	}

	public I2b2DemoDataSourceManager getI2b2DataDataSourceManager() {
		return i2b2DataDataSourceManager;
	}

	public void setI2b2DataDataSourceManager(I2b2DemoDataSourceManager i2b2DataDataSourceManager) {
		this.i2b2DataDataSourceManager = i2b2DataDataSourceManager;
	}

	public I2b2OntologyBuilder getI2b2OntologyBuilder() {
		return i2b2OntologyBuilder;
	}

	public void setI2b2OntologyBuilder(I2b2OntologyBuilder i2b2OntologyBuilder) {
		this.i2b2OntologyBuilder = i2b2OntologyBuilder;
	}

	public I2B2DataDataWriter getI2b2DataDataWriter() {
		return i2b2DataDataWriter;
	}

	public void setI2b2DataDataWriter(I2B2DataDataWriter i2b2DataDataWriter) {
		this.i2b2DataDataWriter = i2b2DataDataWriter;
	}

	public MetaDataDbManager getMetaDataDbManager() {
		return metaDataDbManager;
	}

	public void setMetaDataDbManager(MetaDataDbManager metaDataDbManager) {
		this.metaDataDbManager = metaDataDbManager;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener("message", listener);
	}

}
