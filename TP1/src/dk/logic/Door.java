package dk.logic;

public class Door implements java.io.Serializable {
	private Coordinates coordinates;
	private boolean door_is_open = false;
	 
	public Door(int x, int y) {
		coordinates = new Coordinates(x,y);
	}

	
	public Door(Door i) {
		door_is_open = i.door_is_open;
		coordinates = new Coordinates(i.coordinates);
	}


	public Coordinates getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	public void openDoor(){
		door_is_open = true;
	}
	public boolean isOpen(){
		return door_is_open;
	}

	public int getX() {
		return coordinates.getX();
	}
	
	public int getY() {
		return coordinates.getY();
	}
}
