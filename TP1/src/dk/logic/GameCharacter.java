package dk.logic;

/**
 * Abstract Class for the All the Game Characters
 *
 */
public abstract class GameCharacter implements java.io.Serializable{
	public enum Direction {
		UP, DOWN, RIGHT, LEFT, NONE
	};
	
	protected Coordinates coordinates;
	

	/**
	 * Creates a GameCharacter
	 * @param x	Coordinate x of its initial position
	 * @param y	Coordinate y of its initial position
	 * */
	public GameCharacter(int x, int y){
		coordinates = new Coordinates(x,y);
	}
	
	/**
	 * Creates a GameCharacter with default coordinates
	 * */
	public GameCharacter(){
		coordinates = new Coordinates();
	}
	
	/**
	 * Gets the x coordinate
	 * @return Returns the x coordinate
	 * */
	public int getX() {
		return coordinates.getX();
	}
	
	/**
	 * Gets the y coordinate
	 * @return Returns the y coordinate
	 * */
	public int getY() {
		return coordinates.getY();
	}
	
	/**
	 * Sets the x coordinate
	 * @param x New x coordinate
	 * */
	public void setX(int x) {
		coordinates.setX(x);
	}
	
	/**
	 * Sets the y coordinate
	 * @param y New y coordinate
	 * */
	public void setY(int y) {
		coordinates.setY(y);
	}
	
	/**
	 * Gets the GameCharacter coordinates
	 * @return Returns the GameCharacter coordinates
	 * */
	public Coordinates getCoord(){
		return coordinates;
	}
	
	/**
	 * Sets the GameCharacter coordinates
	 * @param coord Specifies the new GameCharacter coordinates
	 * */
	public void setCoord(Coordinates coord){
		coordinates = coord;
	}
	
	protected abstract boolean moveCharacter(Level level);
	
	/**
	 * Checks whether or not a character is colliding with another character
	 * @param character Character to perform the check to
	 * @return Returns whether or not a character is colliding with another character
	 */
	public boolean checkColision(GameCharacter character) {
		return (character.getX() == this.getX() && character.getY() == this.getY())
		|| (character.getX() == this.getX() && (character.getY() == this.getY() + 1 || character.getY() == this.getY() - 1))
		|| (character.getY() == this.getY() && (character.getX() == this.getX() + 1 || character.getX() == this.getX() - 1));
		}
}
