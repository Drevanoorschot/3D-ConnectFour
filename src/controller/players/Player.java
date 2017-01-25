package controller.players;

import controller.strategies.Strategy;
import model.Mark;

public abstract class Player {
	private Mark mark;
	private String name;

	public Player(Mark m, String n) {
		mark = m;
		name = n;
	}
	
	public Player(Mark m, Strategy strat) {
		mark = m;
		name = strat.getName();
	}

	public Mark getMark() {
		return mark;
	}

	public String getName() {
		return name;
	}

	public abstract int[] determineMove();

}
