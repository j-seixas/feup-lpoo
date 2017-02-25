package dk.logic;

import dk.util.Coordinates;

public class Door {
	private Coordinates coordinates;
	
	public Door(int x, int y) {
		coordinates = new Coordinates(x,y);
	}

	public int getX() {
		return coordinates.getX();
	}
	public int getY() {
		return coordinates.getY();
	}
}
