package dk.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import dk.logic.Level;
import dk.logic.Coordinates;
import dk.logic.Game;
import dk.logic.GameCharacter;
import dk.logic.Hero;
import dk.logic.Guardian;
import dk.logic.Ogre;
import dk.logic.SuspiciousG;
import dk.logic.Door;
import dk.logic.DrunkenG;

public class TestRandomMovement {

	public ArrayList<Level> initTestLevelsOgreAwake() {
		// Variables to init levels
		Hero hero;
		ArrayList<Ogre> ogres = new ArrayList<Ogre>();
		ArrayList<Guardian> guardians = new ArrayList<Guardian>();
		Coordinates key;
		ArrayList<Door> doors = new ArrayList<Door>();
		char map[][];

		hero = new Hero(1, 1);
		key = new Coordinates(1, 3);
		ogres.add(new Ogre(3, 1));
		doors.add(new Door(0, 2));
		doors.add(new Door(0, 3));
		map = new char[][] { { 'X', 'X', 'X', 'X', 'X' }, { 'X', ' ', ' ', ' ', 'X' }, { 'I', ' ', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', 'X' }, { 'X', 'X', 'X', 'X', 'X' } };

		ArrayList<Level> testLevels = new ArrayList<Level>();
		Level testLevel = new Level();
		testLevel.setHero(new Hero(hero));
		testLevel.setGuardians((ArrayList<Guardian>) guardians.clone());
		testLevel.setKey(key);
		testLevel.setDoors((ArrayList<Door>) doors.clone());
		testLevel.setMap(map);
		testLevel.setWonByLever(false);
		testLevel.setOgres((ArrayList<Ogre>) ogres.clone());
		testLevels.add(testLevel);
		return testLevels;
	}

	@Test
	public void testOgre() {
		ArrayList<Level> testLevels = initTestLevelsOgreAwake();
		Game game = new Game(testLevels);
		Coordinates coord1 = new Coordinates(3, 2);
		Coordinates coord2 = new Coordinates(2, 1);
		Coordinates init = new Coordinates(3, 1);
		assertEquals('O', game.getCurrentMap(init));
		game.processInput(GameCharacter.Direction.UP);
		assertTrue('O' == game.getCurrentMap(coord1) || 'O' == game.getCurrentMap(coord2));
	}

	@Test
	public void testClub() {
		ArrayList<Level> testLevels = initTestLevelsOgreAwake();
		Game game = new Game(testLevels);
		Coordinates ogre_pos_1 = new Coordinates(3, 2);
		Coordinates ogre_pos_2 = new Coordinates(2, 1);
		game.processInput(GameCharacter.Direction.UP);
		if ('O' == game.getCurrentMap(ogre_pos_1)) { // Down
			Coordinates club_pos_1 = new Coordinates(3, 1); // Up
			Coordinates club_pos_2 = new Coordinates(2, 2); // Left
			Coordinates club_pos_3 = new Coordinates(3, 3); // Down
			assertTrue('*' == game.getCurrentMap(club_pos_1) || '*' == game.getCurrentMap(club_pos_2)
					|| '*' == game.getCurrentMap(club_pos_3));
		} else if ('O' == game.getCurrentMap(ogre_pos_2)) { // Left
			Coordinates club_pos_1 = new Coordinates(1, 1); // Left
			Coordinates club_pos_2 = new Coordinates(3, 1); // Right
			Coordinates club_pos_3 = new Coordinates(2, 2); // Down
			assertTrue('*' == game.getCurrentMap(club_pos_1) || '*' == game.getCurrentMap(club_pos_2)
					|| '*' == game.getCurrentMap(club_pos_3));
		} else
			fail("Ogre is in incorrect position");
	}

	@Test
	public void testLevelsOfIterationsDrunken() {
		Game game = new Game(1, 1);
		Coordinates coord1 = new Coordinates(8, 1);
		Coordinates coord2 = new Coordinates(7, 1);
		Coordinates init = new Coordinates(8, 1);
		assertEquals('G', game.getCurrentMap(init));
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('g' == game.getCurrentMap(coord1) || 'G' == game.getCurrentMap(coord2));
		assertTrue('k' == game.getCurrentMap(new Coordinates(7, 8)));
	}

	@Test
	public void testLevelsOfIterationsSuspicious() {
		Game game = new Game(1, 2);
		Coordinates coord1 = new Coordinates(7, 1);
		Coordinates coord2 = new Coordinates(8, 2);
		Coordinates init = new Coordinates(8, 1);
		assertEquals('G', game.getCurrentMap(init));
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('G' == game.getCurrentMap(coord1) || 'G' == game.getCurrentMap(coord2));
	}

	@Test
	public void testPosDrunken() {
		Game game = new Game(1, 1);
		Coordinates path[] = { new Coordinates(8, 1), new Coordinates(7, 1), new Coordinates(7, 2),
				new Coordinates(7, 3), new Coordinates(7, 4), new Coordinates(7, 5), new Coordinates(6, 5),
				new Coordinates(5, 5), new Coordinates(4, 5), new Coordinates(3, 5), new Coordinates(2, 5),
				new Coordinates(1, 5), new Coordinates(1, 6), new Coordinates(2, 6), new Coordinates(3, 6),
				new Coordinates(4, 6), new Coordinates(5, 6), new Coordinates(6, 6), new Coordinates(7, 6),
				new Coordinates(8, 6), new Coordinates(8, 5), new Coordinates(8, 4), new Coordinates(8, 3),
				new Coordinates(8, 2) };

		assertTrue('G' == game.getCurrentMap(path[0]) || 'g' == game.getCurrentMap(path[0]));
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());

		int forloop = 0;
		for (int i = 0; i < 50; i++) {
			for (Coordinates coord : path) {
				if (game.getCurrentMap(coord) == 'G' || game.getCurrentMap(coord) == 'g') {
					forloop++;
					break;
				}
			}
			assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
			game.processInput(GameCharacter.Direction.DOWN);
		}
		assertEquals(50, forloop);

	}

	@Test
	public void testPosSuspicious() {
		Game game = new Game(1, 2);
		Coordinates path[] = { new Coordinates(8, 1), new Coordinates(7, 1), new Coordinates(7, 2),
				new Coordinates(7, 3), new Coordinates(7, 4), new Coordinates(7, 5), new Coordinates(6, 5),
				new Coordinates(5, 5), new Coordinates(4, 5), new Coordinates(3, 5), new Coordinates(2, 5),
				new Coordinates(1, 5), new Coordinates(1, 6), new Coordinates(2, 6), new Coordinates(3, 6),
				new Coordinates(4, 6), new Coordinates(5, 6), new Coordinates(6, 6), new Coordinates(7, 6),
				new Coordinates(8, 6), new Coordinates(8, 5), new Coordinates(8, 4), new Coordinates(8, 3),
				new Coordinates(8, 2) };

		assertTrue('G' == game.getCurrentMap(path[0]) || 'g' == game.getCurrentMap(path[0]));
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());

		int forloop = 0;
		for (int i = 0; i < 50; i++) {
			for (Coordinates coord : path) {
				if (game.getCurrentMap(coord) == 'G' || game.getCurrentMap(coord) == 'g') {
					forloop++;
					break;
				}
			}
			assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
			game.processInput(GameCharacter.Direction.DOWN);
		}
		assertEquals(50, forloop);

	}

	@Test
	public void testPosOgre() {
		ArrayList<Level> testLevels = initTestLevelsOgreAwake();
		testLevels.get(0).removeOgre(new Ogre(3,1));
		testLevels.get(0).addElement('X', 2, 1);
		testLevels.get(0).addElement('X', 1, 2);
		testLevels.get(0).addOgre(new Ogre(3, 1));
		Coordinates path[] = { new Coordinates(3, 1), new Coordinates(2, 2), new Coordinates(3, 2),
				new Coordinates(1, 3), new Coordinates(2, 3), new Coordinates(3, 3) };
		Game game = new Game(testLevels);
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());

		int forloop = 0;
		for (int i = 0; i < 50; i++) {
			for (Coordinates coord : path) {
				if (game.getCurrentMap(coord) == 'O' || game.getCurrentMap(coord) == '$') {
					forloop++;
					break;
				}
			}
			
			assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
			game.processInput(GameCharacter.Direction.UP);
		}
		assertEquals(50, forloop);
	}

}
