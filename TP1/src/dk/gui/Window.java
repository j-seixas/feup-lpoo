package dk.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import dk.logic.Game;
import dk.logic.GameCharacter;
import dk.logic.Level;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Window {

	private JFrame frame;
	private JTextField ogreNumber;
	private JComboBox<String> guardSelector;
	//private JTextPane mapPane;
	private GPanel graphics;
	private JButton btnNewGame, btnExit;
	private JButton btnUp, btnDown, btnLeft, btnRight;
	private JLabel lblInstructions;
	private Game game; 

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void directionButtonAction(){
		// Check for final level
		Level currentLevel = game.getCurrentLevel();
		if (currentLevel == null) {
			disableDirectionButtons();
		} else {
			//Print the map
			//mapPane.setText(game.getStringMap());
			graphics.setMap(game.getMap());
			graphics.repaint();
			graphics.revalidate();
		}
		// Check status
		if (game.getGameStatus() == Game.GameStat.LOSE){
			disableDirectionButtons();
			//mapPane.setText(mapPane.getText() + "You Lost!");
			lblInstructions.setText("You Lost! Select Ogre Number and Guard Type to Play.");
		}
		else if(game.getGameStatus() == Game.GameStat.WIN){
			disableDirectionButtons();
			//mapPane.setText(mapPane.getText() + "You Won!");
			lblInstructions.setText("You Won! Select Ogre Number and Guard Type to Play.");
		}
	}
	
	private void disableDirectionButtons() {
		btnUp.setEnabled(false);
		btnDown.setEnabled(false);
		btnRight.setEnabled(false);
		btnLeft.setEnabled(false);
	}

	public Window() {
		initialize();
	}

	private void initialize() {
		// Get Screen Resolution
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();

		// Frame
		frame = new JFrame();
		frame.setBounds(0, 0, 600, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

		//Graphics Pane
		graphics = new GPanel(20, 80, 250, 250);
		
		// Map Pane
		//mapPane = new JTextPane();
		//mapPane.setFont(new Font("Courier New", Font.BOLD, 20));
		//mapPane.setBounds(20, 80, 255, 255);
		//mapPane.setEditable(false);

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
				graphics.setMap(game.getMap());
				graphics.repaint();
				graphics.revalidate();
				//mapPane.setText(game.getStringMap());
			}
		});
		btnNewGame.setBounds(385, 50, 100, 25);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(385, 300, 100, 25);
		
		btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.processInput(GameCharacter.Direction.UP);
				directionButtonAction();
			}
		});
		btnUp.setEnabled(false);
		btnUp.setBounds(395, 130, 80, 25);

		btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.processInput(GameCharacter.Direction.DOWN);
				directionButtonAction();
			}
		});
		btnDown.setEnabled(false);
		btnDown.setBounds(395, 220, 80, 25);

		btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.processInput(GameCharacter.Direction.RIGHT);
				directionButtonAction();
			}
		});
		btnRight.setEnabled(false);
		btnRight.setBounds(455, 175, 80, 25);

		btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.processInput(GameCharacter.Direction.LEFT);
				directionButtonAction();
			}
		});
		btnLeft.setEnabled(false);
		btnLeft.setBounds(335, 175, 80, 25);

		lblInstructions = new JLabel("Select Ogre Number and Guard Type to Play.");
		lblInstructions.setBounds(20, 350, 400, 15);

		// Add elements to the frame
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(ogreNumberLabel);
		frame.getContentPane().add(ogreNumber);
		frame.getContentPane().add(guardSelectorLabel);
		frame.getContentPane().add(guardSelector);
		frame.getContentPane().add(graphics);
		//frame.getContentPane().add(mapPane);
		frame.getContentPane().add(btnNewGame);
		frame.getContentPane().add(btnExit);
		frame.getContentPane().add(btnUp);
		frame.getContentPane().add(btnDown);
		frame.getContentPane().add(btnLeft);
		frame.getContentPane().add(btnRight);
		frame.getContentPane().add(lblInstructions);
	}
}