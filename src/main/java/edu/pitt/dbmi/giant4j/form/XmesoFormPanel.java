package edu.pitt.dbmi.giant4j.form;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.io.FileUtils;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.pitt.dbmi.giant4j.kb.KbPatient;
import edu.pitt.dbmi.giant4j.ontology.PartialPath;

public class XmesoFormPanel extends JPanel implements ChangeListener {

	private static final long serialVersionUID = -6106800407531520261L;

	private int kbPatient;
	private String provider = "Xmeso:expert";
	private TreeSet<PartialPath> partialPathTreeSet = new TreeSet<PartialPath>();
	private HashMap<String, PartialPath> partialPathMap = new HashMap<String, PartialPath>();
	private List<String> topLevelClses = new ArrayList<String>();

	// private JPanel formPanel = new JPanel();

	private JSpinner partChooser;
	private JComboBox<PartialPath> surgicalProcedureBox;
	private JComboBox<PartialPath> histologicTypeBox;
	private JComboBox<PartialPath> tumorSiteBox;
	private JComboBox<PartialPath> tumorConfigurationBox;
	private JComboBox<PartialPath> tumorDifferentiationBox;
	private JTextField sizeOneField = new JTextField(100);
	private JTextField sizeTwoField = new JTextField(100);
	private JTextField sizeThreeField = new JTextField(100);
	private JTextField sizeMaxField = new JTextField(100);

	private ImageIcon resetIcon = null;
	private ImageIcon clearIcon = null;
	private ImageIcon saveIcon = null;
	private JButton saveButton = new JButton("Save");
	private JButton resetButton = new JButton("Reset");
	private JButton clearButton = new JButton("Clear");

	private XmesoFormPartSet formPartSet;
	private int currentPart = 0;

	private CellConstraints cc = new CellConstraints();
	private int yCoor = 1;

	public XmesoFormPanel() {
		cacheIcons();
	}

	private void cacheIcons() {
		new ImageIcon(getClass().getResource("/images/16f/search16.png"));
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

		try {
			initComponents();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String columnLayout = "right:pref, $lcgap, left:100dlu:grow";
		StringBuilder rowLayoutBuilder = new StringBuilder();

		rowLayoutBuilder.append("p"); // Part Bannar
		rowLayoutBuilder.append(", 30dlu, p"); // Part
		rowLayoutBuilder.append(", 30dlu:grow, p"); // Surgical Procedure Bannar
		rowLayoutBuilder.append(", 30dlu, p"); // Surgical Procedure
		rowLayoutBuilder.append(", 30dlu:grow, p"); // Histopathologic Type
													// Bannar
		rowLayoutBuilder.append(", 30dlu, p"); // Histopathologic Type
		rowLayoutBuilder.append(", 30dlu:grow, p"); // Tumor Site Bannar
		rowLayoutBuilder.append(", 30dlu, p"); // Tumor Site
		rowLayoutBuilder.append(", 30dlu:grow, p"); // Tumor Configuration
													// Bannar
		rowLayoutBuilder.append(", 30dlu, p"); // Tumor Configuration
		rowLayoutBuilder.append(", 30dlu:grow, p"); // Tumor Differentiation
													// Bannar
		rowLayoutBuilder.append(", 30dlu, p"); // Tumor Differentiation
		rowLayoutBuilder.append(", 30dlu:grow, p"); // Tumor Size Bannar
		rowLayoutBuilder.append(", 30dlu, p"); // Size X
		rowLayoutBuilder.append(", 20dlu, p"); // Size Y
		rowLayoutBuilder.append(", 20dlu, p"); // Size Z
		rowLayoutBuilder.append(", 20dlu, p"); // Size Max
		rowLayoutBuilder.append(", 30dlu:grow"); // Action Panel Buttons

		FormLayout dataElementLayout = new FormLayout(columnLayout,
				rowLayoutBuilder.toString());
		dataElementLayout.setColumnGroups(new int[][] { { 1 }, { 2 }, { 3 } });
		dataElementLayout.setRowGroups(new int[][] { { 3, 5, 7, 9, 11, 13 } });
		setLayout(dataElementLayout);

		// setBorder(Borders.DIALOG_BORDER);

		// Fill the table with labels and components.

		buildPartChooser();
		buildSurgicalProcedure();
		buildHistopathologicType();
		buildTumorSite();
		buildConfiguration();
		buildTumorDifferentiation();
		buildSize();
		buildActionPanel();

		fillFormDataFromBean();
	}

	/**
	 * Creates and intializes the UI components.
	 * 
	 * @throws IOException
	 */
	private void initComponents() throws IOException {

		SpinnerModel partsModel = new SpinnerNumberModel(currentPart, // initial
																		// value
				0, // min
				formPartSet.getFormData().length - 1, // max
				1);
		partChooser = new JSpinner(partsModel);
		partChooser.addChangeListener(this);

		surgicalProcedureBox = buildComboBoxFromFile("ruta_surgical_procedure.csv");
		clearAll();

		histologicTypeBox = buildComboBoxFromFile("ruta_histological_type.csv");
		clearAll();

		tumorSiteBox = buildComboBoxFromFile("ruta_site_of_tumor.csv");
		clearAll();

		tumorConfigurationBox = buildComboBoxFromFile("ruta_tumor_configuration.csv");
		clearAll();

		tumorDifferentiationBox = buildComboBoxFromFile("ruta_tumor_differentiation.csv");
		clearAll();

		sizeOneField.setText("-1.0");
		sizeTwoField.setText("-1.0");
		sizeThreeField.setText("-1.0");
		sizeMaxField.setText("-1.0");

		clearButton.setActionCommand("Form:Clear");
		clearButton.setEnabled(true);
		clearButton.setIcon(clearIcon);

		resetButton.setActionCommand("Form:Reset");
		resetButton.setEnabled(true);
		resetButton.setIcon(resetIcon);

		saveButton.setActionCommand("Form:Save");
		saveButton.setEnabled(true);
		saveButton.setIcon(saveIcon);

	}

	private void buildActionPanel() {
		String actionsColumnLayout = "1px:grow, center:pref, 1px:grow, "
				+ "center:pref, 1px:grow, center:pref, 1px:grow";
		FormLayout actionLayout = new FormLayout(actionsColumnLayout, "p");
		actionLayout
				.setColumnGroups(new int[][] { { 1, 3, 5, 7 }, { 2, 4, 6 } });
		JPanel actionPanel = new JPanel(actionLayout);
		setBorder(Borders.TABBED_DIALOG_BORDER);
		actionPanel.add(saveButton, cc.xyw(2, 1, 1));
		actionPanel.add(resetButton, cc.xyw(4, 1, 1));
		actionPanel.add(clearButton, cc.xyw(6, 1, 1));

		yCoor += 1;
		add(createSeparator("Form Actions"), cc.xyw(1, yCoor, 3));
		yCoor += 1;
		add(actionPanel, cc.xyw(1, yCoor, 3));
	}

	private void buildPartChooser() {

		yCoor = 1;
		add(createSeparator("Part Chooser"), cc.xyw(1, yCoor, 3));

		yCoor += 1;
		add(new JLabel("Part"), cc.xyw(1, yCoor, 1));
		add(partChooser, cc.xyw(3, yCoor, 1));
	}

	private void buildSurgicalProcedure() {
		yCoor += 1;
		add(createSeparator("Surgical Procedure"), cc.xyw(1, yCoor, 3));

		yCoor += 1;
		add(new JLabel("Surgical Procedure"), cc.xyw(1, yCoor, 1));
		add(surgicalProcedureBox, cc.xyw(3, yCoor, 1));
	}

	private void buildHistopathologicType() {
		yCoor += 1;
		add(createSeparator("Histopathologic Type"), cc.xyw(1, yCoor, 3));

		yCoor += 1;
		add(new JLabel("Histopathologic Type"), cc.xyw(1, yCoor, 1));
		add(histologicTypeBox, cc.xyw(3, yCoor, 1));

		System.out.println("ycoor = " + yCoor);
	}

	private void buildTumorSite() {
		yCoor += 1;
		add(createSeparator("Tumor Site"), cc.xyw(1, yCoor, 3));

		yCoor += 1;
		add(new JLabel("Tumor Site One"), cc.xyw(1, yCoor, 1));
		add(tumorSiteBox, cc.xyw(3, yCoor, 1));

	}

	private void buildConfiguration() {
		yCoor += 1;
		add(createSeparator("Tumor Configuration"), cc.xyw(1, yCoor, 3));

		yCoor += 1;
		add(new JLabel("Tumor Configuration One"), cc.xyw(1, yCoor, 1));
		add(tumorConfigurationBox, cc.xyw(3, yCoor, 1));
	}

	private void buildTumorDifferentiation() {
		yCoor += 1;
		add(createSeparator("Tumor Differentiation"), cc.xyw(1, yCoor, 3));

		yCoor += 1;
		add(new JLabel("Tumor Differentiation One"), cc.xyw(1, yCoor, 1));
		add(tumorDifferentiationBox, cc.xyw(3, yCoor, 1));

	}

	private void buildSize() {

		yCoor += 1;
		add(createSeparator("Tumor Size"), cc.xyw(1, yCoor, 3));

		yCoor += 1;
		add(new JLabel("SizeX"), cc.xyw(1, yCoor, 1));
		add(sizeOneField, cc.xyw(3, yCoor, 1));

		yCoor += 1;
		add(new JLabel("SizeY"), cc.xyw(1, yCoor, 1));
		add(sizeTwoField, cc.xyw(3, yCoor, 1));

		yCoor += 1;
		add(new JLabel("SizeZ"), cc.xyw(1, yCoor, 1));
		add(sizeThreeField, cc.xyw(3, yCoor, 1));

		yCoor += 1;
		add(new JLabel("SizeMax"), cc.xyw(1, yCoor, 1));
		add(sizeMaxField, cc.xyw(3, yCoor, 1));

	}

	private JComboBox<PartialPath> buildComboBoxFromFile(String fileName)
			throws IOException {
		final String path = "C:\\ws\\ws-xmeso\\xmeso\\resources\\ner";
		File terminologyDirectory = new File(path);
		final TreeSet<String> preferredTerms = new TreeSet<String>();
		if (terminologyDirectory.exists() && terminologyDirectory.isDirectory()) {
			File terminologyFile = new File(terminologyDirectory, fileName);
			for (String line : FileUtils.readLines(terminologyFile, "utf-8")) {
				if (line.length() > 0) {
					final String[] tokens = line.split(";");
					String preferredTerm = tokens[0];
					preferredTerms.add(preferredTerm);
				}
			}
		}
		JComboBox<PartialPath> box = new JComboBox<PartialPath>();
		int pathNumber = 0;
		for (String preferredTerm : preferredTerms) {
			PartialPath partialPath = new PartialPath();
			partialPath.setBaseCode(pathNumber + "");
			partialPath.setPath(preferredTerm);
			box.addItem(partialPath);
			pathNumber++;
		}
		return box;
	}

	public void injectActionCommandListener(ActionListener listener) {
		clearButton.addActionListener(listener);
		resetButton.addActionListener(listener);
		saveButton.addActionListener(listener);
	}

	protected void performReset() {
		formPartSet.resetFormData();
		currentPart = 0;
		fillFormDataFromBean();

	}

	protected void performClear() {
		System.out.println("performClear()");

	}

	protected void performSave() {
		System.out.println("performSave()");
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

	public int getKbPatient() {
		return kbPatient;
	}

	public void setKbPatient(int i) {
		this.kbPatient = i;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public XmesoFormPartSet getFormPartSet() {
		return formPartSet;
	}

	public void setFormPartSet(XmesoFormPartSet formPartSet) {
		this.formPartSet = formPartSet;
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
		XmesoFormDataBean formDataBean = formPartSet.getFormData()[currentPart];
		fillComboBoxWithString(surgicalProcedureBox,
				formDataBean.getSurgicalProcedure());
		fillComboBoxWithString(histologicTypeBox,
				formDataBean.getHistologicType());
		fillComboBoxWithString(tumorSiteBox, formDataBean.getTumorSite());
		fillComboBoxWithString(tumorConfigurationBox,
				formDataBean.getTumorConfiguration());
		fillComboBoxWithString(tumorDifferentiationBox,
				formDataBean.getTumorDifferentiation());
		fillTextFieldWithString(sizeOneField, formDataBean.getSizeX());
		fillTextFieldWithString(sizeTwoField, formDataBean.getSizeY());
		fillTextFieldWithString(sizeThreeField, formDataBean.getSizeZ());
		fillTextFieldWithString(sizeMaxField, formDataBean.getSizeMax());
	}

	public void scrapeScreenToBean() {
		XmesoFormDataBean formDataBean = formPartSet.getFormData()[currentPart];
		formDataBean.setSurgicalProcedure(surgicalProcedureBox
				.getSelectedItem().toString());
		formDataBean.setHistologicType(histologicTypeBox.getSelectedItem()
				.toString());
		formDataBean.setTumorSite(tumorSiteBox.getSelectedItem().toString());
		formDataBean.setTumorConfiguration(tumorConfigurationBox
				.getSelectedItem().toString());
		formDataBean.setTumorDifferentiation(tumorDifferentiationBox
				.getSelectedItem().toString());
		formDataBean.setSizeX(sizeOneField.getText());
		formDataBean.setSizeY(sizeTwoField.getText());
		formDataBean.setSizeZ(sizeThreeField.getText());
		formDataBean.setSizeMax(sizeMaxField.getText());
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

	@Override
	public void stateChanged(ChangeEvent e) {
		SpinnerModel model = partChooser.getModel();
		
		if (model instanceof SpinnerNumberModel) {
			SpinnerNumberModel numberModel = (SpinnerNumberModel) model;
			scrapeScreenToBean();
			currentPart = numberModel.getNumber()
					.intValue();
			fillFormDataFromBean();
		}
	}

}
