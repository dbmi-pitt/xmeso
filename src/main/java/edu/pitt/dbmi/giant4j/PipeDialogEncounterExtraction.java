package edu.pitt.dbmi.giant4j;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import edu.pitt.dbmi.giant4j.controller.Controller;
import edu.pitt.dbmi.giant4j.kb.KbEncounter;
import edu.pitt.dbmi.giant4j.kb.KbPatient;
import edu.pitt.dbmi.giant4j.kb.KbSummary;

public class PipeDialogEncounterExtraction extends JDialog implements Runnable,
		ActionListener {

	private static final long serialVersionUID = 1L;
	private PropertyChangeSupport pcs;
	private String message;
	
	private JTextPane messageText;
	private JScrollPane paneScrollPane;
	private List<KbPatient> patients;
	private Controller controller;

	private AnnotationTabPanel annotationTabPanel;

	private JButton confirmationButton = new JButton("Ok");

	public PipeDialogEncounterExtraction(Frame parent) {
		super(parent, "DeepPhe Encounter Summary Extractor", false);
		messageText = new JTextPane();
		paneScrollPane = new JScrollPane(messageText);
		getContentPane().add(paneScrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
		buttonPanel.add(confirmationButton);
		getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
		confirmationButton.setEnabled(false);
		confirmationButton.setActionCommand("closeDialog");
		confirmationButton.addActionListener(this);

		pack();
		setSize(new Dimension(500, 200));
		setResizable(false);
		setLocationRelativeTo(parent);
		pcs = new PropertyChangeSupport(this);
	}

	@Override
	public void run() {
		setMessage("Begin Processing");
		clearOldSummaryData();
		extractEncounterKnowledge();
		annotationTabPanel.reBuild();
		setMessage("Finished Processing");
		confirmationButton.setEnabled(true);
	}

	private void extractEncounterKnowledge() {
		EncounterKnowledgeExtractorInterface encounterKnowledgeExtractor = EncounterKnowlegeExractorFactory
				.getEncounterKnowledgeExtractor();
		for (KbPatient patient : patients) {
			setMessage("Extracting from patient " + patient);
			for (KbEncounter encounter : patient.getEncounters()) {
				setMessage("\tExtracting from encounter " + encounter);
				encounterKnowledgeExtractor.executeEncounter(encounter);
				setMessage("\tDone extracting from encounter " + encounter);
				KbSummary xmiSummary = findXmiSummary(encounter);
				setMessage("\tSaving encounter xmi " + encounter);
				controller.saveOrUpdateEncounterXmi(patient, encounter, xmiSummary);
				setMessage("\tDone saving encounter xmi " + encounter);
			}
			setMessage("Done extracting from patient " + patient);
		}
		setMessage("Done extracting encounter level knowledge.");
	}
	
	private KbSummary findXmiSummary(KbEncounter encounter) {
		KbSummary xmiSummary = null;
		for (KbSummary summary : encounter.getSummaries()) {
			if (summary.getCode().equals("Dphe:cTakes")) {
				xmiSummary = summary;
				break;
			}
		}
		return xmiSummary;
	}

	private void clearOldSummaryData() {
		for (KbPatient patient : patients) {
			setMessage("Clearing summary information for " + patient);
			patient.clearSummaries();
			for (KbEncounter encounter : patient.getEncounters()) {
				setMessage("Clearing summary information for " + encounter);
				clearDerivedSummaries(encounter);
			}
		}
		setMessage("Cleared any previous inference output.");
	}
	
	private void clearDerivedSummaries(KbEncounter encounter) {
		List<KbSummary> summariesToRemove = new ArrayList<KbSummary>();
		for (KbSummary removalCandidate : encounter.getSummaries()) {
			if (!removalCandidate.getCode().matches("Dphe:Anafora|Dphe:Ctakes")) {
				summariesToRemove.add(removalCandidate);
			}
		}
		encounter.getSummaries().removeAll(summariesToRemove);
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String newMessage) {
		String oldMessage = message;
		message = newMessage;
		appendText(newMessage);

		pcs.firePropertyChange("message", oldMessage, newMessage);
	}

	public void appendText(String text) {
		try {
			StyledDocument doc = messageText.getStyledDocument();
			SimpleAttributeSet plainText = new SimpleAttributeSet();
			StyleConstants.setForeground(plainText, Color.BLACK);
			StyleConstants.setBackground(plainText, Color.WHITE);
			StyleConstants.setBold(plainText, true);
			doc.insertString(doc.getLength(), text, plainText);
			doc.insertString(doc.getLength(), "\n", plainText);
			messageText.setCaretPosition(doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == confirmationButton) {
			confirmationButton.setEnabled(false);
			setMessage("Finished");
		}

	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener("message", listener);
	}

	public List<KbPatient> getPatients() {
		return patients;
	}

	public void setPatients(List<KbPatient> patients) {
		this.patients = patients;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public AnnotationTabPanel getAnnotationTabPanel() {
		return annotationTabPanel;
	}

	public void setAnnotationTabPanel(AnnotationTabPanel annotationTabPanel) {
		this.annotationTabPanel = annotationTabPanel;
	}

}
