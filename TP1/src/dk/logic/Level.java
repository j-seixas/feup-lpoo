package dk.logic;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Level {

	private Hero hero;
	private ArrayList<Ogre> ogres;
	private ArrayList<Guardian> guardians;
	private Coordinates key;
	private ArrayList<Door> doors;
	private char initialMap[][];
	private char currentMap[][];
	private boolean won_by_lever; 

	public Level(Hero hero, ArrayList<Ogre> ogres, ArrayList<Guardian> guardians, Coordinates key,
			ArrayList<Door> doors, char[][] initialMap, boolean won_by_lever) {
		this.hero = hero;
		this.ogres = ogres;
		this.guardians = guardians;
		this.key = key;
		this.doors = doors;
		this.initialMap = initialMap;
		this.won_by_lever = won_by_lever;
		updateMap();
		for (Ogre o : ogres) {
			o.getClub().moveCharacter(this);
		}
		updateMap();
	}

	public Level() {
		ogres = new ArrayList<Ogre>();
		guardians = new ArrayList<Guardian>();
		doors = new ArrayList<Door>();
	}

	private void cloneMap() {
		currentMap = new char[initialMap.length][initialMap[0].length];
		for (int i = 0; i < initialMap.length; i++)
			currentMap[i] = (char[]) initialMap[i].clone();
	}

	public void openDoors() {
		for (int i = 0; i < doors.size(); i++) {
			doors.get(i).openDoor();
		}
	}

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

	public boolean handleNPC() {
		moveEnemies();
		return checkCollisions(this.hero);
	}

	public boolean checkCollisions(GameCharacter character) {
		boolean collision = false;
		for (Guardian currentGuardian : guardians) {
			if (currentGuardian.checkColision(hero) && !currentGuardian.IsSleeping())
				collision = true;
		}
		for (Ogre currentOgre : ogres) {
			if (!currentOgre.isStunned() && currentOgre.checkColision(hero))
				collision = true;
			if (currentOgre.getClub().checkColision(hero))
				collision = true;
		}
		return collision;
	}

	public void moveEnemies() {
		for (Guardian currentGuardian : guardians) {
			currentGuardian.moveCharacter(this);
		}
		for (Ogre currentOgre : ogres) {
			currentOgre.moveCharacter(this);
			currentOgre.setIsStunned(currentOgre.checkColision(hero) && hero.getHasClub());
		}
	}

	public void updateMap() {
		cloneMap();
		char draw_char;

		// Draw doors
		for (Door currentDoor : doors) {
			if (currentDoor.isOpen())
				draw_char = 'S';
			else
				draw_char = 'I';
			setMap(currentDoor.getCoordinates(), draw_char);
		}

		// Draw key
		if ((hero != null && !hero.getHasKey()) || won_by_lever)
			setMap(key, 'k');

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

		// Draw Guardian
		for (Guardian currentGuardian : guardians) {
			if (currentGuardian.IsSleeping())
				draw_char = 'g';
			else
				draw_char = 'G';
			setMap(currentGuardian.getCoord(), draw_char);
		}
		for (Ogre currentOgre : ogres) {
			// Draw Ogre
			if (currentOgre.getCoord().equals(key) && hero != null && !hero.getHasKey())
				setMap(currentOgre.getCoord(), '$');
			else if (currentOgre.isStunned())
				setMap(currentOgre.getCoord(), '8');
			else
				setMap(currentOgre.getCoord(), 'O');
			// Draw Club
			if (currentOgre.getClub().getCoord().equals(key) && hero != null && !hero.getHasKey())
				setMap(currentOgre.getClub().getCoord(), '$');
			else if (getMap(currentOgre.getClub().getCoord()) != 'O')
				setMap(currentOgre.getClub().getCoord(), '*');

		}
	}

	// Wrappers / Getters / Setters

	public void setMap(char map[][]) {
		initialMap = map;
		cloneMap();
	}

	public void setMap(Coordinates coordinate, char ch) {
		currentMap[coordinate.getY()][coordinate.getX()] = ch;
	}

	public char getMap(Coordinates coord) {
		return currentMap[coord.getY()][coord.getX()];
	}

	public char getMap(int x, int y) {
		return currentMap[y][x];
	}

	public Hero getHero() {
		return hero;
	}

	public char[][] getMap() {
		return currentMap;
	}

	public void setWonByLever(boolean bool) {
		won_by_lever = bool;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public void addOgre(Ogre ogre) {
		ogres.add(ogre);
	}

	public void addGuardian(Guardian guardian) {
		guardians.add(guardian);
	}

	public void addDoor(Door door) {
		doors.add(door);
	}

	public void addElement(char c, int x, int y){
		initialMap[y][x] = c;
	}
	
	public void setKey(Coordinates key) {
		this.key = key;
	}

	public boolean canAdd(Hero hero) {
		return !checkCollisions(hero) && getMap(hero.getCoord()) == ' ';
	}

	public boolean canAdd(Ogre ogre) {
		if (ogres.size() < Game.MAX_OGRES)
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

	public boolean canAddElement(int x, int y) {
		return getMap(x, y) == ' ';
	}

	public void removeHero() {
		hero = null;
	}

	public void removeOgre(Ogre ogre) {
		for (int i = 0; i < ogres.size(); i++)
			if (ogres.get(i).getX() == ogre.getX() && ogres.get(i).getY() == ogre.getY()) {
				ogres.remove(i);
				return;
			}
	}

	public void removeGuardian(Guardian guardian) {
		for (int i = 0; i < guardians.size(); i++)
			if (guardians.get(i).getX() == guardian.getX() && guardians.get(i).getY() == guardian.getY()) {
				guardians.remove(i);
				return;
			}
	}

	public void removeDoor(Door door) {
		for (int i = 0; i < doors.size(); i++)
			if (doors.get(i).getX() == door.getX() && doors.get(i).getY() == door.getY()) {
				doors.remove(i);
				return;
			}
	}

	public void removeElement(int x, int y){
		initialMap[y][x] = ' ';
	}

	public void removeKey(){
		key = null;
	}

	public boolean isValid(){
		return !doors.isEmpty()
			&& hero != null
			&& key != null;
	}
}
