package edu.jhu.cs.cotterell.tonelearner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ToneLearner extends JFrame {

	private static final int CONTOUR_WIDTH = 800;
	private static final int HEIGHT = 300;
	private static final String TITLE = "Tone Learner";

	private Contour contour;
	private ControlPanel controlPanel;

	public ToneLearner() throws LineUnavailableException {
		super(TITLE);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		contour = new Contour();
		contour.setPreferredSize(new Dimension(CONTOUR_WIDTH, HEIGHT));

		controlPanel = new ControlPanel(this);

		mainPanel.add(controlPanel, BorderLayout.WEST);
		mainPanel.add(contour, BorderLayout.CENTER);

		/*
		 * //create menu bar JMenuBar menuBar = new JMenuBar(); JMenu menu = new
		 * JMenu("Help"); menuBar.add(menu); //add items to menu JMenuItem help
		 * = new JMenuItem("Help"); menu.add(help);
		 */

		// add to frame
		// this.setJMenuBar(menuBar);

		this.add(mainPanel);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	public void addContour(Contour contour) {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		contour.setPreferredSize(new Dimension(CONTOUR_WIDTH, HEIGHT));

		mainPanel.add(controlPanel, BorderLayout.WEST);
		mainPanel.add(contour, BorderLayout.EAST);

		this.add(mainPanel);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			new ToneLearner();
		} catch (LineUnavailableException ex) {
			System.out
					.println("Java is having problems comunicating with your sound card. The program cannot start");
		}
	}
}