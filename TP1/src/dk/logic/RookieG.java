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

		game.setMap(this.coordinates, ' ');
		
		Coordinates newCoordinates = path[indexPath];
		setCoord(newCoordinates);
		
		game.setMap(this.coordinates, 'G');
		return true;
	}

}
