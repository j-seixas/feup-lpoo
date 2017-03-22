package dk.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class DevWindow {
	
	private JFrame devFrame;
	private GPanel graphics;
	private JTextField mapX, mapY; 
	private JButton startDev;

	
	public DevWindow(){
		init();
	}
	
	private void init() {
		// Frame
		devFrame = new JFrame();
		devFrame.setBounds(0, 0, 600, 425);
		devFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Graphics Pane
		graphics = new GPanel(20, 80, 250, 250);
		
		//Dimensions
		
		
		//Buttons
		startDev = new JButton();
		startDev.setBounds(0, 0, 100, 25); //TODO
		
		
	}
}
