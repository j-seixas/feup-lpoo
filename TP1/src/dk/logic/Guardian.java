package dk.logic;

/**
 * Abstract Class for the different types of Guardians
 * 
 * @see GameCharacter
 */
public abstract class Guardian extends GameCharacter{
	
	protected Coordinates path[] = {
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

	protected int indexPath;
	protected boolean sleeping = false;

	/**
	 * Creates a Guardian with the default path
	 * */
	public Guardian(){
		indexPath = 0;
		this.coordinates = this.path[indexPath];
	}
	
	/**
	 * Creates a Guardian that doesn't move
	 * @param x	Coordinate x of its initial position
	 * @param y	Coordinate y of its initial position
	 * */
	public Guardian(int x, int y){
		indexPath = 0;
		this.path = new Coordinates[] { new Coordinates(x, y) };
		this.coordinates = this.path[indexPath];
	}
	
	/**
	 * Creates a Guardian with a specific path
	 * @param path Specifies the path the guardian will walk on
	 * */
	public Guardian(Coordinates path[]) {
		indexPath = 0;
		this.path = path;
		this.coordinates = this.path[indexPath];
	}
		
	protected abstract boolean moveCharacter(Level level);
	
	/**
	 * Checks if the guardian is sleeping
	 * @return Returns whether or not the guardian is sleeping
	 */
	public boolean IsSleeping(){
		return sleeping;
	}

	/**
	 * Makes the guardian go to sleep
	 */
	public void sleep(){
		sleeping = true;
	}
}
