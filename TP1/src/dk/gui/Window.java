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

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Get Screen Resolution
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		
		frame = new JFrame();
		frame.setBounds(0, 0, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel guardSelectorLabel = new JLabel("Guard personality");
		guardSelectorLabel.setBounds(12, 41, 100, 16);
		
		JComboBox guardSelector = new JComboBox();
		guardSelector.setBounds(133, 38, 93, 22);
		guardSelector.setModel(new DefaultComboBoxModel(new String[] {"Rookie", "Drunken", "Suspicious"}));
		
		
		//GET THE GUARD OPTION
		//String x = guardSelector.getSelectedItem().toString();

		
		JLabel ogreNumberLabel = new JLabel("Number of Ogres:");
		ogreNumberLabel.setBounds(12, 16, 103, 16);
		
		ogreNumber = new JTextField();
		ogreNumber.setBounds(133, 13, 31, 22);
		ogreNumber.setColumns(10);
		
		mapPane = new JTextPane();
		mapPane.setBounds(12, 84, 324, 256);
		mapPane.setEditable(false);
		
		//mapPane.setText(x);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(412, 37, 95, 25);
		btnNewGame.setHorizontalTextPosition(SwingConstants.CENTER);
		
		JButton btnUp = new JButton("Up");
		btnUp.setBounds(427, 140, 65, 25);
		btnUp.setMargin(new Insets(0, 0, 0, 0));
		btnUp.setHorizontalTextPosition(SwingConstants.CENTER);
		btnUp.setMinimumSize(new Dimension(80, 25));
		btnUp.setPreferredSize(new Dimension(80, 25));
		btnUp.setMaximumSize(new Dimension(80, 25));
		
		JButton btnDown = new JButton("Down");
		btnDown.setPreferredSize(new Dimension(80, 25));
		btnDown.setBounds(427, 221, 65, 25);
		btnDown.setMinimumSize(new Dimension(80, 25));
		btnDown.setMaximumSize(new Dimension(80, 25));
		
		JButton btnRight = new JButton("Right");
		btnRight.setBounds(493, 178, 65, 25);
		btnRight.setHorizontalTextPosition(SwingConstants.CENTER);
		btnRight.setMinimumSize(new Dimension(80, 25));
		btnRight.setPreferredSize(new Dimension(80, 25));
		btnRight.setMaximumSize(new Dimension(80, 25));
		btnRight.setActionCommand("Right");
		
		JButton btnLeft = new JButton("Left");
		btnLeft.setBounds(369, 178, 65, 25);
		btnLeft.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLeft.setMinimumSize(new Dimension(80, 25));
		btnLeft.setPreferredSize(new Dimension(80, 25));
		btnLeft.setMaximumSize(new Dimension(80, 25));
		btnLeft.setActionCommand("Left");
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(ogreNumberLabel);
		frame.getContentPane().add(guardSelectorLabel);
		frame.getContentPane().add(ogreNumber);
		frame.getContentPane().add(guardSelector);
		frame.getContentPane().add(btnNewGame);
		frame.getContentPane().add(mapPane);
		frame.getContentPane().add(btnUp);
		frame.getContentPane().add(btnDown);
		frame.getContentPane().add(btnLeft);
		frame.getContentPane().add(btnRight);
	}
}
