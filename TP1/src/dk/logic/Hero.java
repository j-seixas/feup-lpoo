package dk.logic;

import dk.logic.GameCharacter;

public class Hero extends GameCharacter{

	private Direction direction;
	private boolean hero_has_key = false;
	private boolean hero_has_club = false;
	
	
	/**
	 * Creates a GameCharacter
	 * @param x	Coordinate x of its initial position
	 * @param y	Coordinate y of its initial position
	 * */
	public Hero(int x, int y) {
		super(x,y);
	}
	
	/**
	 * Copy Constructor
	 * @param h Hero to be copied
	 * */
	public Hero(Hero h){
		this.coordinates = new Coordinates(h.getX(), h.getY());
		this.hero_has_club = h.hero_has_club;
	}

	/**
	 * Moves the hero based on the user input
	 * @param level Indicates the level on which the hero will move
	 * @return Returns the possibility to move	
	 */
	public boolean moveCharacter(Level level) {

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
	
	/**
	 * Gets the hero's last direction
	 * @return Returns the hero's last direction
	 */
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * Sets the hero's last direction
	 * @param direction Specifies the direction of the hero
	 * */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	/**
	 * Gives/takes the key to/from the hero
	 * @param b Specifies whether a key is being given or taken to/from the hero
	 * */
	public void setHasKey(boolean b){
		hero_has_key = b;
	}
	
	/**
	 * Checks if the hero has a key
	 * @return Returns whether or not the hero has a key
	 */
	public boolean getHasKey(){
		return hero_has_key;
	}
	
	/**
	 * Gives/takes the club to/from the hero
	 * @param b Specifies whether a club is being given or taken to/from the hero
	 * */
	public void setHasClub(boolean b){
		this.hero_has_club = b;
	}
	
	/**
	 * Checks if the hero has a club
	 * @return Returns whether or not the hero has a club
	 */
	public boolean getHasClub(){
		return hero_has_club;
	}

}
