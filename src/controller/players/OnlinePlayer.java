package controller.players;

import java.io.BufferedReader;
import java.io.PipedReader;

import model.Mark;

public class OnlinePlayer extends Player {
	private int[] moveBuffer;

	public OnlinePlayer(Mark m, String n) {
		super(m, n);
		moveBuffer = new int[2];
		moveBuffer[0] = -1;

	}

	@Override
	public int[] determineMove() {
			return moveBuffer;
	}

	public void setMoveBuffer(int[] moveBuffer) {
		this.moveBuffer = moveBuffer;
	}
	
	public int getMoveBuffer() {
		return moveBuffer[0];
	}
}
