package model;

public class Board {
	private Mark[][][] boardArray = new Mark[3][3][3];
	private Mark m;
	public Board() {
		reset();
	}
	
	public Board deepCopy() {
		Board copy = new Board();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				for (int z = 0; z < 3; z++) {
					copy.boardArray[x][y][z] = this.boardArray[x][y][z];
				}
			}
		}
		return copy;
	}
	
	public void reset() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				for (int z = 0; z < 3; z++) {
					boardArray[x][y][z] = m.EMPTY;
				}
			}
		}
	}
}
