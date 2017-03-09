package dk.logic;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Level {

	private Hero hero;
	private ArrayList<Ogre> ogres;
	private ArrayList<Guardian> guardians;
	private Coordinates key;
	private ArrayList<Door> doors;
	private final char initialMap[][];
	private char currentMap[][];
	private boolean won_by_lever;

	public Level(Hero hero, ArrayList<Ogre> ogres, ArrayList<Guardian> guardians, Coordinates key, ArrayList<Door> doors,
			char[][] initialMap, boolean won_by_lever) {
		this.hero = hero;
		this.ogres = ogres;
		this.guardians = guardians;
		this.key = key;
		this.doors = doors;
		this.initialMap = initialMap;
		this.won_by_lever = won_by_lever;
		cloneMap();
	}

	private void cloneMap() {
		currentMap = new char[initialMap.length][initialMap[0].length];
		for (int i = 0; i < initialMap.length; i++)
			currentMap[i] = (char[])initialMap[i].clone();
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
		boolean collision = false;
		for (Guardian currentGuardian : guardians) {
			currentGuardian.moveCharacter(this);
			if (currentGuardian.checkColision(hero) && !currentGuardian.IsSleeping())
				collision = true;
		}
		for (Ogre currentOgre : ogres) {
			currentOgre.moveCharacter(this);
			currentOgre.setIsStunned(currentOgre.checkColision(hero));
			if (currentOgre.getClub().checkColision(hero))
				collision = true;
		}
		return collision;
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
		if (!hero.getHasKey() || won_by_lever)
			setMap(key, 'k');

		// Draw hero
		if (hero.getHasKey() && !won_by_lever)
			draw_char = 'K';
		else if (hero.getHasClub())
			draw_char = 'A';
		else
			draw_char = 'H';
		setMap(hero.getCoord(), draw_char);

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
			if (currentOgre.getCoord().equals(key))
				setMap(currentOgre.getCoord(), '$');
			else if (currentOgre.isStunned())
				setMap(currentOgre.getCoord(), '8');
			else
				setMap(currentOgre.getCoord(), 'O');
			// Draw Club
			if (currentOgre.getClub().getCoord().equals(key))
				setMap(currentOgre.getClub().getCoord(), '$');
			else if (getMap(currentOgre.getClub().getCoord()) != 'O')
				setMap(currentOgre.getClub().getCoord(), '*');

		}
	}

	public void setMap(Coordinates coordinate, char ch) {
		currentMap[coordinate.getY()][coordinate.getX()] = ch;
	}

	public char getMap(Coordinates coord) {
		return currentMap[coord.getY()][coord.getX()];
	}

	public char getMap(int x, int y){
		return currentMap[y][x];
	}
	
	public Hero getHero() {
		return hero;
	}

	public char[][] getMap() {
		return currentMap;
	}

}
