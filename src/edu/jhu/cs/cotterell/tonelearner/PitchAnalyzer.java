package edu.jhu.cs.cotterell.tonelearner;

public class PitchAnalyzer {
	private Pitch samplePitch;
	private Pitch userPitch;
	
	
	/**
	 * The two Pitch objects to compare
	 * @param samplePitch sample to compare to
	 * @param userPitch user's input waveform
	 */
	public PitchAnalyzer(Pitch samplePitch, Pitch userPitch)
	{
		this.samplePitch = samplePitch;
		this.userPitch = userPitch;
	}
	
	/**
	 * Performs dynamic time warping on the two Pitch objects.
	 * Original objects are deleted.
	 */
	public void dynamicTimeWarp()
	{
		
	}
	
	/**
	 * Determines how "close" the two Pitch objects are.
	 */
	public void evaluateSimilarity()
	{
		
	}
}
