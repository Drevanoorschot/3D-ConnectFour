package view;

import java.util.Observable;
import java.util.Observer;

import model.Board;
import model.Mark;

public class TUI implements Observer {
	private String graphicBoard;
	private Board board;
	public static final String EMPTY = "   ";
	public static final String ZERO = " O ";
	public static final String CROSS = " X ";

	public TUI(Board b) {
		board = b;

		graphicBoard = boardToString(board);
	}

	public String boardToString(Board board) {
		int h = 0;
		int c = 0;
		graphicBoard = "";
		graphicBoard = graphicBoard + "     layer 0             layer 1    \n";
		for (int j = 0; j < board.getDIM(); j++) {
			for (int i = 0; i < board.getDIM(); i++) {
				graphicBoard = graphicBoard + "+---";
			}
			graphicBoard = graphicBoard + "+  ";
			for (int i = 0; i < board.getDIM(); i++) {
				graphicBoard = graphicBoard + "+---";
			}
			graphicBoard = graphicBoard + "+\n";
			for (int r = 0; r < board.getDIM(); r++) {
				graphicBoard = graphicBoard + "|" + this.fieldToString(c, r, h);
			}
			h++;
			graphicBoard = graphicBoard + "|  ";
			for (int r = 0; r < board.getDIM(); r++) {
				graphicBoard = graphicBoard + "|" + this.fieldToString(c, r, h);
			}
			h--;
			graphicBoard = graphicBoard + "|\n";
			c++;
			c = c % board.getDIM();
		}
		for (int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "+---";
		}
		graphicBoard = graphicBoard + "+  ";
		for (int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "+---";
		}
		graphicBoard = graphicBoard + "+\n";
		graphicBoard = graphicBoard + "\n";
		h = 2;
		graphicBoard = graphicBoard + "     layer 2             layer 3   \n";
		for (int j = 0; j < board.getDIM(); j++) {
			for (int i = 0; i < board.getDIM(); i++) {
				graphicBoard = graphicBoard + "+---";
			}
			graphicBoard = graphicBoard + "+  ";
			for (int i = 0; i < board.getDIM(); i++) {
				graphicBoard = graphicBoard + "+---";
			}
			graphicBoard = graphicBoard + "+\n";
			for (int r = 0; r < board.getDIM(); r++) {
				graphicBoard = graphicBoard + "|" + this.fieldToString(c, r, h);
			}
			h++;
			graphicBoard = graphicBoard + "|  ";
			for (int r = 0; r < board.getDIM(); r++) {
				graphicBoard = graphicBoard + "|" + this.fieldToString(c, r, h);
			}
			h--;
			graphicBoard = graphicBoard + "|\n";
			c++;
			c = c % board.getDIM();
		}
		for (int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "+---";
		}
		graphicBoard = graphicBoard + "+  ";
		for (int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "+---";
		}
		graphicBoard = graphicBoard + "+\n";
		return graphicBoard;
	}

	public String fieldToString(int col, int row, int height) {
		if (board.getField(col, row, height) == Mark.EMPTY) {
			return EMPTY;
		} else if (board.getField(col, row, height) == Mark.O) {
			return ZERO;
		} else if (board.getField(col, row, height) == Mark.X) {
			return CROSS;
		}

		return EMPTY;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		String boardString = this.boardToString(this.getBoard());
		System.out.println(boardString);
	}
	
	public Board getBoard() {
		return board;
	}

}
