/*
 * TCSS 372 Computer Architecture
 * Project 1
 */

package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import view.GUIMain;

public class Computer {
	
	private final static int MAX_MEMORY_TEXT_SEGMENT = 800;
	private final static int MAX_MEMORY_DATA_SEGMENT = 100;
	private final static int MAX_REGISTERS = 32;
	
	/**
	 * This is the array holding all of the registers.
	 */
	private HexadecimalString[] mRegisters;
	
	/**
	 * This is the map that maps the register name to a number.
	 */
	private Map<String, Integer> myRegisterTable;
	
	/**
	 * This is the array representing memory spaces used for instructions. 
	 * Each element in the array represents four bytes in memory.
	 */
	//private HexadecimalString[] mMemoryTextSegment;
	//Check what blank lines do. Say no blank lines , will cause errors.
	private Instruction[] mMemoryTextSegment;
	
	/**
	 * This is the array represent memory spaces in the data segment. Each element in the
	 * array represents four bytes in memory.
	 */
	private HexadecimalString[] mMemoryDataSegment;
	
	/**
	 * This is the array that represents the number of instructions.
	 */
	private int numInstructions;
	
	/** 
	 * This is the hexadecimal string representing the program counter.
	 */
	private HexadecimalString mPC;
	
	/**
	 * This is the map which represents the symbol table.
	 */
	private Map<String, Integer> mySymbolTable;

	
	public Computer() {
		numInstructions = 0;
		mPC = new HexadecimalString();
		mPC.setDecimalValue(4194304);
		mRegisters = new HexadecimalString[MAX_REGISTERS];
		for(int i = 0; i < mRegisters.length; i++) {
			mRegisters[i] = new HexadecimalString();
		}
		/*mMemoryTextSegment = new HexadecimalString[MAX_MEMORY_TEXT_SEGMENT];
		for(int i = 0; i < mMemoryTextSegment.length; i++) {
			mMemoryTextSegment[i] = new HexadecimalString();
		}*/
		mMemoryTextSegment = new Instruction[MAX_MEMORY_TEXT_SEGMENT];
		/*for(int i = 0; i < mMemoryTextSegment.length; i++) {
			mMemoryTextSegment[i] = new Instruction();
		}*/
		mMemoryDataSegment = new HexadecimalString[MAX_MEMORY_DATA_SEGMENT];
		for(int i = 0; i < mMemoryDataSegment.length; i++) {
			mMemoryDataSegment[i] = new HexadecimalString();
		}
		mRegisters[28].setDecimalValue(268468224);
		mRegisters[29].setDecimalValue(2147479548);
		mySymbolTable = new TreeMap<>();
		myRegisterTable = new TreeMap<>();
	}
	
	public HexadecimalString[] getRegisters() {
		return mRegisters;
	}
	
	public Instruction[] getMemoryTextSegment() {
		return mMemoryTextSegment;
	}
	
	public HexadecimalString[] getMemoryDataSegment() {
		return mMemoryDataSegment;
	}
	
	public HexadecimalString getPC() {
		return mPC;
	}
	
	public void executeOneLine() {
		mPC.setDecimalValue(mPC.getDecimalValue() + 4);
	}
	
	public void executeAllLines() {
		//PC starts at  4194304
		//for()
	}
	
	//R FORMAT INSTRUCTIONS
	public void add() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - 4194304)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		int secondReg = myRegisterTable.get(instrArguments[2]);
		// Note: If number cannot be represented in 32 bits, exception will be thrown.
		mRegisters[destReg].setDecimalValue(mRegisters[firstReg].getDecimalValue() + mRegisters[secondReg].getDecimalValue()); 
	}
	
	public void addU() {
		try {
			add();
		}
		catch(IllegalArgumentException e) {
			// Do not throw exception. Simple accept it.
		}
	}
	
	public void and() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - 4194304)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		int secondReg = myRegisterTable.get(instrArguments[2]);
		mRegisters[destReg].setDecimalValue(mRegisters[firstReg].getDecimalValue() & mRegisters[secondReg].getDecimalValue());
	}
	
	public void or() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - 4194304)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		int secondReg = myRegisterTable.get(instrArguments[2]);
		mRegisters[destReg].setDecimalValue(mRegisters[firstReg].getDecimalValue() | mRegisters[secondReg].getDecimalValue());
	}
	//I FORMAT INSTRUCTIONS
	
	public void addI() {
		
	}
	
	public void addIU() {
		
	}
	
	public void andI() {
		
	}
	
	public void orI() {
		
	}
	
	public void lw() {
		
	}
	
	public void sw() {
		
	}
	
	public void beq() {
		
	}
	
	public void bne() {
		
	}
	
	//J FORMAT INSTRUCTIONS
	public void j() {
		
	}
	
	public void jr() {
		
	}
	
	// FOR TESTING GUI ONLY ->>>
	public void execute(GUIMain gui, String instr) {
		//test error
		int i = 0;
		JOptionPane.showMessageDialog(null, "Error loading instructions.\nOn line " + i);
		
	} 
	
	public void loadInstructions() {
		
	}
	
	public void assemble(String text) {
		
		// FIRST SCAN LINE FOR . IF YOU SEE . if it matches .text
		// then go into .text mode. In this mode you put data in the
		// corrent memory addresses.
		// WHEN YOU REACH NEXT . WHICH IS .text THEN THESE ARE INSTRUCTIONS
		// which you can put in the text segment.
		// FIRST SCAN LINE FOR SEMICOLON. IF IT HAS THEN PUT INTO A MAP
		// MAPPING THE WORD TO THE ADDRESS OF NEXT LINE
		Scanner myScanner = new Scanner(text);
		String temp;
		boolean dataSegment = false;
		boolean label = false;
		while(myScanner.hasNextLine()) {
			temp = myScanner.nextLine();
			if(temp.substring(0,5).equals(".data")) {
				// Need to know that the following lines are not instructions 
				// Set boolean data segment to true
				dataSegment = true;
				label = true;
			}
			else if(temp.substring(0,5).equals(".text")) {
				// Need to know that the following lines are instructions 
				// Set boolean data segment to false
				dataSegment = false;
				label = false;
			}
			else if(temp.contains(":")) {
				label = true;
			}
			Scanner lineScanner = new Scanner(temp);
			if(dataSegment) {
				// Put number in segment
				// PUt label
			}
			
		}
		myScanner.close();
	}
	
	public void resetComputer() {
		numInstructions = 0;
		mPC = new HexadecimalString();
		mPC.setDecimalValue(4194304);
		mRegisters = new HexadecimalString[MAX_REGISTERS];
		for(int i = 0; i < mRegisters.length; i++) {
			mRegisters[i] = new HexadecimalString();
		}
		mMemoryTextSegment = new Instruction[MAX_MEMORY_TEXT_SEGMENT];
		mMemoryDataSegment = new HexadecimalString[MAX_MEMORY_DATA_SEGMENT];
		for(int i = 0; i < mMemoryDataSegment.length; i++) {
			mMemoryDataSegment[i] = new HexadecimalString();
		}
		mRegisters[28].setDecimalValue(268468224);
		mRegisters[29].setDecimalValue(2147479548);
		mySymbolTable = new TreeMap<>();
		myRegisterTable = new TreeMap<>();
		
		
	}
	
}
