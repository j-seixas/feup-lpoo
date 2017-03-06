package dk.logic;

import java.util.Objects;

public class Coordinates {
	private int x;
	private int y;
	
	//Constructors
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	public Coordinates(){
		this.x = 0;
		this.y = 0;
	}
	
	//Gets
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	//Sets
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o) {
		// self check
	    if (this == o)
	        return true;
	    // null check
	    if (o == null)
	        return false;
	    // type check and cast
	    if (getClass() != o.getClass())
	        return false;
	    Coordinates coord = (Coordinates) o;
	    return Objects.equals(this.x, coord.getX())
	            && Objects.equals(this.y, coord.getY());
	}
	/*
	public boolean equals(Coordinates c){
		if(this.x == c.getX() && this.y == c.getY())
			return true;
		else
			return false;
	}*/
}
