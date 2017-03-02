package dk.test;

import static org.junit.Assert.*;
import org.junit.Test;
import dk.logic.*;
import dk.logic.Character.Direction;
import dk.util.Coordinates;

public class TestDungeonGameLogic {
	char map[][] = {{'X','X','X','X','X'},
			{'X','H',' ','G','X'},
			{'I',' ',' ',' ','X'},
			{'I','k',' ',' ','X'},
			{'X','X','X','X','X'}};

	@Test
	public void testMoveHeroIntotoFreeCell() {
		Game game = new Game(map);
		assertEquals(new Coordinates(1,1), game.getHero().getCoord());
		game.getHero().setDirection(Direction.DOWN);
		game.getHero().moveCharacter(game);
		assertEquals(new Coordinates(2,1), game.getHero().getCoord());
	}

}
