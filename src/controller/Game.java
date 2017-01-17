package controller;

import exceptions.FieldBelowNotTakenException;
import exceptions.FieldNotFreeException;
import model.Board;
import model.Mark;
import view.TUI;

public class Game {
	private Player player1;
	private Player player2;
	private Board board;
	private TUI tui;
	
	public Game(Player p1, Player p2) {
		player1 = p1;
		player2 = p2;
		board = new Board();
		tui = new TUI(board);
	}
	
	public void makeMove(Player player) 
			throws IndexOutOfBoundsException, FieldNotFreeException, FieldBelowNotTakenException {
		int[] coords = player.determineMove();
		int h = coords[2];
		int dim = board.getDIM();
		if (h > 0) {
			h--;
		}
		if (board.getField(coords[0], coords[1], coords[2]) != Mark.EMPTY) {
			throw new FieldNotFreeException();
		}
		if (board.getField(coords[0], coords[1], h) == Mark.EMPTY) {
			throw new FieldBelowNotTakenException();
		}
		if (coords[0] >= dim || coords[0] < 0 ||
			coords[1] >= dim || coords[1] < 0 ||
			coords[2] >= dim || coords[2] < 0) {
			throw new IndexOutOfBoundsException("Field does not exist!");
		}
		board.setField(coords[0], coords[1], coords[2], player.getMark());
	}
}
