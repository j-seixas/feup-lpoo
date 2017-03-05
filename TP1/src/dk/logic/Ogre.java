package dk.logic;

import java.util.Random;

public class Ogre extends GameCharacter {

	private Club club;
	private boolean ogre_is_stunned = false; 

	public Ogre(int x, int y) {
		super(x, y);
		club = new Club(x, y, this);
	}

	public boolean moveCharacter(Game game) {
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
				if (nextCharacter == 'S' || nextCharacter == 'X' || nextCharacter == 'I')
					can_move = false;
			} else
				can_move = false;
		} while (!can_move);

		moveOgre(ogreDirection, game);

		club.moveCharacter(game);
		
		return true;
	}

	private void moveOgre(int direction, Game game) {

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

	}
	
	public void setIsStunned(boolean stun){
		ogre_is_stunned = stun;
	}
	
	public boolean isStunned(){
		return ogre_is_stunned;
	}
	
	public Club getClub(){
		return club;
	}
}
