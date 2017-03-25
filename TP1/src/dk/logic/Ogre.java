package dk.logic;

import java.util.Random;

public class Ogre extends GameCharacter {

	private Club club;
	private boolean ogre_is_stunned = false;
	private boolean sleeping = false;

	/**
	 * Creates an Ogre and its Club
	 * @param x	Coordinate x of its initial position
	 * @param y	Coordinate y of its initial position
	 * */
	public Ogre(int x, int y) {
		super(x, y);
		club = new Club(x, y, this);
	}

	/**
	 * Creates an Ogre and its Club
	 * @param x	Coordinate x of its initial position
	 * @param y	Coordinate y of its initial position
	 * @param sleeping Boolean that specifies the sleeping state of the ogre
	 * */
	public Ogre(int x, int y, boolean sleeping) {
		super(x, y);
		this.sleeping = sleeping;
		club = new Club(x, y, this);
	}
	
	/**
	 * Copy constructor
	 * @param ogre Ogre to be copied
	 * */
	public Ogre(Ogre ogre) {
		coordinates = new Coordinates(ogre.coordinates);
		ogre_is_stunned = ogre.ogre_is_stunned;
		sleeping = ogre.sleeping;
		club = new Club(ogre.club, this);
	}

	/**
	 * Moves the ogre to a random position
	 * @param level Indicates the level on which the ogre will move
	 * @return Returns the possibility to move
	 * */
	public boolean moveCharacter(Level level) {
		if (!sleeping) {
			boolean can_move;
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
						nextCharacter = level.getMap(getX(), getY() - 1);
					}
					break;
				case 1:
					if (getY() < level.getMap().length - 1) {
						insideCanvas = true;
						nextCharacter = level.getMap(getX(), getY() + 1);
					}
					break;
				case 2:
					if (getX() < level.getMap()[0].length - 1) {
						insideCanvas = true;
						nextCharacter = level.getMap(getX() + 1, getY());

					}
					break;
				case 3:
					if (getX() > 0) {
						insideCanvas = true;
						nextCharacter = level.getMap(getX() - 1, getY());
					}
					break;
				}
				if (insideCanvas) {
					if (nextCharacter == 'S' || nextCharacter == 'X' || nextCharacter == 'I')
						can_move = false;
				} else
					can_move = false;
			} while (!can_move);

			moveOgre(ogreDirection);

			club.moveCharacter(level);

			return true;
		}
		return false;
	}

	private void moveOgre(int direction) {
		switch (direction) {
		case 0:
			setY(getY() - 1); break;
		case 1:
			setY(getY() + 1); break;
		case 2:
			setX(getX() + 1); break;
		case 3:
			setX(getX() - 1); break;
		}
	}

	/**
	 * Sets the stun state of the ogre
	 * @param stun Specifies the stun state of the ogre
	 */
	public void setIsStunned(boolean stun) {
		ogre_is_stunned = stun;
	}

	/**
	 * Gets the stun state of the ogre
	 * @return Returns the stun state of the ogre
	 */
	public boolean isStunned() {
		return ogre_is_stunned;
	}

	/**
	 * Gets the ogre's club
	 * @return Returns the ogre's club
	 */
	public Club getClub() {
		return club;
	}

	/**
	 * Gets the sleeping state of the ogre
	 * @return Returns the sleeping state of the ogre
	 */
	public boolean isSleeping() {
		return sleeping;
	}
}
