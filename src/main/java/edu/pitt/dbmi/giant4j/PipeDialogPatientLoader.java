package edu.pitt.dbmi.giant4j;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
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

import org.sonar.runner.commonsio.FileUtils;

import edu.pitt.dbmi.giant4j.controller.Controller;
import edu.pitt.dbmi.giant4j.kb.KbEncounter;
import edu.pitt.dbmi.giant4j.kb.KbPatient;

public class PipeDialogPatientLoader extends JDialog implements Runnable,
		ActionListener {

	private static final long serialVersionUID = 1L;
	private PropertyChangeSupport pcs;
	private String message;

	private JTextPane messageText;
	private JScrollPane paneScrollPane;
	
	private File patientDirectory;
	private Controller controller;
	private List<KbPatient> patients;

	private JButton confirmationButton = new JButton("Ok");
	
	

	public PipeDialogPatientLoader(Frame parent) {
		super(parent, "Xmeso Patient Loader", false);
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
		setSize(new Dimension(400, 200));
		setResizable(false);
		setLocationRelativeTo(parent);
		pcs = new PropertyChangeSupport(this);
	}

	@Override
	public void run() {
		setMessage("Loading Patient Data into Oracle");
		clearOldSummaryData();
		loadPatients();
		setMessage("Finished Processing");
		confirmationButton.setEnabled(true);
	}


	private void loadPatients() {
		final File[] patientFiles = patientDirectory.listFiles();
		int numberOfPatients = 0;
		for (File patientFile : patientFiles) {
			String patientFileName = patientFile.getName();
			if (patientFileName.matches("report\\d+\\.txt")) {
				
				String patientFileContent = tryReadFileToString(patientFile);
				setMessage("Read file " + patientFileName);
				KbPatient kbPatient = new KbPatient();
				kbPatient.setId(numberOfPatients + Controller.patientOffset);
				kbPatient.setIsActive(1);
				kbPatient.setSequence(numberOfPatients);		
				KbEncounter kbEncounter = new KbEncounter();
				kbEncounter.setId(numberOfPatients + Controller.visitOffset);
				kbEncounter.setPatientId(kbPatient.getId());
				kbEncounter.setIsActive(1);
				kbEncounter.setSequence(numberOfPatients);
				kbEncounter.setUri(patientFile.getAbsolutePath());
				kbEncounter.setContent(patientFileContent);
				kbPatient.addEncounter(kbEncounter);
				
				controller.uploadFsDirectoryRawTexts(kbPatient);
				setMessage("Uploaded file " + patientFileName);		
				
				numberOfPatients++;
			}
			
		}
		
	}
	
	private String tryReadFileToString(File f) {
		String content = null;
		try {
			content = FileUtils.readFileToString(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	private void clearOldSummaryData() {
		for (KbPatient patient : patients) {
			patient.clearSummaries();
			for (KbEncounter encounter : patient.getEncounters()) {
				encounter.clearSummaries();
			}
		}
		setMessage("Cleared any previous inference output.");
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

	public void setPatientDirectory(File patientDirectory) {
		this.patientDirectory = patientDirectory;
		
	}

}
