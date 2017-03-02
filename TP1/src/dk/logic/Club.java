package dk.logic;

import java.util.Random;

public class Club extends Character {

	public Character character;
	
	public Club(int x, int y, Character c) {
		super(x, y);
		character = c;
	}

	private boolean club_on_key = false;

	public boolean moveCharacter(Game game) {
		boolean can_move, on_key = false;
		char draw_char = '\0';
		int clubDirection;
		do {
			can_move = true;
			Random rand = new Random();
			clubDirection = rand.nextInt(4);
			boolean insideCanvas = false;
			char nextCharacter = '\0';
			switch (clubDirection) {
			case 0:
				if (getY() > 0) {
					insideCanvas = true;
					nextCharacter = game.getMap(character.getX(), character.getY() - 1);
				}
				break;
			case 1:
				if (getY() < 8) {
					insideCanvas = true;
					nextCharacter = game.getMap(character.getX(), character.getY() + 1);
				}
				break;
			case 2:
				if (getX() < 8) {
					insideCanvas = true;
					nextCharacter = game.getMap(character.getX() + 1, character.getY());

				}
				break;
			case 3:
				if (getX() > 0) {
					insideCanvas = true;
					nextCharacter = game.getMap(character.getX() - 1, character.getY());
				}
				break;
			}
			if (insideCanvas) {
				if (nextCharacter == ' ' || nextCharacter == '*' || nextCharacter == 'O')
					draw_char = '*';
				else if (nextCharacter == 'k') {
					draw_char = '$';
					on_key = true;
				} else
					can_move = false;
			} else
				can_move = false;
		} while (!can_move);

		moveClub(clubDirection, draw_char, game);

		if (on_key)
			club_on_key = true;

		return true;
	}

	public void moveClub(int clubDirection, char draw_char, Game game) {
		if (club_on_key) {
			game.setMap(coordinates, 'k');
			club_on_key = false;
		} else if (game.getMap(coordinates.getX(), coordinates.getY()) == '*')
			game.setMap(coordinates, ' ');

		switch (clubDirection) {
		case 0:
			setY(character.getY() - 1);
			setX(character.getX());
			break;
		case 1:
			setY(character.getY() + 1);
			setX(character.getX());
			break;
		case 2:
			setY(character.getY());
			setX(character.getX() + 1);
			break;
		case 3:
			setY(character.getY());
			setX(character.getX() - 1);
			break;
		}

		game.setMap(coordinates, draw_char);
	}

}
