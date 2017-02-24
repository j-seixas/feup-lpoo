package dk.cli;

import java.util.Scanner;
import dk.logic.Game;
import dk.logic.Character;

public class cli {
	
	
	private Character.Direction getInput(Scanner keyboard_scanner){
		System.out.print("Move: ");
		String input = keyboard_scanner.nextLine();
		switch (input) {
		case "w":
			return Character.Direction.UP;
		case "a":
			return Character.Direction.LEFT;
		case "s":
			return Character.Direction.DOWN;
		case "d":
			return Character.Direction.RIGHT;
		default:
			return Character.Direction.NONE;
		}
	}
	
	public void run(Game game){
		Scanner keyboard_scanner = new Scanner(System.in);
		game.printMap();
		Game.GameStat currentStatus;
		do{
			currentStatus = game.getGameStatus();
			Character.Direction currentDirection = this.getInput(keyboard_scanner);
			if (currentDirection != Character.Direction.NONE) {
				game.processInput(currentDirection);
				game.printMap();
			}
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
		cli consoleInterface = new cli();
		consoleInterface.run(dungeonKeep);
	}
}
