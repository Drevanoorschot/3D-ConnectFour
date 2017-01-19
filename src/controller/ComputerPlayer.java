package controller;

import model.Mark;

public class ComputerPlayer extends Player {
	private Strategy strat;

	public ComputerPlayer(Mark mark, Strategy strat) {
		super(mark, strat.getName() + "-" + mark);
		this.strat = strat;
	}
	
	@Override
	public int[] determineMove() {
		System.out.println(getName() + " is generating move...");
		return strat.generateMove(getMark());
	}
	
	public Strategy getStrategy() {
		return strat;
	}

}
