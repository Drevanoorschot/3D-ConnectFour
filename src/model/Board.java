package model;

import java.util.Observable;

public class Board extends Observable {
	private static final int DIM = 4;
	private Mark[][][] fields;

	public Board() {
		fields = new Mark[DIM][DIM][DIM];
		reset();
	}

	public int getDIM() {
		return DIM;
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
		this.setChanged();
		this.notifyObservers();
	}

	public void setField(int col, int row, Mark m) {
		int height;
		int temp = 0;
		for (height = 0; height < getDIM(); height++) {
			if (getField(col, row, height) == Mark.EMPTY) {
				temp = height;
				height = getDIM();
			}
		}
		fields[col][row][temp] = m;
		this.setChanged();
		this.notifyObservers();
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

	public boolean hasColumnHeight(Mark m) {
		boolean fullColumnHeight;
		for (int col = 0; col < DIM; col++) {
			fullColumnHeight = true;
			for (int ch = 0; ch < DIM; ch++) {
				if (fields[col][ch][ch] != m) {
					fullColumnHeight = false;
				}
			}
			if (fullColumnHeight) {
				return true;
			}
		}
		for (int col = 0; col < DIM; col++) {
			fullColumnHeight = true;
			for (int ch = 0; ch < DIM; ch++) {
				if (fields[col][ch][DIM - ch - 1] != m) {
					fullColumnHeight = false;
				}
			}
			if (fullColumnHeight) {
				return true;
			}
		}
		return false;
	}

	public boolean hasRowColumnHeight(Mark m) {
		// booleans indicate start of diagonal in bottom level
		boolean diagTopLeft = true;
		boolean diagTopRight = true;
		boolean diagBottomLeft = true;
		boolean diagBottomRight = true;
		for (int rch = 0; rch < DIM; rch++) {
			if (fields[rch][rch][rch] != m) {
				diagTopLeft = false;
			}
			if (fields[DIM - rch - 1][rch][rch] != m) {
				diagTopRight = false;
			}
			if (fields[rch][DIM - rch - 1][rch] != m) {
				diagBottomLeft = false;
			}
			if (fields[DIM - rch - 1][DIM - rch - 1][rch] != m) {
				diagBottomRight = false;
			}
		}

		return diagTopLeft || diagTopRight || diagBottomLeft || diagBottomRight;
	}

	public boolean isFull() {
		boolean full = true;
		for (int row = 0; row < DIM; row++) {
			for (int col = 0; col < DIM; col++) {
				for (int height = 0; height < DIM; height++) {
					if (fields[col][row][height] == Mark.EMPTY) {
						full = false;
					}
				}
			}
		}
		return full;
	}

	public boolean gameOver() {
		return isFull() || hasWinner();
	}

	public boolean hasWinner() {
		return isWinner(Mark.O) || isWinner(Mark.X);
	}

	public boolean isWinner(Mark m) {
		return hasRow(m) || hasColumn(m) || hasHeight(m) 
				|| hasRowHeight(m) || hasRowColumn(m) || hasColumnHeight(m) 
				|| hasRowColumnHeight(m);

	}
	
}
