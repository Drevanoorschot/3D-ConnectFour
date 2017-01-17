package controller;

import model.Mark;

public abstract class Player {
	private Mark mark;
	private String name;

	public Player(Mark m, String n) {
		mark = m;
		name = n;
	}

	public Mark getMark() {
		return mark;
	}

	public String getName() {
		return name;
	}

	public abstract int[] makeMove();

}
