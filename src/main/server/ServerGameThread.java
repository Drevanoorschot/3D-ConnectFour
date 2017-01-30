package main.server;

import exceptions.generalErrors.InternalErrorException;
import exceptions.serverErrors.IllegalMoveException;
import exceptions.serverErrors.PlayerDisconnectException;
import main.Protocol;
import model.Board;
import model.Mark;
/**
 * 
 * Class for checking moves sent by clients and making those moves on
 * the server side model of the board.
 * 
 * @author Dré van Oorschot, Andrei Raureanu
 * @version 1.0
 *
 */
public class ServerGameThread extends Thread {
	/**
	 * Player 1 of the game.
	 */
	private ClientThread clientThread1;
	/**
	 * Player 2 of the game.
	 */
	private ClientThread clientThread2;
	/**
	 * Board on which the game is played.
	 */
	private Board board;
	/**
	 * Number of players participating in this game.
	 */
	private int playerAmount;
	/**
	 * Index of the current player.
	 */
	private int turn;
	/**
	 * Becomes true when a player disconnects.
	 */
	private boolean disconnect;
	/**
	 * Indicates which player has disconnected
	 */
	private ClientThread disconnectedThread;
	/**
	 * Creates a new game object.
	 * @param ct1 player 1 of the new game.
	 * @param ct2 player 2 of the new game.
	 */
	//@invariant getClientThread1() != null;
	//@invariant getClientThread2() != null;
	//@invariant getBoard() != null;
	//@invariant getPlayerAmount() == 2;
	public ServerGameThread(ClientThread ct1, ClientThread ct2) {
		clientThread1 = ct1;
		clientThread2 = ct2;
		clientThread1.setMark(Mark.X);
		clientThread2.setMark(Mark.O);
		clientThread1.setGameThread(this);
		clientThread2.setGameThread(this);
		turn = 0;
		board = new Board();
		playerAmount = 2;
		disconnect = false;
	}
	/**
	 * {@inheritDoc}
	 * Manages the overall game flow including determining turns, messaging players and
	 * ending games.
	 */
	public void run() {
		System.out.println(toString() + " started");
		broadcast(Protocol.START + " " + clientThread1.getClientName() + " " + clientThread2.getClientName());
		while (!board.gameOver() && !disconnect) {
			boolean moveMade = false;
			while (!moveMade) {
				try {
					moveMade = true;
					Integer[] coords = makeMove(this.determineTurn());
					String ctMadeMove = this.determineTurn().getClientName();
					turn++;
					String ctNextMove = this.determineTurn().getClientName();
					if (!board.gameOver()) {
						broadcast(Protocol.SERVER_MOVE + " " + ctMadeMove + " " + coords[0] + " " + coords[1] + " "
								+ ctNextMove);
						System.out.println(Protocol.SERVER_MOVE + " " + ctMadeMove + " " + coords[0] + " " + coords[1] + " "
								+ ctNextMove);
					} else {
						broadcast(Protocol.SERVER_MOVE + " " + ctMadeMove + " " + coords[0] + " " + coords[1]);
						System.out.println(Protocol.SERVER_MOVE + " " + ctMadeMove + " " + coords[0] + " " + coords[1]);
					}

				} catch (IllegalMoveException | InterruptedException e) {
					this.determineTurn().writeToClient(e.getMessage());
					moveMade = false;
				} catch (PlayerDisconnectException e) {
					if (disconnectedThread == clientThread1) {
						broadcast(Protocol.END_WINNER + " " + clientThread2.getClientName());
						System.out.println(toString() + " is won by " + clientThread2.getClientName());
					} else {
						broadcast(Protocol.END_WINNER + " " + clientThread1.getClientName());
						System.out.println(toString() + " is won by " + clientThread1.getClientName());
					}
				}
			}
		}
		if (!disconnect) {
			try {
				broadcast(Protocol.END_WINNER + " " + getWinner().getClientName());
				System.out.println(toString() + " won by " + getWinner().getClientName());
			} catch (InternalErrorException e) {
				broadcast(Protocol.END_DRAW);
				System.out.println(toString() + " ended in a draw");
			}
		}

	}
	/**
	 * Determines whose players turn it is.
	 * @return player whose turn it is
	 */
	//@ensures getTurn() >= 0 && getTurn() < getPlayerAmount();
	public ClientThread determineTurn() {
		turn = turn % playerAmount;
		if (turn == 0) {
			return clientThread1;
		} else {
			return clientThread2;
		}
	}
	/**
	 * Takes the move from the clients move buffer and checks whether the move is valid.
	 * When valid the move will be made on the board. The method returns coordinates of 
	 * the move, which can later be sent to other clients so that
	 * they in order can update their board models to the current state of the game.
	 * @param ct player for who the move should be checked and made
	 * @return coordinates of the move
	 * @throws IllegalMoveException
	 * @throws InterruptedException
	 * @throws PlayerDisconnectException
	 */
	/*@
	  requires ct.getMoveBuffer() != null;
	  ensures (\exists int h; h >= 0 & h < getBoard().getDIM();
	  getBoard().getField(\result[0], \result[1], h) == ct.getMark());
	 */
	public Integer[] makeMove(ClientThread ct)
			throws IllegalMoveException, InterruptedException, PlayerDisconnectException {
		while (ct.getMoveBuffer() == null && !disconnect) {
			sleep(1);
		}
		if (disconnect) {
			throw new PlayerDisconnectException();
		}
		Integer[] coords = ct.getMoveBuffer();
		for (Integer coord : coords) {
			if (coord >= board.getDIM() || coord < 0) {
				throw new IllegalMoveException();
			}
		}
		board.setField(coords[0], coords[1], ct.getMark());
		ct.setMoveBuffer(null);
		return coords;
	}
	/**
	 * Checks whether a player has won by checking their mark in the board for
	 * a win condition.
	 * @return player that has won the game
	 * @throws InternalErrorException Thrown when there is no winner (e.g. in case of a
	 * draw)
	 */
	//@requires getBoard().gameOver();
	//@pure
	public ClientThread getWinner() throws InternalErrorException {
		if (board.isWinner(clientThread1.getMark())) {
			return clientThread1;
		}
		if (board.isWinner(clientThread2.getMark())) {
			return clientThread2;
		} else {
			throw new InternalErrorException();
		}
	}
	/**
	 * Returns the amount of players participating in the game.
	 * @return number of players in the game
	 */
	//@pure
	public int getPlayerAmount() {
		return playerAmount;
	}
	/**
	 * Returns the turn number between 0 and the amount of players - 1. 
	 * Every number corresponds to a player.
	 * @return number between 0 and the amount of players - 1
	 */
	//@pure
	public int getTurn() {
		return turn;
	}
	/**
	 * Returns the board on which the game is played on.
	 * @return the board of the game
	 */
	//@pure
	public Board getBoard() {
		return board;
	}
	/**
	 * Returns player 1 of the game.
	 * @return player 1
	 */
	//@pure
	public ClientThread getClientThread1() {
		return clientThread1;
	}
	/**
	 * Returns player 2 of the game.
	 * @return player 2
	 * 
	 */
	//@pure
	public ClientThread getClientThread2() {
		return clientThread2;
	}
	/**
	 * Returns whether a player in this game has disconnected.
	 * @return true if a player disconnected, false if players of this game are still connected
	 */
	//@pure
	public boolean isDisconnect() {
		return disconnect;
	}
	/**
	 * In case of a disconnection, returns the player that disconnected.
	 * @return disconnected player or <tt>null</tt> if there isn't one
	 */
	//@pure
	public ClientThread getDisconnectedThread() {
		return disconnectedThread;
	}
	/**
	 * Sends out a message to every player participating in the game.
	 * @param msg message that will be broadcasted
	 */
	//@pure
	public void broadcast(String msg) {
		clientThread1.writeToClient(msg);
		clientThread2.writeToClient(msg);
	}
	/**
	 * Returns a string representation of the game with all players participating.
	 * @return String with player names of the players participating preceded by the word "game".
	 */
	//@pure
	public String toString() {
		return "game " + clientThread1.getClientName() + " " + clientThread2.getClientName();
	}
	/**
	 *  In case of a disconnection, sets <tt>disconnect</tt> to true and
	 * <tt>disconnectedThread</tt> to the player who has disconnected.
	 * 
	 * @param disc true if a player disconnected
	 * @param ct player that disconnected
	 */
	//@ensures isDisconnect() == disc && getDisconnectedThread().equals(ct);
	public void setDisconnect(boolean disc, ClientThread ct) {
		this.disconnect = disc;
		this.disconnectedThread = ct;
	}
}
