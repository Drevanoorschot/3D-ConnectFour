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
		Board temp = getBoard();
		/*
		 * PENTRU FIECARE COORDONATA FACI O LISTA APOI IEI DIN FIECARE
		 * COORDONATA IN ORDINE SI BAGI INTR-UN VECTOR TRIDIMENSIONAL
		 * EZEZEZEZEEZZEZEZEZE
		 */
		List<int[]> myList = new ArrayList<int[]>();
		for (int c = 0; c < temp.getDIM(); c++) {
			for (int r = 0; r < temp.getDIM(); r++) {
				for (int h = 0; h < temp.getDIM(); h++) {
					if (h == 0 && temp.getField(c, r, h) == Mark.EMPTY) {
						int[] move = new int[3];
						move[0] = c;
						move[1] = r;
						move[2] = h;
						myList.add(move);
					} else if (h > 0 && temp.getField(c, r, h) == Mark.EMPTY
							&& temp.getField(c, r, h - 1) != Mark.EMPTY) {
						int[] move = new int[3];
						move[0] = c;
						move[1] = r;
						move[2] = h;
						myList.add(move);
					}
				}
			}
		}
		return myList.get((int) (Math.random() * myList.size()));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		board = (Board) arg0;
	}

}
