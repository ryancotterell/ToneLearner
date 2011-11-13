package edu.jhu.cs.cotterell.tonelearner;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Displays the contour of the pitch of the tone
 * 
 * @author ryan
 * 
 */

@SuppressWarnings("serial")
public class ContourPanel extends JPanel {

	/**
	 * The pitch object
	 */

	private Pitch pitch;

	/**
	 * Whether the paint the contour. It does not paint when no sound has been
	 * recorded.
	 */

	private boolean paint;

	/**
	 * Constructor called when no sound has been recorded
	 */

	public ContourPanel() {
		paint = false;
	}

	/**
	 * Constructor called when a sound has been recorded
	 * 
	 * @param fileName
	 *            the name of the sound file
	 */

	public ContourPanel(String fileName) {
		pitch = new Pitch(fileName);
		paint = true;
	}

	/**
	 * Paints the contour
	 */

	public void paintComponent(Graphics g) {
		if (paint) {
			g.setColor(Color.BLACK);

			// an attempt to make the contour
			// look nice. still needs some adjustment
			for (Double k : pitch.getPoints().keySet()) {
				int x = (int) (k * 100);
				x *= 4;
				int y = pitch.getPoints().get(k).intValue();
				y -= 300;
				y = -y;

				g.fillOval(x, y, 5, 5);
			}
		}
	}
}