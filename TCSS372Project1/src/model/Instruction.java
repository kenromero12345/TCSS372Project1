/*
 * TCSS 372 Computer Architecture
 * Project 1
 */

package model;

import java.util.Arrays;
import java.util.Scanner;

/**
 * This is the class that handles a single instruction
 * in assembly language.
 * 
 * @author Michael Zachary Loria, Ken Romero
 * @version 11.11.19
 */
public class Instruction {
	
	/**
	 * The string representing the instruction. 
	 */
	private String myInstruction;
	
	/**
	 * The associated operation of the instruction.
	 */
	private String myOperation; 
	
	/**
	 * The arguments associated with the instruction.
	 */
	private String[] myArgs;
	
	/**
	 * The number of arguments in the instruction.
	 */
	private int myNumArgs;
	
	/**
	 * Constructor that sets fields to default
	 * values.
	 */
	public Instruction() {
		myInstruction = "";
		myOperation = "";
		myArgs = null;
		myNumArgs = 0;
	}
	
	/**
	 * Constructor that sets the instruction based
	 * on the provided string.
	 * 
	 * @param theInstruction The instruction string used for the instruction.
	 */
	public Instruction(String theInstruction) {
		setInstructionString(theInstruction);
	}
	
	/**
	 * Gets the instruction string.
	 * 
	 * @return The string representing the instruction.
	 */
	public String getInstructionString() {
		return myInstruction;
	}
	
	/**
	 * Sets the instruction string.
	 * 
	 * @param theInstruction The string that is used for this instruction.
	 */
	public void setInstructionString(String theInstruction) {
		myInstruction = theInstruction.toUpperCase();
		Scanner scanOperands = new Scanner(myInstruction);
		myOperation = scanOperands.next();
		myArgs = scanOperands.next().split(",");
		myNumArgs = myArgs.length;
		scanOperands.close();
	}
	
	/**
	 * Gets the operation associated with the instruction.
	 * 
	 * @return The operation.
	 */
	public String getOperation() {
		return myOperation;
	}
	
	/**
	 * Gets the number of arguments in the instruction.
	 * 
	 * @return The number of arguments.
	 */
	public int getNumberArguments() {
		return myNumArgs;
	}
	
	/**
	 * Gets the arguments of the instruction.
	 * 
	 * @return The array of arguments for this instruction.
	 */
	public String[] getArguments() {
		return myArgs;
	}
	
	@Override
	public String toString() {
		return "Operation: " + myOperation + ", Arguments: " + Arrays.deepToString(myArgs);
	}
	
	@Override
	public boolean equals(Object theOther) {
		Instruction temp = (Instruction)theOther;
		return toString().equals(temp.toString());
	}
}
