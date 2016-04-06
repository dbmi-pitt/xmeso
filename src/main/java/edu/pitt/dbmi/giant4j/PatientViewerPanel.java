package edu.pitt.dbmi.giant4j;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang3.StringUtils;

import edu.pitt.dbmi.giant4j.controller.Controller;
import edu.pitt.dbmi.giant4j.form.XmesoFormDataBean;
import edu.pitt.dbmi.giant4j.form.XmesoFormPanel;
import edu.pitt.dbmi.giant4j.kb.KbEncounter;
import edu.pitt.dbmi.giant4j.kb.KbPatient;
import edu.pitt.dbmi.giant4j.treeview.artifact.AnnotationsEncounterUserObject;
import edu.pitt.dbmi.giant4j.treeview.artifact.AnnotationsPatientUserObject;
import edu.pitt.dbmi.giant4j.treeview.artifact.AnnotationsSummaryUserObject;
import edu.pitt.dbmi.giant4j.treeview.artifact.AnnotationsTree;

public class PatientViewerPanel extends JSplitPane implements
		TreeSelectionListener, ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel caseChooserPanel;
	private JPanel caseViewerPanel;
	private JPanel formsPanel;

	// private JPanel summarizableFormPanel;

	private JTextPane reportTextPane = new JTextPane();

	private List<KbPatient> patients;
	private Controller controller;
	private AnnotationsTree annotationsTree;

	private KbEncounter currentEncounter;
	private JPanel formBorderedExpertPanel = new JPanel(new BorderLayout());
	private JPanel formBorderedMachinePanel = new JPanel(new BorderLayout());
	private XmesoFormPanel formExpertPanel;
	private XmesoFormPanel formMachinePanel;
	private AnnotationsSummaryUserObject currentExpertSummaryNode;
	private AnnotationsSummaryUserObject currentMachineSummaryNode;
	private XmesoFormDataBean currentFormBeanExpert;
	private XmesoFormDataBean currentFormBeanMachine;

	public PatientViewerPanel() {
	}

	public void build() {
		setLayout(new BorderLayout());
		caseChooserPanel = buildCaseChooser();
		caseViewerPanel = buildCaseViewer();
		formsPanel = buildFormsPanel();

		add(caseChooserPanel, BorderLayout.WEST);
		add(caseViewerPanel, BorderLayout.CENTER);
		add(formsPanel, BorderLayout.EAST);
	}

	private JPanel buildFormsPanel() {
		
		formsPanel = new JPanel(new GridLayout(2, 1));
		
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        TitledBorder titled = BorderFactory.createTitledBorder(
                loweredbevel, "Expert");
        titled.setTitleJustification(TitledBorder.DEFAULT_JUSTIFICATION);
        titled.setTitlePosition(TitledBorder.ABOVE_TOP);
		formBorderedExpertPanel.setBorder(titled);
		
		loweredbevel = BorderFactory.createLoweredBevelBorder();
        titled = BorderFactory.createTitledBorder(
                loweredbevel, "Machine");
        titled.setTitleJustification(TitledBorder.DEFAULT_JUSTIFICATION);
        titled.setTitlePosition(TitledBorder.ABOVE_TOP);
		formBorderedMachinePanel.setBorder(titled);
		
		formsPanel.setPreferredSize(new Dimension(400, 400));

		formBorderedExpertPanel.add(new JScrollPane(formExpertPanel), BorderLayout.CENTER);
		formBorderedMachinePanel.add(new JScrollPane(formMachinePanel), BorderLayout.CENTER);
		
		formsPanel.add(formBorderedExpertPanel);
		formsPanel.add(formBorderedMachinePanel);
		
		return formsPanel;
	}

	private JPanel buildCaseViewer() {
		caseViewerPanel = new JPanel();
		Border border = BorderFactory.createTitledBorder("Report");
		caseViewerPanel.setBorder(border);
		caseViewerPanel.setLayout(new GridLayout(1, 1));
		caseViewerPanel.setPreferredSize(new Dimension(400, 400));
		caseViewerPanel.add(new JScrollPane(reportTextPane));
		return caseViewerPanel;
	}

	public void reBuild() {
		caseChooserPanel.remove(0);
		annotationsTree = new AnnotationsTree();
		annotationsTree.setPatients(patients);
		annotationsTree.setController(controller);
		annotationsTree.setTreeSelectionListener(this);
		annotationsTree.build();
		caseChooserPanel.add(annotationsTree.getScrollableTree());
		reportTextPane.setText("");

	}

	private JPanel buildCaseChooser() {
		caseChooserPanel = new JPanel(new GridLayout(1, 1));
		Border border = BorderFactory.createTitledBorder("Cases");
		caseChooserPanel.setBorder(border);
		caseChooserPanel.setPreferredSize(new Dimension(200, 400));
		annotationsTree.setPatients(patients);
		annotationsTree.setTreeSelectionListener(this);
		annotationsTree.build();
		caseChooserPanel.add(annotationsTree.getScrollableTree());

		return caseChooserPanel;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) annotationsTree
				.getTree().getLastSelectedPathComponent();
		if (selectedNode == null) {
			;
		} else if (selectedNode.getUserObject() == null) {
			;
		} else if (selectedNode.getUserObject() instanceof AnnotationsPatientUserObject) {
			processPatientSelection(selectedNode);

		}
		SwingUtilities.getWindowAncestor(this).repaint(10);
	}

	private void processPatientSelection(DefaultMutableTreeNode selectedNode) {
		DefaultMutableTreeNode encounterNode = (DefaultMutableTreeNode) selectedNode
		.getChildAt(0).getChildAt(0);
		currentEncounter = ((AnnotationsEncounterUserObject) encounterNode.getUserObject()).getEncounter();
		processKbEncounter(currentEncounter);
		DefaultMutableTreeNode summaryNode = (DefaultMutableTreeNode) selectedNode
				.getChildAt(1);
		currentExpertSummaryNode = (AnnotationsSummaryUserObject) summaryNode.getUserObject();
		summaryNode = (DefaultMutableTreeNode) selectedNode
				.getChildAt(2);
		currentMachineSummaryNode = (AnnotationsSummaryUserObject) summaryNode.getUserObject();
		cacheObservationInstance(currentExpertSummaryNode);
		cacheObservationInstance(currentMachineSummaryNode);
		currentFormBeanExpert = controller
				.findFormDataByInstanceNum(currentExpertSummaryNode
						.getObservationInstanceNumber());
		currentFormBeanMachine = controller
				.findFormDataByInstanceNum(currentMachineSummaryNode
						.getObservationInstanceNumber());
		populateForm(currentExpertSummaryNode, formExpertPanel,
				currentFormBeanExpert);
		populateForm(currentMachineSummaryNode, formMachinePanel,
				currentFormBeanMachine);
	}

	private void populateForm(AnnotationsSummaryUserObject summaryUserObject,
			XmesoFormPanel formPanel, XmesoFormDataBean formData) {
		if (formData != null) {
			formPanel.setKbPatient(summaryUserObject.getPatient());
			formPanel.setProvider(summaryUserObject.getProvider());
			formPanel.setFormDataBean(formData);
			formPanel.fillFormDataFromBean();
		}
	}

	private void cacheObservationInstance(
			AnnotationsSummaryUserObject summaryUserObj) {
		long observationInstanceNum = summaryUserObj
				.getObservationInstanceNumber();
		if (observationInstanceNum < 0) {
			KbPatient patient = summaryUserObj.getPatient();
			String provider = summaryUserObj.getProvider();
			observationInstanceNum = controller
					.findObservationInstanceNumberForForm(patient, provider);
			summaryUserObj.setObservationInstanceNumber(observationInstanceNum);
		}
	}

	private void processKbEncounter(KbEncounter encounter) {
		reportTextPane.setText("");
		appendText(encounter.getContent());
		reportTextPane.setCaretPosition(0);
	}

	public void appendText(String text) {
		try {
			StyledDocument doc = reportTextPane.getStyledDocument();
			SimpleAttributeSet plainText = new SimpleAttributeSet();
			StyleConstants.setForeground(plainText, Color.BLACK);
			StyleConstants.setBackground(plainText, Color.WHITE);
			StyleConstants.setBold(plainText, false);
			doc.insertString(doc.getLength(), text, plainText);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void annotateText(int sPos, String underLyingText,
			Color annotationColor) {
		try {
			StyledDocument doc = reportTextPane.getStyledDocument();
			SimpleAttributeSet annotationAttributes = new SimpleAttributeSet();
			StyleConstants.setForeground(annotationAttributes, Color.BLACK);
			StyleConstants.setBackground(annotationAttributes, annotationColor);
			StyleConstants.setBold(annotationAttributes, true);
			doc.remove(sPos, underLyingText.length());
			doc.insertString(sPos, underLyingText, annotationAttributes);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton srcButton = (JButton) e.getSource();	
			if (srcButton.getParent().getParent() == formExpertPanel) {
				System.out.println("Got action from Expert panel");
				processFormPanelAction(e, currentExpertSummaryNode,
						formExpertPanel, currentFormBeanExpert);
			} else if (srcButton.getParent() == formMachinePanel) {
				System.out.println("Got action from Machine panel");
				processFormPanelAction(e, currentMachineSummaryNode,
						formMachinePanel, currentFormBeanMachine);
			}
		}
	}

	private void processFormPanelAction(ActionEvent e,
			AnnotationsSummaryUserObject summaryUserObject, XmesoFormPanel formPanel, XmesoFormDataBean formData) {
		if (e.getActionCommand().startsWith("Form:")) {
			String commandSuffix = StringUtils.substringAfterLast(
					e.getActionCommand(), ":");
			if (commandSuffix.equals("Clear")) {
				processClearForm(formPanel);
			} else if (commandSuffix.equals("Reset")) {
				processResetForm(formPanel, formData);
			} else if (commandSuffix.equals("Save")) {
				processSaveForm(summaryUserObject, formPanel);
			}
		}
	}

	private void processClearForm(XmesoFormPanel formPanel) {
		XmesoFormDataBean formData = new XmesoFormDataBean();
		formPanel.setFormDataBean(formData);
		formPanel.fillFormDataFromBean();
		SwingUtilities.getWindowAncestor(this).repaint(10);
	}
	
	private void processResetForm(
			XmesoFormPanel formPanel, XmesoFormDataBean formData) {
		formPanel.setFormDataBean(formData);
		formPanel.fillFormDataFromBean();
		SwingUtilities.getWindowAncestor(this).repaint(10);
	}

	private void processSaveForm(
			AnnotationsSummaryUserObject summaryUserObject, XmesoFormPanel formPanel) {
		long observationInstanceNumber = summaryUserObject
				.getObservationInstanceNumber();
		formPanel.scrapeScreenToBean();
		controller.saveObservationFormData(observationInstanceNumber,
				formPanel.getFormDataBean());
	}


	public AnnotationsTree getAnnotationsTree() {
		return annotationsTree;
	}

	public void setAnnotationsTree(AnnotationsTree annotationsTree) {
		this.annotationsTree = annotationsTree;
	}

	public List<KbPatient> getPatients() {
		return patients;
	}

	public void setPatients(List<KbPatient> patients) {
		this.patients = patients;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setExpertFormPanel(XmesoFormPanel formPanelExpert) {
		this.formExpertPanel = formPanelExpert;
		this.formExpertPanel.injectActionCommandListener(this);
		
	}

	public void setMachineFormPanel(XmesoFormPanel formPanelMachine) {
		this.formMachinePanel = formPanelMachine;
		
	}

}
