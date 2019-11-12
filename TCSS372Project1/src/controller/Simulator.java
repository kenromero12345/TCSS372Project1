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
	private Computer comp;
	
	/** The GUI used to display the computer. */
	private GUI gui;
	
	/**
	 * Sets up the computer and the GUI.
	 */
	public Simulator() {
		comp = new Computer();
		gui = new GUI(this);
	}
	
	/**
	 * Executes the lines and updates the GUI
	 * accordingly.
	 */
	public void execute() {
		comp.executeAllLines();
		for (int i = 0; i < 32; i++) {
			gui.setRegisterValue(i, "" + comp.getRegisters()[i].getDecimalValue());
		}
		
		for (int i = 0; i < 100; i++) {
			gui.setMemoryValue(i, "" + comp.getMemoryDataSegment()[i].getDecimalValue());
		}
	}
	
	/**
	 * Assembles the program.
	 * 
	 * @param s The string that contains the instructions.
	 */
	public void assemble(String s) {
		try {
		comp.assemble(s);
		} catch (NoSuchElementException e) {
			throw e;
		}
	}
	
	/**
	 * Executes one line of instructions.
	 */
	public void executeOneLine() {
		comp.executeOneLine();
		for (int i = 0; i < 32; i++) {
			gui.setRegisterValue(i, "" + comp.getRegisters()[i].getDecimalValue());
		}
		
		for (int i = 0; i < 100; i++) {
			gui.setMemoryValue(i, "" + comp.getMemoryDataSegment()[i].getDecimalValue());
		}
	}

}
