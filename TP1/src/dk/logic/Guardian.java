package dk.logic;
import dk.util.Coordinates;

public class Guardian extends Character {
	
	private Coordinates path[] = {
			new Coordinates(8,1),
			new Coordinates(7,1),
			new Coordinates(7,2),
			new Coordinates(7,3),
			new Coordinates(7,4),
			new Coordinates(7,5),
			new Coordinates(6,5),
			new Coordinates(5,5),
			new Coordinates(4,5),
			new Coordinates(3,5),
			new Coordinates(2,5),
			new Coordinates(1,5),
			new Coordinates(1,6),
			new Coordinates(2,6),
			new Coordinates(3,6),
			new Coordinates(4,6),
			new Coordinates(5,6),
			new Coordinates(6,6),
			new Coordinates(7,6),
			new Coordinates(8,6),
			new Coordinates(8,5),
			new Coordinates(8,4),
			new Coordinates(8,3),
			new Coordinates(8,2)
	};

	private int indexPath = 0;
	

	public Guardian() {
		coordinates = path[indexPath];
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
