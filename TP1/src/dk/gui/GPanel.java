package dk.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
		this.setBounds(getX(), getY(), pixelWidth * map[0].length, pixelHeight * map.length);
	}

	private BufferedImage getBackgroundImage(){
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("imgs/floor 1.png"));

		} catch (IOException e) {
			return null;
		}
		return image;
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
		case 'I': {
			// BROWN
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

	private BufferedImage getImage(char c) {
		BufferedImage image = null;
		
		switch (c) {
		case 'X':
			try {
				image = ImageIO.read(new File("imgs/wall 2.png"));

			} catch (IOException e) {
				return null;
			}
			return image;
		
		case 'I':
			try {
				image = ImageIO.read(new File("imgs/door_closed.png"));

			} catch (IOException e) {
				return null;
			}
			return image;
			
		case 'S':
			try {
				image = ImageIO.read(new File("imgs/door_open.png"));

			} catch (IOException e) {
				return null;
			}
			return image;
			
		case '*':
			try {
				image = ImageIO.read(new File("imgs/club.png"));

			} catch (IOException e) {
				return null;
			}
			return image;
			
		case 'k':
			try {
				image = ImageIO.read(new File("imgs/key.png"));

			} catch (IOException e) {
				return null;
			}
			return image;
			
		case 'H':
			try {
				image = ImageIO.read(new File("imgs/hero.png"));

			} catch (IOException e) {
				return null;
			}
			return image;
			
		case 'G':
			try {
				image = ImageIO.read(new File("imgs/guardian.png"));

			} catch (IOException e) {
				return null;
			}
			return image;
		case 'g':
			try {
				image = ImageIO.read(new File("imgs/guardian_sleeping.png"));

			} catch (IOException e) {
				return null;
			}
			return image;
		
		 case 'O': 
			 try {
					image = ImageIO.read(new File("imgs/ogre.png"));

				} catch (IOException e) {
					return null;
				}
				return image;
		 
		default:
			return null;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		BufferedImage image = null;
		BufferedImage background = getBackgroundImage();
		if (map == null)
			return;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				image = getImage(map[i][j]);
				g.drawImage(background, j * pixelWidth, i * pixelHeight, pixelWidth, pixelHeight, null);
				if(image != null || map[i][j] == ' '){
					g.drawImage(image, j * pixelWidth, i * pixelHeight, pixelWidth, pixelHeight, null);
				} else {
					g.setColor(getColor(map[i][j]));
					g.fillRect(j * pixelWidth, i * pixelHeight, pixelWidth, pixelHeight);
				}
			}
		}
	}
}
