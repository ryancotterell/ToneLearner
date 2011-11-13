package edu.jhu.cs.cotterell.tonelearner;

import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioFileFormat;
import java.io.ByteArrayOutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Thread;

/**
 * Records wave files
 * @author ryan
 *
 */

public class WavAudioRecorder extends Thread {
	
	/**
	 * Wav extension
	 */
	
	private static final String EXTENSION = ".wav";
	
	/**
	 * The audio format 
	 */
	
	private static final AudioFormat format = new AudioFormat(
			AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2, 4, 44100.0F,
			false);
	
	/**
	 * The target audio format
	 */
	
	private static final AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;
	
	/**
	 * Timer that can be used to set a maximum recording time
	 */
	
	private Timer timer;
	
	/**
	 * Whether the thread is currently recording
	 */
	
	private boolean recording;
	
	/**
	 * byte array output
	 */
	
	private ByteArrayOutputStream out;
	
	/**
	 * target line to write audio
	 */
	
	private TargetDataLine line;
	
	/**
	 * buffer 
	 */
	
	private byte[] buffer;

	/**
	 * Creates a new wave recording
	 * @throws LineUnavailableException if the sound recording device can't be reached
	 */
	
	public WavAudioRecorder() throws LineUnavailableException {
		
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		line = (TargetDataLine) AudioSystem.getLine(info);

		line.open(format);
		line.start();

		int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
		buffer = new byte[bufferSize];
		out = new ByteArrayOutputStream();
	}

	/**
	 * Writes the audio file
	 * 
	 * @param fileName name of file to be written
	 * @throws IOException if the file can't be written
	 * @throws NotRecordedYetException if nothing has been recorded yet
	 */
	
	public void write(String fileName) throws IOException,
			NotRecordedYetException {
		if (out == null) {
			throw new NotRecordedYetException(
					"You haven't recorded anything yet");
		}

		File outputFile = new File(fileName + EXTENSION);
		byte[] audio = out.toByteArray();
		InputStream input = new ByteArrayInputStream(audio);
		AudioInputStream ais = new AudioInputStream(input, format, audio.length
				/ format.getFrameSize());
		AudioSystem.write(ais, targetType, outputFile);
	}

	/**
	 * Stops the recording
	 */
	
	public void stopRecording() {
		this.recording = false;
		line.stop();
		line.close();
	}

	/**
	 * The main run thread
	 */
	
	public void run() {
		recording = true;
		while (recording) {
			int count = line.read(buffer, 0, buffer.length);
			if (count > 0) {
				out.write(buffer, 0, count);
			}
		}
		try {
			out.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Returns whether the thread is recording
	 * @return whether the thread is recording
	 */

	public boolean getRecording() {
		return recording;
	}
	
	/**
	 * A timer task that occurs a set period of time
	 * after the recording has started. This puts a cap
	 * on the max recording time.
	 * @author ryan
	 *
	 */

	class EndRecordingTask extends TimerTask {
		
		/**
		 * The run method
		 */
		
		public void run() {
			recording = false;
			timer.cancel();
		}
	}
}