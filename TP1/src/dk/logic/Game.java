package dk.logic;

import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import dk.logic.Hero;

public class Game implements java.io.Serializable{
	public enum GameStat {
		LOSE, WIN, RUNNING
	}

	
	public static final int MAX_OGRES = 5;
	public static final int GUARDIAN_TYPES = 3;
	public int level = 0;
	public ArrayList<Level> levels;
	private GameStat game_stat = GameStat.RUNNING;

	public Game(int ogreNumber, int guardianType) {
		initLevels(ogreNumber, guardianType);
	}

	public Game(ArrayList<Level> customLevels){
		this.levels = new ArrayList<Level>();
		for(Level current : customLevels)
			levels.add(new Level(current));
	}
	
	private void initLevels(int ogreNumber, int guardianType) {
		levels = new ArrayList<Level>();
		
		//Variables to init levels
		Hero hero;
		ArrayList<Ogre> ogres = new ArrayList<Ogre>();
		ArrayList<Guardian> guardians = new ArrayList<Guardian>();
		Coordinates key;
		ArrayList<Door> doors = new ArrayList<Door>();
		char map[][];
		
		//Level 1
		Level level_1 = new Level();
		hero = new Hero(1, 1);
		guardians.add(generateGuardian(guardianType));
		key = new Coordinates(7, 8);		
		doors.add(new Door(0,5));
		doors.add(new Door(0,6));
		map = new char[][]{
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', },
			{ 'X', ' ', ' ', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X', },
			{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X', },
			{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X', },
			{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', } };
		level_1.setHero(new Hero(hero));
		level_1.setGuardians((ArrayList<Guardian>)guardians.clone());
		level_1.setKey(key);
		level_1.setDoors((ArrayList<Door>)doors.clone());
		level_1.setMap(map);
		level_1.setWonByLever(true);
		level_1.setOgres((ArrayList<Ogre>)ogres.clone());
		levels.add(level_1);
		guardians.clear();
		doors.clear();
		ogres.clear();
		
		//Level 2
		Level level_2 = new Level();
		hero = new Hero(1, 7);
		hero.setHasClub(true);
		hero.setHasKey(false);
		key = new Coordinates(7, 1);
		doors.add(new Door(0, 1));
		map = new char[][]{
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', }, 
			{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, 
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, 
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, 
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', } };
		for(int i = 0; i < ogreNumber; i++){
			ogres.add(generateOgre(hero, map));
		}
		level_2.setHero(new Hero(hero));
		level_2.setGuardians((ArrayList<Guardian>)guardians.clone());
		level_2.setKey(key);
		level_2.setDoors((ArrayList<Door>)doors.clone());
		level_2.setMap(map);
		level_2.setWonByLever(false);
		level_2.setOgres((ArrayList<Ogre>)ogres.clone());
		levels.add(level_2);
	}
	
	private Guardian generateGuardian(int guardianType) {		
		switch (guardianType) {
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

	private Ogre generateOgre(Hero hero, char map[][]){
		int height = map.length;
		int width = map[0].length;
		Random rng = new Random();
		
		int x;
		int y;
		
		while(true){
			y = rng.nextInt(height);
			x = rng.nextInt(width);
			if(!hero.checkColision(new Ogre(x,y)))
				if(map[y][x] == ' ' || map[y][x] == 'O' || map[y][x] == '*')
					return new Ogre(x, y);
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
			
			updateMap();
		}
	}

	

	//Wrappers / Getters / Setters
	
	public boolean isOver(){
		return game_stat != GameStat.RUNNING || getCurrentLevel() == null;
	}
	
	public GameStat getGameStatus() {
		return this.game_stat;
	}

	public char[][] getCurrentMap(){
		Level currentLevel = getCurrentLevel();
		if(currentLevel != null)
			return currentLevel.getMap();
		else return null;
	}
	
	public char getCurrentMap(Coordinates c){
		return getCurrentMap()[c.getY()][c.getX()];
	}
	
 	public Level getCurrentLevel(){
		if(level < levels.size())
			return levels.get(level);
		else if(level - 1 < levels.size())
			return levels.get(level - 1);	
		else return null;
	}
	
	public String getStringMap(){
		char map[][] = getCurrentMap();
		String result = new String();
		for(char i[] : map){
			for(char j : i)
				result += j + " ";
			result += "\n";
		}
		return result;
	}

	public void updateMap(){
		Level currentLevel = getCurrentLevel();
		if(currentLevel != null)
			currentLevel.updateMap();
	}

	public Hero getCurrentHero(){
		Level currentLevel = getCurrentLevel();
		if(currentLevel != null)
			return currentLevel.getHero();
		else return null;
	}
}
