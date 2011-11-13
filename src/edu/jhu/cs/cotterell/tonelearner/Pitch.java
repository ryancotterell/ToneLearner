package edu.jhu.cs.cotterell.tonelearner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class Pitch {
	private static final String LINUX = "Linux";
	private static final String UNIX = "Unix";
	private static final String MAC = "Mac OS X";

	private Map<Double, Double> points;

	public Pitch(String file) {
		points = new HashMap<Double, Double>();
		String s = null;
		String os = System.getProperty("os.name");
		String location;
		if (os.equals(LINUX) || os.equals(UNIX)) {
			location = "praat " + System.getProperty("user.dir") + "/pitch.praat " + file;
		} else if (os.equals(MAC)) {
			location = "/Applications/Praat.app/Contents/MacOS/Praat " + System.getProperty("user.dir") + "/pitch.praat "
					+ file;
		} else {
			location = null;
			System.out.println("Fuck Windows");
			System.exit(0);
		}

		try {
			Process p = Runtime.getRuntime().exec(location);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(
					p.getErrorStream()));

			boolean point = true;
			// hack
			double key = 0;

			while ((s = stdInput.readLine()) != null) {
				if (point) {
					key = Double.parseDouble(s);
				} else {
					double value = Double.parseDouble(s);
					points.put(key, value);
				}

				point = !point;
			}
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}

		} catch (IOException e) {
			System.out.println("exception occured!");
			e.printStackTrace();
		}
	}

	public Map<Double, Double> getPoints() {
		return points;
	}
}