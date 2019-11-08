package application;
import java.awt.EventQueue;

import controller.Simulator;
import model.Computer;
import view.GUI;

public class Main {
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
