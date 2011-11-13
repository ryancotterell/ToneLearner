package edu.jhu.cs.cotterell.tonelearner;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Contour extends JPanel {

    private Pitch pitch;
    private boolean paint;

    public Contour() {
    	paint = false;
    }
    public Contour(String fileName) {
    	pitch = new Pitch(fileName);
    	paint = true;
    }

    public void paintComponent(Graphics g) {
    	if (paint) {
    		g.setColor(Color.BLACK);
	    
    		for (Double k : pitch.getPoints().keySet()) {
    			int x = (int)(k * 100);
    			x *= 4;
    			int y = pitch.getPoints().get(k).intValue();
    			y -= 300;
    			y = -y;
		
    			g.fillOval(x, y, 5, 5);
    		}
    	}
    }
}