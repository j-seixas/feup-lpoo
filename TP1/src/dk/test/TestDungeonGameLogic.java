package dk.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import dk.logic.*;

public class TestDungeonGameLogic {
	/*char map[][] = {
			{'X','X','X','X','X'},
			{'X','H',' ','G','X'},
			{'I',' ',' ',' ','X'},
			{'I','k',' ',' ','X'},
			{'X','X','X','X','X'}};*/
	
	public ArrayList<Level> initTestLevels() {
		//Variables to init levels
		Hero hero;
		ArrayList<Ogre> ogres = new ArrayList<Ogre>();
		ArrayList<Guardian> guardians = new ArrayList<Guardian>();
		Coordinates key;
		ArrayList<Door> doors = new ArrayList<Door>();
		char map[][];
				
		hero = new Hero(1,1);
		guardians.add(new RookieG(new Coordinates[]{new Coordinates(3, 1)}));
		key = new Coordinates(1,3);
		doors.add(new Door(0, 2));
		doors.add(new Door(0, 3));
		map = new char[][]
				{{'X','X','X','X','X'},
				{'X',' ',' ',' ','X'},
				{'I',' ',' ',' ','X'},
				{'I',' ',' ',' ','X'},
				{'X','X','X','X','X'}};
				
		ArrayList<Level> testLevels = new ArrayList<Level>();
		testLevels.add(new Level(new Hero(hero), (ArrayList<Ogre>)ogres.clone(), (ArrayList<Guardian>)guardians.clone(), key, (ArrayList<Door>)doors.clone(),
				map, true));
		return testLevels;
	}
	
	@Test
	public void testMoveHeroIntoToFreeCell() {
		ArrayList<Level> testLevels = initTestLevels();
		Game game = new Game(testLevels);
		game.getCurrentLevel().updateMap();
		assertEquals(new Coordinates(1,1), game.getCurrentLevel().getHero().getCoord());
		game.processInput(GameCharacter.Direction.DOWN);
		game.getCurrentLevel().updateMap();
		assertEquals(new Coordinates(1,2), game.getCurrentLevel().getHero().getCoord()); 
	}
	
	@Test
	public void testMoveHeroIntoToWall() {
		ArrayList<Level> testLevels = initTestLevels();
		Game game = new Game(testLevels);
		game.getCurrentLevel().updateMap();
		assertEquals(new Coordinates(1,1), game.getCurrentLevel().getHero().getCoord());
		game.processInput(GameCharacter.Direction.UP);
		game.getCurrentLevel().updateMap();
		assertEquals(new Coordinates(1,1), game.getCurrentLevel().getHero().getCoord()); 
		game.processInput(GameCharacter.Direction.LEFT);
		game.getCurrentLevel().updateMap();
		assertEquals(new Coordinates(1,1), game.getCurrentLevel().getHero().getCoord()); 
	}

	@Test
	public void testHeroIsCapturedByGuard() {
		ArrayList<Level> testLevels = initTestLevels();
		Game game = new Game(testLevels);
		game.getCurrentLevel().updateMap();
		assertFalse(game.isGameOver());
		game.processInput(GameCharacter.Direction.RIGHT);
		game.getCurrentLevel().updateMap();
		assertTrue(game.isGameOver());
		assertEquals(Game.GameStat.LOSE, game.getGameStatus()); 
	}

	@Test
	public void testMoveHeroIntoToClosedDoors() {
		ArrayList<Level> testLevels = initTestLevels();
		Game game = new Game(testLevels);
		game.getCurrentLevel().updateMap();
		assertEquals(new Coordinates(1,1), game.getCurrentLevel().getHero().getCoord());
		game.processInput(GameCharacter.Direction.DOWN);
		game.processInput(GameCharacter.Direction.LEFT);
		game.getCurrentLevel().updateMap();
		assertEquals('I', game.getCurrentLevel().getMap(new Coordinates(0,2)));
		assertEquals(new Coordinates(1,2), game.getCurrentLevel().getHero().getCoord());
		assertEquals(Game.GameStat.RUNNING, game.getGameStatus());
	}
	
	@Test
	public void testMoveHeroOpensDoorsAndWins() {
		ArrayList<Level> testLevels = initTestLevels();
		Game game = new Game(testLevels);
		game.getCurrentLevel().updateMap();
		assertEquals(new Coordinates(1,1), game.getCurrentLevel().getHero().getCoord());
		game.processInput(GameCharacter.Direction.DOWN);
		game.getCurrentLevel().updateMap();
		assertEquals('I', game.getCurrentLevel().getMap(new Coordinates(0,2)));
		assertEquals('I', game.getCurrentLevel().getMap(new Coordinates(0,3)));
		game.processInput(GameCharacter.Direction.DOWN);
		game.getCurrentLevel().updateMap();
		assertTrue(game.getCurrentLevel().getHero().getHasKey());
		assertEquals('S', game.getCurrentLevel().getMap(new Coordinates(0,2)));
		assertEquals('S', game.getCurrentLevel().getMap(new Coordinates(0,3)));
		game.processInput(GameCharacter.Direction.LEFT);
		game.getLastLevel().updateMap();
		assertEquals(new Coordinates(0,3), game.getLastLevel().getHero().getCoord());
		assertEquals(Game.GameStat.WIN, game.getGameStatus());
	}
	
	
}
