package dk.logic;

import java.util.Random;


public class Ogre extends Character {

	private boolean ogre_on_key = false;
	
	
	public Ogre(int x, int y) {
		super(x,y);
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
				nextCharacter = game.getMap(getY() - 1,getX());
			}
			break;
		case 1:
			if (getY() < 8) {
				insideCanvas = true;
				nextCharacter = game.getMap(getY() + 1,getX());
			}
			break;
		case 2:
			if (getX() < 8) {
				insideCanvas = true;
				nextCharacter = game.getMap(getY(),getX() + 1);

			}
			break;
		case 3:
			if (getX() > 0) {
				insideCanvas = true;
				nextCharacter = game.getMap(getY(),getX() - 1);
			}
			break;
		}
		if (insideCanvas) {
			if (nextCharacter == ' ' || nextCharacter == '*')
				draw_char = 'O';
			else if(nextCharacter == 'k'){
				draw_char = '$';
				on_key = true;
			}
			else can_move = false; 
		}
		else can_move = false;
		}while(!can_move);
		
		
		moveOgre(ogreDirection, draw_char, game);
		
		if(on_key)
			ogre_on_key = true;
		
		return true;
	}

	private void moveOgre(int direction, char draw_char, Game game) {
		if(ogre_on_key){
			game.setMap(coordinates,'k');
			ogre_on_key = false;
		}
		else game.setMap(coordinates,' ');


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

		game.setMap(coordinates,draw_char);
	}
	
}
