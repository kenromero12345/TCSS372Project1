package model;

import javax.swing.JOptionPane;

import application.GUIMain;

public class Computer {
	
	public Computer() {
		
	}
	
	public void execute(GUIMain gui, String instr) {
		//test error
		int i = 0;
		JOptionPane.showMessageDialog(null, "Error loading instructions.\nOn line " + i);
		
	}
}
