package dk.logic;

import java.util.Random;

import dk.util.Coordinates;

public class DrunkenG extends Guardian {
	private boolean dir = true;
	private int wait = 0;

	public DrunkenG() {
		super();
	}

	private int directionMove() {
		if (wait == 0) {
			Random rand = new Random();
			int sleep = rand.nextInt(5);
			if (sleep == 0) {
				wait = 2;
				sleeping = true;
				return 1;
			}
			if (dir) {
				indexPath = indexPath + 1;
				if (indexPath >= path.length)
					indexPath = 0;
			} else {
				indexPath--;
				if (indexPath < 0)
					indexPath = path.length - 1;
			}
			return 0;
		} else if (wait == 1) {
			Random rand = new Random();
			int direction = rand.nextInt(2);
			if (direction == 1)
				dir = true;
			else
				dir = false;
		}
		return 1;
	}

	public boolean moveCharacter(Game game) {
		if (directionMove() == 1) {
			game.setMap(this.coordinates, 'g');
			wait--;
			return true;
		}

		game.setMap(this.coordinates, ' ');

		Coordinates newCoordinates = path[indexPath];
		setCoord(newCoordinates);
		sleeping = false;
		game.setMap(this.coordinates, 'G');

		return true;
	}

}
