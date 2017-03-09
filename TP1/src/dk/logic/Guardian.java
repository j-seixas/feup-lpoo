package dk.logic;

public abstract class Guardian extends GameCharacter {
	boolean hasPath = false;
	
	protected Coordinates path[];

	protected int indexPath = 0;
	protected boolean sleeping = false;

	public Guardian(Coordinates[] guard_path) {
		hasPath = true;
		path = guard_path;
		coordinates = path[indexPath];
	}
	
	public Guardian(int x, int y){
		super(x,y);
	}
	
	protected abstract boolean moveCharacter(Game game);
	
	public boolean IsSleeping(){
		return sleeping;
	}
}
