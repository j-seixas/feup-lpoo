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
import dk.logic.Door;
 


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
		ogres.add(new Ogre(3,1));
		doors.add(new Door(0, 2));
		doors.add(new Door(0, 3));
		map = new char[][] { 
			{ 'X', 'X', 'X', 'X', 'X' }, 
			{ 'X', ' ', ' ', ' ', 'X' }, 
			{ 'I', ' ', ' ', ' ', 'X' },
			{ 'I', ' ', ' ', ' ', 'X' }, 
			{ 'X', 'X', 'X', 'X', 'X' } };

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
	public void testOgre(){
		ArrayList<Level> testLevels = initTestLevelsOgreAwake();
		Game game = new Game(testLevels);
		Coordinates coord1 = new Coordinates(3,2);
		Coordinates coord2 = new Coordinates(2,1);
		Coordinates init = new Coordinates(3,1);
		assertEquals('O', game.getCurrentMap(init));
		game.processInput(GameCharacter.Direction.UP);
		assertTrue('O'== game.getCurrentMap(coord1) || 'O' == game.getCurrentMap(coord2));
	}
	
	@Test
	public void testClub(){
		ArrayList<Level> testLevels = initTestLevelsOgreAwake();
		Game game = new Game(testLevels);
		Coordinates ogre_pos_1 = new Coordinates(3,2);
		Coordinates ogre_pos_2 = new Coordinates(2,1);
		game.processInput(GameCharacter.Direction.UP);
		if('O'== game.getCurrentMap(ogre_pos_1)){ //Down
			Coordinates club_pos_1 = new Coordinates(3, 1); //Up
			Coordinates club_pos_2 = new Coordinates(2, 2); //Left
			Coordinates club_pos_3 = new Coordinates(3, 3); //Down
			assertTrue('*' == game.getCurrentMap(club_pos_1)
					|| '*' == game.getCurrentMap(club_pos_2)
					|| '*' == game.getCurrentMap(club_pos_3));			
		}
		else if('O' == game.getCurrentMap(ogre_pos_2)){ //Left
			Coordinates club_pos_1 = new Coordinates(1, 1); //Left
			Coordinates club_pos_2 = new Coordinates(3, 1); //Right
			Coordinates club_pos_3 = new Coordinates(2, 2); //Down
			assertTrue('*' == game.getCurrentMap(club_pos_1)
					|| '*' == game.getCurrentMap(club_pos_2)
					|| '*' == game.getCurrentMap(club_pos_3));	
		}
		else fail("Ogre is in incorrect position");	
	}

	@Test
	public void testLevelsOfIterationsDrunken(){
		Game game = new Game(1, 1);
		Coordinates coord1 = new Coordinates(8,1);
		Coordinates coord2 = new Coordinates(7,1);
		Coordinates init = new Coordinates(8,1);
		assertEquals('G', game.getCurrentMap(init));
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('g'== game.getCurrentMap(coord1) || 'G' == game.getCurrentMap(coord2));
		assertTrue('k' == game.getCurrentMap(new Coordinates(7,8)));
	}
	
	@Test
	public void testLevelsOfIterationsSuspicious(){
		Game game = new Game(1, 2);
		Coordinates coord1 = new Coordinates(7,1);
		Coordinates coord2 = new Coordinates(8,2);
		Coordinates init = new Coordinates(8,1);
		assertEquals('G', game.getCurrentMap(init));
		assertEquals(new Coordinates(1, 1), game.getCurrentHero().getCoord());
		game.processInput(GameCharacter.Direction.RIGHT);
		assertEquals(new Coordinates(2, 1), game.getCurrentHero().getCoord());
		assertTrue('G'== game.getCurrentMap(coord1) || 'G' == game.getCurrentMap(coord2));
	}

}
