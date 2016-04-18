package edu.pitt.dbmi.giant4j.form;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class FormDefinerRunner {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	protected static void createAndShowGUI() {
		FormPanel formPanel = new FormPanel();
		formPanel.buildPanel();
		// panel.setSize(new Dimension(1200, 900));
		JFrame frame = new JFrame("Testing Forms");
		frame.getContentPane().add(formPanel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
