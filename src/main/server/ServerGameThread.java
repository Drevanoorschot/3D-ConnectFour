package main.server;

import exceptions.generalErrors.InternalErrorException;
import exceptions.serverErrors.IllegalMoveException;
import exceptions.serverErrors.PlayerDisconnectException;
import main.Protocol;
import model.Board;
import model.Mark;

public class ServerGameThread extends Thread {
	private ClientThread clientThread1;
	private ClientThread clientThread2;
	private Board board;
	private int playerAmount;
	private int turn = 0;
	private boolean disconnect;
	private ClientThread disconnectedThread;

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
		board = new Board();
		playerAmount = 2;
		disconnect = false;
	}

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
					} else {
						broadcast(Protocol.SERVER_MOVE + " " + ctMadeMove + " " + coords[0] + " " + coords[1]);
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
	//@ensures getTurn() >= 0 && getTurn() < getPlayerAmount();
	public ClientThread determineTurn() {
		turn = turn % playerAmount;
		if (turn == 0) {
			return clientThread1;
		} else {
			return clientThread2;
		}
	}
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
	
	//@pure
	public int getPlayerAmount() {
		return playerAmount;
	}
	//@pure
	public int getTurn() {
		return turn;
	}
	//@pure
	public Board getBoard() {
		return board;
	}
	
	//@pure
	public ClientThread getClientThread1() {
		return clientThread1;
	}
	
	//@pure
	public ClientThread getClientThread2() {
		return clientThread2;
	}
	//@pure
	public boolean isDisconnect() {
		return disconnect;
	}
	
	//@pure
	public ClientThread getDisconnectedThread() {
		return disconnectedThread;
	}

	//@pure
	public void broadcast(String msg) {
		clientThread1.writeToClient(msg);
		clientThread2.writeToClient(msg);
	}
	//@pure
	public String toString() {
		return "game " + clientThread1.getClientName() + " " + clientThread2.getClientName();
	}
	
	//@ensures isDisconnect() == disc && getDisconnectedThread().equals(ct);
	public void setDisconnect(boolean disc, ClientThread ct) {
		this.disconnect = disc;
		this.disconnectedThread = ct;

	}
}
