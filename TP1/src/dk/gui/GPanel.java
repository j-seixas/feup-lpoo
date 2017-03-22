package dk.gui;

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
	
	private BufferedImage getImage(char c) {
		
		BufferedImage image = null;
		
		try{
			switch (c) {
			case 'X':
				image = ImageIO.read(new File("imgs/wall 2.png"));
				return image;
			case 'I':
				image = ImageIO.read(new File("imgs/door_closed.png"));
				return image;
			case 'S':
				image = ImageIO.read(new File("imgs/door_open.png"));
				return image;
			case '*':
				image = ImageIO.read(new File("imgs/club.png"));
				return image;
			case 'k':
				image = ImageIO.read(new File("imgs/key.png"));
				return image;
			case 'H':
			case 'A': //TODO
			case 'K': //TODO
				image = ImageIO.read(new File("imgs/hero.png"));
				return image;
			case 'G':
				image = ImageIO.read(new File("imgs/guardian.png"));
				return image;
			case 'g':
				image = ImageIO.read(new File("imgs/guardian_sleeping.png"));
				return image;
			case 'O': 
			case '8': //TODO
				image = ImageIO.read(new File("imgs/ogre.png"));
				return image;
		 	default:
		 		return null;
			}
		} catch (IOException e){
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
				if(image != null){
					g.drawImage(image, j * pixelWidth, i * pixelHeight, pixelWidth, pixelHeight, null);
				}
			}
		}
	}
}
