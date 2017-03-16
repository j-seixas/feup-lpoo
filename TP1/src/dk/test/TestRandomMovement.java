package dk.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import dk.logic.*;

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
		testLevels.add(new Level(new Hero(hero), (ArrayList<Ogre>) ogres.clone(),
				(ArrayList<Guardian>) guardians.clone(), key, (ArrayList<Door>) doors.clone(), map, false));
		return testLevels;
	}
	
	@Test
	public void testOgre(){
		ArrayList<Level> testLevels = initTestLevelsOgreAwake();
		Game game = new Game(testLevels);
		game.getCurrentLevel().updateMap();
		Coordinates coord1 = new Coordinates(3,2);
		Coordinates coord2 = new Coordinates(2,1);
		Coordinates init = new Coordinates(3,1);
		assertEquals('O', game.getCurrentLevel().getMap(init));
		game.processInput(GameCharacter.Direction.UP);
		game.getCurrentLevel().updateMap();
		assertTrue('O'== game.getCurrentLevel().getMap(coord1) || 'O' == game.getCurrentLevel().getMap(coord2));
	}
	
	@Test
	public void testClub(){
		ArrayList<Level> testLevels = initTestLevelsOgreAwake();
		Game game = new Game(testLevels);
		Coordinates ogre_pos_1 = new Coordinates(3,2);
		Coordinates ogre_pos_2 = new Coordinates(2,1);
		game.processInput(GameCharacter.Direction.UP);
		game.getCurrentLevel().updateMap();
		if('O'== game.getCurrentLevel().getMap(ogre_pos_1)){ //Down
			Coordinates club_pos_1 = new Coordinates(3, 1); //Up
			Coordinates club_pos_2 = new Coordinates(2, 2); //Left
			Coordinates club_pos_3 = new Coordinates(3, 3); //Down
			assertTrue('*' == game.getCurrentLevel().getMap(club_pos_1)
					|| '*' == game.getCurrentLevel().getMap(club_pos_2)
					|| '*' == game.getCurrentLevel().getMap(club_pos_3));			
		}
		else if('O' == game.getCurrentLevel().getMap(ogre_pos_2)){ //Left
			Coordinates club_pos_1 = new Coordinates(1, 1); //Left
			Coordinates club_pos_2 = new Coordinates(3, 1); //Right
			Coordinates club_pos_3 = new Coordinates(2, 2); //Down
			assertTrue('*' == game.getCurrentLevel().getMap(club_pos_1)
					|| '*' == game.getCurrentLevel().getMap(club_pos_2)
					|| '*' == game.getCurrentLevel().getMap(club_pos_3));	
		}
		else fail("Ogre is in incorrect position");	
	}


}
