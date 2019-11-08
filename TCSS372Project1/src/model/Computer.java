/**
 * This class performs the computer operations.
 */

package model;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import view.GUIMain;

public class Computer {
	
	private final static int MAX_MEMORY = 56;
	private final static int MAX_REGISTERS = 32;
	
	/**
	 * This is the array holding all of the registers.
	 */
	private HexadecimalString[] mRegisters;
	
	/**
	 * This is the array representing all of the memory spaces. Each element in the
	 * array represents four bytes in memory.
	 */
	private HexadecimalString[] mMemory;
	
	/** 
	 * This is the hexadecimal string representing the program counter.
	 */
	private HexadecimalString mPC;

	
	public Computer() {
		mPC = new HexadecimalString();
		mPC.setDecimalValue(4194304);
		mRegisters = new HexadecimalString[MAX_REGISTERS];
		for(int i = 0; i < mRegisters.length; i++) {
			mRegisters[i] = new HexadecimalString();
		}
		mMemory = new HexadecimalString[MAX_MEMORY];
		for(int i = 0; i < mMemory.length; i++) {
			mMemory[i] = new HexadecimalString();
		}
		mRegisters[28].setDecimalValue(268468224);
		mRegisters[29].setDecimalValue(2147479548);
		
	}
	
	public void executeOneLine() {
		mPC.setDecimalValue(mPC.getDecimalValue() + 4);
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
