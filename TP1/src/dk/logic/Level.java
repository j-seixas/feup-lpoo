package dk.logic;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class for a Game Level
 *
 */
public class Level implements java.io.Serializable {

	private Hero hero;
	private ArrayList<Ogre> ogres;
	private ArrayList<Guardian> guardians;
	private Coordinates key;
	private ArrayList<Door> doors;
	private char initialMap[][];
	private char currentMap[][];
	private boolean won_by_lever;

	/**
	 * Creates a Level (initializes everything with null)
	 */
	public Level() {
		hero = null;
		key = null;
		won_by_lever = true;
		ogres = new ArrayList<Ogre>();
		guardians = new ArrayList<Guardian>();
		doors = new ArrayList<Door>();
	}
	
	/**
	 * Creates a Level (Copy Constructor)
	 * @param level Level to copy
	 */
	public Level(Level level){
		this();
		hero = new Hero(level.hero);
		won_by_lever = level.won_by_lever;
		initialMap = level.initialMap;
		cloneMap();
		for(Ogre i : level.ogres)
			ogres.add(new Ogre(i));
		for(Guardian i : level.guardians){
			guardians.add(new RookieG(i));
		}
		for(Door i : level.doors)
			doors.add(new Door(i));
		key = new Coordinates(level.key);
		updateMap();
	}

	private void cloneMap() {
		currentMap = new char[initialMap.length][initialMap[0].length];
		for (int i = 0; i < initialMap.length; i++)
			currentMap[i] = (char[]) initialMap[i].clone();
	}

	/**
	 * Opens the Doors
	 */
	public void openDoors() {
		for (int i = 0; i < doors.size(); i++) {
			doors.get(i).openDoor();
		}
	}

	/**
	 * Gets the Char of the next position of the Hero
	 * @param direction Direction of the Movement
	 * @param insideCanvas If it's inside the map
	 * @return Returns the Char
	 */
	public char getNextCharacter(GameCharacter.Direction direction, AtomicBoolean insideCanvas) {
		hero.setDirection(direction);
		switch (direction) {
		case UP:
			if (hero.getY() > 0) {
				insideCanvas.set(true);
				return currentMap[hero.getY() - 1][hero.getX()];
			}
			break;
		case DOWN:
			if (hero.getY() < currentMap.length - 1) {
				insideCanvas.set(true);
				return currentMap[hero.getY() + 1][hero.getX()];
			}
			break;
		case RIGHT:
			if (hero.getX() < currentMap.length - 1) {
				insideCanvas.set(true);
				return currentMap[hero.getY()][hero.getX() + 1];
			}
			break;
		case LEFT:
			if (hero.getX() > 0) {
				insideCanvas.set(true);
				return currentMap[hero.getY()][hero.getX() - 1];
			}
			break;
		default:
			break;
		}
		return '\0';
	}

	/**
	 * Handler of NPCs' Movement 
	 * @return Returns whether or not they found/killed the Hero
	 */
	public boolean handleNPC() {
		moveEnemies();
		return checkCollisions(this.hero);
	}

	/**
	 * Checks Collision of NPCs with a character
	 * @param character Game Character
	 * @return Returns whether or not there was a collision
	 */
	public boolean checkCollisions(GameCharacter character) {
		boolean collision = false;
		for (Guardian currentGuardian : guardians) {
			if (currentGuardian.checkColision(character) && !currentGuardian.IsSleeping())
				collision = true;
		}
		for (Ogre currentOgre : ogres) {
			if (!currentOgre.isStunned() && currentOgre.checkColision(character))
				collision = true;
			if (currentOgre.getClub().checkColision(character))
				collision = true;
		}
		return collision;
	}

	/**
	 * Moves the NPCs
	 */
	public void moveEnemies() {
		for (Guardian currentGuardian : guardians) {
			currentGuardian.moveCharacter(this);
		}
		for (Ogre currentOgre : ogres) {
			currentOgre.moveCharacter(this);
			currentOgre.setIsStunned(currentOgre.checkColision(hero) && hero.getHasClub());
		}
	}

	/**
	 * Updates/Draws the Doors in the Game map
	 */
	public void updateDoors(){
		char draw_char;

		// Draw doors
		for (Door currentDoor : doors) {
			if (currentDoor.isOpen())
				draw_char = 'S';
			else
				draw_char = 'I';
			setMap(currentDoor.getCoordinates(), draw_char);
		}
	}
	
	/**
	 * Updates/Draws the key in the Game map
	 */
	public void updateKey(){
		// Draw key
		if (hero != null) {
			if ((!hero.getHasKey() || won_by_lever) && key != null)
				setMap(key, 'k');
		} else if (key != null)
		setMap(key, 'k');
	}
	
	/**
	 * Updates/Draws the Hero in the Game map
	 */
	public void updateHero(){
		char draw_char;
		
		// Draw hero
		if (hero != null) {
			if (hero.getHasKey() && !won_by_lever)
				draw_char = 'K';
			else if (hero.getHasClub())
				draw_char = 'A';
			else
				draw_char = 'H';
			setMap(hero.getCoord(), draw_char);
		}
	}
	
	/**
	 * Updates/Draws the Guardians in the Game map
	 */
	public void updateGuardians(){
		char draw_char;
		
		// Draw Guardian
		for (Guardian currentGuardian : guardians) {
			if (currentGuardian.IsSleeping())
				draw_char = 'g';
			else
				draw_char = 'G';
			setMap(currentGuardian.getCoord(), draw_char);
		}
	}
	
	/**
	 * Updates/Draws the Ogres and Clubs in the Game map
	 */
	public void updateOgres(){
		for (Ogre currentOgre : ogres) {
			// Draw Ogre
			if (key != null && hero != null){
				if (currentOgre.getCoord().equals(key) && !hero.getHasKey())
					setMap(currentOgre.getCoord(), '&');
				else if (currentOgre.isStunned())
					setMap(currentOgre.getCoord(), '8');
				else
					setMap(currentOgre.getCoord(), 'O');
			} else if (currentOgre.isStunned())
				setMap(currentOgre.getCoord(), '8');
			else
				setMap(currentOgre.getCoord(), 'O');
			// Draw Club
			if (key != null && hero != null){
				if (currentOgre.getClub().getCoord().equals(key) && !hero.getHasKey())
					setMap(currentOgre.getClub().getCoord(), '$');
				else if (getMap(currentOgre.getClub().getCoord()) != 'O')
					setMap(currentOgre.getClub().getCoord(), '*');
			} else if (getMap(currentOgre.getClub().getCoord()) != 'O')
				setMap(currentOgre.getClub().getCoord(), '*');

		}
	}
	
	/**
	 * Updates the Map and all its elements
	 */
	public void updateMap() {
		cloneMap();
	
		updateDoors();
		updateKey();
		updateHero();
		updateGuardians();
		updateOgres();
	}

	// Wrappers / Getters / Setters

	/**
	 * Sets the Game Map
	 * @param map Game map
	 */
	public void setMap(char map[][]) {
		initialMap = map;
		cloneMap();
	}
	
	/**
	 * Sets the Doors
	 * @param doors Doors of the Level
	 */
	public void setDoors(ArrayList<Door> doors){
		this.doors = doors;
	}
	
	/**
	 * Sets the Guardians
	 * @param guardians Guardians of the Level
	 */
	public void setGuardians(ArrayList<Guardian> guardians){
		this.guardians = guardians;
	}
	
	/**
	 * Sets the Ogres
	 * @param ogres Ogres of the Level
	 */
	public void setOgres(ArrayList<Ogre> ogres){
		this.ogres = ogres;
		updateMap();
		for (Ogre o : ogres) {
			o.getClub().moveCharacter(this);
		}
		updateMap();
	}

	/**
	 * Changes a Char in the Game's Level map 
	 * @param coordinate Coordinates of the char to change
	 * @param ch Char to change
	 */
	public void setMap(Coordinates coordinate, char ch) {
		currentMap[coordinate.getY()][coordinate.getX()] = ch;
	}

	/**
	 * Gets a Char of the Game's Level map
	 * @param coord Coordinates of the char
	 * @return Returns the char from the map based on the coordinates
	 */
	public char getMap(Coordinates coord) {
		return currentMap[coord.getY()][coord.getX()];
	}

	/**
	 * Gets a Char of the Game's Level map
	 * @param x Coordinate x of the Game's Level map
	 * @param y Coordinate y of the Game's Level map
	 * @return Returns the char from the map based on the coordinates
	 */
	public char getMap(int x, int y) {
		return currentMap[y][x];
	}

	/**
	 * Gets the Hero
	 * @return Returns the Hero
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * Gets the Game's Level Map
	 * @return Returns the Game's Level Map
	 */
	public char[][] getMap() {
		return currentMap;
	}

	/**
	 * Sets whether its a Lever or a Key
	 * @param bool True if its a lever, false if its a key
	 */
	public void setWonByLever(boolean bool) {
		won_by_lever = bool;
	}

	/**
	 * Sets the Game Hero
	 * @param hero Hero of the Level
	 */
	public void setHero(Hero hero) {
		this.hero = hero;
	}

	/**
	 * Adds an Ogre
	 * @param ogre Ogre to add
	 */
	public void addOgre(Ogre ogre) {
		ogres.add(ogre);
		updateMap();
		ogre.getClub().moveCharacter(this);
	}

	/**
	 * Adds a Guardian
	 * @param guardian Guardian to add
	 */
	public void addGuardian(Guardian guardian) {
		guardians.add(guardian);
	}

	/**
	 * Adds a Door
	 * @param door Door to add
	 */
	public void addDoor(Door door) {
		doors.add(door);
	}

	/**
	 * Adds an element
	 * @param c Char of the element to add
	 * @param x Coordinate x
	 * @param y Coordinate y
	 */
	public void addElement(char c, int x, int y) {
		initialMap[y][x] = c;
	}

	/**
	 * Sets the Level's key
	 * @param key Key of the Level
	 */
	public void setKey(Coordinates key) {
		this.key = key;
	}

	/**
	 * Checks if can add the Hero
	 * @param hero Hero to check
	 * @return Returns whether or not its possible to add the Hero
	 */
	public boolean canAdd(Hero hero) {
		return !checkCollisions(hero) && getMap(hero.getCoord()) == ' ';
	}

	/**
	 * Checks if can add an Ogre
	 * @param ogre Ogre to check
	 * @return Returns whether or not its possible to add the Ogre
	 */
	public boolean canAdd(Ogre ogre) {
		if (ogres.size() >= Game.MAX_OGRES)
			return false;
		if (getMap(ogre.getCoord()) != ' ' && getMap(ogre.getCoord()) != 'G' && getMap(ogre.getCoord()) != 'O'
				&& getMap(ogre.getCoord()) != '*')
			return false;
		if (hero == null)
			return true;
		if (!hero.checkColision(ogre))
			return true;
		return false;
	}

	/**
	 * Checks if can add a Guardian
	 * @param guardian Guardian to check
	 * @return Returns whether or not its possible to add the Guardian
	 */
	public boolean canAdd(Guardian guardian) {
		if (getMap(guardian.getCoord()) != ' ' && getMap(guardian.getCoord()) != 'G'
				&& getMap(guardian.getCoord()) != 'O' && getMap(guardian.getCoord()) != '*')
			return false;
		if (hero == null)
			return true;
		if (!hero.checkColision(guardian))
			return true;
		return false;
	}

	/**
	 * Checks if can add an element
	 * @param x Coordinate x
	 * @param y Coordinate y
	 * @return Returns whether or not its possible to add the element
	 */
	public boolean canAddElement(int x, int y) {
		return getMap(x, y) == ' ';
	}

	/**
	 * Removes the Level's Hero
	 */
	public void removeHero() {
		hero = null;
	}

	/**
	 * Removes an Ogre
	 * @param ogre Ogre to remove
	 */
	public void removeOgre(Ogre ogre) {
		for (int i = 0; i < ogres.size(); i++)
			if (ogres.get(i).getX() == ogre.getX() && ogres.get(i).getY() == ogre.getY()) {
				ogres.remove(i);
				return;
			}
	}

	/**
	 * Removes a Guardian
	 * @param guardian Guardian to remove
	 */
	public void removeGuardian(Guardian guardian) {
		for (int i = 0; i < guardians.size(); i++)
			if (guardians.get(i).getX() == guardian.getX() && guardians.get(i).getY() == guardian.getY()) {
				guardians.remove(i);
				return;
			}
	}

	/**
	 * Removes a Door
	 * @param door Door to remove
	 */
	public void removeDoor(Door door) {
		for (int i = 0; i < doors.size(); i++)
			if (doors.get(i).getX() == door.getX() && doors.get(i).getY() == door.getY()) {
				doors.remove(i);
				return;
			}
	}

	/**
	 * Removes an element
	 * @param x Coordinate x
	 * @param y Coordinate y
	 */
	public void removeElement(int x, int y) {
		initialMap[y][x] = ' ';
	}

	/**
	 * Removes the Level's key
	 */
	public void removeKey() {
		key = null;
	}

	/**
	 * Checks if the Level is valid
	 * @return Returns true if exists Hero, Doors and a key, false otherwise
	 */
	public boolean isValid() {
		return !doors.isEmpty() && hero != null && key != null;
	}
}
