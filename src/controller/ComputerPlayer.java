package controller;

import model.Mark;

public class ComputerPlayer extends Player {
	private Strategy strat;
	
	public ComputerPlayer(Mark m, String n) {
		super(m, n);
		// TODO Auto-generated constructor stub
	}

	public ComputerPlayer (Mark mark, Strategy strat) {
		super(mark, strat.getName() + "-" + mark);
		this.strat = strat;
	}
	
	@Override
	public int[] determineMove() {
		// TODO Auto-generated method stub
		return strat.determineMove(Board b, mark);
	}

}
