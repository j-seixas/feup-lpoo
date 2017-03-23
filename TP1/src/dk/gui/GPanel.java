package dk.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GPanel extends JPanel {

	private TreeMap<Character, BufferedImage> images;
	private int pixelHeight, pixelWidth; 
	private char map[][];

	public GPanel(int x, int y, int width, int height) {
		super();
		setBounds(x, y, width, height);
		loadImages();
	}

	public void loadImages(){
		images = new TreeMap<Character, BufferedImage>();
				
		try{
				images.put('X', ImageIO.read(new File("imgs/wall.png")));
				images.put('I', ImageIO.read(new File("imgs/door.png")));
				images.put('S', ImageIO.read(new File("imgs/stairs.png")));
				images.put('*', ImageIO.read(new File("imgs/club.png")));
				images.put('k', ImageIO.read(new File("imgs/key.png")));
				images.put('H', ImageIO.read(new File("imgs/hero.png")));
				images.put('A', ImageIO.read(new File("imgs/hero_shield.png")));
				images.put('K', ImageIO.read(new File("imgs/hero_shield_key.png")));
				images.put('G', ImageIO.read(new File("imgs/guardian.png")));
				images.put('g', ImageIO.read(new File("imgs/guardian_sleeping.png")));
				images.put('O', ImageIO.read(new File("imgs/ogre.png")));
				images.put('8', ImageIO.read(new File("imgs/ogre_stunned.png")));
				images.put(' ', ImageIO.read(new File("imgs/floor.png")));
		} catch (IOException e){
			return;
		}
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

	public int getCellX(int mouseX){
		return mouseX / pixelWidth;
	}
	
	public int getCellY(int mouseY){
		return mouseY / pixelWidth;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if (map == null)
			return;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				g.drawImage(images.get(' '), j * pixelWidth, i * pixelHeight, pixelWidth, pixelHeight, null);
				BufferedImage image = images.get(map[i][j]);
				if(image != null){
					g.drawImage(image, j * pixelWidth, i * pixelHeight, pixelWidth, pixelHeight, null);
				}
			}
		}
	}
}
