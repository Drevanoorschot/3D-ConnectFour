package main.server;

import exceptions.generalErrors.InternalErrorException;
import exceptions.serverErrors.IllegalMoveException;
import exceptions.serverErrors.PlayerDisconnectException;
import main.Protocol;
import model.Board;
import model.Mark;

public class GameThread extends Thread {
	private ClientThread clientThread1;
	private ClientThread clientThread2;
	private Board board;
	private int playerAmount;
	private int turn = 0;
	private boolean disconnect;
	private ClientThread disconnectedThread;

	public GameThread(ClientThread ct1, ClientThread ct2) {
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
		while (!board.gameOver() || !disconnect) {
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
				}
			}
		}
		try {
			broadcast(Protocol.END_WINNER + " " + getWinner().getClientName());
			System.out.println(toString() + " won by " + getWinner().getClientName());
		} catch (InternalErrorException e) {
			if (!disconnect) {
				broadcast(Protocol.END_DRAW);
				System.out.println(toString() + " ended in a draw");
			} else {
				try {
					throw new PlayerDisconnectException();
				} catch (PlayerDisconnectException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public ClientThread determineTurn() {
		turn = turn % playerAmount;
		if (turn == 0) {
			return clientThread1;
		} else {
			return clientThread2;
		}
	}

	public Integer[] makeMove(ClientThread ct) throws IllegalMoveException, InterruptedException {
		while (ct.getMoveBuffer() == null) {
			sleep(500);
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

	public void broadcast(String msg) {
		clientThread1.writeToClient(msg);
		clientThread2.writeToClient(msg);
	}

	public String toString() {
		return "game " + clientThread1.getClientName() + " " + clientThread2.getClientName();
	}

	public void setDisconnect(boolean disc, ClientThread ct) {
		this.disconnect = disc;
		this.disconnectedThread = ct;

	}
}
