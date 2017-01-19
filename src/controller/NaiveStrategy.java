package controller;

import model.Board;
import model.Mark;

public class NaiveStrategy extends Strategy {

	public NaiveStrategy(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Naive";
	}

	@Override
	public int[] generateMove(Mark m) {
		return randomMove(getBoard());
		// TODO Auto-generated method stub
	}

}
