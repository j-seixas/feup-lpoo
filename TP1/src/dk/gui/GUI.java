package dk.gui;

import java.awt.EventQueue;

public class GUI {
 
	public enum Window { PlayDefault, PlayCustom, Developer, Main }	
	
	private GameWindow gameWindow;
	private DevWindow devWindow;
	private MainWindow mainWindow;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI gui = new GUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		gameWindow = new GameWindow(this);
		devWindow = new DevWindow(this);
		mainWindow = new MainWindow(this);
		mainWindow.enable();
	}

	public void run(Window button){
		switch(button){
			case PlayDefault: 
				gameWindow.enable(); 
				gameWindow.setCustom(null); 
				return;
			case PlayCustom:
				if(devWindow.getLevels() == null || devWindow.getLevels().isEmpty()){
					mainWindow.enable();
					mainWindow.setInfoLabel("There are no custom levels!");
					return;
				}
				gameWindow.enable(); 
				gameWindow.setCustom(devWindow.getLevels()); 
				return;
			case Developer: 
				devWindow.enable(); 
				return;
			case Main: 
				mainWindow.enable(); 
				return;
		}
	}
}