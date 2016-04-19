package edu.pitt.dbmi.giant4j.form;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class XmesoFormDefinerRunner {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	protected static void createAndShowGUI() {
		XmesoFormPanel formPanel = new XmesoFormPanel();
		formPanel.setFormPartSet(new XmesoFormPartSet());
		formPanel.buildPanel();
		formPanel.setPreferredSize(new Dimension(1200, 900));
		JFrame frame = new JFrame("Testing Forms");
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        TitledBorder titled = BorderFactory.createTitledBorder(
                loweredbevel, "Expert");
        titled.setTitleJustification(TitledBorder.DEFAULT_JUSTIFICATION);
        titled.setTitlePosition(TitledBorder.ABOVE_TOP);
		formPanel.setBorder(titled);
		frame.getContentPane().add(formPanel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
