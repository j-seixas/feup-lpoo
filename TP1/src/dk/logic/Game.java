package dk.logic;

import java.util.ArrayList;
import java.util.Vector;

import dk.logic.Hero;

public class Game {
	public enum GameStat {
		LOSE, WIN, RUNNING
	}

	private boolean lever;
	private Hero hero;
	private Vector<Ogre> ogres;
	private Guardian guardian;
	private Coordinates key;
	private ArrayList<Door> door;
	private GameStat game_stat = GameStat.RUNNING;
	private char map[][], map1[][];
	private boolean has_guardian;
	private boolean has_ogres;


	public Game(char gameMap[][], Hero h, Guardian g, Coordinates k, ArrayList<Door> doors, boolean l) {
		door = doors;
		map1 = gameMap;
		map = new char[map1.length][map1.length];
		for (int i = 0; i < map1.length; i++)
			map[i] = map1[i].clone();
		
		hero = h;
		guardian = g;
		key = k;
		has_guardian = true;
		has_ogres = false;
		lever = l;
		for (int i = 0; i < door.size(); i++) 
			setMap(door.get(i).getCoordinates(), 'I');
	}

	public Game(char gameMap[][], boolean l) {
		lever = true;
		map1 = gameMap;
		map = new char[map1.length][map1.length];
		has_guardian = false;
		has_ogres = false;
		door = new ArrayList<Door>();
		for (int i = 0; i < map1.length; i++) {
			for (int j = 0; j < map1[i].length; j++) {
				if (map1[i][j] == 'H')
					hero = new Hero(j, i);
				else if (map1[i][j] == 'G'){
					guardian = new RookieG(j, i);
					has_guardian = true;
				}else if (map1[i][j] == 'I')
					door.add(new Door(j, i));
				else if (map1[i][j] == 'k')
					key = new Coordinates(j, i);
			}
		}
		for (int i = 0; i < door.size(); i++) 
			setMap(door.get(i).getCoordinates(), 'I');
	}

	public Game(char[][] gameMap, Hero h, Vector<Ogre> o, Coordinates k, ArrayList<Door> doors, boolean l) {
		door = doors;
		map1 = gameMap;
		map = new char[map1.length][map1.length];
		for (int i = 0; i < map1.length; i++)
			map[i] = map1[i].clone();
		hero = h;
		ogres = o;
		key = k;
		has_ogres = true;
		has_guardian = false;
		lever = l;
		for (int i = 0; i < door.size(); i++) 
			setMap(door.get(i).getCoordinates(), 'I');
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
			if (nextCharacter == ' ') {
				hero.moveCharacter(this);
			} else if (nextCharacter == 'S') {
				hero.moveCharacter(this);
				this.game_stat = Game.GameStat.WIN;

			} else if (nextCharacter == 'k') {
				hero.moveCharacter(this);
				if (lever) {
					this.openDoors();
					hero.setHasKey(true);
				} else {
					hero.setHasKey(true);
				}
			} else if (nextCharacter == 'I' && hero.getHasKey()) {
				this.openDoors();
			}

			if (has_guardian) {
				guardian.moveCharacter(this);
				if (guardian.checkColision(hero) && !guardian.IsSleeping())
					this.game_stat = Game.GameStat.LOSE;
			} else if (has_ogres) {
				for (Ogre currentOgre : ogres) {
					currentOgre.moveCharacter(this);
					currentOgre.setIsStunned(currentOgre.checkColision(hero));
					if (currentOgre.getClub().checkColision(hero))
						this.game_stat = Game.GameStat.LOSE;
				}
			}
			updateMap();
		}
	}

	public void updateMap() {
		/*
		 * for (int i = 0; i < map.length; i++) map[i] = map[i].clone();
		 */
		for (int i = 0; i < map1.length; i++)
			map[i] = map1[i].clone();
		char draw_char;

		// Draw doors
		for (int i = 0; i < door.size(); i++) {
			if (door.get(i).isOpen())
				draw_char = 'S';
			else
				draw_char = 'I';
			//System.out.print(draw_char);
			setMap(door.get(i).getCoordinates(), draw_char);
		}

		// Draw key
		if (!hero.getHasKey() || lever)
			setMap(key, 'k');

		// Draw hero
		if (hero.getHasKey() && lever == false) {
			draw_char = 'K';
		} else if (hero.getHasClub())
			draw_char = 'A';
		else
			draw_char = 'H';
		setMap(hero.getCoord(), draw_char);

		if (has_guardian) {
			// Draw Guardian
			if (guardian.IsSleeping())
				draw_char = 'g';
			else
				draw_char = 'G';
			setMap(guardian.getCoord(), draw_char);
		} else if (has_ogres) {
			for (Ogre currentOgre : ogres) {
				// Draw Ogre
				if (currentOgre.getCoord().equals(key))
					setMap(currentOgre.getCoord(), '$');
				else if (currentOgre.isStunned())
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
		if (this.game_stat == GameStat.LOSE)
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
