/*
 * TCSS 372 Computer Architecture
 * Project 1
 */

package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import view.GUIMain;

public class Computer {
	
	private final static int MAX_MEMORY_TEXT_SEGMENT = 100;
	private final static int MAX_MEMORY_DATA_SEGMENT = 100;
	
	private final static int STARTING_ADDRESS_TEXT = 4194304;
	private final static int STARTING_ADDRESS_DATA = 26850092;
	// Formula for data (nonstack) (address - STARTING_ADDRESS_DATA)/4)
	private final static int STARTING_ADDRESS_STACK = 2147479548;
	// Formula for stack (address - STARTING_ADDRESS_STACK)/4 + STACK_INDEX
	private final static int STARTING_ADDRESS_GLOBAL = 268468224;
	private final static int STACK_INDEX = 50;
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
	private Instruction[] mMemoryTextSegment;
	
	/**
	 * This is the array represent memory spaces used for storing memory. Each element in the
	 * array represents four bytes in memory.
	 */
	private HexadecimalString[] mMemoryDataSegment;
	
	/**
	 * This is the integer that represents the last instruction index in array.
	 */
	private int maxInstructionIndex;
	
	/** 
	 * This is the hexadecimal string representing the program counter.
	 */
	private HexadecimalString mPC;
	
	/**
	 * This is the index counter used internally when assembling the instructions
	 * for the memory data segment.
	 */
	private int memoryDataIndex;
	
	/**
	 * This is the index counter used internally when assembling the instructions
	 * for the memory text segment.
	 */
	private int memoryTextIndex;
	
	/**
	 * This is the map which represents the symbol table.
	 */
	private Map<String, Integer> mySymbolTable;

	
	public Computer() {
		memoryDataIndex = 0;
		memoryTextIndex = 0;
		maxInstructionIndex = -1;
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
		mRegisters[28].setDecimalValue(STARTING_ADDRESS_GLOBAL);
		mRegisters[29].setDecimalValue(STARTING_ADDRESS_STACK);
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
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String operation = mMemoryTextSegment[currentInstrIndex].getOperation();
		// Determine operation to be performed
		if(operation.equals("ADD")) {
			add();
		}
		else if(operation.equals("ADDU")) {
			addU();
		}
		else if(operation.equals("AND")) {
			and();
		}
		else if(operation.equals("OR")) {
			or();
		}
		else if(operation.equals("ADDI")) {
			addI();
		}
		else if(operation.equals("ADDIU")) {
			addIU();
		}
		else if(operation.equals("ANDI")) {
			andI();
		}
		else if(operation.equals("ORI")) {
			orI();
		}
		else if(operation.equals("LW")) {
			lw();
		}
		else if(operation.equals("SW")) {
			sw();
		}
		else if(operation.equals("BEQ")) {
			beq();
		}
		else if(operation.equals("BNE")) {
			bne();
		}
		else if(operation.equals("J")) {
			j();
		}
		else if(operation.equals("JR")) {
			jr();
		}
		else {
			throw new IllegalArgumentException("Unknown operation detected: " + operation + ".");
		}
		mPC.setDecimalValue(mPC.getDecimalValue() + 4);
	}
	
	public void executeAllLines() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		for(int i = currentInstrIndex; i <= maxInstructionIndex; i++) {
			executeOneLine();
		}
	}
	
	//R FORMAT INSTRUCTIONS
	public void add() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
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
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		int secondReg = myRegisterTable.get(instrArguments[2]);
		mRegisters[destReg].setDecimalValue(mRegisters[firstReg].getDecimalValue() & mRegisters[secondReg].getDecimalValue());
	}
	
	public void or() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		int secondReg = myRegisterTable.get(instrArguments[2]);
		mRegisters[destReg].setDecimalValue(mRegisters[firstReg].getDecimalValue() | mRegisters[secondReg].getDecimalValue());
	}
	//I FORMAT INSTRUCTIONS
	
	public void addI() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		long immediateOperand = Long.getLong(instrArguments[2]);
		mRegisters[destReg].setDecimalValue(mRegisters[firstReg].getDecimalValue() + immediateOperand);
	}
	
	public void addIU() {
		try {
			addI();
		}
		catch(IllegalArgumentException e) {
			// Do not throw exception. Simple accept it.
		}
	}
	
	public void andI() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		long immediateOperand = Long.getLong(instrArguments[2]);
		mRegisters[destReg].setDecimalValue(mRegisters[firstReg].getDecimalValue() & immediateOperand);
	}
	
	public void orI() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		long immediateOperand = Long.getLong(instrArguments[2]);
		mRegisters[destReg].setDecimalValue(mRegisters[firstReg].getDecimalValue() | immediateOperand);
	}
	
	public void lw() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		if(instrArguments[1].contains("(")) {
			String[] findAddress = instrArguments[1].split("(");
			int addressOffset = Integer.getInteger(findAddress[0]);
			int sourceReg = myRegisterTable.get(findAddress[1].substring(0,findAddress[1].length()-1));
			int address = (int)mRegisters[sourceReg].getDecimalValue() + addressOffset;
			mRegisters[destReg].setDecimalValue(mMemoryDataSegment[address].getDecimalValue());
		// UNSURE ABOUT ADDRESSING ->>> DO WE DIVDE BY FOUR? DO WE ADD SOMETHIHNG TO IT?
		}
		else {
			int address = mySymbolTable.get(instrArguments[1]);
			mRegisters[destReg].setDecimalValue(mMemoryDataSegment[address].getDecimalValue());
		}
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
	
	public void assemble(String text) {
		resetComputer();
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
		while(myScanner.hasNextLine()) {
			temp = myScanner.nextLine().trim();
			System.out.println("H" + temp + "H");
			if(temp.length() == 0) {
				continue;
			}
			if(temp.contains(".data")) {

				System.out.println("LJS:DKJFS:LDKJFA:LKFJDSA:LFKA:LFJDK");
				// Need to know that the following lines are not instructions 
				// Set boolean data segment to true
				dataSegment = true;
			}
			else if(temp.contains(".text")) {
				// Need to know that the following lines are instructions 
				// Set boolean data segment to false
				dataSegment = false;
			}
			else if(temp.contains(":")) {
				// Label
				// Put the label and the associated instr index into the 
				// symbol table.
				// Do not increment arrayInstrIndex
				if(dataSegment) {
					scanDataLabelLine(temp);
				}
				else {
					scanTextLabelLine(temp);
				}
			}
			else if(dataSegment) {
				System.out.println("MEMORY DATA INDEX" + memoryDataIndex);
				scanDataLine(temp);
				//maxInstructionIndex++;
			}
			else {
				// Regular instruction
				// Put instruction in memory text segment and increment
				// next arrayInstructionIndex by 1
				putInstructionLine(temp);
				maxInstructionIndex++;
			}	
			System.out.println("MAX INSTR INDEX: " + maxInstructionIndex);
		}
		System.out.println("MAX INSTRUCTION INDEX" + maxInstructionIndex);
		System.out.println("MYSYBMOLTABLE" + mySymbolTable.toString());
		System.out.println("MYINSTRUCTIONS" + Arrays.deepToString(mMemoryTextSegment));
		System.out.println("MYDATA" + Arrays.deepToString(mMemoryDataSegment));
		myScanner.close();
	}
	
	public int getMaxInstructionIndex() {
		return maxInstructionIndex;
	}
	
	public Map<String, Integer> getSymbolTable() {
		return mySymbolTable;
	}
	
	public void scanDataLine(String oneLine) {
		System.out.println("HERE IS THE DATA LINE" + oneLine + ".");
		Scanner myScanner = new Scanner(oneLine);
		if(myScanner.next().equals(".word")) {
			long value = Long.valueOf(myScanner.next());
			HexadecimalString variable = new HexadecimalString();
			System.out.println("HERE IS THE VALUE: " + value);
			variable.setDecimalValue(value);
			mMemoryDataSegment[memoryDataIndex] = variable;
			memoryDataIndex++;
			myScanner.close();
		}
		else {
			myScanner.close();
			throw new IllegalArgumentException("Unable to determine"
					+ "the function.");
		}
	}
	
	public void scanDataLabelLine(String labelLine) {
		Scanner myScanner = new Scanner(labelLine);
		String varName = myScanner.next();
		// Account for semicolon (:)
		mySymbolTable.put(varName.substring(0, varName.length()-1), memoryDataIndex);
		myScanner.close();
	}
	
	public void scanTextLabelLine(String labelLine) {
		Scanner myScanner = new Scanner(labelLine);
		String varName = myScanner.next();
		// Account for semicolon (:)
		mySymbolTable.put(varName.substring(0, varName.length()-1), memoryTextIndex);
		myScanner.close();
	}
	
	public void putInstructionLine(String instrLine) {
		Instruction instr = new Instruction();
		instr.setInstructionString(instrLine);
		mMemoryTextSegment[memoryTextIndex] = instr;
		memoryTextIndex++;
	}
	
	public void resetComputer() {
		memoryDataIndex = 0;
		memoryTextIndex = 0;
		maxInstructionIndex = -1;
		mPC = new HexadecimalString();
		mPC.setDecimalValue(STARTING_ADDRESS_TEXT);
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
