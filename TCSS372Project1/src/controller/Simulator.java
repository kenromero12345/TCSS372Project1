package controller;

import java.util.NoSuchElementException;

import model.Computer;
import view.GUI;

/*
 * TCSS 372 Machine Organization
 * Project 1
 */

/**
 * 
 * 
 * @author Michael Zachary Loria
 * @author Ken Gil Romero
 * @version November 4 2019
 */
public class Simulator {
	
	private Computer comp;
	private GUI gui;
	
	public Simulator() {
		comp = new Computer();
		gui = new GUI(this);
	}

//	public static void main(String[] args) {
//
//		
//	//	comp.execute(gui,);
//	}
	
	public void execute() {
		// example on how to use gui
//		gui.setMemoryValue(0, "1");
//		gui.setRegisterValue(0, "1");
		comp.executeAllLines();
		//TODO gui does stuff
	}
	
	public void assemble(String s) {
		try {
		comp.assemble(s);
		} catch (NoSuchElementException e) {
//			gui.showDialog(e.getMessage());
			throw e;
		}
	}
	
	public void executeOneLine() {
		comp.executeOneLine();
		//TODO gui does stuff
	}

}
