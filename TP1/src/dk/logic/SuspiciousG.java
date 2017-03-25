package dk.logic;

import java.util.Random;

public class SuspiciousG extends Guardian {
	private boolean dir = true;
	private int wait = 0;

	/**
	 * Creates a Suspicious Guardian with the default path
	 * */
	public SuspiciousG() {
		super();
	}
	
	/**
	 * Creates a Suspicious Guardian that doesn't move
	 * @param x	Coordinate x of its initial position
	 * @param y	Coordinate y of its initial position
	 * */
	public SuspiciousG(int x, int y) {
		super(x, y);
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

	/**
	 * Moves the guardian to its next position. There is a possibility for the guardian to change his direction.
	 * @param level Indicates the level on which the guardian will move
	 * @return Returns true
	 * */
	public boolean moveCharacter(Level level) {
		directionMove();
		wait--;

		Coordinates newCoordinates = path[indexPath];
		setCoord(newCoordinates);

		return true;
	}

}
