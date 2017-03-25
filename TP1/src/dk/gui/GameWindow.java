package dk.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dk.logic.Game;
import dk.logic.GameCharacter;
import dk.logic.Level;

import java.awt.Font;

public class GameWindow implements java.io.Serializable {

	private GUI gui;
	private JFrame gameFrame;
	private JTextField ogreNumber;
	private JComboBox<String> guardSelector;
	private GPanel graphics;
	private KeyListener keyListener;
	private JButton btnNewGame, btnExit, btnBack;
	private JButton btnUp, btnDown, btnLeft, btnRight;
	private JButton btnSave, btnLoad;
	private JLabel ogreNumberLabel, guardSelectorLabel, lblInstructions;
	private Game game;
	private ArrayList<Level> customLevels;

	public GameWindow(GUI gui) {
		this.gui = gui;
		init();
	}

	public void directionButtonAction() {
		// Print map
		graphics.requestFocusInWindow();
		graphics.setMap(game.getCurrentMap());
		graphics.repaint();
		graphics.revalidate();
		// Check for game over
		if (game.isOver()) {
			setDirectionButtons(false);
			if (game.getGameStatus() == Game.GameStat.LOSE) {
				lblInstructions.setText("You Lost! Select Ogre Number and Guard Type to Play.");
			} else if (game.getGameStatus() == Game.GameStat.WIN) {
				lblInstructions.setText("You Won! Select Ogre Number and Guard Type to Play.");
			}
		}
	}

	public void setDirectionButtons(boolean b) {
		btnUp.setEnabled(b);
		btnDown.setEnabled(b);
		btnRight.setEnabled(b);
		btnLeft.setEnabled(b);
		graphics.removeKeyListener(keyListener);
	}

	private void labelsInit() {
		ogreNumberLabel = new JLabel("Number of Ogres");
		ogreNumberLabel.setBounds(20, 15, 115, 15);

		guardSelectorLabel = new JLabel("Guard Personality");
		guardSelectorLabel.setBounds(20, 45, 115, 15);

		lblInstructions = new JLabel("Select Ogre Number and Guard Type to Play.");
		lblInstructions.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblInstructions.setBounds(20, 600, 700, 30);
	}

	private void graphicsPaneInit() {
		// Graphics Pane
		graphics = new GPanel(20, 80, 500, 500);
		graphics.setFocusable(true);
		keyListener = new KeyListener() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					game.processInput(GameCharacter.Direction.UP);
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					game.processInput(GameCharacter.Direction.DOWN);
					break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					game.processInput(GameCharacter.Direction.RIGHT);
					break;
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					game.processInput(GameCharacter.Direction.LEFT);
					break;
				default:
					return;
				}
				directionButtonAction();
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		};
	}

	private void selectorsInit() {
		// Selector
		guardSelector = new JComboBox<String>();
		guardSelector.setBounds(150, 45, 100, 20);
		guardSelector.setModel(new DefaultComboBoxModel<String>(new String[] { "Rookie", "Drunken", "Suspicious" }));

		// Ogre Number
		ogreNumber = new JTextField(2);
		ogreNumber.setBounds(150, 15, 30, 20);
	}

	public boolean validGameArgs(int numOgres){
		return (numOgres > Game.MAX_OGRES || numOgres <= 0);
	}
	
	public Game setGame(int numOgres, String guard){
		if (guard == "Rookie")
			return new Game(numOgres, 0);
		else if (guard == "Drunken")
			return new Game(numOgres, 1);
		else if (guard == "Suspicious")
			return new Game(numOgres, 2);
		else return null;
	}
	
	private void newDefaultGame(){
		int numOgres;
		try {
			numOgres = Integer.parseInt(ogreNumber.getText());
		} catch (NumberFormatException e) {
			lblInstructions.setText("Invalid Number of Ogres. Select Ogre Number and Guard Type to Play.");
			return;
		}
		if (validGameArgs(numOgres)) {
			lblInstructions.setText("Invalid Number of Ogres. Select Ogre Number and Guard Type to Play.");
			return;
		}
		String guard = (String) guardSelector.getSelectedItem();
		game = setGame(numOgres, guard);
	}
	
	private void newCustomGame(){
		game = new Game(customLevels);
	}
	
	private void setSelectorsVisible(boolean b){
		this.guardSelectorLabel.setVisible(b);
		this.ogreNumberLabel.setVisible(b);
		this.guardSelector.setVisible(b);
		this.ogreNumber.setVisible(b);
	}
	
	private void newGame(){
		graphics.setVisible(true);
		lblInstructions.setText("Use the Buttons to Move the Hero.");
		setDirectionButtons(true);
		graphics.setMap(game.getCurrentMap());
		graphics.repaint();
		graphics.revalidate();
		graphics.requestFocusInWindow();
		graphics.addKeyListener(keyListener);
	}
	
	
	private void newGameButtonInit() {
		btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(customLevels == null)
					newDefaultGame();
				else newCustomGame();
				newGame();
			}
		});
		btnNewGame.setBounds(735, 50, 100, 25);
	}

	private void exitButtonInit() {
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(735, 550, 100, 25);
	}

	private void upButtonInit() {
		btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.processInput(GameCharacter.Direction.UP);
				directionButtonAction();
			}
		});
		btnUp.setEnabled(false);
		btnUp.setBounds(745, 255, 80, 25);
	}

	private void downButtonInit() {
		btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.processInput(GameCharacter.Direction.DOWN);
				directionButtonAction();
			}
		});
		btnDown.setEnabled(false);
		btnDown.setBounds(745, 345, 80, 25);
	}

	private void leftButtonInit() {
		btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.processInput(GameCharacter.Direction.LEFT);
				directionButtonAction();
			}
		});
		btnLeft.setEnabled(false);
		btnLeft.setBounds(685, 300, 80, 25);
	}

	private void rightButtonInit() {
		btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.processInput(GameCharacter.Direction.RIGHT);
				directionButtonAction();
			}
		});
		btnRight.setEnabled(false);
		btnRight.setBounds(805, 300, 80, 25);
	}

	private void saveButtonInit() {
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FileOutputStream fileOut = new FileOutputStream("save/savedgame.ser");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(game);
					out.close();
					fileOut.close();
				} catch (IOException i) {
					i.printStackTrace();
				}
				directionButtonAction();
			}
		});
		btnSave.setBounds(685, 500, 80, 25);
	}

	private void loadButtonInit() {
		btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FileInputStream fileIn = new FileInputStream("save/savedgame.ser");
					ObjectInputStream in = new ObjectInputStream(fileIn);
					game = (Game) in.readObject();
					in.close();
					fileIn.close();
				} catch (IOException i) {
					i.printStackTrace();
					return;
				} catch (ClassNotFoundException c) {
					System.out.println("Game class not found");
					c.printStackTrace();
					return;
				}
				lblInstructions.setText("Use the Buttons to Move the Hero.");
				setDirectionButtons(true);
				graphics.addKeyListener(keyListener);
				directionButtonAction();
			}
		});
		btnLoad.setBounds(805, 500, 80, 25);
	}

	
	public void backButtonInit(){
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disable();
			}
		});
		btnBack.setBounds(735, 450, 100, 25);
	}
	
	private void buttonsInit() {
		newGameButtonInit();
		upButtonInit();
		downButtonInit();
		leftButtonInit();
		rightButtonInit();
		exitButtonInit();
		saveButtonInit();
		loadButtonInit();
		backButtonInit();
	}

	private void addAllElements() {
		gameFrame.setResizable(false);
		gameFrame.getContentPane().setLayout(null);
		gameFrame.getContentPane().add(ogreNumberLabel);
		gameFrame.getContentPane().add(ogreNumber);
		gameFrame.getContentPane().add(guardSelectorLabel);
		gameFrame.getContentPane().add(guardSelector);
		gameFrame.getContentPane().add(graphics);
		gameFrame.getContentPane().add(btnNewGame);
		gameFrame.getContentPane().add(btnExit);
		gameFrame.getContentPane().add(btnUp);
		gameFrame.getContentPane().add(btnDown);
		gameFrame.getContentPane().add(btnLeft);
		gameFrame.getContentPane().add(btnRight);
		gameFrame.getContentPane().add(btnLoad);
		gameFrame.getContentPane().add(btnSave);
		gameFrame.getContentPane().add(btnBack);
		gameFrame.getContentPane().add(lblInstructions);
	}

	private void initFrame(){
		// Frame
		gameFrame = new JFrame();
		gameFrame.setBounds(0, 0, 1000, 700);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void init() {
		initFrame();
		labelsInit();
		graphicsPaneInit();
		selectorsInit();
		buttonsInit();
		addAllElements();
	}

	public void enable() {
		gameFrame.setVisible(true);
	}

	public void disable() {
		gameFrame.setVisible(false);
		gui.run(GUI.Window.Main);
	}
	
	public void setDefault(){
		customLevels = null;
	}
	
	public void setCustom(ArrayList<Level> custom){
		if(custom != null){
			if(!custom.isEmpty()){
				customLevels = custom;
				setSelectorsVisible(false);
			}
		} else {
			customLevels = null;
			setSelectorsVisible(true);
		}
		graphics.setVisible(false);
	}
}