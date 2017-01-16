package model;

public class Board {
	private static final int DIM = 4;
	private Mark[][][] fields;

	public Board() {
		fields = new Mark[DIM][DIM][DIM];
		reset();
	}

	public Board deepCopy() {
		Board copy = new Board();
		for (int col = 0; col < DIM; col++) {
			for (int row = 0; row < DIM; row++) {
				for (int height = 0; height < DIM; height++) {
					copy.fields[col][row][height] = this.fields[col][row][height];
				}
			}
		}
		return copy;
	}

	public void reset() {
		for (int col = 0; col < DIM; col++) {
			for (int row = 0; row < DIM; row++) {
				for (int height = 0; height < DIM; height++) {
					fields[col][row][height] = Mark.EMPTY;
				}
			}
		}
	}

	public void setField(int col, int row, int height, Mark m) {
		fields[col][row][height] = m;
	}

	public Mark getField(int col, int row, int height) {
		return fields[col][row][height];
	}

	public boolean hasRow(Mark m) {
		boolean fullRow;
		for (int row = 0; row < DIM; row++) {
			for (int height = 0; height < DIM; height++) {
				fullRow = true;
				for (int col = 0; col < DIM; col++) {
					if (fields[col][row][height] != m) {
						fullRow = false;
					}
				}
				if (fullRow) {
					return true;

				}
			}
		}
		return false;

	}
	
	public boolean hasColumn(Mark m) {
		boolean fullCol;
		for (int col = 0; col < DIM; col++) {
			for (int height = 0; height < DIM; height++) {
				fullCol = true;
				for (int row = 0; row < DIM; row++) {
					if (fields[col][row][height] != m) {
						fullCol = false;
					}
				}
				if (fullCol) {
					return true;

				}
			}
		}
		return false;
	}
	public boolean hasHeight(Mark m) {
		boolean fullHeight;
		for (int row = 0; row < DIM; row++) {
			for (int col = 0; col < DIM; col++) {
				fullHeight = true;
				for (int height = 0; height < DIM; height++) {
					if (fields[col][row][height] != m) {
						fullHeight = false;
					}
				}
				if (fullHeight) {
					return true;

				}
			}
		}
		return false;
	}
	
	public boolean hasRowColumn(Mark m) {
		boolean fullRowColumn;
		for (int height = 0; height < DIM; height++) {
			fullRowColumn = true;
			for (int rc = 0; rc < DIM; rc++) {
				if (fields[rc][rc][height] != m) {
					fullRowColumn = false;
				}
			}
			if (fullRowColumn) {
				return true;
			}
		}
		for (int height = 0; height < DIM; height++) {
			fullRowColumn = true;
			for (int rc = 0; rc < DIM; rc++) {
				if (fields[rc][DIM - rc - 1][height] != m) {
					fullRowColumn = false;
				}
			}
			if (fullRowColumn) {
				return true;
			}
		}
		return false;
	}
	public boolean hasRowHeight(Mark m) {
		boolean fullRowHeight;
		for (int row = 0; row < DIM; row++) {
			fullRowHeight = true;
			for (int rh = 0; rh < DIM; rh++) {
				if (fields[rh][row][rh] != m) {
					fullRowHeight = false;
				}
			}
			if (fullRowHeight) {
				return true;
			}
		}
		for (int row = 0; row < DIM; row++) {
			fullRowHeight = true;
			for (int rh = 0; rh < DIM; rh++) {
				if (fields[rh][row][DIM - rh - 1] != m) {
					fullRowHeight = false;
				}
			}
			if (fullRowHeight) {
				return true;
			}
		}
		return false;
	}
}
