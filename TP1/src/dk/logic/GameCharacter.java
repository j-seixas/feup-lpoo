package dk.logic;

import dk.util.Coordinates;

public abstract class GameCharacter {
	public enum Direction {
		UP, DOWN, RIGHT, LEFT, NONE
	};
	
	protected Coordinates coordinates;
	
	//Constructors
	public GameCharacter(int x, int y){
		coordinates = new Coordinates(x,y);
	}
	public GameCharacter(){
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
	public Coordinates getCoord(){
		return coordinates;
	}
	public void setCoord(Coordinates coord){
		coordinates = coord;
	}
	
	public Coordinates getCoord(){
		return coordinates;
	}
	
	//Methods
	protected abstract boolean moveCharacter(Game game);
	
	public boolean checkColision(GameCharacter character) {
		return (character.getX() == this.getX() && character.getY() == this.getY())
		|| (character.getX() == this.getX() && (character.getY() == this.getY() + 1 || character.getY() == this.getY() - 1))
		|| (character.getY() == this.getY() && (character.getX() == this.getX() + 1 || character.getX() == this.getX() - 1));
		}
	
}
