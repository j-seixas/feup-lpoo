package dk.logic;

/**
 * Class for the Game Character Rookie Guardian
 * 
 * @see Guardian
 */
public class RookieG extends Guardian {

	/**
	 * Creates a Rookie Guardian with the default path
	 * */
	public RookieG() {
		super();
	}
	
	/**
	 * Creates a Rookie Guardian with a specific path
	 * @param path Specifies the path the guardian will walk on
	 * */
	public RookieG(Coordinates path[]){
		super(path);
	}
	
	/**
	 * Creates a Rookie Guardian that doesn't move
	 * @param x	Coordinate x of its initial position
	 * @param y	Coordinate y of its initial position
	 * */
	public RookieG(int x, int y){
		super(x, y);
	}

	/**
	 * Copy constructor 
	 * @param g	Guardian to be copied
	 * */
	public RookieG(Guardian g) {
		super(g.getX(), g.getY());
	}

	/**
	 * Moves the guardian to its next position
	 * @param level Indicates the level on which the guardian will move
	 * @return Returns whether or not the guardian moved
	 * */
	public boolean moveCharacter(Level level) {
		if (!sleeping) {
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
