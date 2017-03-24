package dk.logic;

import java.util.Random;

public class Ogre extends GameCharacter {

	private Club club;
	private boolean ogre_is_stunned = false;
	private boolean sleeping = false;

	public Ogre(int x, int y) {
		super(x, y);
		club = new Club(x, y, this);
	}

	public Ogre(int x, int y, boolean sleeping) {
		super(x, y);
		this.sleeping = sleeping;
		club = new Club(x, y, this);
	}

	public Ogre(Ogre ogre) {
		coordinates = new Coordinates(ogre.coordinates);
		ogre_is_stunned = ogre.ogre_is_stunned;
		sleeping = ogre.sleeping;
		club = new Club(ogre.club, this);
	}

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

	public void setIsStunned(boolean stun) {
		ogre_is_stunned = stun;
	}

	public boolean isStunned() {
		return ogre_is_stunned;
	}

	public Club getClub() {
		return club;
	}

	public boolean isSleeping() {
		return sleeping;
	}
}
