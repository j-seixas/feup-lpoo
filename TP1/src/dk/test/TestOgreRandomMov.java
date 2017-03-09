package dk.test;

import static org.junit.Assert.*;
import org.junit.Test;
import dk.logic.*;

public class TestOgreRandomMov {
	char map[][] = { { 'X', 'X', 'X', 'X', 'X' }, 
			{ 'X', 'H', ' ', 'O', 'X' }, 
			{ 'I', ' ', ' ', ' ', 'X' },
			{ 'I', 'k', ' ', ' ', 'X' }, 
			{ 'X', 'X', 'X', 'X', 'X' } };

	@Test
	public void testRandomPosOgre() {
		Game game = new Game(map, true, true, false);
		game.updateMap();
		Coordinates coord1 = new Coordinates(3,2);
		Coordinates coord2 = new Coordinates(2,1);
		assertEquals('O', game.getMap(new Coordinates(3,1)));
		game.processInput(GameCharacter.Direction.UP);
		assertTrue('O'== game.getMap(coord1) || 'O' == game.getMap(coord2));
	}
	
	@Test
	public void testRandomPosClub() {
		Game game = new Game(map, true, false, true);
		game.updateMap();
		Coordinates coord1 = new Coordinates(3,2);
		Coordinates coord2 = new Coordinates(2,1);
		assertEquals('O', game.getMap(new Coordinates(3,1)));
		game.processInput(GameCharacter.Direction.LEFT);
		assertTrue('*'== game.getMap(coord1) || '*' == game.getMap(coord2));
		game.printMap();
	}
	
}