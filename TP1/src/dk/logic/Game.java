package dk.logic;

import dk.logic.Hero;
import dk.util.Coordinates;

public class Game {
	public enum GameStat {
		LOSE, WIN, RUNNING
	}

	public int level = 1;
	private Hero hero;
	private Club club;
	private Ogre ogre;
	private Guardian guardian;
	private GameStat game_stat = GameStat.RUNNING;
	
	private char map[][];
	
	private void advanceLevel(int currentLevel){
		/*TODO*/
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
				hero.moveCharacter(map);
				} else if (nextCharacter == 'S') {
				if (level == 1) {
					changeMap = true;
					this.advanceLevel(level);
					map[club.getY()][club.getX()] = '*';
				}
				else {
					hero.moveCharacter(map);
					this.game_stat = Game.GameStat.WIN;
				}
			} else if (nextCharacter == 'k') {
				hero.moveCharacter(map);
				if (level == 1) {
					this.openDoors(level);
				} else {
					hero.setHasKey(true);
				}
			} else if(nextCharacter == 'I' && hero.getHasKey()) {
				this.openDoors(level);
			}
			
			if (level == 1) {
				guardian.moveCharacter(map);
				guardian.checkColision(hero);
			} else if(!changeMap){
				ogre.moveCharacter(map);
				club.moveCharacter(map);
				ogre.checkColision(hero);
				club.checkColision(hero);
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
	
	public GameStat getGameStatus(){
		return this.game_stat;
	}

}
