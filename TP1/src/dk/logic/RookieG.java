package dk.logic;

public class RookieG extends Guardian {

	public RookieG() {
		super();
	}

	public RookieG(int x, int y) {
		super(x, y);
	}

	public boolean moveCharacter(Game game) {
		if (hasPath) {
		this.indexPath++;	
		if (this.indexPath >= path.length)
			this.indexPath = 0;
		
		Coordinates newCoordinates = path[indexPath];
		setCoord(newCoordinates);
		
		return true;
		}
		return false;
	}

}
