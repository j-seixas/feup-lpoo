package dk.logic;

import dk.util.Coordinates;

public abstract class Character {
	public enum Direction {
		UP, DOWN, RIGHT, LEFT, NONE
	};
	
	private Coordinates coordinates;
	
	//Constructors
	public Character(int x, int y){
		coordinates = new Coordinates(x,y);
	}
	public Character(){
		coordinates = new Coordinates();
	}
	
	//Gets/Sets
	public int getX() {
		return coordinates.getX();
	}
	public int getY() {
		return coordinates.getY();
	}
	public void setX(int x) {
		coordinates.setX(x);
	}
	public void setY(int y) {
		coordinates.setY(y);
	}
	
	//Methods
	protected abstract boolean moveCharacter(char[][] map);
	
}
