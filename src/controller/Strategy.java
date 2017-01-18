package controller;

import java.util.Observable;
import java.util.Observer;

import model.Board;
import model.Mark;

public abstract class Strategy implements Observer {
	private Board board;
	
	public abstract String getName();
	public abstract int generateMove(Mark m);
	public Board getBoard() {
		return board;
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		board = (Board) arg0;	
	}
	
}
