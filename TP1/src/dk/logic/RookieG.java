package dk.logic;

import dk.util.Coordinates;

public class RookieG extends Guardian {

	public RookieG() {
		super();
	}

	public boolean moveCharacter(Game game) {
		this.indexPath++;	
		if (this.indexPath >= path.length)
			this.indexPath = 0;
		
		Coordinates newCoordinates = path[indexPath];
		setCoord(newCoordinates);
		
		return true;
	}

}
