package dk.logic;

import dk.logic.GameCharacter;

public class Hero extends GameCharacter {

	public Hero(int x, int y) {
		super(x,y);
	}
	
	private Direction direction;
	private boolean hero_has_key = false;
	private boolean hero_has_club = false;
	
	public boolean moveCharacter(Game game) {

		if (direction != Direction.NONE) {

			switch (direction) {
			case UP:
				setY(getY() - 1);
				break;
			case DOWN:
				setY(getY() + 1);
				break;
			case RIGHT:
				setX(getX() + 1);
				break;
			case LEFT:
				setX(getX() - 1);
				break;
			default:
				return false;
			}
			
			return true;
		}
		return false;
	}
	
	//Gets/Sets
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public void setHasKey(boolean key){
		hero_has_key = key;
	}
	public boolean getHasKey(){
		return hero_has_key;
	}
	public void setHasClub(boolean club){
		this.hero_has_club = club;
	}
	public boolean getHasClub(){
		return hero_has_club;
	}

}
