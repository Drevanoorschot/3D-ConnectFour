package controller;

import java.util.List;

import model.Board;
import model.Mark;

public class SmartStrategy extends Strategy {
	
	public SmartStrategy() {
		super();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Smart";
	}

	@Override
	public int[] generateMove(Mark m) {
		List<int[]> myList = getPossibleMoves();
		Board board = getBoard();
		for(int i = 0; i < myList.size(); i++) {
			Board temp = board.deepCopy();
			temp.setField(myList.get(i)[0], myList.get(i)[1], m);
			if(temp.isWinner(m)) {
				return myList.get(i);
			}
		}
		for(int i = 0; i < myList.size(); i++) {
			Board temp = board.deepCopy();
			temp.setField(myList.get(i)[0], myList.get(i)[1], m.next(m));
			if(temp.isWinner(m.next(m))); {
				return myList.get(i);
			}
		}
		return randomMove();
	}
}
