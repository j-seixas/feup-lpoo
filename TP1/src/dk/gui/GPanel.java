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
	
	public char[][] setDefaultMap(int x, int y){
		this.map = new char[y][x];
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				map[i][j] = ' ';
			}
		}
		setMap(map);
		return map;
	}
	
	private BufferedImage getBackgroundImage(){
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("imgs/floor.png"));

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
				image = ImageIO.read(new File("imgs/wall.png"));
				return image;
			case 'I':
				image = ImageIO.read(new File("imgs/door.png"));
				return image;
			case 'S':
				image = ImageIO.read(new File("imgs/stairs.png"));
				return image;
			case '*':
				image = ImageIO.read(new File("imgs/club.png"));
				return image;
			case 'k':
				image = ImageIO.read(new File("imgs/key.png"));
				return image;
			case 'H':
				image = ImageIO.read(new File("imgs/hero.png"));
				return image;
			case 'A': 
				image = ImageIO.read(new File("imgs/hero_shield.png"));
				return image;
			case 'K': 
				image = ImageIO.read(new File("imgs/hero_shield_key.png"));
				return image;
			case 'G':
				image = ImageIO.read(new File("imgs/guardian.png"));
				return image;
			case 'g':
				image = ImageIO.read(new File("imgs/guardian_sleeping.png"));
				return image;
			case 'O': 
				image = ImageIO.read(new File("imgs/ogre.png"));
				return image;
			case '8': 
				image = ImageIO.read(new File("imgs/ogre_stunned.png"));
				return image;
		 	default:
		 		return null;
			}
		} catch (IOException e){
			return null;
		}
	}

	public int getCellX(int mouseX){
		return mouseX / pixelWidth;
	}
	
	public int getCellY(int mouseY){
		return mouseY / pixelWidth;
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
