/*
 * TCSS 372 Computer Architecture
 * Project 1
 */

package model;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * This is the computer class that represents the backend
 * of the simulator. The computer controls all of the
 * registers, memory, and instructions associated with the
 * program.
 *
 * @author Michael Zachary Loria, Ken Romero
 * @version 11.11.19
 */
public class Computer {

	/** The maximum amount of memory allocated for the text segment. */
	private final static int MAX_MEMORY_TEXT_SEGMENT = 100;

	/** The maximum amount of memory allocated for the data segment. */
	private final static int MAX_MEMORY_DATA_SEGMENT = 100;

	/** The starting address of the text segment. */
	private final static int STARTING_ADDRESS_TEXT = 4194304;

	/** The starting address of the data segment. */
	private final static int STARTING_ADDRESS_DATA = 26850092;

	/** The starting address of stack pointer. */
	private final static int STARTING_ADDRESS_STACK = 2147479548;

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

	/** This is the array representing memory spaces used for instructions. Each element is four bytes. */
	private Instruction[] mMemoryTextSegment;

	/** This is the array represent memory spaces used for storing memory. Each element is four bytes. */
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

	/**
	 * Gets the array of registers.
	 *
	 * @return Array containing the registers.
	 */
	public HexadecimalString[] getRegisters() {
		return mRegisters;
	}

	/**
	 * Gets the array representing the memory text segment.
	 *
	 * @return Array containing the instructions in memory.
	 */
	public Instruction[] getMemoryTextSegment() {
		return mMemoryTextSegment;
	}

	/**
	 * Gets the array representing the memory data segment.
	 *
	 * @return Array containing the data memory.
	 */
	public HexadecimalString[] getMemoryDataSegment() {
		return mMemoryDataSegment;
	}

	/**
	 * Gets the hexadecimal string representing the program counter.
	 *
	 * @return The program counter.
	 */
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

	/**
	 * Executes one line in the program based on the current
	 * program counter value.
	 */
	public void executeOneLine() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String operation = mMemoryTextSegment[currentInstrIndex].getOperation();
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

	/**
	 * Executes all of the lines of instructions in the
	 * program.
	 */
	public void executeAllLines() {
		while(mPC.getDecimalValue() <= maxInstructionIndex * 4 + STARTING_ADDRESS_TEXT) {
			executeOneLine();
		}
	}

	/**
	 * Performs the ADD operation. Takes in three operands: the destination register,
	 * the first source register, and the second source register. Performs ADD operation
	 * on the first source register and the second source register, and puts the result
	 * in the destination register. Throws IllegalArgumentException if resulting number
	 * cannot be represented in 32 bits.
	 */
	public void add() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		int secondReg = myRegisterTable.get(instrArguments[2]);
		mRegisters[destReg].setDecimalValue(mRegisters[firstReg].getDecimalValue() + mRegisters[secondReg].getDecimalValue());
	}

	/**
	 * Performs the ADDU operation. Takes in three operands: the destination register,
	 * the first source register, and the second source register. Performs ADDU operation
	 * on the first source register and the second source register, and puts the result
	 * in the destination register. If the result overflows, no exception will be thrown.
	 */
	public void addU() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		int secondReg = myRegisterTable.get(instrArguments[2]);
		mRegisters[destReg].setDecimalValueUnsigned(mRegisters[firstReg].getDecimalValue() + mRegisters[secondReg].getDecimalValue());
	}

	/**
	 * Performs the AND operation. Takes in three operands: the destination register,
	 * the first source register, and the second source register. Performs AND operation
	 * on the first source register and the second source register, and puts the result
	 * in the destination register.
	 */
	public void and() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		int secondReg = myRegisterTable.get(instrArguments[2]);
		mRegisters[destReg].setDecimalValue(mRegisters[firstReg].getDecimalValue() & mRegisters[secondReg].getDecimalValue());
	}

	/**
	 * Performs the OR operation. Takes in three operands: the destination register,
	 * the first source register, and the second source register. Performs OR operation
	 * on the first source register and the second source register, and puts the result
	 * in the destination register.
	 */
	public void or() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		int secondReg = myRegisterTable.get(instrArguments[2]);
		mRegisters[destReg].setDecimalValue(mRegisters[firstReg].getDecimalValue() | mRegisters[secondReg].getDecimalValue());
	}

	/**
	 * Performs the ADDI operation. Takes in three operands: the destination register,
	 * the first source register, and the immediate operand. Performs ADDI operation
	 * on the first source register and the immediate operand, and puts the result
	 * in the destination register.
	 */
	public void addI() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		long immediateOperand = Long.parseLong(instrArguments[2]);
		mRegisters[destReg].setDecimalValue(mRegisters[firstReg].getDecimalValue() + immediateOperand);
	}

	/**
	 * Performs the ADDIU operation. Takes in three operands: the destination register,
	 * the first source register, and the immediate operand. Performs ADDIU operation
	 * on the first source register and the immediate operand, and puts the result
	 * in the destination register. Note that if the operation results in an overflow,
	 * no exception will be thrown.
	 */
	public void addIU() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		long immediateOperand = Long.parseLong(instrArguments[2]);
		mRegisters[destReg].setDecimalValueUnsigned(mRegisters[firstReg].getDecimalValue() + immediateOperand);
	}

	/**
	 * Performs the ANDI operation. Takes in three operands: the destination register,
	 * the first source register, and the immediate operand. Performs ANDI operation
	 * on the first source register and the immediate operand, and puts the result
	 * in the destination register.
	 */
	public void andI() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		long immediateOperand = Long.parseLong(instrArguments[2]);
		mRegisters[destReg].setDecimalValue(mRegisters[firstReg].getDecimalValue() & immediateOperand);
	}

	/**
	 * Performs the ORI operation. Takes in three operands: the destination register,
	 * the first source register, and the immediate operand. Performs ORI operation
	 * on the first source register and the immediate operand, and puts the result
	 * in the destination register.
	 */
	public void orI() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		int firstReg = myRegisterTable.get(instrArguments[1]);
		long immediateOperand = Long.parseLong(instrArguments[2]);
		mRegisters[destReg].setDecimalValue(mRegisters[firstReg].getDecimalValue() | immediateOperand);
	}

	/**
	 * Performs the LW operation. If using a label it will take in two operands: the destination
	 * register and the label. The format will be as follows: LW REG,LABEL. LD can also take
	 * another format with three operands. In this format, it will take in the destination register,
	 * the offset, and the register. The format will be as follows: LW DESTREG,OFFSET(REG). Note that
	 * if the stack pointer is used as the source register, the data taken from the stack. All other
	 * load operations will take the data from the non-stack data segment.
	 */
	public void lw() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destReg = myRegisterTable.get(instrArguments[0]);
		if(instrArguments[1].contains("(")) {
			String[] findAddress = instrArguments[1].split("\\(");
			int addressOffset = Integer.parseInt(findAddress[0]);
			int sourceReg = myRegisterTable.get(findAddress[1].substring(0,findAddress[1].length()-1));
			int address = (int)mRegisters[sourceReg].getDecimalValue();
			if(address == mRegisters[29].getDecimalValue()) {
				mRegisters[destReg].setDecimalValue(mMemoryDataSegment[(address + addressOffset - STARTING_ADDRESS_STACK)/4 + STACK_INDEX].getDecimalValue());
			}
			else {
				mRegisters[destReg].setDecimalValue(mMemoryDataSegment[(address + addressOffset - STARTING_ADDRESS_DATA)/4].getDecimalValue());
			}
		}
		else {
			int address = mySymbolTable.get(instrArguments[1]);
			mRegisters[destReg].setDecimalValue(mMemoryDataSegment[address].getDecimalValue());
		}
	}

	/**
	 * Performs the SW operation. If using a label it will take in two operands: the source
	 * register and the label. The format will be as follows: SW REG,LABEL. SW will store the
	 * data in the register into the memory address corresponding to the label. SW can also take
	 * another format with three operands. In this format, it will take in the source register,
	 * the offset, and the register containing the address. The format will be as follows:
	 * SW REG1,OFFSET(REG2). SW will store data from REG1 into the memory address that is in REG2
	 * plus the offset. Note that if the stack pointer is used as the second register, the data will be
	 * stored in the stack. All other store operations will store the data into the non-stack data segment.
	 */
	public void sw() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int sourceReg = myRegisterTable.get(instrArguments[0]);
		if(instrArguments[1].contains("(")) {
			String[] findAddress = instrArguments[1].split("\\(");
			int addressOffset = Integer.parseInt(findAddress[0]);
			int addressInReg = myRegisterTable.get(findAddress[1].substring(0,findAddress[1].length()-1));
			int address = (int)mRegisters[addressInReg].getDecimalValue();
			if(address == mRegisters[29].getDecimalValue()) {
				mMemoryDataSegment[(address + addressOffset - STARTING_ADDRESS_STACK)/4 + STACK_INDEX].setDecimalValue(mRegisters[sourceReg].getDecimalValue());
			}
			else {
				mMemoryDataSegment[(address + addressOffset - STARTING_ADDRESS_DATA)/4].setDecimalValue(mRegisters[sourceReg].getDecimalValue());
			}
		}
		else {
			int address = mySymbolTable.get(instrArguments[1]);
			mMemoryDataSegment[address].setDecimalValue(mRegisters[sourceReg].getDecimalValue());
		}
	}

	/**
	 * Performs the BEQ operation. Takes in three operands: the first register,
	 * the second register, and the label to which to jump to if the contents of
	 * the first register and the second register are equal. Note that the format
	 * of this operation is: BEQ REG1,REG2,LABEL.
	 */
	public void beq() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int firstReg = myRegisterTable.get(instrArguments[0]);
		int secondReg = myRegisterTable.get(instrArguments[1]);
		if(mRegisters[firstReg].equals(mRegisters[secondReg])) {
			int address = mySymbolTable.get(instrArguments[2]);
			mPC.setDecimalValue(STARTING_ADDRESS_TEXT + address * 4 - 4);
		}
	}

	/**
	 * Performs the BNE operation. Takes in three operands: the first register,
	 * the second register, and the label to which to jump to if the contents of
	 * the first register and the second register are not equal. Note that the format
	 * of this operation is: BNE REG1,REG2,LABEL.
	 */
	public void bne() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int firstReg = myRegisterTable.get(instrArguments[0]);
		int secondReg = myRegisterTable.get(instrArguments[1]);
		if(!mRegisters[firstReg].equals(mRegisters[secondReg])) {
			int address = mySymbolTable.get(instrArguments[2]);
			mPC.setDecimalValue(STARTING_ADDRESS_TEXT + address * 4 - 4);
		}
	}

	/**
	 * Performs the J operation. Takes in one operand: the label which we
	 * will jump to. This is an unconditional jump. Note that the format of
	 * this operation is: J LABEL.
	 */
	public void j() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destAddrIndex = mySymbolTable.get(instrArguments[0]);
		mPC.setDecimalValue(STARTING_ADDRESS_TEXT + destAddrIndex * 4 - 4);
	}

	/**
	 * Performs the JR operation. Takes in one operand: the register containing
	 * the address where will we jump to. This is an unconditional jump. Note that
	 * the format of this operation is: JR REG.
	 */
	public void jr() {
		int currentInstrIndex = ((int)mPC.getDecimalValue() - STARTING_ADDRESS_TEXT)/4;
		String[] instrArguments = mMemoryTextSegment[currentInstrIndex].getArguments();
		int destAddrIndex = myRegisterTable.get(instrArguments[0]);
		mPC.setDecimalValue(mRegisters[destAddrIndex].getDecimalValue() - 4);
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
		String varName = myScanner.next().toUpperCase();
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
		String varName = myScanner.next().toUpperCase();
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
	 * Gets the current memory data index.
	 */
	public int getMemoryDataIndex() {
		return memoryDataIndex;
	}

	/**
	 * Gets the current memory text index.
	 */
	public int getMemoryTextIndex() {
		return memoryTextIndex;
	}

	/**
	 * Gets the starting address of the text.
	 */
	public int getStartingTextAddress() {
		return STARTING_ADDRESS_TEXT;
	}

	/**
	 * Gets the starting address of the global pointer.
	 */
	public int getAddressGlobal() {
		return STARTING_ADDRESS_GLOBAL;
	}

	/**
	 * Gets the starting address of the stack pointer.
	 */
	public int getAddressStack() {
		return STARTING_ADDRESS_STACK;
	}

	/**
	 * Sets up the mapping of registers.
	 */
	private void setUpRegisterMapping() {
		myRegisterTable = new TreeMap<>();
		myRegisterTable.put("$ZERO", 0);
		myRegisterTable.put("$AT", 1);
		myRegisterTable.put("$V0", 2);
		myRegisterTable.put("$V1", 3);
		myRegisterTable.put("$A0", 4);
		myRegisterTable.put("$A1", 5);
		myRegisterTable.put("$A2", 6);
		myRegisterTable.put("$A3", 7);
		for(int i = 0; i <= 7; i++) {
			myRegisterTable.put("$T" + i, i + 8);
		}
		for(int i = 0; i <= 7; i++) {
			myRegisterTable.put("$S" + i, i + 16);
		}
		myRegisterTable.put("$T8", 24);
		myRegisterTable.put("$T9", 25);
		myRegisterTable.put("$K0", 26);
		myRegisterTable.put("$K1", 27);
		myRegisterTable.put("$GP", 28);
		myRegisterTable.put("$SP", 29);
		myRegisterTable.put("$FP", 30);
		myRegisterTable.put("$RA", 31);
	}
}
