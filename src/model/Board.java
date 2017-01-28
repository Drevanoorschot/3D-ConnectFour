package model;

import java.util.Observable;

public class Board extends Observable {
	private static int dim = 4;
	private Mark[][][] fields;
	/*@
	 invariant (\forall int c, r, h; 0 <= c & c < getDIM() & 0 <= r & r < getDIM() &
	   0 <= h & h < getDIM(); getField(c, r, h) == Mark.EMPTY || getField(c, r, h) == Mark.O ||
	   getField(c, r, h) == Mark.X);
	 */
	
	/*@
	  ensures (\forall int c, r, h; 0 <= c & c < getDIM() & 0 <= r & r < getDIM() &
	   0 <= h & h < getDIM(); getField(c, r, h) == Mark.EMPTY);
	 */
	public Board() {
		fields = new Mark[dim][dim][dim];
		reset();
	}
	
	//@pure
	public int getDIM() {
		return dim;
	}
	//@requires dimension > 0;
	//@ensures (\forall Board b; b.getDIM() == dimension);
	public static void setDIM(int dimension) {
		dim = dimension;
	}
	//@ensures this.equals(deepCopy());
	//@pure
	public Board deepCopy() {
		Board copy = new Board();
		for (int col = 0; col < dim; col++) {
			for (int row = 0; row < dim; row++) {
				for (int height = 0; height < dim; height++) {
					copy.fields[col][row][height] = this.fields[col][row][height];
				}
			}
		}
		return copy;
	}
	/*@
	  ensures (\forall int c, r, h; 0 <= c & c < getDIM() & 0 <= r & r < getDIM() &
	   0 <= h & h < getDIM(); getField (c, r, h) == Mark.EMPTY);
	 */
	public void reset() {
		for (int col = 0; col < dim; col++) {
			for (int row = 0; row < dim; row++) {
				for (int height = 0; height < dim; height++) {
					fields[col][row][height] = Mark.EMPTY;
				}
			}
		}
		this.setChanged();
		this.notifyObservers();
	}
	//@requires col >= 0 & col < getDIM();
	//@requires row >= 0 & row < getDIM();
	//@ensures (\exists int h; h >= 0 & h < getDIM(); getField(col, row, h) == m);
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
	//@requires col >= 0 & col < getDIM();
	//@requires row >= 0 & row < getDIM();
	//@requires height >= 0 & height < getDIM();
	//@pure
	public Mark getField(int col, int row, int height) {
		return fields[col][row][height];
	}
	/*@ensures \result == (\exists int c, h; c >= 0 & h >= 0 & c < getDIM() & h < getDIM();
	  (\forall int r; r >= 0 & r < getDIM(); getField(c, r, h) == m));
	@pure
	 */
	public boolean hasRow(Mark m) {
		boolean fullRow;
		for (int row = 0; row < dim; row++) {
			for (int height = 0; height < dim; height++) {
				fullRow = true;
				for (int col = 0; col < dim; col++) {
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
	/*@ensures \result == (\exists int r, h; r >= 0 & h >= 0 & r < getDIM() & h < getDIM();
	  (\forall int c; c >= 0 & c < getDIM(); getField(c, r, h) == m));
	@pure
	 */
	public boolean hasColumn(Mark m) {
		boolean fullCol;
		for (int col = 0; col < dim; col++) {
			for (int height = 0; height < dim; height++) {
				fullCol = true;
				for (int row = 0; row < dim; row++) {
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
	/*@ensures \result == (\exists int r, c; r >= 0 & c >= 0 & r < getDIM() & c < getDIM();
	  (\forall int h; h >= 0 & h < getDIM(); getField(c, r, h) == m));
	@pure
	 */
	public boolean hasHeight(Mark m) {
		boolean fullHeight;
		for (int row = 0; row < dim; row++) {
			for (int col = 0; col < dim; col++) {
				fullHeight = true;
				for (int height = 0; height < dim; height++) {
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
	/*@ensures \result == (\exists int h; h >= 0 & h < getDIM();
	  (\forall int rc; rc >= 0 & rc < getDIM(); getField(rc, rc, h) == m) ||
	  (\forall int rc; rc >= 0 & rc < getDIM(); getField(rc, rc - getDIM(), h) == m));
	@pure
	 */
	public boolean hasRowColumn(Mark m) {
		boolean fullRowColumn;
		for (int height = 0; height < dim; height++) {
			fullRowColumn = true;
			for (int rc = 0; rc < dim; rc++) {
				if (fields[rc][rc][height] != m) {
					fullRowColumn = false;
				}
			}
			if (fullRowColumn) {
				return true;
			}
		}
		for (int height = 0; height < dim; height++) {
			fullRowColumn = true;
			for (int rc = 0; rc < dim; rc++) {
				if (fields[rc][dim - rc - 1][height] != m) {
					fullRowColumn = false;
				}
			}
			if (fullRowColumn) {
				return true;
			}
		}
		return false;
	}
	/*@ensures \result == (\exists int c; c >= 0 & c < getDIM();
	  (\forall int rh; rh >= 0 & rh < getDIM(); getField(c, rh, rh) == m) ||
	  (\forall int rh; rh >= 0 & rh < getDIM(); getField(c, rh - getDIM(), rh) == m));
	@pure
	 */
	public boolean hasRowHeight(Mark m) {
		boolean fullRowHeight;
		for (int row = 0; row < dim; row++) {
			fullRowHeight = true;
			for (int rh = 0; rh < dim; rh++) {
				if (fields[rh][row][rh] != m) {
					fullRowHeight = false;
				}
			}
			if (fullRowHeight) {
				return true;
			}
		}
		for (int row = 0; row < dim; row++) {
			fullRowHeight = true;
			for (int rh = 0; rh < dim; rh++) {
				if (fields[rh][row][dim - rh - 1] != m) {
					fullRowHeight = false;
				}
			}
			if (fullRowHeight) {
				return true;
			}
		}
		return false;
	}
	/*@ensures \result == (\exists int r; r >= 0 & r < getDIM();
	  (\forall int ch; ch >= 0 & ch < getDIM(); getField(ch, r, ch) == m) ||
	  (\forall int ch; ch >= 0 & ch < getDIM(); getField(ch - getDIM(), r, ch) == m));
	@pure
	 */
	public boolean hasColumnHeight(Mark m) {
		boolean fullColumnHeight;
		for (int col = 0; col < dim; col++) {
			fullColumnHeight = true;
			for (int ch = 0; ch < dim; ch++) {
				if (fields[col][ch][ch] != m) {
					fullColumnHeight = false;
				}
			}
			if (fullColumnHeight) {
				return true;
			}
		}
		for (int col = 0; col < dim; col++) {
			fullColumnHeight = true;
			for (int ch = 0; ch < dim; ch++) {
				if (fields[col][ch][dim - ch - 1] != m) {
					fullColumnHeight = false;
				}
			}
			if (fullColumnHeight) {
				return true;
			}
		}
		return false;
	}
	/*
	@ensures \result == (\forall int i; i >= 0 & i < getDIM(); getField(i, i, i) == m) ||
	(\forall int i; i >= 0 & i < getDIM(); getField(i - getDIM(), i, i) == m) ||
	(\forall int i; i >= 0 & i < getDIM(); getField(i, i - getDIM(), i) == m) ||
	(\forall int i; i >= 0 & i < getDIM(); getField(i - getDIM(), i - getDIM(), i) == m);
	@pure
	 */
	public boolean hasRowColumnHeight(Mark m) {
		// booleans indicate start of diagonal in bottom level
		boolean diagTopLeft = true;
		boolean diagTopRight = true;
		boolean diagBottomLeft = true;
		boolean diagBottomRight = true;
		for (int rch = 0; rch < dim; rch++) {
			if (fields[rch][rch][rch] != m) {
				diagTopLeft = false;
			}
			if (fields[dim - rch - 1][rch][rch] != m) {
				diagTopRight = false;
			}
			if (fields[rch][dim - rch - 1][rch] != m) {
				diagBottomLeft = false;
			}
			if (fields[dim - rch - 1][dim - rch - 1][rch] != m) {
				diagBottomRight = false;
			}
		}

		return diagTopLeft || diagTopRight || diagBottomLeft || diagBottomRight;
	}
	/*@
	  ensures \result == (\forall int c, r, h; 0 <= c & c < getDIM() & 0 <= r & r < getDIM() &
	   0 <= h & h < getDIM(); getField(c, r, h) != Mark.EMPTY);
	 */
	//@pure
	public boolean isFull() {
		boolean full = true;
		for (int row = 0; row < dim; row++) {
			for (int col = 0; col < dim; col++) {
				for (int height = 0; height < dim; height++) {
					if (fields[col][row][height] == Mark.EMPTY) {
						full = false;
					}
				}
			}
		}
		return full;
	}
	/*
	@requires m != Mark.EMPTY;
	@ensures \result == hasRow(m) || hasColumn(m) || hasHeight(m) 
	|| hasRowHeight(m) || hasRowColumn(m) || hasColumnHeight(m) 
	|| hasRowColumnHeight(m);
	*/
	//@pure
	public boolean isWinner(Mark m) {
		return hasRow(m) || hasColumn(m) || hasHeight(m) 
				|| hasRowHeight(m) || hasRowColumn(m) || hasColumnHeight(m) 
				|| hasRowColumnHeight(m);
	
	}
	//@ensures \result == isWinner(Mark.O) || isWinner(Mark.X);
	//@pure
	public boolean hasWinner() {
		return isWinner(Mark.O) || isWinner(Mark.X);
	}
	//@ensures \result == isFull() || hasWinner();
	//@pure
	public boolean gameOver() {
		return isFull() || hasWinner();
	}
	
}
