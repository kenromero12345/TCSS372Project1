/*
 * TCSS 372 Computer Architecture
 * Project 1
 */

package controller;

import java.util.NoSuchElementException;

import model.Computer;
import view.GUI;

/**
 * This is the simulator class that communicates with
 * the front end GUI and the back end MIPS computer.
 *
 * @author Michael Zachary Loria, Ken Romero
 * @version 11.11.19
 */
public class Simulator {
	
	/** The computer used to simulate MIPS. */
	private Computer myComp;
	
	/** The GUI used to display the computer. */
	private GUI myGUI;
	
	/**
	 * Sets up the computer and the GUI.
	 */
	public Simulator() {
		myComp = new Computer();
		myGUI = new GUI(this);
	}
	
	/**
	 * Executes the lines and updates the GUI
	 * accordingly.
	 */
	public void execute() {
		myComp.executeAllLines();
		updateValues();
	}
	
	/**
	 * Assembles the program.
	 * 
	 * @param s The string that contains the instructions.
	 */
	public void assemble(String s) {
		try {
			myComp.assemble(s);
		} catch (NoSuchElementException e) {
			throw e;
		}
	}
	
	/**
	 * Executes one line of instructions.
	 */
	public void executeOneLine() {
		myComp.executeOneLine();
		updateValues();
		if(myComp.programFinished()) {
			throw new NoSuchElementException();
		}
	}
	
	/**
	 * Updates the register values and memory values accordingly.
	 */
	private void updateValues() {
		for (int i = 0; i < 32; i++) {
			myGUI.setRegisterValue(i, "" + myComp.getRegisters()[i].getDecimalValue());
		}
		
		for (int i = 0; i < 100; i++) {
			myGUI.setMemoryValue(i, "" + myComp.getMemoryDataSegment()[i].getDecimalValue());
		}
	}
}
