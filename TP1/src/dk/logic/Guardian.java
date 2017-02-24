package dk.logic;
import dk.util.Coordinates;

public class Guardian extends Character {
	
	private Coordinates path[] = {
			new Coordinates(0,0) /*TODO*/
	};

	private int indexPath = 0;
	
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
