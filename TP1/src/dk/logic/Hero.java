package dk.logic;

import dk.logic.Character;

public class Hero extends Character {

	private Direction direction;
	
	public boolean moveCharacter(Game game) {

		if (direction != Direction.NONE) {
			game.setMap(this.coordinates, ' ');

			switch (direction) {
			case UP:
				setY(getY() - 1);
				break;
			case DOWN:
				setY(getY() + 1);
				break;
			case RIGHT:
				setX(getX() + 1);
				break;
			case LEFT:
				setX(getX() - 1);
				break;
			default:
				return false;
			}

			game.setMap(this.coordinates, 'H');
			return true;
		}
		return false;
	}
	
	//Gets/Sets
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	

}
