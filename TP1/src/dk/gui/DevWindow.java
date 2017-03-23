package dk.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import dk.logic.Coordinates;
import dk.logic.Door;
import dk.logic.DrunkenG;
import dk.logic.Guardian;
import dk.logic.Hero;
import dk.logic.Level;
import dk.logic.Ogre;
import dk.logic.RookieG;
import dk.logic.SuspiciousG;

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
	private String element;
	private MouseListener mouseListener;

	public DevWindow() {
		init();
	}

	private void handleAddition(int cellX, int cellY) {
		if(element == null)
			return;
		switch (element) {
		case "Hero": {
			Hero hero = new Hero(cellX, cellY);
			if (level.canAdd(hero))
				level.setHero(hero);
			break;
		}
		case "Ogre": {
			Ogre ogre = new Ogre(cellX, cellY);
			if (level.canAdd(ogre))
				level.addOgre(ogre);
			break;
		}
		case "Drunk Guardian": {
			Guardian drunkG = new DrunkenG(cellX, cellY);
			if (level.canAdd(drunkG))
				level.addGuardian(drunkG);
			break;
		}
		case "Rookie Guardian": {
			Guardian rookieG = new RookieG(cellX, cellY);
			if (level.canAdd(rookieG))
				level.addGuardian(rookieG);
			break;
		}
		case "Suspicious Guardian": {
			Guardian suspiciousG = new SuspiciousG(cellX, cellY);
			if (level.canAdd(suspiciousG))
				level.addGuardian(suspiciousG);
			break;
		}
		case "Door": {
			Door door = new Door(cellX, cellY);
			if (level.canAddElement(cellX, cellY))
				level.addDoor(door);
			break;
		}
		case "Key": {
			Coordinates key = new Coordinates(cellX, cellY);
			if (level.canAddElement(cellX, cellY))
				level.setKey(key);
			break;
		}
		case "Wall": {
			if (level.canAddElement(cellX, cellY))
				level.addElement('X', cellX, cellY);
			break;
		}
		case "Rock": {
			if (level.canAddElement(cellX, cellY))
				level.addElement('I', cellX, cellY);
			break;
		}
		default:
			return;
		}
	}

	private void handleDeletion(int cellX, int cellY) {
		if(element == null)
			return;
		switch (element) {
		case "Hero": {
			level.removeHero();
			break;
		}
		case "Ogre": {
			Ogre ogre = new Ogre(cellX, cellY);
			level.removeOgre(ogre);
			break;
		}
		case "Drunk Guardian": {
			Guardian drunkG = new DrunkenG(cellX, cellY);
			level.removeGuardian(drunkG);
			break;
		}
		case "Rookie Guardian": {
			Guardian rookieG = new RookieG(cellX, cellY);
			level.removeGuardian(rookieG);
			break;
		}
		case "Suspicious Guardian": {
			Guardian suspiciousG = new SuspiciousG(cellX, cellY);
			level.removeGuardian(suspiciousG);
			break;
		}
		case "Door": {
			Door door = new Door(cellX, cellY);
			level.removeDoor(door);
			break;
		}
		case "Key": {
			level.removeKey();
			break;
		}
		case "Wall":
		case "Rock":{
			level.removeElement(cellX, cellY);
			break;
		}
		default:
			return;
		}
	}

	private void init() {
		// TODO SETBOUNDS
		// TODO LABELS

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
				if (button == MouseEvent.BUTTON1) {
					handleAddition(cellX, cellY);
				} else if (button == MouseEvent.BUTTON3) {
					handleDeletion(cellX, cellY);
				}
				level.updateMap();
				graphics.setMap(level.getMap());
				graphics.repaint();
				graphics.revalidate();
				graphics.requestFocusInWindow();
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

		// Dimensions
		mapX = new JTextField(2);
		mapX.setBounds(0, 0, 30, 20);
		mapY = new JTextField(2);
		mapY.setBounds(60, 0, 30, 20);

		// Buttons
		startDev = new JButton("New Level");
		startDev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				level = new Level();
				//TODO ADD TRY BLOCK
				int mapHeight = Integer.parseInt(mapY.getText());
				int mapWidth = Integer.parseInt(mapX.getText());
				char defaultMap[][] = graphics.setDefaultMap(mapWidth, mapHeight);
				level.setMap(defaultMap);
				graphics.repaint();
				graphics.revalidate();
				graphics.removeMouseListener(mouseListener);
				graphics.addMouseListener(mouseListener);
				graphics.requestFocusInWindow();
			}
		});
		startDev.setBounds(385, 25, 100, 25);

		btnHero = new JButton("Hero");
		btnHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnHero.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnHero.setBounds(385, 50, 100, 25);

		btnDoor = new JButton("Door");
		btnDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnDoor.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnDoor.setBounds(385, 75, 100, 25);

		btnOgre = new JButton("Ogre");
		btnOgre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnOgre.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnOgre.setBounds(385, 100, 100, 25);

		btnDrunkG = new JButton("Drunk Guardian");
		btnDrunkG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnDrunkG.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnDrunkG.setBounds(385, 125, 100, 25);

		btnSuspiciousG = new JButton("Suspicious Guardian");
		btnSuspiciousG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnSuspiciousG.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnSuspiciousG.setBounds(385, 150, 100, 25);

		btnRookieG = new JButton("Rookie Guardian");
		btnRookieG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnRookieG.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnRookieG.setBounds(385, 175, 100, 25);

		btnWall = new JButton("Wall");
		btnWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnWall.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnWall.setBounds(385, 200, 100, 25);

		btnRock = new JButton("Rock");
		btnRock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnRock.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnRock.setBounds(385, 225, 100, 25);

		btnKey = new JButton("Key");
		btnKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnKey.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnKey.setBounds(385, 250, 100, 25);

		// Add elements to frame
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

	public void disable() {
		devFrame.setVisible(false);
	}
}
