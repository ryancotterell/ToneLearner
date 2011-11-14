package edu.jhu.cs.cotterell.tonelearner;

public class Test {
	
	public static void main(String[] args) {
		Pitch p1 = new Pitch("test_tones/cai1_good");
		Pitch p2 = new Pitch("test_tones/cai2_good");
		
		PitchAnalyzer pa = new PitchAnalyzer(p1,p2);
		System.out.println("Cost: " + pa.getTotalCost());
		pa.printDTW();
		
	}

}
