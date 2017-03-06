package dk.logic;

import java.util.Random;

public class SuspiciousG extends Guardian {
	private boolean dir = true;
	private int wait = 0;

	public SuspiciousG() {
		super();
	}

	private void directionMove() {
		if (wait <= 0) {
			Random rand = new Random();
			int change_dir = rand.nextInt(5);
			if (change_dir == 1) {
				wait = 3;
				dir = !dir;
			}
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
	}

	public boolean moveCharacter(Game game) {
		directionMove();
		wait--;


		Coordinates newCoordinates = path[indexPath];
		setCoord(newCoordinates);

		return true;
	}

}
