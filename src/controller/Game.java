package controller;



import controller.players.ComputerPlayer;
import controller.players.OnlinePlayer;
import controller.players.Player;

import exceptions.HasNoWinnerException;
import main.Protocol;
import main.client.Client;
import model.Board;
import view.TUI;

public class Game extends Thread {
	private Player player1;
	private Player player2;
	private Board board;
	private TUI tui;
	private int turn;
	private int playerAmount;
	private Client client;

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

	public void run() {
		System.out.println(tui.boardToString(board));
		while (!board.gameOver()) {
			boolean moveMade = false;
			while (!moveMade) {
				try {
					moveMade = true;
					makeMove(this.detemineTurn());
				} catch (IndexOutOfBoundsException | InterruptedException e) {
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
			throws IndexOutOfBoundsException, InterruptedException {
		if (player instanceof OnlinePlayer) {
			OnlinePlayer onlinePlayer = (OnlinePlayer) player;
			while (onlinePlayer.getMoveBuffer() == -1) {
				sleep(100);
			}
		}
		int[] coords = player.determineMove();
		for (int coord : coords) {
			if (coord >= board.getDIM() || coord < 0) {
				throw new IndexOutOfBoundsException("Field does not exist! Try a new move");
			}
		}
		board.setField(coords[0], coords[1], player.getMark());
		turn++;
		if (player instanceof OnlinePlayer) {
			int[] reset = new int[2];
			reset[0] = -1;
			OnlinePlayer onlinePlayer = (OnlinePlayer) player;
			onlinePlayer.setMoveBuffer(reset);
		} else {
			client.writeToServer(Protocol.CLIENT_MOVE + " " + coords[0] + " " + coords[1]);
		}
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

	public void setClient(Client client) {
		this.client = client;
	}
}
