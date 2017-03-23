package dk.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import dk.logic.Level;

public class DevWindow {
	
	private JFrame devFrame;
	private GPanel graphics;
	private JTextField mapX, mapY; 
	private JButton startDev;
	private JButton btnHero;
	private JButton btnDoor;
	private JButton btnOgre;
	private JButton btnDrunkG;
	private JButton btnSuspiciousG;
	private JButton btnRookieG;
	private JButton btnWall;
	private JButton btnRock;
	private JButton btnKey;
	private Level level;
	private MouseListener mouseListener;

	public DevWindow(){
		init();
	}
	
	private void init() {
		//TODO SETBOUNDS
		//TODO LABELS
		
		// Frame
		devFrame = new JFrame();
		devFrame.setBounds(0, 0, 600, 425);
		devFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Graphics Pane
		graphics = new GPanel(20, 80, 250, 250);
		graphics.setFocusable(true);
		mouseListener = new MouseListener() {
 
			@Override
			public void mouseClicked(MouseEvent e) {
				int cellX = graphics.getCellX(e.getX());
				int cellY = graphics.getCellY(e.getY());
				int button = e.getButton();
				if(button == MouseEvent.BUTTON1){
					//add
				}
				else if(button == MouseEvent.BUTTON2){
					//remove
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}
			
		};
		
		//Dimensions
		mapX = new JTextField(2);
		mapX.setBounds(0, 0, 30, 20);
		mapY = new JTextField(2);
		mapY.setBounds(60, 0, 30, 20);
		
		//Buttons
		startDev = new JButton("New Level");
		startDev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				level = new Level();
				
				int mapHeight = Integer.parseInt(mapY.getText());
				int mapWidth = Integer.parseInt(mapX.getText());
				char defaultMap[][] = graphics.setDefaultMap(mapWidth, mapHeight);
				level.setMap(defaultMap);	
				graphics.repaint();
				graphics.revalidate();
				graphics.removeMouseListener(mouseListener);
				graphics.addMouseListener(mouseListener);
			}});
		startDev.setBounds(385, 25, 100, 25);
		
		btnHero = new JButton("Hero");
		btnHero.setBounds(385, 50, 100, 25);
		
		btnDoor = new JButton("Door");
		btnDoor.setBounds(385, 75, 100, 25);
		
		btnOgre = new JButton("Ogre");
		btnOgre.setBounds(385, 100, 100, 25);

		btnDrunkG = new JButton("Drunk Guardian");
		btnDrunkG.setBounds(385, 125, 100, 25);

		btnSuspiciousG = new JButton("Suspicious Guardian");
		btnSuspiciousG.setBounds(385, 150, 100, 25);

		btnRookieG = new JButton("Rookie Guardian");
		btnRookieG.setBounds(385, 175, 100, 25);

		btnWall = new JButton("Wall");
		btnWall.setBounds(385, 200, 100, 25);

		btnRock = new JButton("Rock");
		btnRock.setBounds(385, 225, 100, 25);

		btnKey = new JButton("Key");
		btnKey.setBounds(385, 250, 100, 25);
		
		//Add elements to frame
		devFrame.setResizable(false);
		devFrame.getContentPane().setLayout(null);
		devFrame.getContentPane().add(mapX);
		devFrame.getContentPane().add(mapY);
		devFrame.getContentPane().add(graphics);
		devFrame.getContentPane().add(startDev);
		devFrame.getContentPane().add(btnDoor);
		devFrame.getContentPane().add(btnDrunkG);
		devFrame.getContentPane().add(btnHero);
		devFrame.getContentPane().add(btnKey);
		devFrame.getContentPane().add(btnOgre);
		devFrame.getContentPane().add(btnRock);
		devFrame.getContentPane().add(btnRookieG);
		devFrame.getContentPane().add(btnSuspiciousG);
		devFrame.getContentPane().add(btnWall);

	}

	public void enable() {
		devFrame.setVisible(true);
	}
	
	public void disable(){
		devFrame.setVisible(false);
	}
}
