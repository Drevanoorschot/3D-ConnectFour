package controller;

import model.Mark;

public class NaiveStrategy extends Strategy {

	public NaiveStrategy() {
		super();

	}

	@Override
	public String getName() {

		return "Naive";
	}

	@Override
	public int[] generateMove(Mark m) {
		return randomMove();
	}

}
