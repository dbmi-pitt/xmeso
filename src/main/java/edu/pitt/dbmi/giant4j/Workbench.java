package edu.pitt.dbmi.giant4j;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.apache.uima.analysis_engine.AnalysisEngine;

import edu.pitt.dbmi.giant4j.controller.Controller;
import edu.pitt.dbmi.giant4j.form.XmesoFormDataBean;
import edu.pitt.dbmi.giant4j.form.XmesoFormPanel;
import edu.pitt.dbmi.giant4j.form.XmesoFormPartSet;
import edu.pitt.dbmi.giant4j.kb.KbPatient;
import edu.pitt.dbmi.giant4j.ontology.I2b2OntologyBuilder;
import edu.pitt.dbmi.giant4j.ontology.MetaDataDbManager;
import edu.pitt.dbmi.giant4j.ontology.OntologyCleaner;
import edu.pitt.dbmi.giant4j.ontology.PartialPath;
import edu.pitt.dbmi.giant4j.treeview.artifact.AnnotationsTree;
import edu.pitt.dbmi.xmeso.i2b2.I2B2DataDataWriter;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.I2b2DemoDataSourceManager;

public class Workbench extends JFrame implements ActionListener,
		PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	public static String PROJECT_LOCATION = null;

	public static void main(String[] args) {
		if (args.length > 0)
			PROJECT_LOCATION = args[0];
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		new Workbench("Xmeso Quality Assurance Workbench");
	}

	private final I2b2DemoDataSourceManager i2b2DataDataSourceManager = new I2b2DemoDataSourceManager();
	private final I2b2OntologyBuilder i2b2OntologyBuilder = new I2b2OntologyBuilder();
	private final I2B2DataDataWriter i2b2DataDataWriter = new I2B2DataDataWriter();
	private final MetaDataDbManager metaDataDbManager = new MetaDataDbManager();
	private final TreeSet<PartialPath> partialPathTreeSet = new TreeSet<PartialPath>();
	private final HashMap<String, PartialPath> partialPathMap = new HashMap<>();
	private final List<KbPatient> patients = new ArrayList<>();

	private WindowAdapter windowAdapter;

	private JPanel mainPanel;
	// private ImageIcon iconOne = new ImageIcon(
	// Workbench.class.getResource("/images/24f/dashboardico.gif"));
	// private JTabbedPane mainTabbedPane = new JTabbedPane();
	// private AnnotationTabPanel annotationTabPanel;
	private PatientViewerPanel patientViewerPanel;

	private XmesoFormPanel formPanelExpert = new XmesoFormPanel();
	private XmesoFormPartSet formDataExpert = new XmesoFormPartSet();

	private XmesoFormPanel formPanelMachine = new XmesoFormPanel();
	private XmesoFormPartSet formDataMachine = new XmesoFormPartSet();

	private OntologyCleaner ontologyCleaner;
	private Controller controller;
	private AnalysisEngine cTakesAnalysisEngine;
	private AnnotationsTree annotationsTree = new AnnotationsTree();

	private PipeDialogPatientLoader dialogWorkerPatientLoader;
	private PipeDialogEncounterExtraction encounterExtractor;

	private JFileChooser fc;

	public Workbench(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		WorkbenchMenu mainMenu = new WorkbenchMenu();
		mainMenu.setActionListener(this);
		mainMenu.injectActionListener();
		setJMenuBar(mainMenu);

		establishWindowControls();

		controller = new Controller();
		controller.setKbPatients(patients);
//		controller.constructFastDagFromRdbms();
		controller.constructFastPatientEncountersFromRdbms();

		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File(
				"C:\\ws\\ws-xmeso\\xmeso\\data\\reports"));

		// buildCtakesAnalysisEngine();

		establishExtractor();

		// buildAnnotationTabPanel();
		buildFormPanels();
		buildPatientViewerPanel();
	
		buildMainPanel();
		JPanel mainPanel = getMainPanel();

		getContentPane().add(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void establishWindowControls() {
		windowAdapter = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				// You can still stop closing if you want to
				int res = JOptionPane.showConfirmDialog(Workbench.this,
						"Are you sure you want to close?", "Close?",
						JOptionPane.YES_NO_OPTION);
				if (res == 0) {
					// dispose method issues the WINDOW_CLOSED event
					if (Workbench.this.controller != null) {
						Workbench.this.controller.closeUp();
					}
					Workbench.this.dispose();
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				System.exit(0);
			}
		};

		addWindowListener(windowAdapter);
	}

	private void buildFormPanels() {

//		i2b2OntologyBuilder
//				.setOntologyPath("http://localhost:8080/deepphe/ontologies/modelBreastCancer.owl");
//		i2b2OntologyBuilder.setSourceSystemCode("Xmeso");
//		i2b2OntologyBuilder.setPartialPathTreeSet(partialPathTreeSet);
//		i2b2OntologyBuilder.setPartialPathMap(partialPathMap);
//		topLevelClses.add("http://www.w3.org/2002/07/owl#Thing");
//		i2b2OntologyBuilder.setTopLevelClses(topLevelClses);

		formPanelExpert = new XmesoFormPanel();
		formDataExpert = new XmesoFormPartSet();
		Border border = BorderFactory.createTitledBorder("Expert");
		formPanelExpert.setBorder(border);
		formPanelExpert.setFormPartSet(formDataExpert);
		formPanelExpert.buildPanel();
	
		formPanelMachine = new XmesoFormPanel();
		formDataMachine = new XmesoFormPartSet();
		border = BorderFactory.createTitledBorder("Xmeso");
		formPanelMachine.setBorder(border);
		formPanelMachine.setFormPartSet(formDataMachine);
		formPanelMachine.buildPanel();
	}

	private void buildPatientViewerPanel() {
		annotationsTree.setController(controller);
		patientViewerPanel = new PatientViewerPanel();
		patientViewerPanel.setAnnotationsTree(annotationsTree);
		patientViewerPanel.setPatients(patients);
		patientViewerPanel.setController(controller);
		patientViewerPanel.setExpertFormPanel(formPanelExpert);
		patientViewerPanel.setMachineFormPanel(formPanelMachine);
		patientViewerPanel.build();
	}

	private void buildMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		// mainTabbedPane.addTab("Annotation", iconOne, annotationTabPanel,
		// "AnnotateTab");
		// mainTabbedPane.setSelectedIndex(0);
		// mainPanel.setLayout(new GridLayout(1, 1));
		// mainPanel.add(mainTabbedPane);
		mainPanel.setPreferredSize(new Dimension(1200, 900));
		mainPanel.add(patientViewerPanel, BorderLayout.CENTER);
	}

	private void establishExtractor() {
		try {
			EncounterKnowlegeExractorFactory
					.setEncounterKnowledgeExtractor(new EncounterKnowledgeExtractorXmeso());
			EncounterKnowlegeExractorFactory.getEncounterKnowledgeExtractor()
					.setAnalysisEngine(cTakesAnalysisEngine);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println(actionCommand);
		if (actionCommand.equals("Exit")) {
			closeWindow();
		} else if (actionCommand.equals("Load Patients From Directory")) {
			processLoadPatients();
		} else if (actionCommand.equals("Extract Encounters")) {
			processExtractEncounters();
		} else if (actionCommand.equals("Ontology Clean")) {
			processOntologyClean();
		} else if (actionCommand.equals("Patient Clean")) {
			processPatientClean();
		}
	}

	private void processLoadPatients() {
		int returnVal = fc.showOpenDialog(Workbench.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			dialogWorkerPatientLoader = new PipeDialogPatientLoader(this);
			dialogWorkerPatientLoader.setPatientDirectory(file);
			dialogWorkerPatientLoader.setPatients(patients);
			dialogWorkerPatientLoader.setController(controller);
			dialogWorkerPatientLoader.addPropertyChangeListener(this);
			dialogWorkerPatientLoader.setVisible(true);
			(new Thread(dialogWorkerPatientLoader)).start();
		} else {
			System.out.println("Directory selection canceled by the user");
		}

	}

	private void processExtractEncounters() {
		encounterExtractor = new PipeDialogEncounterExtraction(this);
		// encounterExtractor.setAnnotationTabPanel(annotationTabPanel);
		encounterExtractor.setPatients(patients);
		encounterExtractor.setController(controller);
		encounterExtractor.addPropertyChangeListener(this);
		encounterExtractor.setVisible(true);
		(new Thread(encounterExtractor)).start();
	}

	private void processPatientClean() {
		try {
			final I2b2DemoDataSourceManager i2b2DataDataSourceManager = new I2b2DemoDataSourceManager();
			final I2B2DataDataWriter i2b2DataDataWriter = new I2B2DataDataWriter();
			i2b2DataDataWriter.setDataSourceMgr(i2b2DataDataSourceManager);
			i2b2DataDataWriter.setSourceSystemCd("Xmeso");
			i2b2DataDataWriter.execute();
			i2b2DataDataSourceManager.destroy();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void processOntologyClean() {
		partialPathTreeSet.clear();
		partialPathMap.clear();
		ontologyCleaner = new OntologyCleaner(this);
		ontologyCleaner.setI2b2DataDataSourceManager(i2b2DataDataSourceManager);
		ontologyCleaner.setI2b2DataDataWriter(i2b2DataDataWriter);
		ontologyCleaner.setI2b2OntologyBuilder(i2b2OntologyBuilder);
		ontologyCleaner.setMetaDataDbManager(metaDataDbManager);
		ontologyCleaner.setPartialPathTreeSet(partialPathTreeSet);
		ontologyCleaner.setPartialPathMap(partialPathMap);
		ontologyCleaner.addPropertyChangeListener(this);
		ontologyCleaner.setVisible(true);
		(new Thread(ontologyCleaner)).start();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == encounterExtractor
				&& evt.getNewValue().equals("Finished")) {
			encounterExtractor.dispose();
		} else if (evt.getSource() == dialogWorkerPatientLoader
				&& evt.getNewValue().equals("Finished")) {
			dialogWorkerPatientLoader.dispose();
			patients.clear();
//			controller.constructFastDagFromRdbms();
			controller.constructFastPatientEncountersFromRdbms();
			buildFormPanels();
			buildPatientViewerPanel();
			mainPanel.remove(patientViewerPanel);
			buildPatientViewerPanel();
			mainPanel.add(patientViewerPanel, BorderLayout.CENTER);
			// mainTabbedPane.removeTabAt(0);
			// mainTabbedPane.addTab("Annotation", iconOne, annotationTabPanel,
			// "AnnotateTab");
			mainPanel.repaint();
		}
	}

	private void closeWindow() {
		WindowEvent closingEvent = new WindowEvent(this,
				WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue()
				.postEvent(closingEvent);
	}
}