package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import model.Board;
import model.Mark;

public abstract class Strategy implements Observer {
	private Board board;
	public abstract String getName();
	public abstract int[] generateMove(Mark m);
	
	public Player player;
	
	public Strategy(Player player) {
		this.player = player;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int[] randomMove(Board board) {
		Board temporary = new Board();
		temporary = board.deepCopy();
		/* PENTRU FIECARE COORDONATA FACI O LISTA
		 * APOI IEI DIN FIECARE COORDONATA IN ORDINE SI BAGI INTR-UN VECTOR TRIDIMENSIONAL
		 * EZEZEZEZEEZZEZEZEZE
		 */
		List<int[]> myList = new ArrayList<int[]>();
		int[] move = new int[2];
		for(int c = 0; c < board.getDIM(); c++) {
			for(int r = 0; r < board.getDIM(); r++) {
				for(int h = 0; h < board.getDIM(); h++) {
						if(temporary.getField(c, r, h).equals(Mark.EMPTY) && temporary.getField(c, r, h - 1) != Mark.EMPTY) {
							move[0] = c;
							move[1] = r;
							move[2] = h;
							myList.add(move);
						}
					}
				}
			}
		//why the fuck do I get an JML error here
		return myList.get((int) Math.random() * myList.size());
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		board = (Board) arg0;	
	}
	
}
