package dk.Interface;

import java.util.Scanner;
import dk.logic.Game;
import dk.logic.GameCharacter;
import dk.logic.Level;

public class CLI {

	private GameCharacter.Direction getInput(Scanner keyboard_scanner) {
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
		Scanner keyboard_scanner = new Scanner(System.in);
		game.getCurrentLevel().updateMap();
		printMap(game.getCurrentLevel().getMap());
		Game.GameStat currentStatus = Game.GameStat.RUNNING;
		while(true) {
			GameCharacter.Direction currentDirection = this.getInput(keyboard_scanner);
			if (currentDirection != GameCharacter.Direction.NONE) {
				//Process input
				game.processInput(currentDirection);
				//Check for final level
				Level currentLevel = game.getCurrentLevel();
				if(currentLevel == null){
					currentStatus = game.getGameStatus();
					break;
				}
				else {
					//Update and print the map
					currentLevel.updateMap();
					printMap(game.getCurrentLevel().getMap());
				}
				//Check status
				currentStatus = game.getGameStatus();
				if(currentStatus != Game.GameStat.RUNNING)
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

	public static void main(String[] args) {
		Game dungeonKeep = new Game();
		CLI consoleInterface = new CLI();
		consoleInterface.run(dungeonKeep);
	}
}
