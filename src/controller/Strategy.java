package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.Board;
import model.Mark;

public abstract class Strategy implements Observer {
	private Board board;

	public abstract String getName();

	public abstract int[] generateMove(Mark m);

	public Strategy() {
		board = new Board();
	}

	public Board getBoard() {
		return board;
	}

	public int[] randomMove() {
		List<int[]> myList = getPossibleMoves();
		return myList.get((int) (Math.random() * myList.size()));
	}

	public List<int[]> getPossibleMoves() {
		Board temp = getBoard();
		List<int[]> myList = new ArrayList<int[]>();
		for (int c = 0; c < temp.getDIM(); c++) {
			for (int r = 0; r < temp.getDIM(); r++) {
				
				if (temp.getField(c, r, temp.getDIM() - 1) == Mark.EMPTY) {
					int[] move = new int[2];
					move[0] = c;
					move[1] = r;
					myList.add(move);
				}
			}
		}
		return myList;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		board = (Board) arg0;
	}

}
