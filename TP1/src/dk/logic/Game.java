package dk.logic;

import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import dk.logic.Hero;

public class Game {
	public enum GameStat {
		LOSE, WIN, RUNNING
	}

	public int level = 0;
	public ArrayList<Level> levels;
	private GameStat game_stat = GameStat.RUNNING;

	public Game() {
		initLevels();
	}

	public Game(ArrayList<Level> testLevels){
		this.levels = testLevels;
	}
	
	private void initLevels() {
		levels = new ArrayList<Level>();
		
		//Variables to init levels
		Hero hero;
		ArrayList<Ogre> ogres = new ArrayList<Ogre>();
		ArrayList<Guardian> guardians = new ArrayList<Guardian>();
		Coordinates key;
		ArrayList<Door> doors = new ArrayList<Door>();
		char map[][];
		
		//Level 1
		hero = new Hero(1, 1);
		guardians.add(generateGuardian());
		key = new Coordinates(7, 8);		
		doors.add(new Door(0,5));
		doors.add(new Door(0,6));
		map = new char[][]{
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', },
			{ 'X', ' ', ' ', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X', },
			{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X', },
			{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X', },
			{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', } };
		levels.add(new Level(new Hero(hero), (ArrayList<Ogre>)ogres.clone(), (ArrayList<Guardian>)guardians.clone(), key, (ArrayList<Door>)doors.clone(), map, true));
		guardians.clear();
		doors.clear();
		ogres.clear();
		
		//Level 2
		hero = new Hero(1, 7);
		hero.setHasClub(true);
		hero.setHasKey(false);
		key = new Coordinates(7, 1);
		doors.add(new Door(0, 1));
		ogres.add(new Ogre(1, 1));
		ogres.add(new Ogre(7,7));
		map = new char[][]{
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', }, 
			{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, 
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, 
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, 
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', } };
		levels.add(new Level(new Hero(hero), (ArrayList<Ogre>)ogres.clone(), (ArrayList<Guardian>)guardians.clone(), key, (ArrayList<Door>)doors.clone(), map, false));
		
		guardians.clear();
		doors.clear();
		ogres.clear();		
	}
	
	private Guardian generateGuardian() {
		Random rng = new Random();
		
		int result = rng.nextInt(3);
		switch (result) {
		case 0:
			return new RookieG();
		case 1:
			return new DrunkenG();
		case 2:
			return new SuspiciousG();
		default:
			return null;
		}
	}

	private void advanceLevel() {
		level++;
		if(level == levels.size())
			game_stat = GameStat.WIN;
		else
			levels.get(level).updateMap();
	}

	public void processInput(GameCharacter.Direction direction) {
		AtomicBoolean insideCanvas = new AtomicBoolean(false);
		char nextCharacter = levels.get(level).getNextCharacter(direction, insideCanvas);
		
		
		if (insideCanvas.get()) {
			boolean changeMap = false;

			if (nextCharacter == ' ') {
				levels.get(level).getHero().moveCharacter(levels.get(level));
			} else if (nextCharacter == 'S') {
				levels.get(level).getHero().moveCharacter(levels.get(level));
				advanceLevel();
				changeMap = true;
			} else if (nextCharacter == 'k') {
				levels.get(level).getHero().moveCharacter(levels.get(level));
				if (level == 0) {
					levels.get(level).openDoors();
					levels.get(level).getHero().setHasKey(true);
				} else {
					levels.get(level).getHero().setHasKey(true);
				}
			} else if (nextCharacter == 'I' && levels.get(level).getHero().getHasKey()) {
				levels.get(level).openDoors();
			}

			if(!changeMap)
				if(levels.get(level).handleNPC())
					game_stat = GameStat.LOSE;
		}
	}

	public Level getCurrentLevel(){
		if(level < levels.size())
			return levels.get(level);
		else return null;
	}

	public GameStat getGameStatus() {
		return this.game_stat;
	}

	public boolean isGameOver() {
		return game_stat.ordinal() == GameStat.LOSE.ordinal();
	}

	public Level getLastLevel(){
		return levels.get(level - 1);
	}
}
