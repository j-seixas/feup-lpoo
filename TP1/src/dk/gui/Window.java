package dk.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.sun.org.apache.xpath.internal.operations.Number;

import dk.logic.Game;

import java.awt.ComponentOrientation;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Window {

	private JFrame frame;
	private JTextField ogreNumber;
	private JComboBox<String> guardSelector;
	private JTextPane mapPane;
	private JButton btnNewGame;
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

	public Window() {
		initialize();
	}

	private void initialize() {
		// Get Screen Resolution
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		// Frame
		frame = new JFrame();
		frame.setBounds(0, 0, 600, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Labels
		JLabel ogreNumberLabel = new JLabel("Number of Ogres");
		ogreNumberLabel.setBounds(20, 15, 100, 15);
		JLabel guardSelectorLabel = new JLabel("Guard personality");
		guardSelectorLabel.setBounds(20, 45, 100, 15);

		// Selector
		guardSelector = new JComboBox<String>();
		guardSelector.setBounds(135, 45, 100, 20);
		guardSelector.setModel(new DefaultComboBoxModel<String>(new String[] { "Rookie", "Drunken", "Suspicious" }));

		// Ogre Number
		ogreNumber = new JTextField(2);
		ogreNumber.setBounds(135, 15, 30, 20);

		// Map Pane
		mapPane = new JTextPane();
		mapPane.setBounds(20, 80, 255, 255);
		mapPane.setEditable(false);

		// Buttons
		btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int numOgres;
				try {
					numOgres = Integer.parseInt(ogreNumber.getText());
				} catch (NumberFormatException e) {
					lblInstructions.setText("Invalid number of Ogres");
					return;
				}
				if (numOgres > 5 || numOgres <= 0) {
					lblInstructions.setText("Invalid number of Ogres");
					return;
				}

				String guard = (String) guardSelector.getSelectedItem();
				if (guard == "Rookie")
					game = new Game(numOgres, 0);
				else if (guard == "Drunken")
					game = new Game(numOgres, 1);
				else if (guard == "Suspicious")
					game = new Game(numOgres, 2);
				btnUp.setEnabled(true);
				btnDown.setEnabled(true);
				btnLeft.setEnabled(true);
				btnRight.setEnabled(true);
			}
		});
		btnNewGame.setBounds(385, 30, 100, 25);

		btnUp = new JButton("Up");
		btnUp.setEnabled(false);
		btnUp.setBounds(395, 130, 80, 25);

		btnDown = new JButton("Down");
		btnDown.setEnabled(false);
		btnDown.setBounds(395, 220, 80, 25);

		btnRight = new JButton("Right");
		btnRight.setEnabled(false);
		btnRight.setBounds(455, 175, 80, 25);

		btnLeft = new JButton("Left");
		btnLeft.setEnabled(false);
		btnLeft.setBounds(335, 175, 80, 25);

		lblInstructions = new JLabel("Select Ogres and Guard Type to Play");
		lblInstructions.setBounds(20, 349, 278, 16);

		// Add elements to the frame
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(ogreNumberLabel);
		frame.getContentPane().add(ogreNumber);
		frame.getContentPane().add(guardSelectorLabel);
		frame.getContentPane().add(guardSelector); 
		frame.getContentPane().add(mapPane);
		frame.getContentPane().add(btnNewGame);
		frame.getContentPane().add(btnUp);
		frame.getContentPane().add(btnDown);
		frame.getContentPane().add(btnLeft);
		frame.getContentPane().add(btnRight);
		frame.getContentPane().add(lblInstructions);
	}
}
