package main.server;

import exceptions.generalErrors.InternalErrorException;
import exceptions.serverErrors.IllegalMoveException;
import main.Protocol;
import model.Board;
import model.Mark;

public class GameThread extends Thread {
	private ClientThread clientThread1;
	private ClientThread clientThread2;
	private Board board;
	private int playerAmount;
	private int turn = 0;

	public GameThread(ClientThread ct1, ClientThread ct2) {
		clientThread1 = ct1;
		clientThread2 = ct2;
		clientThread1.setMark(Mark.X);
		clientThread2.setMark(Mark.O);
		clientThread1.setGameThread(this);
		clientThread2.setGameThread(this);
		board = new Board();
		playerAmount = 2;
	}

	public void run() {
		broadcast(Protocol.START + " " 
			+ clientThread1.getClientName() 
			+ " " + clientThread2.getClientName());
		while (!board.gameOver()) {
			boolean moveMade = false;
			while (!moveMade) {
				try {
					moveMade = true;
					int[] coords = makeMove(this.determineTurn());
					String ctMadeMove = this.determineTurn().getClientName();
					turn++;
					String ctNextMove = this.determineTurn().getClientName();
					broadcast(Protocol.SERVER_MOVE + " " + ctMadeMove + " "  
							+ coords[0] + " " + coords[1] + " " + ctNextMove);
				} catch (IllegalMoveException | InterruptedException e) {
					this.determineTurn().writeToClient(e.getMessage());
				}
			}
		}
		try {
			broadcast(Protocol.END_WINNER + " " + getWinner().getClientName());
		} catch (InternalErrorException e) {
			broadcast(Protocol.END_DRAW);
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

	public int[] makeMove(ClientThread ct) throws IllegalMoveException, InterruptedException {
		if (ct.getMoveBuffer().equals(null)) {
			wait();
		}
		int[] coords = ct.getMoveBuffer(); 
		for (int coord : coords) {
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
}
