package dk.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dk.logic.Game;
import dk.logic.GameCharacter;
import java.awt.Font;

public class GameWindow {

	private JFrame gameFrame;
	private JTextField ogreNumber;
	private JComboBox<String> guardSelector;
	private GPanel graphics;
	private KeyListener keyListener;
	private JButton btnNewGame, btnExit;
	private JButton btnUp, btnDown, btnLeft, btnRight;
	private JLabel lblInstructions;
	private Game game;
	
	public GameWindow() {
		init();
	}
	
	public void directionButtonAction() {
		//Print map
		graphics.requestFocusInWindow();
		graphics.setMap(game.getCurrentMap());
		graphics.repaint();
		graphics.revalidate();
		// Check for game over
		if (game.isOver()) {
			disableDirectionButtons();
			if (game.getGameStatus() == Game.GameStat.LOSE) {
				lblInstructions.setText("You Lost! Select Ogre Number and Guard Type to Play.");
			} else if (game.getGameStatus() == Game.GameStat.WIN) {
				lblInstructions.setText("You Won! Select Ogre Number and Guard Type to Play.");
			}
		}	
	}

	public void disableDirectionButtons() {
		btnUp.setEnabled(false);
		btnDown.setEnabled(false);
		btnRight.setEnabled(false);
		btnLeft.setEnabled(false);
		graphics.removeKeyListener(keyListener);
	}

	private void init() {
		// Frame
		gameFrame = new JFrame();
		gameFrame.setBounds(0, 0, 1000, 700);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Labels
		JLabel ogreNumberLabel = new JLabel("Number of Ogres");
		ogreNumberLabel.setBounds(20, 15, 115, 15);
		JLabel guardSelectorLabel = new JLabel("Guard Personality");
		guardSelectorLabel.setBounds(20, 45, 115, 15);

		// Selector
		guardSelector = new JComboBox<String>();
		guardSelector.setBounds(150, 45, 100, 20);
		guardSelector.setModel(new DefaultComboBoxModel<String>(new String[] { "Rookie", "Drunken", "Suspicious" }));

		// Ogre Number
		ogreNumber = new JTextField(2);
		ogreNumber.setBounds(150, 15, 30, 20);

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

		// Buttons
		btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int numOgres;
				try {
					numOgres = Integer.parseInt(ogreNumber.getText());
				} catch (NumberFormatException e) {
					lblInstructions.setText("Invalid Number of Ogres. Select Ogre Number and Guard Type to Play.");
					return;
				}
				if (numOgres > 5 || numOgres <= 0) {
					lblInstructions.setText("Invalid Number of Ogres. Select Ogre Number and Guard Type to Play.");
					return;
				}

				String guard = (String) guardSelector.getSelectedItem();
				if (guard == "Rookie")
					game = new Game(numOgres, 0);
				else if (guard == "Drunken")
					game = new Game(numOgres, 1);
				else if (guard == "Suspicious")
					game = new Game(numOgres, 2);
				lblInstructions.setText("Use the Buttons to Move the Hero.");
				btnUp.setEnabled(true);
				btnDown.setEnabled(true);
				btnLeft.setEnabled(true);
				btnRight.setEnabled(true);
				graphics.setMap(game.getCurrentMap());
				graphics.repaint();
				graphics.revalidate();
				graphics.requestFocusInWindow();
				graphics.removeKeyListener(keyListener);
				graphics.addKeyListener(keyListener);
			}
		});
		btnNewGame.setBounds(735, 50, 100, 25);

		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(735, 550, 100, 25);

		btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.processInput(GameCharacter.Direction.UP);
				directionButtonAction();
			}
		});
		btnUp.setEnabled(false);
		btnUp.setBounds(745, 255, 80, 25);

		btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.processInput(GameCharacter.Direction.DOWN);
				directionButtonAction();
			}
		});
		btnDown.setEnabled(false);
		btnDown.setBounds(745, 345, 80, 25);

		btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.processInput(GameCharacter.Direction.RIGHT);
				directionButtonAction();
			}
		});
		btnRight.setEnabled(false);
		btnRight.setBounds(805, 300, 80, 25);

		btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.processInput(GameCharacter.Direction.LEFT);
				directionButtonAction();
			}
		});
		btnLeft.setEnabled(false);
		btnLeft.setBounds(685, 300, 80, 25);

		lblInstructions = new JLabel("Select Ogre Number and Guard Type to Play.");
		lblInstructions.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblInstructions.setBounds(20, 600, 700, 30);
		

		// Add elements to the frame
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
		gameFrame.getContentPane().add(lblInstructions);
	}

	public void enable(){
		gameFrame.setVisible(true);
	}

	public void disable(){
		gameFrame.setVisible(false);
	}
}