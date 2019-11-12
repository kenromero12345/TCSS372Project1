/*
 * TCSS 372 Computer Architecture
 * Project 1
 */

package application;

import java.awt.EventQueue;

import controller.Simulator;

/**
 * This is the main class that will run the application
 * of the MIPS simulator.
 *
 * @author Michael Zachary Loria, Ken Romero
 * @version 11.12.19
 */
public class Driver {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Simulator();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
