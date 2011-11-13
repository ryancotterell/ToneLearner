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

public class WavAudioRecorder extends Thread {
	private static final AudioFormat format = new AudioFormat(
			AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2, 4, 44100.0F,
			false);
	private static final AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;
	private Timer timer;
	private boolean recording;
	private ByteArrayOutputStream out;
	private TargetDataLine line;
	private byte[] buffer;

	public WavAudioRecorder() throws LineUnavailableException {

		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		line = (TargetDataLine) AudioSystem.getLine(info);

		line.open(format);
		line.start();

		int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
		buffer = new byte[bufferSize];
		out = new ByteArrayOutputStream();

	}

	public void write(String fileName) throws IOException,
			NotRecordedYetException {
		if (out == null) {
			throw new NotRecordedYetException(
					"You haven't recorded anything yet");
		}

		File outputFile = new File(fileName + ".wav");
		byte[] audio = out.toByteArray();
		InputStream input = new ByteArrayInputStream(audio);
		AudioInputStream ais = new AudioInputStream(input, format, audio.length
				/ format.getFrameSize());
		AudioSystem.write(ais, targetType, outputFile);
	}

	public void stopRecording() {
		this.recording = false;
		line.stop();
		line.close();

	}

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

	public boolean getRecording() {
		return recording;
	}

	class EndRecordingTask extends TimerTask {
		public void run() {
			recording = false;
			timer.cancel();
		}
	}
}