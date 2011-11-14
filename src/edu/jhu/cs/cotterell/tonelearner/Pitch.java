package edu.jhu.cs.cotterell.tonelearner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains the pitch of the sound over time
 * 
 * @author ryan
 * 
 */

public class Pitch {

	/**
	 * Linux identification
	 */

	private static final String LINUX = "Linux";

	/**
	 * Unix identification
	 */

	private static final String UNIX = "Unix";

	/**
	 * Mac OSX identification
	 */

	private static final String MAC = "Mac OS X";

	/**
	 * The filename of the sound file that this pitch object
	 * has analyzed
	 */
	
	private String file;
	
	/**
	 * HashMap that maps time points to pitch values
	 */

	private Map<Double, Double> points;

	/**
	 * Creates a new pitch object from the file passed in the constructor
	 * 
	 * @param file
	 *            the name of the file
	 */

	public Pitch(String file) {
		this.updatePoints(file);
	}
	
	/**
	 * Updates the hashmap based on the suond file
	 * entered in the constructor.
	 */
	
	public void updatePoints() {
		updatePoints(this.file);
	}
	
	/**
	 * Updates the hashmap based on the sound file entered
	 * @param file the file entered
	 */
	
	public void updatePoints(String file) {
		this.file = file;
		points = new HashMap<Double, Double>();
		String s = null;
		String os = System.getProperty("os.name");
		String location;
		// determines the os and calls praat appropriately
		if (os.equals(LINUX) || os.equals(UNIX)) {
			location = "praat " + System.getProperty("user.dir")
					+ "/pitch.praat " + file;
		} else if (os.equals(MAC)) {
			location = "/Applications/Praat.app/Contents/MacOS/Praat "
					+ System.getProperty("user.dir") + "/pitch.praat " + file;
		} else {
			location = null;
			System.out.println("Sorry, no Windows");
			System.exit(0);
		}

		// attempts to read the file
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

		} catch (IOException ex) {
			System.out.println("exception occured!");
			ex.printStackTrace();
		}
	}

	/**
	 * Returns the the map of pitch values of time
	 * 
	 * @return the hashmap of pitch values over time
	 */

	public Map<Double, Double> getPoints() {
		return points;
	}
}