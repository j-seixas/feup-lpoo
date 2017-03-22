package dk.gui;

import javax.swing.JFrame;

public class DevWindow {
	
	private JFrame devFrame;
	
	public DevWindow(){
		init();
	}
	
	private void init() {
		// Frame
		devFrame = new JFrame();
		devFrame.setBounds(0, 0, 600, 425);
		devFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Graphics Pane
		//graphics = new GPanel(20, 80, 250, 250);
	}
}
