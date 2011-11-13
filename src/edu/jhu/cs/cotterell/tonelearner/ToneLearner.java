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
	 * File menu text
	 */
	
	private static final String FILE = "File";

	/**
	 * Load menu item text
	 */
	
	private static final String LOAD = "Load";
	
	/**
	 * Exit menu item text
	 */
	
	private static final String EXIT = "Exit";
	
	/**
	 * The Contour JPanel
	 */

	private ContourPanel contourPanel;

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
		contourPanel = new ContourPanel();
		contourPanel.setPreferredSize(new Dimension(CONTOUR_WIDTH, HEIGHT));

		controlPanel = new ControlPanel(new NewSoundListener());

		mainPanel.add(controlPanel, BorderLayout.WEST);
		mainPanel.add(contourPanel, BorderLayout.CENTER);

		// create menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu(FILE);
		menuBar.add(menu); // add items to menu JMenuItem help
		JMenuItem load = new JMenuItem(LOAD);
		JMenuItem exit = new JMenuItem(EXIT);
		load.addActionListener(new MenuListener());
		exit.addActionListener(new MenuListener());
		menu.add(load);
		menu.add(exit);

		// add to frame
		this.setJMenuBar(menuBar);
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
	
	/**
	 * Listens for actions regarding the menu
	 * @author ryan
	 *
	 */
	
	class MenuListener implements ActionListener {

		/**
		 * If a action occurs.
		 * In this case if a button is pressed
		 */
		
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(LOAD)) {
				ToneLearner.this.contourPanel.updatePitch("forvo");
				ToneLearner.this.controlPanel.setSoundFile("forvo");
			}
			else if (e.getActionCommand().equals(EXIT)) {
				System.exit(0);
			}
		}
	}
	
	/**
	 * Listens for changes in the contour in
	 * the control panel  and updates the gui accordingly
	 * @author ryan
	 *
	 */
	
	class NewSoundListener {
		
		/**
		 * Tells the contour panel to the
		 * contour of the sound file given
		 * @param file the sound file name
		 */
		
		public void updateContour(String file) {
			ToneLearner.this.contourPanel.updatePitch(file);

		}
	}
}