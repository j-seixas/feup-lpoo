package dk.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import dk.logic.*;

public class TestDungeonGameLogic {

	public ArrayList<Level> initTestLevelsGuardian() {
		// Variables to init levels
		Hero hero;
		ArrayList<Ogre> ogres = new ArrayList<Ogre>();
		ArrayList<Guardian> guardians = new ArrayList<Guardian>();
		Coordinates key;
		ArrayList<Door> doors = new ArrayList<Door>();
		char map[][];

		hero = new Hero(1, 1);
		guardians.add(new RookieG(new Coordinates[] { new Coordinates(3, 1) }));
		key = new Coordinates(1, 3);
		doors.add(new Door(0, 2));
		doors.add(new Door(0, 3));
		map = new char[][] { { 'X', 'X', 'X', 'X', 'X' }, { 'X', ' ', ' ', ' ', 'X' }, { 'I', ' ', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', 'X' }, { 'X', 'X', 'X', 'X', 'X' } };

		ArrayList<Level> testLevels = new ArrayList<Level>();
		Level testLevel = new Level();
		testLevel.setHero(new Hero(hero));
		testLevel.setGuardians((ArrayList<Guardian>)guardians.clone());
		testLevel.setKey(key);
		testLevel.setDoors((ArrayList<Door>)doors.clone());
		testLevel.setMap(map);
		testLevel.setWonByLever(true);
		testLevel.setOgres((ArrayList<Ogre>)ogres.clone());
		testLevels.add(testLevel);
		return testLevels;
	}

	@Test
	public void testMoveHeroIntoToFreeCell() {
		ArrayList<Level> testLevels = initTestLevelsGuardian();
		Game game = new Game(testLevels);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		game.processInput(GameCharacter.Direction.DOWN);
		assertEquals(new Coordinates(1, 2), game.getCurrentHero().getCoord());
	}

	@Test
	public void testMoveHeroIntoToWall() {
		ArrayList<Level> testLevels = initTestLevelsGuardian();
		Game game = new Game(testLevels);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		game.processInput(GameCharacter.Direction.UP);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
	}

	@Test
	public void testHeroIsCapturedByGuard() {
		ArrayList<Level> testLevels = initTestLevelsGuardian();
		Game game = new Game(testLevels);
		assertFalse(game.isOver());
		game.processInput(GameCharacter.Direction.RIGHT);
		assertTrue(game.isOver());
		assertEquals(Game.GameStat.LOSE, game.getGameStatus());
	}

	@Test
	public void testMoveHeroIntoToClosedDoorsGuardian() {
		ArrayList<Level> testLevels = initTestLevelsGuardian();
		Game game = new Game(testLevels);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		game.processInput(GameCharacter.Direction.DOWN);
		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals('I', game.getCurrentMap(new Coordinates(0, 2)));
		assertEquals(new Coordinates(1, 2), game.getCurrentHero().getCoord());
		assertEquals(Game.GameStat.RUNNING, game.getGameStatus());
	}

	@Test
	public void testMoveHeroOpensDoorsAndWinsGuardian() {
		ArrayList<Level> testLevels = initTestLevelsGuardian();
		Game game = new Game(testLevels);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		game.processInput(GameCharacter.Direction.DOWN);
		assertEquals('I', game.getCurrentMap(new Coordinates(0, 2)));
		assertEquals('I', game.getCurrentMap(new Coordinates(0, 3)));
		game.processInput(GameCharacter.Direction.DOWN);
		assertTrue(game.getCurrentHero().getHasKey());
		assertEquals('S', game.getCurrentMap(new Coordinates(0, 2)));
		assertEquals('S', game.getCurrentMap(new Coordinates(0, 3)));
		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(0, 3), game.getCurrentHero().getCoord());
		assertEquals(Game.GameStat.WIN, game.getGameStatus());
	}

	public ArrayList<Level> initTestLevelsOgreSleeping() {
		// Variables to init levels
		Hero hero;
		ArrayList<Ogre> ogres = new ArrayList<Ogre>();
		ArrayList<Guardian> guardians = new ArrayList<Guardian>();
		Coordinates key;
		ArrayList<Door> doors = new ArrayList<Door>();
		char map[][];

		hero = new Hero(1, 1);
		key = new Coordinates(1, 3);
		ogres.add(new Ogre(3, 1, true));
		doors.add(new Door(0, 2));
		doors.add(new Door(0, 3));
		map = new char[][] { { 'X', 'X', 'X', 'X', 'X' }, { 'X', ' ', ' ', ' ', 'X' }, { 'I', ' ', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', 'X' }, { 'X', 'X', 'X', 'X', 'X' } };

		ArrayList<Level> testLevels = new ArrayList<Level>();
		Level testLevel = new Level();
		testLevel.setHero(new Hero(hero));
		testLevel.setGuardians((ArrayList<Guardian>)guardians.clone());
		testLevel.setKey(key);
		testLevel.setDoors((ArrayList<Door>)doors.clone());
		testLevel.setMap(map);
		testLevel.setWonByLever(false);
		testLevel.setOgres((ArrayList<Ogre>)ogres.clone());
		testLevels.add(testLevel);
		return testLevels;
	}

	@Test
	public void testHeroIsCapturedByOgre() {
		ArrayList<Level> testLevels = initTestLevelsOgreSleeping();
		Game game = new Game(testLevels);
		assertFalse(game.isOver());
		game.processInput(GameCharacter.Direction.RIGHT);
		assertTrue(game.isOver());
		assertEquals(Game.GameStat.LOSE, game.getGameStatus());
	}

	@Test
	public void testHeroGetsKey() {
		ArrayList<Level> testLevels = initTestLevelsOgreSleeping();
		Game game = new Game(testLevels);
		game.processInput(GameCharacter.Direction.DOWN);
		game.processInput(GameCharacter.Direction.DOWN);
		assertTrue(game.getCurrentHero().getHasKey());
		assertEquals('K', game.getCurrentMap(game.getCurrentHero().getCoord()));
	}

	@Test
	public void testMoveHeroIntoToClosedDoorsOgre() {
		ArrayList<Level> testLevels = initTestLevelsOgreSleeping();
		Game game = new Game(testLevels);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		game.processInput(GameCharacter.Direction.DOWN);
		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals('I', game.getCurrentMap(new Coordinates(0, 2)));
		assertEquals(new Coordinates(1, 2), game.getCurrentHero().getCoord());
		assertEquals(Game.GameStat.RUNNING, game.getGameStatus());
	}

	@Test
	public void testMoveHeroOpensDoorsAndWinsOgre() {
		ArrayList<Level> testLevels = initTestLevelsOgreSleeping();
		Game game = new Game(testLevels);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		game.processInput(GameCharacter.Direction.DOWN);
		assertEquals('I', game.getCurrentMap(new Coordinates(0, 2)));
		assertEquals('I', game.getCurrentMap(new Coordinates(0, 3)));
		game.processInput(GameCharacter.Direction.DOWN);
		assertTrue(game.getCurrentHero().getHasKey());
		assertEquals('S', game.getCurrentMap(new Coordinates(0, 2)));
		assertEquals('S', game.getCurrentMap(new Coordinates(0, 3)));
		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(0, 3), game.getCurrentHero().getCoord());
		assertEquals(Game.GameStat.WIN, game.getGameStatus());
	}

	@Test
	public void testLevelsOfIterationsAllPosRookie() {
		Game game = new Game(1, 0);
		assertEquals('G', game.getCurrentMap(new Coordinates(8, 1)));
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());

		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(7, 1)));
		assertTrue('k' == game.getCurrentMap(new Coordinates(7, 8)));

		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(3, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(7, 2)));

		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(7, 3)));

		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(7, 4)));

		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(7, 5)));

		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(3, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(6, 5)));

		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(5, 5)));

		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(4, 5)));

		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(3, 5)));

		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(3, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(2, 5)));

		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(1, 5)));

		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(1, 6)));

		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(2, 6)));

		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(3, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(3, 6)));

		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(4, 6)));

		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(5, 6)));

		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(6, 6)));

		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(3, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(7, 6)));

		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(8, 6)));

		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(8, 5)));

		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(8, 4)));

		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(3, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(8, 3)));

		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(8, 2)));

		game.processInput(GameCharacter.Direction.LEFT);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(new Coordinates(8, 1)));
	}

	@Test
	public void testCreatingLevel() {
		Level level = new Level();
		char map1[][] = new char[][] { { 'X', 'X', 'X', 'X', 'X' }, { 'X', ' ', ' ', ' ', 'X' },
				{ ' ', ' ', ' ', ' ', 'X' }, { ' ', ' ', ' ', ' ', 'X' }, { 'X', 'X', 'X', 'X', 'X' } };
		char map2[][] = new char[][] { { 'X', 'X', 'X', 'X', 'X' }, { 'X', 'H', ' ', 'G', 'X' },
				{ 'I', 'k', ' ', ' ', 'X' }, { 'X', ' ', ' ', 'O', 'X' }, { 'X', 'X', 'X', 'X', 'X' } };
		level.setMap(map1);
		for (int i = 0; i < map1.length; i++) {
			for (int j = 0; j < map1[i].length; j++)
				assertEquals(map1[i][j], level.getMap()[i][j]);
		}

		level.setWonByLever(true);
		Hero hero = new Hero(1, 1);
		assertTrue(level.canAdd(hero));
		level.setHero(hero);

		Ogre ogre = new Ogre(3, 3, true);
		assertTrue(level.canAdd(ogre));
		level.addOgre(ogre);

		Guardian guard = new DrunkenG(1, 1);
		assertFalse(level.canAdd(guard));
		Guardian guard1 = new SuspiciousG(3, 1);
		assertTrue(level.canAdd(guard1));
		level.addGuardian(guard1);

		assertFalse(level.isValid());

		assertTrue(level.canAddElement(0, 2));
		Door door = new Door(0, 2);
		level.addDoor(door);
		level.updateMap();
		assertFalse(level.canAddElement(0, 2));

		assertTrue(level.canAddElement(0, 3));
		level.addElement('X', 0, 3);
		level.updateMap();
		assertFalse(level.canAddElement(0, 3));
		
		assertTrue(level.canAddElement(1, 2));
		level.setKey(new Coordinates(1, 2));
		level.updateMap();
		
		assertTrue(level.isValid());
		
		for (int i = 0; i < map2.length; i++) {
			for (int j = 0; j < map2[i].length; j++)
				assertEquals(map2[i][j], level.getMap()[i][j]);	
		}
		
		level.removeHero();
		level.removeDoor(door);
		level.removeGuardian(guard1);
		level.removeElement(0, 3);
		level.removeKey();
		level.removeOgre(ogre);
		level.updateMap();
		
		assertFalse(level.isValid());
		
		for (int i = 0; i < map1.length; i++) {
			for (int j = 0; j < map1[i].length; j++){
				assertEquals(map1[i][j], level.getMap()[i][j]);
			}
		}
		
	}
}
