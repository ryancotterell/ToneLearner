package edu.jhu.cs.cotterell.tonelearner;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.awt.Color;

/**
 * Contains various control buttons
 * 
 * @author ryan
 * 
 */

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {

	/**
	 * The width of the buttons
	 */

	private static final int BUTTON_WIDTH = 100;

	/**
	 * The height of the button
	 */

	private static final int BUTTON_HEIGHT = 50;

	/**
	 * The height of the panel
	 */

	private static final int HEIGHT = 400;

	/**
	 * Text of the record button when not recording
	 */

	private static final String START_RECORDING = "Start Recording";

	/**
	 * Text of the record button when recording
	 */

	private static final String STOP_RECORDING = "Stop Recording";

	/**
	 * Text of the play button
	 */

	private static final String PLAY = "Play";

	/**
	 * Delay between finishing recording the sound and analyzing it with praat
	 */

	private static final int DELAY = 2000;

	/**
	 * Default name of the sound file
	 */

	private static final String SOUND_FILE = "tone-learner-sound-file";

	/**
	 * Default font of the button text
	 */

	private static final Font font = new Font("Arial", Font.PLAIN, 8);

	/**
	 * Record Button
	 */

	private JButton record;

	/**
	 * Play button
	 */

	private JButton play;

	/**
	 * Audio recorder
	 */

	private WavAudioRecorder audioRecorder;

	/**
	 * The main gui, needed to update the contour
	 */

	private ToneLearner gui;

	/**
	 * Creates a new control panel
	 * 
	 * @param gui
	 *            the gui it needs to modify
	 */

	public ControlPanel(ToneLearner gui) {
		this.gui = gui;
		this.setBackground(Color.BLUE);

		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(BUTTON_WIDTH, HEIGHT));
		this.record = new JButton(START_RECORDING);
		this.record.setFont(font);

		this.record
				.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		this.record.addActionListener(new RecordListener());
		this.play = new JButton(PLAY);
		this.play.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		this.play.setFont(font);
		this.play.addActionListener(new PlayListener());
		this.add(record);
		this.add(play);
	}

	/**
	 * ActionListener for the play button. Plays the sound
	 * 
	 * @author ryan
	 * 
	 */

	class PlayListener implements ActionListener {

		/**
		 * Starts playing the sound if it has been recorded
		 * 
		 * still need to determine whether the sound has been recorded and throw
		 * and handle the error
		 * 
		 * @param e
		 *            the action event
		 */

		public void actionPerformed(ActionEvent e) {
			String location = System.getProperty("user.dir") + "/" + SOUND_FILE;
			new WavAudioPlayer(location).start();
		}
	}

	/**
	 * ActionListener for the record button Records the sound (start and end)
	 * 
	 * @author ryan
	 * 
	 */

	class RecordListener implements ActionListener {

		/**
		 * Starts recording if the audio recorder has not been initialized and
		 * ends recording otherwise. There is a two second delay between ending
		 * the recording and calling the praat script the analyze the sound
		 * possibly should place an upper bound on recording time
		 * 
		 * @param e
		 *            the action event
		 */

		public void actionPerformed(ActionEvent e) {
			// if the audio recorder has been initialized
			if (audioRecorder == null) {
				ControlPanel.this.play.setEnabled(false);

				try {
					audioRecorder = new WavAudioRecorder();
					ControlPanel.this.record.setText(STOP_RECORDING);
					audioRecorder.start();

				} catch (LineUnavailableException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
					System.out
							.println("The application could not communicate with the sound card");
					ControlPanel.this.play.setEnabled(true);
				}
			}
			// if the audio recorder hasn't been initialized
			else {
				ControlPanel.this.record.setText(START_RECORDING);
				audioRecorder.stopRecording();
				try {
					audioRecorder.write(SOUND_FILE);

				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				} catch (NotRecordedYetException ex) {
					System.out.println(ex.getMessage());
				}

				audioRecorder = null;

				// start the delay
				Timer delay = new Timer();
				delay.schedule(new DelayRendering(), DELAY);

				ControlPanel.this.play.setEnabled(false);
				ControlPanel.this.record.setEnabled(false);
			}
		}
	}

	/**
	 * Creates a new contour after a delay. This ensures that the sound has been
	 * fully written before the analysis is performed
	 * 
	 * @author ryan
	 * 
	 */

	class DelayRendering extends TimerTask {

		/**
		 * The run method in the thread
		 */

		public void run() {
			ControlPanel.this.gui.updateContour(new ContourPanel(SOUND_FILE));
			ControlPanel.this.play.setEnabled(true);
			ControlPanel.this.record.setEnabled(true);
		}
	}
}