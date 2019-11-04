package model;

import view.GUIMain;

/*
 * TCSS 372 Machine Organization
 * Project 1
 */

/**
 * 
 * 
 * @author Michael Zachary Loria
 * @version 11.3.19
 */
public class Simulator {

	public static void main(String[] args) {

		Computer comp = new Computer();
		GUIMain gui = new GUIMain();
		comp.execute();
	}
	
	public void simulate() {
		
	}

}
