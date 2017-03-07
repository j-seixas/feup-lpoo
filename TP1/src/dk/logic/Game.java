package dk.logic;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import dk.logic.Hero;

public class Game {
	public enum GameStat {
		LOSE, WIN, RUNNING
	}

	public int level = 1;
	private Hero hero;
	private Vector<Ogre> ogres;
	private Guardian guardian;
	private Coordinates key;
	private Door doors[][] = { { new Door(0, 5), new Door(0, 6) }, { new Door(0, 1) } };
	private ArrayList<Door> door;
	private GameStat game_stat = GameStat.RUNNING;

	private char maps[][][] = {
			{
					// Map_1
					{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', },
					{ 'X', ' ', ' ', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
					{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X', },
					{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
					{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X', },
					{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
					{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
					{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X', },
					{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
					{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', } },
			{
					// Map_2
					{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', }, { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
					{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
					{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
					{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
					{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', } } };

	private char map[][];

	/*public Game() {
		map = new char[maps[level - 1].length][maps[level - 1].length];
		for (int i = 0; i < maps[level - 1].length; i++)
			map[i] = maps[level - 1][i].clone();
		hero = new Hero(1, 1);
		Random rand = new Random();
		int guard = rand.nextInt(3);
		switch (guard) {
		case 0:
			guardian = new RookieG();
			break;
		case 1:
			guardian = new DrunkenG();
			break;
		case 2:
			guardian = new SuspiciousG();
			break;
		}
		ogres = new Vector<Ogre>();
		// TODO Change to random
		ogres.addElement(new Ogre(1, 1));
		ogres.addElement(new Ogre(7,7));
		key = new Coordinates(7, 8);
	}*/

	public Game(char gameMap[][], Hero h, Guardian g, Coordinates k){
		map = gameMap;
		hero = h;
		guardian = g;
		key = k;
		
	}
	
	public Game(char gameMap[][]){
		map = gameMap;
		door = new ArrayList<Door>();
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				if(map[i][j] == 'H')
					hero = new Hero(j,i);
				else if(map[i][j] == 'G')
					guardian = new RookieG(j,i);
				else if (map[i][j] == 'I')
					door.add(new Door(j,i));
				else if (map[i][j] == 'k')
					key = new Coordinates(j,i);
			}
		}
		
		
	}
	
	private void advanceLevel() {
		level++;

		map = new char[maps[level - 1].length][maps[level - 1].length];
		updateMap();

		if (level == 2) {
			hero = new Hero(1, 7);
			hero.setHasClub(true);
			hero.setHasKey(false);
			key = new Coordinates(7, 1);
			for(Ogre currentOgre : ogres){
				currentOgre.getClub().moveCharacter(this);
			}
		}

		updateMap();
	}

	public void openDoors() {
		for (int i = 0; i < door.size(); i++) {
			Door doortemp = door.get(i);
			doortemp.openDoor();
			door.set(i, doortemp);
			
		}
	}

	public void processInput(GameCharacter.Direction direction) {
		Boolean insideCanvas = false;
		char nextCharacter = '\0';
		hero.setDirection(direction);
		switch (direction) {
		case UP:
			if (hero.getY() > 0) {
				insideCanvas = true;
				nextCharacter = map[hero.getY() - 1][hero.getX()];
			}
			break;
		case DOWN:
			if (hero.getY() < map.length - 1) {
				insideCanvas = true;
				nextCharacter = map[hero.getY() + 1][hero.getX()];
			}
			break;
		case RIGHT:
			if (hero.getX() < map.length - 1) {
				insideCanvas = true;
				nextCharacter = map[hero.getY()][hero.getX() + 1];

			}
			break;
		case LEFT:
			if (hero.getX() > 0) {
				insideCanvas = true;
				nextCharacter = map[hero.getY()][hero.getX() - 1];
			}
			break;
		default:
			return;
		}
		if (insideCanvas) {
			boolean changeMap = false;

			if (nextCharacter == ' ') {
				hero.moveCharacter(this);
			} else if (nextCharacter == 'S') {
				if (level == 1) {
					changeMap = true;
					this.advanceLevel();
				} else {
					hero.moveCharacter(this);
					this.game_stat = Game.GameStat.WIN;
				}
			} else if (nextCharacter == 'k') {
				hero.moveCharacter(this);
				if (level == 1) {
					this.openDoors();
					hero.setHasKey(true);
				} else {
					hero.setHasKey(true);
				}
			} else if (nextCharacter == 'I' && hero.getHasKey()) {
				this.openDoors();
			}

			if (level == 1) {
				guardian.moveCharacter(this);
				if (guardian.checkColision(hero) && !guardian.IsSleeping())
					this.game_stat = Game.GameStat.LOSE;
			} else if (!changeMap) {
				for (Ogre currentOgre : ogres) {
					currentOgre.moveCharacter(this);
					currentOgre.setIsStunned(currentOgre.checkColision(hero));
					if(currentOgre.getClub().checkColision(hero))
						this.game_stat = Game.GameStat.LOSE;
				}
			}
			updateMap();
		}
	}

	public void updateMap() {
		/*
		for (int i = 0; i < map.length; i++)
			map[i] = map[i].clone();*/
		char draw_char;

		// Draw doors
		for (int i = 0; i < door.size(); i++) {
			if (door.get(i).isOpen())
				draw_char = 'S';
			else
				draw_char = 'I';
			System.out.print(draw_char);
			setMap(door.get(i).getCoordinates(), draw_char);
		}

		// Draw key
		if (!hero.getHasKey() || level == 1)
			setMap(key, 'k');

		// Draw hero
		if (hero.getHasKey() && level == 2) {
			draw_char = 'K';
		} else if (hero.getHasClub())
			draw_char = 'A';
		else
			draw_char = 'H';
		setMap(hero.getCoord(), draw_char);

		if (level == 1) {
			// Draw Guardian
			if (guardian.IsSleeping())
				draw_char = 'g';
			else
				draw_char = 'G';
			setMap(guardian.getCoord(), draw_char);
		} else if (level == 2) {
			for (Ogre currentOgre : ogres) {
				// Draw Ogre
				if (currentOgre.getCoord().equals(key))
					setMap(currentOgre.getCoord(), '$');
				else if(currentOgre.isStunned())
					setMap(currentOgre.getCoord(), '8');
				else
					setMap(currentOgre.getCoord(), 'O');
				// Draw Club
				if (currentOgre.getClub().getCoord().equals(key))
					setMap(currentOgre.getClub().getCoord(), '$');
				else if (getMap(currentOgre.getClub().getCoord()) != 'O')
					setMap(currentOgre.getClub().getCoord(), '*');
			}
		}
	}

	public char getMap(Coordinates coord) {
		return map[coord.getY()][coord.getX()];
	}

	public void printMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
	}

	public void setMap(Coordinates coordinate, char ch) {
		map[coordinate.getY()][coordinate.getX()] = ch;
	}

	public char getMap(int x, int y) {
		return map[y][x];
	}

	public GameStat getGameStatus() {
		return this.game_stat;
	}

	public boolean isGameOver() {
		if(this.game_stat == GameStat.LOSE)
			return true;
		return false;
	}
	
	public GameCharacter getHero() { 
		return hero;
	}
	
	public boolean getHeroHasKey() {
		return hero.getHasKey();
	}

}
