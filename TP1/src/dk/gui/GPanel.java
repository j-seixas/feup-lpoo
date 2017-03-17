package dk.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GPanel extends JPanel {

	private int pixelHeight, pixelWidth;
	private char map[][];

	public GPanel(int x, int y, int width, int height) {
		super();
		setBounds(x, y, width, height);
	}

	public void setMap(char map[][]) {
		this.map = map;
		pixelHeight = this.getHeight() / map.length;
		pixelWidth = this.getWidth() / map[0].length;
	}

	private Color getColor(char c) {
		switch (c) {
		case 'X':
			return Color.BLACK;
		case ' ':
			return Color.WHITE;
		case 'H':
			return Color.BLUE;
		case 'K':
			return Color.ORANGE;
		case 'A':
			return Color.YELLOW;
		case 'k':
			return Color.GREEN;
		case 'S':
			return Color.LIGHT_GRAY;
		case 'I':{
			//BROWN
			return Color.getHSBColor(0.05f, 1.0f, 0.65f);
		}
		case 'G':
			return Color.PINK;
		case 'g':
			return Color.PINK;
		case 'O':
			return Color.RED;
		case '*':
			return Color.DARK_GRAY;
		default:
			return null;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		if (map == null)
			return;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				g.setColor(getColor(map[i][j]));
				g.fillRect(j * pixelWidth, i * pixelHeight, pixelWidth, pixelHeight);
			}
		}
	}
}
