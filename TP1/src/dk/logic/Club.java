package dk.logic;

import java.util.Random;

/**
 * Class for the Game Character Ogre's Club
 * @see GameCharacter
 */
public class Club extends GameCharacter {

	public GameCharacter character;

	/**
	 * Creates a Club, bound to a generic GameCharacter
	 * @param x	Coordinate x of its initial position
	 * @param y	Coordinate y of its initial position
	 * @param c GameCharacter to whom the club is bound to
	 * */
	public Club(int x, int y, GameCharacter c) {
		super(x, y);
		character = c;
	}
	
	/**
	 * Copy Constructor
	 * @param club Club to be copied
	 * @param ogre Ogre to whom its bound to
	 * */
	public Club(Club club, Ogre ogre){
		this.coordinates = new Coordinates(club.coordinates);
		this.character = ogre; 
	}

	/**
	 * Moves the club to a random position next to its character
	 * @param level Indicates the level on which the club will move
	 * @return Returns the possibility to move
	 * */
	public boolean moveCharacter(Level level) {
		if (!((Ogre) character).isSleeping()) {

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
					if (character.getY() > 0) {
						insideCanvas = true;
						nextCharacter = level.getMap(character.getX(), character.getY() - 1);
					}
					break;
				case 1:
					if (character.getY() < level.getMap().length - 1) {
						insideCanvas = true;
						nextCharacter = level.getMap(character.getX(), character.getY() + 1);
					}
					break;
				case 2:
					if (character.getX() < level.getMap()[0].length - 1) {
						insideCanvas = true;
						nextCharacter = level.getMap(character.getX() + 1, character.getY());

					}
					break;
				case 3:
					if (character.getX() > 0) {
						insideCanvas = true;
						nextCharacter = level.getMap(character.getX() - 1, character.getY());
					}
					break;
				}
				if (insideCanvas) {
					if (nextCharacter == 'X' || nextCharacter == 'I' || nextCharacter == 'S')
						can_move = false;
				} else
					can_move = false;
			} while (!can_move);

			moveClub(clubDirection);

			return true;
		}
		return false;
	}

	private void moveClub(int clubDirection) {
		switch (clubDirection) {
		case 0: setY(character.getY() - 1); setX(character.getX());
			break;
		case 1: setY(character.getY() + 1); setX(character.getX());
			break;
		case 2: setY(character.getY()); setX(character.getX() + 1);
			break;
		case 3: setY(character.getY()); setX(character.getX() - 1);
			break;
		}
	}
}
