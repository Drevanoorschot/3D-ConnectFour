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
	public final int layerLength = "layer  ".length();

	public TUI(Board b) {
		board = b;

		graphicBoard = boardToString(board);
	}

	public String drawHeaders(String graphBoard, Board brd) {
		// HACK: (int) ((3 * brd.getDIM() + brd.getDIM() + 1) - layerLength)/2
		// calculates how many
		// spaces does layer i need to its left and its right for it
		// to be centered on top of its board
		for (int i = 0; i < brd.getDIM(); i++) {
			for (int j = 0; j < (int) 
					((3 * brd.getDIM() + brd.getDIM() + 1) - layerLength) / 2; j++) {
				graphicBoard += " ";
			}
			graphicBoard += "layer " + i;
			for (int j = 0; j < (int) 
					((3 * brd.getDIM() + brd.getDIM() + 1) - layerLength) / 2; j++) {
				graphicBoard += " ";
			}
			graphicBoard += "  ";
		}
		graphicBoard += "\n";
		return graphicBoard;
	}

	public String drawRows(String graphBoard, Board brd, int c, int r, int h) {
		int col = c;
		int row = r;
		int height = h;
		for (int k = 0; k < brd.getDIM(); k++) {
			for (int i = 0; i < brd.getDIM(); i++) {
				for (int j = 0; j < brd.getDIM(); j++) {
					graphicBoard += "+---";
				}
				graphicBoard += "+  ";
			}
			graphicBoard += "\n";
			for (int i = 0; i < brd.getDIM(); i++) {
				for (int j = 0; j < brd.getDIM(); j++) {
					graphicBoard += "|" + fieldToString(col, row, height);
					col++;
				}
				height++;
				col = 0;
				graphicBoard += "|  ";
			}
			graphicBoard += "\n";
			height = 0;
			row++;
		}
		return graphicBoard;
	}

	public String lastLine(String graphBoard, Board brd) {
		for (int i = 0; i < brd.getDIM(); i++) {
			for (int j = 0; j < brd.getDIM(); j++) {
				graphicBoard += "+---";
			}
			graphicBoard += "+  ";
		}
		graphicBoard += "\n";
		return graphicBoard;
	}

	public String boardToString(Board brd) {
		int h = 0; // height
		int r = 0; // row
		int c = 0; // column
		graphicBoard = "";
		graphicBoard = drawHeaders(graphicBoard, brd);
		graphicBoard = drawRows(graphicBoard, brd, c, r, h);
		graphicBoard = lastLine(graphicBoard, brd);
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
		String boardString = this.boardToString(board);
		System.out.println(boardString);
	}

	public Board getBoard() {
		return board;
	}

}
