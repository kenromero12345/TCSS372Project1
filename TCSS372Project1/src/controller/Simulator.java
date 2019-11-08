package controller;

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
		gui.setMemoryValue(0, "1");
		gui.setRegisterValue(0, "1");
	}
	
	public void assemble() {
		
	}

}
