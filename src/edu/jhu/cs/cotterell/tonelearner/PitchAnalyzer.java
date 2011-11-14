package edu.jhu.cs.cotterell.tonelearner;

public class PitchAnalyzer {
	private Double[] sampleValues;
	private Double[] userValues;
	private double dtw[][];
	
	/**
	 * The two Pitch objects to compare
	 * @param samplePitch sample to compare to
	 * @param userPitch user's input waveform
	 */
	public PitchAnalyzer(Pitch samplePitch, Pitch userPitch)
	{
		this.sampleValues = new Double[samplePitch.getPoints().values().size()];
		samplePitch.getPoints().values().toArray(this.sampleValues);
		
		this.userValues = new Double[userPitch.getPoints().values().size()];
		userPitch.getPoints().values().toArray(this.userValues);
		
		this.dynamicTimeWarp();
	}
	
	/**
	 * Performs dynamic time warping on the two Pitch objects.
	 * Original objects are deleted.
	 */
	private void dynamicTimeWarp()
	{		
		dtw = new double[userValues.length+1][sampleValues.length+1];
		double cost = 0;
		
		dtw[0][0] = 0;
		
		for(int i = 1; i <= sampleValues.length; i++)
			dtw[0][i] = sampleValues[i-1];
	
		for(int i = 1; i <= userValues.length; i++)
			dtw[i][0] = userValues[i-1];
		
		for(int i = 1; i <= sampleValues.length; i++)
			for(int j = 1; j <= userValues.length; j++)
			{
				cost = calculateCost(sampleValues[i-1],userValues[j-1]);
				
				dtw[j][i] = cost + Math.min(Math.min(dtw[j-1][i], dtw[j][i-1]), dtw[j-1][i-1]);
			}
		
	}
	
	public double getTotalCost()
	{
		return dtw[dtw.length-1][dtw[0].length-1];
	}
	
	/**
	 * Determines how "close" the two Pitch objects are.
	 */
	public void evaluateSimilarity()
	{
		
	}
	
	public void printDTW()
	{
		for(int j = 0; j < dtw.length; j++)
		{
			for(int i = 0; i < dtw[0].length; i++)
			{
				System.out.printf("%8.02f ",dtw[j][i]);
			}
			
			System.out.println();
		}
	}

	private double calculateCost(double samp, double user)
	{
		return Math.abs(samp-user);
	}
}
