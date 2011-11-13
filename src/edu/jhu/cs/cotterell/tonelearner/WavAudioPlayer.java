package edu.jhu.cs.cotterell.tonelearner;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Plays wave files
 * @author ryan
 *
 */

public class WavAudioPlayer extends Thread {

	/**
	 * Wave extension
	 */
	
	private static final String EXTENSION = ".wav";
	
	/**
	 * Buffer size
	 */
	
	private static final int BUFFER_SIZE = 128000;
	
	/**
	 * The file that will be played
	 */
	
	private File soundFile;
	
	/**
	 * The stream the audio is saved to
	 */
	
	private AudioInputStream audioStream;
	
	/**
	 * The format of the audio
	 */
	
	private AudioFormat audioFormat;
	
	/**
	 * The line to which the audio is sent
	 */
	
	private SourceDataLine sourceLine;

	/**
	 * Creates a new WavAudioPlayer
	 * @param filename the name of the file to be played
	 */
	
	public WavAudioPlayer(String filename) {
		soundFile = new File(filename + EXTENSION);
	}

	/**
	 * The run method in the thread
	 */
	
	public void run() {
		
		//loads the audio stream
		try {
			audioStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}

		audioFormat = audioStream.getFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		
		//sends the audio to the line
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(audioFormat);
		} catch (LineUnavailableException ex) {
			ex.getMessage();
			ex.printStackTrace();		
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}

		//starts the playing
		sourceLine.start();
		int nBytesRead = 0;
		byte[] abData = new byte[BUFFER_SIZE];
		while (nBytesRead != -1) {
			try {
				nBytesRead = audioStream.read(abData, 0, abData.length);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (nBytesRead >= 0) {
				@SuppressWarnings("unused")
				int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
			}
		}

		//kills the line
		sourceLine.drain();
		sourceLine.stop();
		sourceLine.close();
	}
}