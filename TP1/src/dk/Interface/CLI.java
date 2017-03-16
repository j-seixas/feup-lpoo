package dk.Interface;

import java.util.Scanner;
import dk.logic.Game;
import dk.logic.GameCharacter;
import dk.logic.Level;

public class CLI {

	private Scanner keyboard_scanner = new Scanner(System.in);
	
	private GameCharacter.Direction getInput() {
		System.out.print("Move: ");
		
		String input = keyboard_scanner.nextLine();

		switch (input) {
		case "w":
			return GameCharacter.Direction.UP;
		case "a":
			return GameCharacter.Direction.LEFT;
		case "s":
			return GameCharacter.Direction.DOWN;
		case "d":
			return GameCharacter.Direction.RIGHT;
		default:
			return GameCharacter.Direction.NONE;
		}
	}

	public static void printMap(char map[][]) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
	}

	public void run(Game game) {
		game.getCurrentLevel().updateMap();
		printMap(game.getCurrentLevel().getMap());
		Game.GameStat currentStatus = Game.GameStat.RUNNING;
		while (true) {
			GameCharacter.Direction currentDirection = this.getInput();
			if (currentDirection != GameCharacter.Direction.NONE) {
				// Process input
				game.processInput(currentDirection);
				// Check for final level
				Level currentLevel = game.getCurrentLevel();
				if (currentLevel == null) {
					currentStatus = game.getGameStatus();
					break;
				} else {
					// Update and print the map
					currentLevel.updateMap();
					printMap(game.getCurrentLevel().getMap());
				}
				// Check status
				currentStatus = game.getGameStatus();
				if (currentStatus != Game.GameStat.RUNNING)
					break;
			}
		}

		keyboard_scanner.close();
		if (currentStatus == Game.GameStat.LOSE) {
			System.out.println("You lost!");
		} else if (currentStatus == Game.GameStat.WIN) {
			System.out.println("You won!");
		}
	}

	private int[] getArguments() {
		int args[] = new int[2];

		// Read Ogre Number
		while (true) {
			System.out.print("Number of Ogres: ");
			
			String ogreStr = keyboard_scanner.nextLine();
			int ogreNumber;
			try {
				ogreNumber = Integer.parseInt(ogreStr);
			} catch (NumberFormatException e) {
				System.out.println("Invalid number!");
				continue;
			}
			if (ogreNumber > 0 && ogreNumber <= Game.MAX_OGRES) {
				args[0] = ogreNumber;
				break;
			} else {
				System.out.println("Invalid number!");
			}
		}
		// Read Guard Type
		while (true) {
			System.out.print("Select a guardian Type:\n"
					     	+ " 1. Rookie\n"
							+ " 2. Drunk\n"
					     	+ " 3. Suspicious\n");
			
			String guardStr = keyboard_scanner.nextLine();
			int guardNumber;
			try {
				guardNumber = Integer.parseInt(guardStr);
			} catch (NumberFormatException e) {
				System.out.println("Invalid type!");
				continue;
			}
			if (guardNumber >= 1 && guardNumber <= Game.GUARDIAN_TYPES) {
				args[1] = guardNumber - 1;				
				break;
			} else {
				System.out.println("Invalid type!");
			}
		}

		return args;
	}

	public static void main(String[] args) {
		CLI consoleInterface = new CLI();
		int arg[] = consoleInterface.getArguments();
		Game dungeonKeep = new Game(arg[0], arg[1]);
		consoleInterface.run(dungeonKeep);
	}
}
