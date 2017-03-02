package dk.logic;

import java.util.Random;
import java.util.Vector;

import dk.logic.Hero;
import dk.util.Coordinates;

public class Game {
	public enum GameStat {
		LOSE, WIN, RUNNING
	}

	public int level = 1;
	private Hero hero;
	private Vector<Ogre> ogres;
	private Guardian guardian;
	private Door doors[][] = { { new Door(0, 5), new Door(0, 6) }, { new Door(0, 1) } };
	private GameStat game_stat = GameStat.RUNNING;

	private char maps[][][] = {
			{
					// Map_1
					{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', },
					{ 'X', 'H', ' ', ' ', 'I', ' ', 'X', ' ', 'G', 'X', },
					{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X', },
					{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
					{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X', },
					{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
					{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
					{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X', },
					{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X', },
					{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', } },
			{
					// Map_2
					{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', }, { 'I', ' ', ' ', ' ', 'O', ' ', ' ', 'k', 'X', },
					{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
					{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
					{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, { 'X', 'H', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
					{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', } } };

	private char map[][] = maps[level - 1];

	public Game() {
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
		ogres.addElement(new Ogre(4,1));
		ogres.addElement(new Ogre(4,4));
		ogres.addElement(new Ogre(6,6));

	}

	private void advanceLevel() {
		level++;

		if (level == 2) {
			hero.setX(1);
			hero.setY(7);
			hero.setHasClub(true);
		}

		map = maps[level - 1];
	}

	public void openDoors() {
		for (int i = 0; i < doors[level - 1].length; i++) {
			int currentDoor_x = doors[level - 1][i].getX();
			int currentDoor_y = doors[level - 1][i].getY();
			map[currentDoor_y][currentDoor_x] = 'S';
		}
	}

	public void processInput(Character.Direction direction) {
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
					//map[club.getY()][club.getX()] = '*';
				} else {
					hero.moveCharacter(this);
					this.game_stat = Game.GameStat.WIN;
				}
			} else if (nextCharacter == 'k') {
				hero.moveCharacter(this);
				if (level == 1) {
					this.openDoors();
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
				for(Ogre currentOgre : ogres){
					currentOgre.moveCharacter(this);
					if (currentOgre.checkColision(hero) || currentOgre.getClub().checkColision(hero))
						this.game_stat = Game.GameStat.LOSE;
				}
				//club.moveCharacter(this);
			}
		}
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

}
