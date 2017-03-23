package dk.gui;

import java.awt.EventQueue;

public class GUI {
 
	private GameWindow gameWindow;
	private DevWindow devWindow;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					//window.gameWindow.enable();
					window.devWindow.enable();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		gameWindow = new GameWindow();
		devWindow = new DevWindow();
	}
}