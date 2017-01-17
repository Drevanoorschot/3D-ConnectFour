package view;

import model.Board;
import model.Mark;

public class TUI {
	private String graphicBoard;
	private Board board;
	public static final String empty = "   ";
	public static final String zero = " O ";
	public static final String cross = " X ";
	public TUI(Board b) {
		Mark m = Mark.EMPTY;
		board = b;

		graphicBoard = boardToString();
	}
	
	public String boardToString() {
		int h = 0;
		int c = 0;
		graphicBoard = "";
		graphicBoard = graphicBoard + "     layer 1             layer 2    \n";
		for(int j = 0; j < board.getDIM(); j++) {
		for(int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "+---";
		}
		graphicBoard = graphicBoard + "+  ";
		for(int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "+---";
		}
		graphicBoard = graphicBoard + "+\n";
		for(int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "|" + this.fieldToString(c, i, h);
		}
		h++;
		graphicBoard = graphicBoard + "|  ";
		for(int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "|" + this.fieldToString(c, i, h);
		}
		h--;
		graphicBoard = graphicBoard + "|\n";
		c++;
		c = c % board.getDIM();
		}
		for(int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "+---";
		}
		graphicBoard = graphicBoard + "+  ";
		for(int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "+---";
		}
		graphicBoard = graphicBoard + "+\n";
		graphicBoard = graphicBoard + "\n";
		graphicBoard = graphicBoard + "     layer 3             layer 4    \n";
		for(int j = 0; j < board.getDIM(); j++) {
		for(int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "+---";
		}
		graphicBoard = graphicBoard + "+  ";
		for(int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "+---";
		}
		graphicBoard = graphicBoard + "+\n";
		for(int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "|" + this.fieldToString(c, i, h);
		}
		h++;
		graphicBoard = graphicBoard + "|  ";
		for(int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "|" + this.fieldToString(c, i, h);
		}
		h--;
		graphicBoard = graphicBoard + "|\n";
		c++;
		c = c % board.getDIM();
		}
		for(int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "+---";
		}
		graphicBoard = graphicBoard + "+  ";
		for(int i = 0; i < board.getDIM(); i++) {
			graphicBoard = graphicBoard + "+---";
		}
		graphicBoard = graphicBoard + "+\n";
		return graphicBoard;
	}
	
	public String fieldToString(int col, int row, int height) {
		if(board.getField(col, row, height) == Mark.EMPTY) {
			return empty;
			} else if(board.getField(col, row, height) == Mark.O) {
				return zero;
			} else if(board.getField(col, row, height) == Mark.X) {
				return cross;
				}
		return empty;
		}
	}
