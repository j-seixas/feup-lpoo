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
import java.awt.ComponentOrientation;
import java.awt.Insets;

public class Window {

	private JFrame frame;
	private JTextField ogreNumber;
	private JTextPane mapPane;

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
		//Get Screen Resolution
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		
		//Frame
		frame = new JFrame();
		frame.setBounds(0, 0, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Labels
		JLabel ogreNumberLabel = new JLabel("Number of Ogres");
		ogreNumberLabel.setBounds(20, 15, 100, 15);
		JLabel guardSelectorLabel = new JLabel("Guard personality");
		guardSelectorLabel.setBounds(20, 45, 100, 15);		
		
		//Selector
		JComboBox guardSelector = new JComboBox();
		guardSelector.setBounds(135, 45, 100, 20);
		guardSelector.setModel(new DefaultComboBoxModel(new String[] {"Rookie", "Drunken", "Suspicious"}));
		
		//Ogre Number
		ogreNumber = new JTextField();
		ogreNumber.setBounds(135, 15, 30, 20);

		//Map Pane
		mapPane = new JTextPane();
		mapPane.setBounds(20, 80, 255, 255);
		mapPane.setEditable(false);
		
		//Buttons
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(385, 30, 100, 25);
		
		JButton btnUp = new JButton("Up");
		btnUp.setBounds(395, 130, 80, 25);
		
		JButton btnDown = new JButton("Down");
		btnDown.setBounds(395, 220, 80, 25);
		
		JButton btnRight = new JButton("Right");
		btnRight.setBounds(455, 175, 80, 25);
		
		JButton btnLeft = new JButton("Left");
		btnLeft.setBounds(335, 175, 80, 25);
		
		//Add elements to the frame
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
	}
}
