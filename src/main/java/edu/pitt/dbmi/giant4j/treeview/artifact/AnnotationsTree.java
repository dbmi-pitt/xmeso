
package edu.pitt.dbmi.giant4j.treeview.artifact;

import java.awt.Component;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import edu.pitt.dbmi.giant4j.controller.Controller;
import edu.pitt.dbmi.giant4j.kb.KbEncounter;
import edu.pitt.dbmi.giant4j.kb.KbPatient;

/**
 * JTree basic tutorial and example
 * 
 * @author wwww.codejava.net
 */

public class AnnotationsTree {

	private JTree tree;
	private List<KbPatient> patients;
	private TreeSelectionListener treeSelectionListener;
	private Controller controller;

	public AnnotationsTree() {
	}

	public void build() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		DefaultMutableTreeNode cohortNode = new DefaultMutableTreeNode(new AnnotationsCohortUserObject());
		root.add(cohortNode);
		Iterator<KbPatient> patientIterator = patients.iterator();
		while (patientIterator.hasNext()) {
			KbPatient kbPatient = patientIterator.next();
			AnnotationsPatientUserObject patientUserObject = new AnnotationsPatientUserObject();
			patientUserObject.setPatient(kbPatient);
			DefaultMutableTreeNode patientNode = new DefaultMutableTreeNode(
					patientUserObject);
			cohortNode.add(patientNode);
			Iterator<KbEncounter> encounterIterator = kbPatient.getEncounters()
					.iterator();
			while (encounterIterator.hasNext()) {
				KbEncounter kbEncounter = encounterIterator.next();
				AnnotationsEncounterUserObject encounterUserObject = new AnnotationsEncounterUserObject();
				encounterUserObject.setEncounter(kbEncounter);
				DefaultMutableTreeNode encounterNode = new DefaultMutableTreeNode(
						encounterUserObject);
				patientNode.add(encounterNode);
			}
		}
		tree = new JTree(root);

		EncounterTreeCellRenderer renderer = new EncounterTreeCellRenderer();
		tree.setCellRenderer(renderer);
		tree.setShowsRootHandles(true);
		tree.setRootVisible(false);

		tree.getSelectionModel()
				.addTreeSelectionListener(treeSelectionListener);
	}
	
	public JTree getTree() {
		return tree;
	}

	public JScrollPane getScrollableTree() {
		return new JScrollPane(tree);
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

	public TreeSelectionListener getTreeSelectionListener() {
		return treeSelectionListener;
	}

	public void setTreeSelectionListener(
			TreeSelectionListener treeSelectionListener) {
		this.treeSelectionListener = treeSelectionListener;
	}

	class EncounterTreeCellRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 1L;
		private ImageIcon malePatientIcon = new ImageIcon(
				AnnotationsTree.class.getResource("/images/16f/male.gif"));
		private ImageIcon femalePatientIcon = new ImageIcon(
				AnnotationsTree.class
						.getResource("/images/16f/male.gif"));
		private ImageIcon encounterIcon = new ImageIcon(
				AnnotationsTree.class.getResource("/images/16f/encounter.png"));

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean exp, boolean leaf, int row,
				boolean hasFocus) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			if (node.getUserObject() instanceof AnnotationsPatientUserObject) {
				KbPatient patient = ((AnnotationsPatientUserObject) node.getUserObject()).getPatient();
				if (patient.getGender().equals("Male")) {
					setOpenIcon(malePatientIcon);
					setClosedIcon(malePatientIcon);
					setLeafIcon(malePatientIcon);
				} else {
					setOpenIcon(femalePatientIcon);
					setClosedIcon(femalePatientIcon);
					setLeafIcon(femalePatientIcon);
				}
			} else {
				setOpenIcon(encounterIcon);
				setClosedIcon(encounterIcon);
				setLeafIcon(encounterIcon);
			}
			return super.getTreeCellRendererComponent(tree, value, sel, exp, leaf,
					row, hasFocus);
		}
	}
}