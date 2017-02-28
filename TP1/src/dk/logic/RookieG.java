package dk.logic;

import dk.util.Coordinates;

public class RookieG extends Guardian{

	public RookieG() {
		super();
	}

	public boolean moveCharacter(Game game) {
		indexPath++;
		if(indexPath >= path.length)
			indexPath = 0;
		
		game.setMap(this.coordinates, ' ');
		
		Coordinates newCoordinates = path[indexPath];
		setX(newCoordinates.getX());
		setY(newCoordinates.getY());

		game.setMap(this.coordinates, 'G');
		
		return true;
	}

}
