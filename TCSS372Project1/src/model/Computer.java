/**
 * This class performs the computer operations.
 */

package model;

import javax.swing.JOptionPane;

import view.GUIMain;

public class Computer {
	
	private final static int MAX_MEMORY = 50;
	private final static int MAX_REGISTERS = 32;
	
	
	private BitString mRegisters[];
	private BitString mMemory[];

	
	public Computer() {

		
	}
	
	public void execute() {
		
	}
	
	public void execute(GUIMain gui, String instr) {
		//test error
		int i = 0;
		JOptionPane.showMessageDialog(null, "Error loading instructions.\nOn line " + i);
		
	}
	
	public void loadInstructions() {
		
	}
	
	public void parseInstructions() {
		
	}
}
