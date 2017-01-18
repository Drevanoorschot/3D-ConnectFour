package test;

import model.Board;
import model.Mark;
import view.TUI;

public class TUITest {
	public static void main(String[] args) {
		Board board = new Board();
		TUI tui = new TUI(board);
		board.setField(1, 2, 0, Mark.O);
		System.out.println(tui.boardToString(board));
	}
}
