package edu.pitt.dbmi.giant4j.form;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.pitt.dbmi.giant4j.ontology.I2b2OntologyBuilder;
import edu.pitt.dbmi.giant4j.ontology.PartialPath;
import edu.pitt.dbmi.giant4j.kb.KbPatient;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class FormPanel extends JPanel {

	private static final long serialVersionUID = -6106800407531520261L;

	public static final String CONST_NAMESPACE_UTAH_BLULAB = "http://blulab.chpc.utah.edu/ontologies/v2/SchemaOntology.owl#";
	public static final String CONST_NAMESPACE_CANCER = "http://localhost:8080/deepphe/ontologies/modelCancer.owl#";
	public static final String CONST_NAMESPACE_BREAST_CANCER = "http://localhost:8080/deepphe/ontologies/modelBreastCancer.owl#";

	public final String[] entryPoints = {
			CONST_NAMESPACE_BREAST_CANCER + "Tumor_Size",
			CONST_NAMESPACE_CANCER + "TNM_Modifier",
			CONST_NAMESPACE_CANCER + "Generic_Primary_Tumor_TNM_Finding",
			CONST_NAMESPACE_CANCER + "Generic_Regional_Lymph_Nodes_TNM_Finding",
			CONST_NAMESPACE_CANCER + "Generic_Distant_Metastasis_TNM_Finding",
			CONST_NAMESPACE_CANCER + "CancerStage",
			CONST_NAMESPACE_BREAST_CANCER + "Receptor_Status",
			CONST_NAMESPACE_UTAH_BLULAB + "OrdinalInterpretation" };

	private KbPatient kbPatient;
	private String provider = "DpheProvider:expert";
	private TreeSet<PartialPath> partialPathTreeSet;
	private HashMap<String, PartialPath> partialPathMap;
	private List<String> topLevelClses;

	// private final String metastaticSites = "bone|brain|liver|lung";

	private I2b2OntologyBuilder i2b2OntologyBuilder;

	// private List<String> tumorSizeList = null;

	private JTextField primaryDiseaseTextField = new JTextField();
	private JButton primaryDiseaseSearchButton = new JButton();
	private JTextField metastaticSiteOneTextField = new JTextField();
	private JTextField metastaticSiteTwoTextField = new JTextField();
	private JTextField metastaticSiteThreeTextField = new JTextField();
	private JButton metastaticSiteOneSearchButton = new JButton();
	private JButton metastaticSiteTwoSearchButton = new JButton();
	private JButton metastaticSiteThreeSearchButton = new JButton();
	private JComboBox<PartialPath> sizeBox;
	// private JComboBox<PartialPath> tnmModifierBox;
	private JComboBox<PartialPath> tnmTumorBox;
	private JComboBox<PartialPath> tnmNodeBox;
	private JComboBox<PartialPath> tnmMetastasisBox;
	private JComboBox<PartialPath> stageBox;
	private JComboBox<PartialPath> erStatusBox;
	private JComboBox<PartialPath> prStatusBox;
	private JComboBox<PartialPath> her2NeuStatusBox;
	private ImageIcon searchIcon = null;
	private ImageIcon resetIcon = null;
	private ImageIcon clearIcon = null;
	private ImageIcon saveIcon = null;
	private JButton saveButton = new JButton("Save");
	private JButton resetButton = new JButton("Reset");
	private JButton clearButton = new JButton("Clear");

	private FormDataBean formDataBean;

	private CellConstraints cc = new CellConstraints();
	private int yCoor = 1;

	public FormPanel() {
		cacheIcons();
	}

	private void cacheIcons() {
		searchIcon = new ImageIcon(getClass().getResource(
				"/images/16f/search16.png"));
		resetIcon = new ImageIcon(getClass()
				.getResource("/images/16f/undo.png"));
		clearIcon = new ImageIcon(getClass().getResource(
				"/images/16f/clear.gif"));
		saveIcon = new ImageIcon(getClass().getResource("/images/16f/save.gif"));
	}

	/**
	 * Builds the pane.
	 * 
	 * @return the built panel
	 */
	public void buildPanel() {

		initComponents();

		String columnLayout = "right:pref, 3dlu, 150dlu, 3dlu, center:pref";
		StringBuilder rowLayoutBuilder = new StringBuilder();
		rowLayoutBuilder.append("p"); // Primary Disease Border
		rowLayoutBuilder.append(", 3dlu, p"); // Primary Disease Searcher
		rowLayoutBuilder.append(", 9dlu, p"); // Metastatic Site Border
		rowLayoutBuilder.append(", 3dlu, p"); // Metastatic Site Searcher One
		rowLayoutBuilder.append(", 3dlu, p"); // Metastatic Site Searcher Two
		rowLayoutBuilder.append(", 3dlu, p"); // Metastatic Site Searcher Three
		rowLayoutBuilder.append(", 9dlu, p"); // Tumor Size Border
		rowLayoutBuilder.append(", 3dlu, p"); // Tumor Size Combo
		rowLayoutBuilder.append(", 9dlu, p"); // TNM Border
		rowLayoutBuilder.append(", 3dlu, p"); // TNM Prefix
		rowLayoutBuilder.append(", 3dlu, p"); // TNM T
		rowLayoutBuilder.append(", 3dlu, p"); // TNM N
		rowLayoutBuilder.append(", 3dlu, p"); // TNM M
		rowLayoutBuilder.append(", 9dlu, p"); // Cancer Stage Border
		rowLayoutBuilder.append(", 3dlu, p"); // Cancer Stage Combo
		rowLayoutBuilder.append(", 9dlu, p"); // Receptor Status Border
		rowLayoutBuilder.append(", 3dlu, p"); // ER Status Combo
		rowLayoutBuilder.append(", 3dlu, p"); // PR Status Combo
		rowLayoutBuilder.append(", 3dlu, p"); // Her2Neu Status Combo
		rowLayoutBuilder.append(", 9dlu, p"); // Action Panel
		rowLayoutBuilder.append(", 3dlu, p"); // Action Panel Buttons
		rowLayoutBuilder.append(", 9dlu"); // Action Panel Buttons

		setLayout(new FormLayout(columnLayout, rowLayoutBuilder.toString()));
		setBorder(Borders.DIALOG_BORDER);

		// Fill the table with labels and components.

		buildPrimaryDisease();
		buildMetastaticSite();
		buildTumorSize();
		buildTnm();
		buildCancerStage();
		buildReceptorStatus();

		String actionsColumnLayout = "1px:grow, center:pref, 1px:grow, center:pref, 1px:grow, center:pref, 1px:grow";
		FormLayout actionLayout = new FormLayout(actionsColumnLayout, "p");
		actionLayout
				.setColumnGroups(new int[][] { { 1, 3, 5, 7 }, { 2, 4, 6 } });
		JPanel actionPanel = new JPanel(actionLayout);
		setBorder(Borders.TABBED_DIALOG_BORDER);
		actionPanel.add(resetButton, cc.xyw(2, 1, 1));
		actionPanel.add(clearButton, cc.xyw(4, 1, 1));
		actionPanel.add(saveButton, cc.xyw(6, 1, 1));

		yCoor += 2;
		add(createSeparator("Form Actions"), cc.xyw(1, yCoor, 5));
		yCoor += 2;
		add(actionPanel, cc.xyw(1, yCoor, 5));

		fillFormDataFromBean();
	}

	/**
	 * Creates and intializes the UI components.
	 */
	private void initComponents() {
		try {
			primaryDiseaseTextField.setText("NA");
			primaryDiseaseSearchButton.setEnabled(true);
			primaryDiseaseSearchButton.setIcon(searchIcon);
			primaryDiseaseSearchButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					FormPanel.this.performPrimaryDiseaseSearch();
				}
			});

			metastaticSiteOneTextField.setText("NA");
			metastaticSiteOneSearchButton.setEnabled(true);
			metastaticSiteOneSearchButton.setIcon(searchIcon);
			metastaticSiteOneSearchButton
					.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							FormPanel.this.performMetastaticSiteOneSearch();
						}
					});

			metastaticSiteTwoTextField.setText("NA");
			metastaticSiteTwoSearchButton.setEnabled(true);
			metastaticSiteTwoSearchButton.setIcon(searchIcon);
			metastaticSiteTwoSearchButton
					.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							FormPanel.this.performMetastaticSiteTwoSearch();
						}
					});

			metastaticSiteThreeTextField.setText("NA");
			metastaticSiteThreeSearchButton.setEnabled(true);
			metastaticSiteThreeSearchButton.setIcon(searchIcon);
			metastaticSiteThreeSearchButton
					.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							FormPanel.this.performMetastaticSiteThreeSearch();
						}
					});

			clearAll();
			sizeBox = buildComboBoxFromSubclasses(CONST_NAMESPACE_BREAST_CANCER
					+ "TumorSize");
			clearAll();
			// tnmModifierBox =
			// buildComboBoxFromSubclasses(CONST_NAMESPACE_CANCER
			// + "#TNM_Modifier");
			// clearAll();
			tnmTumorBox = buildComboBoxFromSubclasses(CONST_NAMESPACE_BREAST_CANCER
					+ "BreastCancerPrimaryTumorClassification");
			clearAll();
			tnmNodeBox = buildComboBoxFromSubclasses(CONST_NAMESPACE_BREAST_CANCER
					+ "ClinicalRegionalLymphNodeClassification");
			clearAll();
			tnmMetastasisBox = buildComboBoxFromSubclasses(CONST_NAMESPACE_BREAST_CANCER
					+ "BreastCancerDistantMetastasisClassification");
			clearAll();
			stageBox = buildComboBoxFromSubclasses(CONST_NAMESPACE_BREAST_CANCER
					+ "BreastCancerStage");
			clearAll();
			erStatusBox = buildComboBoxFromSubclasses(CONST_NAMESPACE_CANCER
					+ "Interpretation");
			clearAll();
			prStatusBox = buildComboBoxFromSubclasses(CONST_NAMESPACE_CANCER
					+ "Interpretation");
			clearAll();
			her2NeuStatusBox = buildComboBoxFromSubclasses(CONST_NAMESPACE_CANCER
					+ "Interpretation");

			clearButton.setActionCommand("Form:Clear");
			clearButton.setEnabled(true);
			clearButton.setIcon(clearIcon);
			// clearButton.addActionListener(new ActionListener() {
			// @Override
			// public void actionPerformed(ActionEvent e) {
			// FormPanel.this.performClear();
			// }});
			resetButton.setActionCommand("Form:Reset");
			resetButton.setEnabled(true);
			resetButton.setIcon(resetIcon);
			// resetButton.addActionListener(new ActionListener() {
			// @Override
			// public void actionPerformed(ActionEvent e) {
			// FormPanel.this.performReset();
			// }});
			saveButton.setActionCommand("Form:Save");
			saveButton.setEnabled(true);
			saveButton.setIcon(saveIcon);
			// saveButton.addActionListener(new ActionListener() {
			// @Override
			// public void actionPerformed(ActionEvent e) {
			// FormPanel.this.performSave();
			// }});

		} catch (OWLOntologyCreationException | ClassNotFoundException
				| IOException | SQLException e) {
			e.printStackTrace();
		}

	}

	public void injectActionCommandListener(ActionListener listener) {
		clearButton.addActionListener(listener);
		resetButton.addActionListener(listener);
		saveButton.addActionListener(listener);
	}

	protected void performMetastaticSiteOneSearch() {
		System.out.println("performMetastaticSiteSearch()");

	}

	protected void performMetastaticSiteTwoSearch() {
		System.out.println("performMetastaticSiteSearch()");

	}

	protected void performMetastaticSiteThreeSearch() {
		System.out.println("performMetastaticSiteSearch()");

	}

	protected void performPrimaryDiseaseSearch() {
		System.out.println("performPrimaryDiseaseSearch()");

	}

	protected void performReset() {
		formDataBean.resetFormData();
		fillFormDataFromBean();

	}

	protected void performClear() {
		System.out.println("performClear()");

	}

	protected void performSave() {
		System.out.println("performSave()");
	}

	private void buildPrimaryDisease() {
		yCoor = 1;
		add(createSeparator("Primary Disease"), cc.xyw(1, yCoor, 5));

		yCoor += 2;
		add(new JLabel("Primary Disease"), cc.xyw(1, yCoor, 1));
		add(primaryDiseaseTextField, cc.xyw(3, yCoor, 1));
		add(primaryDiseaseSearchButton, cc.xyw(5, yCoor, 1));
	}

	private void buildMetastaticSite() {
		yCoor += 2;
		add(createSeparator("Metastatic Sites"), cc.xyw(1, yCoor, 5));

		yCoor += 2;
		add(new JLabel("Site One"), cc.xyw(1, yCoor, 1));
		add(metastaticSiteOneTextField, cc.xyw(3, yCoor, 1));
		add(metastaticSiteOneSearchButton, cc.xyw(5, yCoor, 1));

		yCoor += 2;
		add(new JLabel("Site Two"), cc.xyw(1, yCoor, 1));
		add(metastaticSiteTwoTextField, cc.xyw(3, yCoor, 1));
		add(metastaticSiteTwoSearchButton, cc.xyw(5, yCoor, 1));

		yCoor += 2;
		add(new JLabel("Site Three"), cc.xyw(1, yCoor, 1));
		add(metastaticSiteThreeTextField, cc.xyw(3, yCoor, 1));
		add(metastaticSiteThreeSearchButton, cc.xyw(5, yCoor, 1));

	}

	private void buildTumorSize() {
		yCoor += 2;
		add(createSeparator("Tumor Size"), cc.xyw(1, yCoor, 5));

		yCoor += 2;
		add(new JLabel("Tumor Size"), cc.xyw(1, yCoor, 1));
		add(sizeBox, cc.xyw(3, yCoor, 1));
	}

	private void buildTnm() {
		yCoor += 2;
		add(createSeparator("TNM"), cc.xyw(1, yCoor, 5));

		// yCoor += 2;
		// add(new JLabel("Modifier"), cc.xyw(1, yCoor, 1));
		// add(tnmModifierBox, cc.xyw(3, yCoor, 1));
		yCoor += 2;
		add(new JLabel("Primary Tumor"), cc.xyw(1, yCoor, 1));
		add(tnmTumorBox, cc.xyw(3, yCoor, 1));
		yCoor += 2;
		add(new JLabel("Regional Lymph Node"), cc.xyw(1, yCoor, 1));
		add(tnmNodeBox, cc.xyw(3, yCoor, 1));
		yCoor += 2;
		add(new JLabel("Distant Metastasis"), cc.xyw(1, yCoor, 1));
		add(tnmMetastasisBox, cc.xyw(3, yCoor, 1));
	}

	private void buildCancerStage() {
		yCoor += 2;
		add(createSeparator("Cancer Stage"), cc.xyw(1, yCoor, 5));

		yCoor += 2;
		add(new JLabel("Cancer Stage"), cc.xyw(1, yCoor, 1));
		add(stageBox, cc.xyw(3, yCoor, 1));

	}

	public FormDataBean getFormDataBean() {
		return formDataBean;
	}

	public void setFormDataBean(FormDataBean formDataBean) {
		this.formDataBean = formDataBean;
	}

	public I2b2OntologyBuilder getI2b2OntologyBuilder() {
		return i2b2OntologyBuilder;
	}

	public void setI2b2OntologyBuilder(I2b2OntologyBuilder i2b2OntologyBuilder) {
		this.i2b2OntologyBuilder = i2b2OntologyBuilder;
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

	public List<String> getTopLevelClses() {
		return topLevelClses;
	}

	public void setTopLevelClses(List<String> topLevelClses) {
		this.topLevelClses = topLevelClses;
	}

	public KbPatient getKbPatient() {
		return kbPatient;
	}

	public void setKbPatient(KbPatient kbPatient) {
		this.kbPatient = kbPatient;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	private void buildReceptorStatus() {
		yCoor += 2;
		add(createSeparator("Receptor Status"), cc.xyw(1, yCoor, 5));

		yCoor += 2;
		add(new JLabel("ER"), cc.xyw(1, yCoor, 1));
		add(erStatusBox, cc.xyw(3, yCoor, 1));

		yCoor += 2;
		add(new JLabel("PR"), cc.xyw(1, yCoor, 1));
		add(prStatusBox, cc.xyw(3, yCoor, 1));

		yCoor += 2;
		add(new JLabel("HER2NEU"), cc.xyw(1, yCoor, 1));
		add(her2NeuStatusBox, cc.xyw(3, yCoor, 1));
	}

	private void clearAll() {
		partialPathTreeSet.clear();
		partialPathMap.clear();
		topLevelClses.clear();
	}

	@SuppressWarnings("unused")
	private int findMaxLevel(TreeSet<PartialPath> partialPathTreeSet) {
		int maxLevel = Integer.MIN_VALUE;
		for (PartialPath partialPath : partialPathTreeSet) {
			if (partialPath.getLevel() > maxLevel) {
				maxLevel = partialPath.getLevel();
			}
		}
		return maxLevel;
	}

	private JComboBox<PartialPath> buildComboBoxFromSubclasses(
			String clsEntryPoint) throws OWLOntologyCreationException,
			ClassNotFoundException, IOException, SQLException {
		List<String> topLevelClasses = new ArrayList<String>();
		topLevelClasses.add(clsEntryPoint);
		i2b2OntologyBuilder.setTopLevelClses(topLevelClasses);
		i2b2OntologyBuilder
				.setOntologyPath("http://130.49.179.85:8080/deepphe/ontologies/modelBreastCancer.owl");
		;
		i2b2OntologyBuilder.execute();
		JComboBox<PartialPath> comboBox = new JComboBox<PartialPath>();
		for (PartialPath partialPath : i2b2OntologyBuilder
				.getPartialPathTreeSet()) {
			if (partialPath.getLevel() > 0) {
				comboBox.addItem(partialPath);
			}
		}
		return comboBox;
	}

	public JPanel wrapInFlowLayout(JComponent component, int flowOrientation) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(flowOrientation));
		add(component);
		return panel;
	}

	/**
	 * Creates and answer a separator with a label in the left hand side.
	 * 
	 * @param text
	 *            the label's text
	 * @return a separator with label in the left hand side
	 */
	private Component createSeparator(String text) {
		return DefaultComponentFactory.getInstance().createSeparator(text);
	}

	public void fillFormDataFromBean() {
		if (formDataBean != null) {
			fillTextFieldWithString(primaryDiseaseTextField,
					formDataBean.getPrimaryDisease());
			fillTextFieldWithString(metastaticSiteOneTextField,
					formDataBean.getMetastaticSiteOne());
			fillTextFieldWithString(metastaticSiteTwoTextField,
					formDataBean.getMetastaticSiteTwo());
			fillTextFieldWithString(metastaticSiteThreeTextField,
					formDataBean.getMetastaticSiteThree());
			fillComboBoxWithString(sizeBox, formDataBean.getSize());
			// fillComboBoxWithString(tnmModifierBox,
			// formDataBean.getTnmModifier());
			fillComboBoxWithString(tnmTumorBox, formDataBean.getTnmTumor());
			fillComboBoxWithString(tnmNodeBox, formDataBean.getTnmNode());
			fillComboBoxWithString(tnmMetastasisBox,
					formDataBean.getTnmMetastasis());
			fillComboBoxWithString(stageBox, formDataBean.getStage());
			fillComboBoxWithString(erStatusBox, formDataBean.getErStatus());
			fillComboBoxWithString(prStatusBox, formDataBean.getPrStatus());
			fillComboBoxWithString(her2NeuStatusBox,
					formDataBean.getHer2NeuStatus());
		}
	}

	public void scrapeScreenToBean() {
		formDataBean.setPrimaryDisease(primaryDiseaseTextField.getText());
		formDataBean.setMetastaticSiteOne(metastaticSiteOneTextField.getText());
		formDataBean.setMetastaticSiteTwo(metastaticSiteTwoTextField.getText());
		formDataBean.setMetastaticSiteThree(metastaticSiteThreeTextField
				.getText());
		formDataBean.setSize(sizeBox.getSelectedItem().toString());
		// formDataBean.setTnmModifier(tnmModifierBox.getSelectedItem().toString());
		formDataBean.setTnmTumor(tnmTumorBox.getSelectedItem().toString());
		formDataBean.setTnmNode(tnmNodeBox.getSelectedItem().toString());
		formDataBean.setTnmMetastasis(tnmMetastasisBox.getSelectedItem()
				.toString());
		formDataBean.setStage(stageBox.getSelectedItem().toString());
		formDataBean.setErStatus(erStatusBox.getSelectedItem().toString());
		formDataBean.setPrStatus(prStatusBox.getSelectedItem().toString());
		formDataBean.setHer2NeuStatus(her2NeuStatusBox.getSelectedItem()
				.toString());
	}

	private void fillComboBoxWithString(JComboBox<PartialPath> comboBox,
			String selectedValue) {
		int newSelectedIndex = 0;
		for (int itemIdx = 0; itemIdx < comboBox.getItemCount(); itemIdx++) {
			PartialPath pathAtIndex = comboBox.getItemAt(itemIdx);
			if (pathAtIndex.toString().equals(selectedValue)) {
				newSelectedIndex = itemIdx;
				break;
			}
		}
		if (comboBox.getItemCount() > 0) {
			comboBox.setSelectedIndex(newSelectedIndex);
		}
	}

	private void fillTextFieldWithString(JTextField textField,
			String selectedValue) {
		textField.setText(selectedValue);
	}

}
