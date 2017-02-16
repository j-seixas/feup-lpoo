import java.util.Scanner;

public class DungeonKeep {

	public void printMap(char[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
	}

	public char getInput() {
		Scanner s = new Scanner(System.in);
		char answer = s.nextLine().charAt(0);
		s.close();
		return answer;
	}

	public int checkNext(char[][] map, char answer, int[] posHero) {
		int check = 0;
		if (answer == 'a') {
			if (map[posHero[0] - 1][posHero[1]] == ' ') {
				map[posHero[0] - 1][posHero[1]] = 'H';
				map[posHero[0]][posHero[1]] = ' ';
				check = 1;
			}

		} else if (answer == 'w') {
			if (map[posHero[0]][posHero[1] + 1] == ' ') {
				map[posHero[0]][posHero[1] + 1] = 'H';
				map[posHero[0]][posHero[1]] = ' ';
				check = 2;
			}
		} else if (answer == 's') {
			if (map[posHero[0]][posHero[1] - 1] == ' ') {
				map[posHero[0]][posHero[1] - 1] = 'H';
				map[posHero[0]][posHero[1]] = ' ';
				check = 3;
			}
		} else if (answer == 'd') {
			if (map[posHero[0] + 1][posHero[1]] == ' ') {
				map[posHero[0] + 1][posHero[1]] = 'H';
				map[posHero[0]][posHero[1]] = ' ';
				check = 4;
			} 
		}
		return check;
	}

	public void play(char[][] map) {
		int posHero[] = new int[2];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 'H') {
					posHero[0] = i;
					posHero[1] = j;
					break;
				}
			}
		}
		int dir = 1;
		while (dir != 5) {
			printMap(map);
			char answer = getInput();
			dir = checkNext(map, answer, posHero);
		}

	}

	public static void main(String[] args) {

		char map[][] = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', },
				{ 'X', 'H', ' ', ' ', 'I', ' ', 'X', ' ', 'G', 'X', },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X', },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X', },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X', },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X', },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X', },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', } };

		DungeonKeep dungeonKeep = new DungeonKeep();

		dungeonKeep.play(map);


	}

}
