package dk.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import dk.logic.Coordinates;
import dk.logic.Door;
import dk.logic.Guardian;
import dk.logic.Hero;
import dk.logic.Level;
import dk.logic.Ogre;
import dk.logic.RookieG;

public class DevWindow {

	private GUI gui;
	private JFrame devFrame;
	private GPanel graphics;
	private JTextField mapX, mapY;
	private JButton startDev, btnFinished, btnBack;
	private JButton btnHero, btnDoor, btnOgre, btnGuardian, btnWall, btnRock, btnKey;
	private Level level;
	private ArrayList<Level> levels;
	private String element;
	private MouseListener mouseListener;

	public DevWindow(GUI gui) {
		this.gui = gui;
		levels = new ArrayList<Level>();
		init();
	}

	private void handleAddition(int cellX, int cellY) {
		if (element == null)
			return;
		switch (element) {
		case "Hero":
			Hero hero = new Hero(cellX, cellY);
			if (level.canAdd(hero))
				level.setHero(hero);
			break;
		case "Ogre":
			Ogre ogre = new Ogre(cellX, cellY);
			if (level.canAdd(ogre))
				level.addOgre(ogre);
			break;
		case "Guardian":
			Guardian rookieG = new RookieG(cellX, cellY);
			if (level.canAdd(rookieG))
				level.addGuardian(rookieG);
			break;
		case "Door":
			Door door = new Door(cellX, cellY);
			if (level.canAddElement(cellX, cellY))
				level.addDoor(door);
			break;
		case "Key":
			Coordinates key = new Coordinates(cellX, cellY);
			if (level.canAddElement(cellX, cellY))
				level.setKey(key);
			break;
		case "Wall":
			if (level.canAddElement(cellX, cellY))
				level.addElement('X', cellX, cellY);
			break;
		case "Rock":
			if (level.canAddElement(cellX, cellY))
				level.addElement('I', cellX, cellY);
			break;
		default:
			return;
		}
	}

	private void handleDeletion(int cellX, int cellY) {
		if (element == null)
			return;
		switch (element) {
		case "Hero":
			level.removeHero();
			break;
		case "Ogre":
			Ogre ogre = new Ogre(cellX, cellY);
			level.removeOgre(ogre);
			break;
		case "Guardian":
			Guardian rookieG = new RookieG(cellX, cellY);
			level.removeGuardian(rookieG);
			break;
		case "Door":
			Door door = new Door(cellX, cellY);
			level.removeDoor(door);
			break;
		case "Key":
			level.removeKey();
			break;
		case "Wall":
		case "Rock":
			level.removeElement(cellX, cellY);
			break;
		default:
			return;
		}
	}

	private void resetLevel() {
		level = new Level();
		try {
			int mapHeight = Integer.parseInt(mapY.getText());
			int mapWidth = Integer.parseInt(mapX.getText());
			char defaultMap[][] = graphics.setDefaultMap(mapWidth, mapHeight);
			level.setMap(defaultMap);
		} catch (NumberFormatException e) {
			return;
		}
		graphics.repaint();
		graphics.revalidate();
		graphics.removeMouseListener(mouseListener);
		graphics.addMouseListener(mouseListener);
		graphics.requestFocusInWindow();
	}

	private void initFrame() {
		devFrame = new JFrame();
		devFrame.setBounds(0, 0, 600, 425);
		devFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initGraphics() {
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
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}};
	}

	public void initDimensions(){
			mapX = new JTextField(2);
			mapX.setBounds(0, 0, 30, 20);
			mapY = new JTextField(2);
			mapY.setBounds(60, 0, 30, 20);
	}
	
	
	private void initButtons(){
		initDevButton();
		initHeroButton();
		initDoorButton();
		initOgreButton();
		initGuardianButton();
		initWallButton();
		initRockButton();
		initKeyButton();
		initFinishedButton();
		initBackButton();
	}
	
	private void initBackButton() {
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disable();
			}
		});
		btnBack.setBounds(385, 300, 100, 25);		
	}

	private void initFinishedButton() {
		btnFinished = new JButton("Finished Editting");
		btnFinished.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (level.isValid()) {
					levels.add(level);
					resetLevel();
				}
			}
		});
		btnFinished.setBounds(385, 275, 100, 25);		
	}

	private void initKeyButton() {
		btnKey = new JButton("Key");
		btnKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnKey.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnKey.setBounds(385, 250, 100, 25);		
	}

	private void initRockButton() {
		btnRock = new JButton("Rock");
		btnRock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnRock.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnRock.setBounds(385, 225, 100, 25);		
	}

	private void initWallButton() {
		btnWall = new JButton("Wall");
		btnWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnWall.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnWall.setBounds(385, 200, 100, 25);		
	}

	private void initGuardianButton() {
		btnGuardian = new JButton("Guardian");
		btnGuardian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnGuardian.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnGuardian.setBounds(385, 175, 100, 25);		
	}

	private void initOgreButton() {
		btnOgre = new JButton("Ogre");
		btnOgre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnOgre.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnOgre.setBounds(385, 100, 100, 25);		
	}

	private void initDoorButton() {
		btnDoor = new JButton("Door");
		btnDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnDoor.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnDoor.setBounds(385, 75, 100, 25);		
	}

	private void initHeroButton() {
		btnHero = new JButton("Hero");
		btnHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				element = new String(btnHero.getText());
				graphics.requestFocusInWindow();
			}
		});
		btnHero.setBounds(385, 50, 100, 25);		
	}

	private void initDevButton() {
		startDev = new JButton("New Level");
		startDev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				resetLevel();
			}
		});
		startDev.setBounds(385, 25, 100, 25);
	}

	private void init() {
		// TODO SETBOUNDS
		// TODO LABELS
		initFrame();
		initGraphics();
		initDimensions();
		initButtons();
		addElementsToFrame();
	}
	
	public void addElementsToFrame(){
		devFrame.setResizable(false);
		devFrame.getContentPane().setLayout(null);
		devFrame.getContentPane().add(mapX);
		devFrame.getContentPane().add(mapY);
		devFrame.getContentPane().add(graphics);
		devFrame.getContentPane().add(startDev);
		devFrame.getContentPane().add(btnDoor);
		devFrame.getContentPane().add(btnHero);
		devFrame.getContentPane().add(btnKey);
		devFrame.getContentPane().add(btnOgre);
		devFrame.getContentPane().add(btnRock);
		devFrame.getContentPane().add(btnGuardian);
		devFrame.getContentPane().add(btnWall);
		devFrame.getContentPane().add(btnFinished);
		devFrame.getContentPane().add(btnBack);
	}
	
	public void enable() {
		devFrame.setVisible(true);
	}

	public void disable() {
		devFrame.setVisible(false);
		gui.run(GUI.Window.Main);
	}

	public ArrayList<Level> getLevels() {
		return levels;
	}
}
