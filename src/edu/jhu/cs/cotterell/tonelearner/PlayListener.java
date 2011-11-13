package edu.jhu.cs.cotterell.tonelearner;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class PlayListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		Toolkit.getDefaultToolkit().beep();

	}

}