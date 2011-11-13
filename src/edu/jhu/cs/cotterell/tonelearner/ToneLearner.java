package edu.jhu.cs.cotterell.tonelearner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * The ToneLearner GUI
 * 
 * @author ryan
 * 
 */

@SuppressWarnings("serial")
public class ToneLearner extends JFrame {

	/**
	 * Width of the contour jpanel
	 */

	private static final int CONTOUR_WIDTH = 800;

	/**
	 * Height of the GUI
	 */

	private static final int HEIGHT = 300;

	/**
	 * Title of the JFrame
	 */

	private static final String TITLE = "Tone Learner";

	/**
	 * The Contour JPanel
	 */

	private ContourPanel ContourPanel;

	/**
	 * ControlPanel JPanel
	 */

	private ControlPanel controlPanel;

	/**
	 * Creates a new ToneLearner GUI
	 */

	public ToneLearner() {
		super(TITLE);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		ContourPanel = new ContourPanel();
		ContourPanel.setPreferredSize(new Dimension(CONTOUR_WIDTH, HEIGHT));

		controlPanel = new ControlPanel(this);

		mainPanel.add(controlPanel, BorderLayout.WEST);
		mainPanel.add(ContourPanel, BorderLayout.CENTER);

		// create menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu); // add items to menu JMenuItem help
		JMenuItem load = new JMenuItem("Load");
		load.addActionListener(new MenuListener());
		menu.add(load);

		// add to frame
		this.setJMenuBar(menuBar);
		this.add(mainPanel);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Updates the contour. Generally called when a new sound has been recorded
	 * and analyzed
	 * 
	 * @param contourPanel
	 */

	public void updateContour(ContourPanel contourPanel) {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		contourPanel.setPreferredSize(new Dimension(CONTOUR_WIDTH, HEIGHT));

		mainPanel.add(controlPanel, BorderLayout.WEST);
		mainPanel.add(contourPanel, BorderLayout.EAST);

		this.add(mainPanel);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * The main method
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		new ToneLearner();
	}
	
	class MenuListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			ToneLearner.this.updateContour(new ContourPanel("forvo"));
		}
	}
}