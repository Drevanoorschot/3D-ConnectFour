package model;

public class Board {
	private String[][][] boardArray = new String[3][3][3];
	private Mark mark;
	
	public Board() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				for (int z = 0; z < 3; z++) {
					boardArray[x][y][z] = mark.EMPTY;
				}
			}
		}
	}
	
	public Board deepCopy() {
		Board copy = new Board();
		
	}
}
