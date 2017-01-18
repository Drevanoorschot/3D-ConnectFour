package controller;

import exceptions.FieldBelowNotTakenException;
import exceptions.FieldNotFreeException;
import exceptions.HasNoWinnerException;
import model.Board;
import model.Mark;
import view.TUI;

public class Game {
	private Player player1;
	private Player player2;
	private Board board;
	private TUI tui;
	private int turn;
	private int playerAmount;

	public Game(Player p1, Player p2) {
		player1 = p1;
		player2 = p2;
		board = new Board();
		tui = new TUI(board);
		board.addObserver(tui);
		if (p1 instanceof ComputerPlayer) {
			board.addObserver(((ComputerPlayer) p1).getStrategy());
		}
		if (p2 instanceof ComputerPlayer) {
			board.addObserver(((ComputerPlayer) p2).getStrategy());
		}
		turn = 0;
		playerAmount = 2;
	}

	public void play() {
		System.out.println(tui.boardToString(board));
		while (!board.gameOver()) {
			boolean moveMade = false;
			while (!moveMade) {
				try {
					moveMade = true;
					makeMove(this.detemineTurn());
				} catch (IndexOutOfBoundsException | FieldNotFreeException | FieldBelowNotTakenException e) {
					System.out.println(e.getMessage());
					moveMade = false;
				}
			}
		}
		try {
			System.out.println(getWinner().getName() + " has won the game!");
		} catch (HasNoWinnerException e) {
			System.out.println("it's a draw!");
		}
	}

	public void makeMove(Player player)
			throws IndexOutOfBoundsException, FieldNotFreeException, FieldBelowNotTakenException {
		int[] coords = player.determineMove();
		int dim = board.getDIM();
		if (board.getField(coords[0], coords[1], coords[2]) != Mark.EMPTY) {
			throw new FieldNotFreeException();
		}
		if (coords[2] != 0 && board.getField(coords[0], coords[1], coords[2] - 1) == Mark.EMPTY) {
			throw new FieldBelowNotTakenException();
		}
		if (coords[0] >= dim || coords[0] < 0 || coords[1] >= dim || coords[1] < 0 || coords[2] >= dim
				|| coords[2] < 0) {
			throw new IndexOutOfBoundsException("Field does not exist!");
		}
		board.setField(coords[0], coords[1], coords[2], player.getMark());
		turn++;
	}

	public Player detemineTurn() {
		turn = turn % playerAmount;
		if (turn == 0) {
			return player1;
		} else {
			return player2;
		}
	}

	public Player getWinner() throws HasNoWinnerException {
		if (board.isWinner(player1.getMark())) {
			return player1;
		}
		if (board.isWinner(player2.getMark())) {
			return player2;
		} else {
			throw new HasNoWinnerException("getGame was called on board without winner");
		}
	}
}
