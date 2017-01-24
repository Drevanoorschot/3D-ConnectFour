package controller;

import java.util.ArrayList;
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
		List<int[]> myList = getPossibleMoves(getBoard());
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
			if(temp.isWinner(m.next(m))) {
				return myList.get(i);
			}
		}
		
		List<int[]> safeMoves = new ArrayList<>();;
		for(int i = 0; i < myList.size(); i++) {
			Board temp = board.deepCopy();
			temp.setField(myList.get(i)[0], myList.get(i)[1], m);
			List<int[]> futureList = getPossibleMoves(temp);
			boolean valid = true;
			for(int j = 0; j < futureList.size(); j++) {
				temp.setField(futureList.get(j)[0], futureList.get(j)[1], m.next(m));
				if(temp.isWinner(m.next(m))) {
					valid = false;
				}
				if(valid) {
					safeMoves.add(myList.get(i));
				}
			}
		} 
		return safeMoves.get((int) (Math.random() * safeMoves.size()));
	}
}
