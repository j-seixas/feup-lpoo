package dk.logic;

/**
 * Class for the Game's Doors
 *
 */
public class Door implements java.io.Serializable {
	private Coordinates coordinates;
	private boolean door_is_open = false;
	 
	
	/**
	 * Creates a door in the specified coordinates
	 * @param x	Coordinate x of its initial position
	 * @param y	Coordinate y of its initial position
	 * */
	public Door(int x, int y) {
		coordinates = new Coordinates(x,y);
	}

	/**
	 * Copy constructor
	 * @param d	Door to be copied
	 * */
	public Door(Door d) {
		door_is_open = d.door_is_open;
		coordinates = new Coordinates(d.coordinates);
	}

	/**
	 * Gets the door's coordinates
	 * @return Return the door's coordinates
	 * */
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	/**
	 * Sets the door's coordinates
	 * @param coordinates New coordinates of the door
	 * */
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	
	/**
	 * Opens the door
	 * */
	public void openDoor(){
		door_is_open = true;
	}
	
	/**
	 * Checks if the door is open
	 * @return Returns true if the door is open. False otherwise.
	 * */
	public boolean isOpen(){
		return door_is_open;
	}

	/**
	 * Gets the x coordinate of the door	
	 * @return Returns the x coordinate of the door
	 */
	public int getX() {
		return coordinates.getX();
	}
	
	/**
	 * Gets the y coordinate of the door	
	 * @return Returns the y coordinate of the door
	 */
	public int getY() {
		return coordinates.getY();
	}
}
