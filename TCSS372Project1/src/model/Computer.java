/*
 * TCSS 372 Computer Architecture
 * Project 1
 */

package model;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Computer {
	
	/** The maximum amount of memory allocated for the text segment. */
	private final static int MAX_MEMORY_TEXT_SEGMENT = 100;
	
	/** The maximum amount of memory allocated for the data segment. */
	private final static int MAX_MEMORY_DATA_SEGMENT = 100;
	
	/** The starting address of the text segment. */
	private final static int STARTING_ADDRESS_TEXT = 4194304;
	
	/** The starting address of the data segment. */
	private final static int STARTING_ADDRESS_DATA = 26850092;
	// Formula for data (nonstack) (address - STARTING_ADDRESS_DATA)/4)
	
	/** The starting address of stack pointer. */
	private final static int STARTING_ADDRESS_STACK = 2147479548;
	// Formula for stack (address - STARTING_ADDRESS_STACK)/4 + STACK_INDEX
	
	/** The staring address of the global pointer. */
	private final static int STARTING_ADDRESS_GLOBAL = 268468224;
	
	/** The index in the array where stack begins. */
	private final static int STACK_INDEX = 50;
	
	/** The maximum amount of registers in the computer. */
	private final static int MAX_REGISTERS = 32;
	
	/** This is the array holding all of the registers. */
	private HexadecimalString[] mRegisters;
	
	/** This is the map that maps the register name to a number. */
	private Map<String, Integer> myRegisterTable;
	
	/** This is the array representing memory spaces used for instructions. */ 
	private Instruction[] mMemoryTextSegment;
	 //Each element in the array represents four bytes in memory.
	
	/** This is the array represent memory spaces used for storing memory. */
	//Each element in the array represents four bytes in memory.
	private HexadecimalString[] mMemoryDataSegment;
	
	/** This is the integer that represents the last instruction index in array. */
	private int maxInstructionIndex;
	
	/** This is the hexadecimal string representing the program counter. */
	private HexadecimalString mPC;
	
	/** Index counter used internally when assembling the instructions for the memory data segment. */
	private int memoryDataIndex;
	
	/** Index counter used internally when assembling the instructions for the memory text segment. */
	private int memoryTextIndex;
	
	/** This is the map which represents the symbol table. */
	private Map<String, Integer> mySymbolTable;

	/**
	 * Default constructor that initializes fields to their 
	 * default values.
	 */
	public Computer() {
		resetComputer();
		setUpRegisterMapping();
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
	
	
	/**
	 * Gets the maximum instruction index.
	 * 
	 * @return The maximum instruction index.
	 */
	public int getMaxInstructionIndex() {
		return maxInstructionIndex;
	}
	
	/**
	 * Gets the map containing the symbol table for the program.
	 * 
	 * @return The mapping of string to integer for the symbols in the program.
	 */
	public Map<String, Integer> getSymbolTable() {
		return mySymbolTable;
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
			mRegisters[destReg].setDecimalValue(mMemoryDataSegment[(address - STARTING_ADDRESS_DATA)/4].getDecimalValue());
		}
		else {
			int address = mySymbolTable.get(instrArguments[1]);
			mRegisters[destReg].setDecimalValue(mMemoryDataSegment[address].getDecimalValue());
		}
	}
	
	public void sw() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int sourceReg = myRegisterTable.get(instrArguments[0]);
		// FORMAT: SW $REG1,4($REG2)
		if(instrArguments[1].contains("(")) {
			String[] findAddress = instrArguments[1].split("(");
			int addressOffset = Integer.getInteger(findAddress[0]);
			int addressInReg = myRegisterTable.get(findAddress[1].substring(0,findAddress[1].length()-1));
			int address = (int)mRegisters[addressInReg].getDecimalValue() + addressOffset;
			if(addressInReg == mRegisters[29].getDecimalValue()) {
				// It is for stack
				mMemoryDataSegment[(address - STARTING_ADDRESS_STACK)/4 + STACK_INDEX].setDecimalValue(mRegisters[sourceReg].getDecimalValue());
			}
			else {
				// Non stack data
				mMemoryDataSegment[(address - STARTING_ADDRESS_DATA)/4].setDecimalValue(mRegisters[sourceReg].getDecimalValue());
			}
		}
	}
	
	public void beq() {
		// FORMAT: BEQ REG1, REG2, LABEL
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int firstReg = myRegisterTable.get(instrArguments[0]);
		int secondReg = myRegisterTable.get(instrArguments[1]);
		if(mRegisters[firstReg] == mRegisters[secondReg]) {
			int address = mySymbolTable.get(instrArguments[2]);
			// Subtract 4 because we will add 4 in the execute loop
			mRegisters[29].setDecimalValue(mMemoryDataSegment[address].getDecimalValue() - 4);
		}
	}
	
	public void bne() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int firstReg = myRegisterTable.get(instrArguments[0]);
		int secondReg = myRegisterTable.get(instrArguments[1]);
		if(mRegisters[firstReg] != mRegisters[secondReg]) {
			int destAddrIndex = mySymbolTable.get(instrArguments[2]);
			// Subtract 4 because we will add 4 in the execute loop
			mRegisters[29].setDecimalValue(STARTING_ADDRESS_TEXT + destAddrIndex * 4 - 4);
		}
	}
	
	//J FORMAT INSTRUCTIONS
	public void j() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destAddrIndex = mySymbolTable.get(instrArguments[0]);
		// Subtract 4 because we will add 4 in the execute loop
		mRegisters[29].setDecimalValue(STARTING_ADDRESS_TEXT + destAddrIndex * 4 - 4);
	}
	
	public void jr() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destAddrIndex = mySymbolTable.get(instrArguments[0]);
		// Subtract 4 because we will add 4 in the execute loop
		mRegisters[29].setDecimalValue(mRegisters[destAddrIndex].getDecimalValue() - 4);
	}
	
	/**
	 * Assembles the program based on the text input from
	 * the user.
	 * 
	 * @param text The text that contains the instructions.
	 */
	public void assemble(String text) {
		resetComputer();
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
				dataSegment = true;
			}
			else if(temp.contains(".text")) {
				dataSegment = false;
			}
			else if(temp.contains(":")) {
				if(dataSegment) {
					scanDataLabelLine(temp);
				}
				else {
					scanTextLabelLine(temp);
				}
			}
			else if(dataSegment) {
				scanDataLine(temp);
			}
			else {
				putInstructionLine(temp);
				maxInstructionIndex++;
			}
		}
		myScanner.close();
	}
	
	/**
	 * Scans one line in the data portion of the instructions, 
	 * and puts the word in appropriate memory location and updates
	 * the index accordingly.
	 * 
	 * @param oneLine The line that will be scanned.
	 */
	private void scanDataLine(String oneLine) {
		Scanner myScanner = new Scanner(oneLine);
		if(myScanner.next().equals(".word")) {
			long value = Long.valueOf(myScanner.next());
			HexadecimalString variable = new HexadecimalString();
			variable.setDecimalValue(value);
			mMemoryDataSegment[memoryDataIndex] = variable;
			memoryDataIndex++;
			myScanner.close();
		}
		else {
			myScanner.close();
			throw new IllegalArgumentException("Unable to determine the function:"
					+ oneLine + ".");
		}
	}
	
	/**
	 * Scans a label in the data section and puts the label
	 * and corresponding location in the symbol table.
	 * 
	 * @param labelLine The line containing the label.
	 */
	private void scanDataLabelLine(String labelLine) {
		Scanner myScanner = new Scanner(labelLine);
		String varName = myScanner.next();
		mySymbolTable.put(varName.substring(0, varName.length()-1), memoryDataIndex);
		myScanner.close();
	}
	
	/**
	 * Scans a label in the text section and puts the label
	 * and corresponding location in the symbol table.
	 * 
	 * @param labelLine The line containing the label.
	 */
	private void scanTextLabelLine(String labelLine) {
		Scanner myScanner = new Scanner(labelLine);
		String varName = myScanner.next();
		mySymbolTable.put(varName.substring(0, varName.length()-1), memoryTextIndex);
		myScanner.close();
	}
	
	/**
	 * Puts the line of instruction into the text segment
	 * of memory and increments the index.
	 * 
	 * @param instrLine The line of instruction to be added into memory.
	 */
	private void putInstructionLine(String instrLine) {
		Instruction instr = new Instruction();
		instr.setInstructionString(instrLine);
		mMemoryTextSegment[memoryTextIndex] = instr;
		memoryTextIndex++;
	}
	
	/** 
	 * Resets the computer by setting all fields and variables
	 * back to their initial state.
	 */
	private void resetComputer() {
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
		mRegisters[28].setDecimalValue(STARTING_ADDRESS_GLOBAL);
		mRegisters[29].setDecimalValue(STARTING_ADDRESS_STACK);
		mySymbolTable = new TreeMap<>();
	}
	
	/**
	 * Sets up the mapping of registers.
	 */
	private void setUpRegisterMapping() {
		myRegisterTable = new TreeMap<>();
		myRegisterTable.put("$zero", 0);
		myRegisterTable.put("$at", 1);
		myRegisterTable.put("$v0", 2);
		myRegisterTable.put("$v1", 3);
		myRegisterTable.put("$a0", 4);
		myRegisterTable.put("$a1", 5);
		myRegisterTable.put("$a2", 6);
		myRegisterTable.put("$a3", 7);
		for(int i = 0; i <= 7; i++) {
			myRegisterTable.put("$t" + i, i + 8);
		}
		for(int i = 0; i <= 7; i++) {
			myRegisterTable.put("$s" + i, i + 16);
		}
		myRegisterTable.put("$t8", 24);
		myRegisterTable.put("$t9", 25);
		myRegisterTable.put("$k0", 26);
		myRegisterTable.put("$k1", 27);
		myRegisterTable.put("$gp", 28);
		myRegisterTable.put("$sp", 29);
		myRegisterTable.put("$fp", 30);
		myRegisterTable.put("$ra", 31);
	}
}
