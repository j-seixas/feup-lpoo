package dk.logic;

import java.util.Random;

public class Club extends GameCharacter {

	public GameCharacter character;
	
	public Club(int x, int y, GameCharacter c) {
		super(x, y);
		character = c;
	}

	public boolean moveCharacter(Game game) {
		boolean can_move;
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
				if (nextCharacter == 'X' || nextCharacter == 'I' || nextCharacter == 'S')
					can_move = false;
			} else
				can_move = false;
		} while (!can_move);

		moveClub(clubDirection, game);

		return true;
	}

	public void moveClub(int clubDirection, Game game) {
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
	}

}
