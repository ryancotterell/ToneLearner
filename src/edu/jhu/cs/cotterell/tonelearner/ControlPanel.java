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

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 50;
	private static final int HEIGHT = 400;
	private static final String START_RECORDING = "Start Recording";
	private static final String STOP_RECORDING = "Stop Recording";
	private static final String PLAY = "Play";
	private static final String SOUND_FILE = "tone-learner-sound-file";

	private static final Font font = new Font("Arial", Font.PLAIN, 8);

	private JButton record;
	private JButton play;
	private WavAudioRecorder audioRecorder;
	private Timer timer;

	private ToneLearner gui;

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

	class PlayListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String location = System.getProperty("user.dir") + "/" + SOUND_FILE;
			new WavAudioPlayer(location).start();

		}
	}

	class RecordListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (audioRecorder == null) {
				ControlPanel.this.play.setEnabled(false);

				try {
					audioRecorder = new WavAudioRecorder();
					ControlPanel.this.record.setText(STOP_RECORDING);
					// in case we want to set a maxmium recording time
					// timer = new Timer();
					// timer.schedule(new EndRecordingTask(), 5*1000);
					audioRecorder.start();
				} catch (LineUnavailableException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
					System.out
							.println("We couldn not communicate with your sound card");
					// renable play if it fails
					ControlPanel.this.play.setEnabled(true);
					// System.exit(0);
				}
			} else {
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
				Timer delay = new Timer();
				delay.schedule(new DelayRendering(), 2000);

				ControlPanel.this.play.setEnabled(false);
				ControlPanel.this.record.setEnabled(false);
			}
		}
	}

	class EndRecordingTask extends TimerTask {
		public void run() {
			try {
				audioRecorder.stopRecording();
				audioRecorder.write("test");
			} catch (Exception ex) {
				timer.cancel();
			}
		}
	}

	class DelayRendering extends TimerTask {
		public void run() {
			ControlPanel.this.gui.addContour(new Contour(SOUND_FILE));
			ControlPanel.this.play.setEnabled(true);
			ControlPanel.this.record.setEnabled(true);
		}
	}
}