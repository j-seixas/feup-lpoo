package dk.logic;

import java.util.Random;

public class Club extends Character{

	public Club(int x, int y) {
		super(x,y);
	}
	
	private boolean club_on_key = false;
	
	public boolean moveCharacter(Game game) {
		boolean can_move = true;
		char draw_char = '\0';
		int clubDirection;
		do {
		Random rand = new Random();
		clubDirection = rand.nextInt(4);
		boolean insideCanvas = false;
		char nextCharacter = '\0';
		switch (clubDirection) {
		case 0:
			if (getY() > 0) {
				insideCanvas = true;
				nextCharacter = game.getMap(game.getOgre().getY() - 1,game.getOgre().getX());
			}
			break;
		case 1:
			if (getY() < 9) {
				insideCanvas = true;
				nextCharacter = game.getMap(game.getOgre().getY() + 1,game.getOgre().getX());
			}
			break;
		case 2:
			if (getX() < 9) {
				insideCanvas = true;
				nextCharacter = game.getMap(game.getOgre().getY(),game.getOgre().getX() + 1);

			}
			break;
		case 3:
			if (getX() > 0) {
				insideCanvas = true;
				nextCharacter = game.getMap(game.getOgre().getY(),game.getOgre().getX() - 1);
			}
			break;
		}
		if (insideCanvas) {
			if (nextCharacter == ' ')
				draw_char = '*';
			else if(nextCharacter == 'k'){
				draw_char = '$';
				club_on_key = true;
			}
			else can_move = false; 
		}
		else can_move = false;
		}while(!can_move);
		
	
		moveClub(clubDirection, draw_char, game);
		
		return true;
	}

	public void moveClub(int clubDirection, char draw_char, Game game) {
		if(club_on_key){
			game.setMap(coordinates, 'k');
			club_on_key = false;
		}
		else game.setMap(coordinates, ' ');


		switch (clubDirection) {
		case 0:
			setY(game.getOgre().getY() - 1);
			setX(game.getOgre().getX());
			break;
		case 1:
			setY(game.getOgre().getY() + 1);
			setX(game.getOgre().getX());
			break;
		case 2:
			setY(game.getOgre().getY());
			setX(game.getOgre().getX() + 1);
			break;
		case 3:
			setY(game.getOgre().getY());
			setX(game.getOgre().getX() - 1);
			break;
		}

		game.setMap(coordinates, draw_char);
	}

}
