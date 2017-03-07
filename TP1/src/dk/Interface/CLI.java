package dk.Interface;

import java.util.Random;
import java.util.Scanner;

import dk.logic.Coordinates;
import dk.logic.DrunkenG;
import dk.logic.Game;
import dk.logic.GameCharacter;
import dk.logic.Guardian;
import dk.logic.Hero;
import dk.logic.RookieG;
import dk.logic.SuspiciousG;

public class CLI {
	private Hero hero1 = new Hero(1, 1);
	private Hero hero2 = new Hero(1, 7);
	private Guardian guard;
	private Coordinates key1 = new Coordinates(7, 8);
	private Coordinates key2 = new Coordinates(7, 1);

	private Coordinates[] guard_path = { new Coordinates(8, 1), new Coordinates(7, 1), new Coordinates(7, 2),
			new Coordinates(7, 3), new Coordinates(7, 4), new Coordinates(7, 5), new Coordinates(6, 5),
			new Coordinates(5, 5), new Coordinates(4, 5), new Coordinates(3, 5), new Coordinates(2, 5),
			new Coordinates(1, 5), new Coordinates(1, 6), new Coordinates(2, 6), new Coordinates(3, 6),
			new Coordinates(4, 6), new Coordinates(5, 6), new Coordinates(6, 6), new Coordinates(7, 6),
			new Coordinates(8, 6), new Coordinates(8, 5), new Coordinates(8, 4), new Coordinates(8, 3),
			new Coordinates(8, 2) };
	
	private char map1[][] = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', },
			{ 'X', ' ', ' ', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X', },
			{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X', },
			{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X', },
			{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', } };
	private char map2[][] = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', },
			{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', }, { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', } };

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

	public void run() {
		Random rand = new Random();
		int guard1 = rand.nextInt(3);
		switch (guard1) {
		case 0:
			guard = new RookieG(guard_path);
			break;
		case 1:
			guard = new DrunkenG(guard_path);
			break;
		case 2:
			guard = new SuspiciousG(guard_path);
			break;
		}
		Game game = new Game(map1, hero1, guard, key1);
		Scanner keyboard_scanner = new Scanner(System.in);
		game.updateMap();
		game.printMap();
		Game.GameStat currentStatus;
		do {
			GameCharacter.Direction currentDirection = this.getInput(keyboard_scanner);
			if (currentDirection != GameCharacter.Direction.NONE) {
				game.processInput(currentDirection);
				//game.updateMap();
				game.printMap();
			}
			currentStatus = game.getGameStatus();
		} while (currentStatus == Game.GameStat.RUNNING);

		keyboard_scanner.close();
		if (currentStatus == Game.GameStat.LOSE) {
			System.out.println("You lost!");
		} else if (currentStatus == Game.GameStat.WIN) {
			System.out.println("You won!");
		}
	}

	public static void main(String[] args) {
		// Game dungeonKeep = new Game();
		CLI consoleInterface = new CLI();
		consoleInterface.run();
	}
}
