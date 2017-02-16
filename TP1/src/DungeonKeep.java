import java.awt.event.KeyEvent;

public class DungeonKeep {

	public void printMap(char[][] map){
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				System.out.print(map[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
	}
	
	public void getInput() {
		KeyEvent press = new KeyEvent();
		char key = press.getKeyCode();
		switch(key) {
		
		}
	}
	
	
	public static void main(String[] args) {
		
		char map[][] = {
				{'X','X','X','X','X','X','X','X','X','X',}, 
				{'X','H',' ',' ','I',' ','X',' ','G','X',},
				{'X','X','X',' ','X','X','X',' ',' ','X',},
				{'X',' ','I',' ','I',' ','X',' ',' ','X',},
				{'X','X','X',' ','X','X','X',' ',' ','X',},
				{'I',' ',' ',' ',' ',' ',' ',' ',' ','X',},
				{'I',' ',' ',' ',' ',' ',' ',' ',' ','X',},
				{'X','X','X',' ','X','X','X','X',' ','X',},
				{'X',' ','I',' ','I',' ','X','k',' ','X',},
				{'X','X','X','X','X','X','X','X','X','X',}};
		
		DungeonKeep dungeonKeep = new DungeonKeep();
		
		dungeonKeep.printMap(map);

	}

}
