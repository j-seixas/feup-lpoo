package dk.logic;

/**
 * Class for the Coordinates of the Game Map
 *
 */
public class Coordinates implements java.io.Serializable {
	private int x;
	private int y;
	
	/**
	 * Creates a set of 2D coordinates
	 * @param x	Coordinate x
	 * @param y	Coordinate y
	 * */
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Creates a set of 2D coordinates which defaults all components to zero
	 * */
	public Coordinates(){
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Copy constructor
	 * @param c Coordinates to be copied
	 * */
	public Coordinates(Coordinates c) {
		x = c.x;
		y = c.y;
	}
	
	
	/**
	 * Gets the x coordinate
	 * @return Returns the x coordinate
	 * */
	public int getX() {
		return x;
	}
	
	/**
	 * Gets the y coordinate
	 * @return Returns the y coordinate
	 * */
	public int getY() {
		return y;
	}

	/**
	 * Sets the x coordinate
	 * @param x New x coordinate
	 * */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Sets the y coordinate
	 * @param y New y coordinate
	 * */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Check whether a coordinate is equal to another
	 * @return Returns the equality of the coordinates
	 * */
	@Override
	public boolean equals(Object o){
		return x == ((Coordinates) o).x && y == ((Coordinates) o).y;
	}
	
}
