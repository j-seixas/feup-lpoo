package dk.Interface;

import java.util.Scanner;
import dk.logic.Game;
import dk.logic.GameCharacter;

public class CLI {
	 
	
	private GameCharacter.Direction getInput(Scanner keyboard_scanner){
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
	
	public void run(Game game){
		Scanner keyboard_scanner = new Scanner(System.in);
		game.updateMap();
		game.printMap();
		Game.GameStat currentStatus;
		do{
			GameCharacter.Direction currentDirection = this.getInput(keyboard_scanner);
			if (currentDirection != GameCharacter.Direction.NONE) {
				game.processInput(currentDirection);
				game.updateMap();
				game.printMap();
			}
			currentStatus = game.getGameStatus();
		}while(currentStatus == Game.GameStat.RUNNING);
			
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
