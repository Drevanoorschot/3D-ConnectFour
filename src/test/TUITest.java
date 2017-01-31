package test;

import model.Board;
import model.Mark;
import view.TUI;

public class TUITest {
	public static void main(String[] args) {
		Board board = new Board();
		TUI tui = new TUI(board);
		board.setField(1, 2, Mark.O);
		board.setField(1, 2, Mark.X);
		board.setField(0, 0, Mark.X);
		System.out.println(tui.boardToString(board));
	}
}
