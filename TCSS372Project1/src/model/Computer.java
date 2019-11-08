/**
 * This class performs the computer operations.
 */

package model;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import view.GUIMain;

public class Computer {
	
	private final static int MAX_MEMORY = 50;
	private final static int MAX_REGISTERS = 32;
	
	
	private HexadecimalString mRegisters[];
	private HexadecimalString mMemory[];

	
	public Computer() {
	/*	for (int i = 20; i < MAX_REGISTERS; i++) {
			mRegisters[i] = new BitString();
			mRegisters[i].setValue(0);
		} */
		
	}
	
	public void executeAllLines() {
		//PC starts at  4194304
		//for()
	}
	
	
	public void execute(GUIMain gui, String instr) {
		//test error
		int i = 0;
		JOptionPane.showMessageDialog(null, "Error loading instructions.\nOn line " + i);
		
	}
	
	public void loadInstructions() {
		
	}
	
	public ArrayList<String[]> parseInstructions(String text) {
		Scanner myScanner = new Scanner(text);
		ArrayList<String[]> myInstructions = new ArrayList<>();
		String temp;
		while(myScanner.hasNextLine()) {
			temp = myScanner.nextLine();
			myInstructions.add(temp.split(" "));
		}
		myScanner.close();
		return myInstructions;
	}
	
}
