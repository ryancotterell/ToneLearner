package edu.jhu.cs.cotterell.tonelearner;

/**
 * A exception thrown when the application tries to play a sound that has not
 * been recorded yet
 * 
 * @author ryan
 * 
 */

@SuppressWarnings("serial")
public class NotRecordedYetException extends Exception {

	/**
	 * The constructor for the exception
	 * 
	 * @param msg
	 *            the error message
	 */

	public NotRecordedYetException(String msg) {
		super(msg);
	}
}