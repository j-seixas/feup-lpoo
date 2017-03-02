package dk.logic;

import java.util.Random;

public class Ogre extends Character {

	private boolean ogre_on_key = false;
	private Club club;

	public Ogre(int x, int y) {
		super(x, y);
		club = new Club(x, y + 1, this);
	}

	public boolean moveCharacter(Game game) {
		boolean can_move, on_key = false;
		char draw_char = '\0';
		int ogreDirection;
		do {
			can_move = true;
			Random rand = new Random();
			ogreDirection = rand.nextInt(4);
			boolean insideCanvas = false;
			char nextCharacter = '\0';
			switch (ogreDirection) {
			case 0:
				if (getY() > 0) {
					insideCanvas = true;
					nextCharacter = game.getMap(getX() ,getY() - 1 );
				}
				break;
			case 1:
				if (getY() < 8) {
					insideCanvas = true;
					nextCharacter = game.getMap(getX() ,getY() + 1 );
				}
				break;
			case 2:
				if (getX() < 8) {
					insideCanvas = true;
					nextCharacter = game.getMap(getX() + 1,getY());

				}
				break;
			case 3:
				if (getX() > 0) {
					insideCanvas = true;
					nextCharacter = game.getMap(getX() - 1,getY());
				}
				break;
			}
			if (insideCanvas) {
				if (nextCharacter == ' ' || nextCharacter == '*' || nextCharacter == 'O')
					draw_char = 'O';
				else if (nextCharacter == 'k') {
					draw_char = '$';
					on_key = true;
				} else
					can_move = false;
			} else
				can_move = false;
		} while (!can_move);

		moveOgre(ogreDirection, draw_char, game);

		if (on_key)
			ogre_on_key = true;

		club.moveCharacter(game);
		
		return true;
	}

	private void moveOgre(int direction, char draw_char, Game game) {
		if (ogre_on_key) {
			game.setMap(coordinates, 'k');
			ogre_on_key = false;
		} else
			game.setMap(coordinates, ' ');

		switch (direction) {
		case 0:
			setY(getY() - 1);
			break;
		case 1:
			setY(getY() + 1);
			break;
		case 2:
			setX(getX() + 1);
			break;
		case 3:
			setX(getX() - 1);
			break;
		}

		game.setMap(coordinates, draw_char);
	}

	
	public Club getClub(){
		return club;
	}
}
