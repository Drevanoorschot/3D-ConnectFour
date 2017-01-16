package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Board;
import model.Mark;

public class BoardTest {
	private Board board;
	@Before
	public void setup() {
		board = new Board();
	}
	
	@Test
	public void testHasRow() {
		board.setField(1, 0, 0, Mark.O);
		board.setField(2, 0, 0, Mark.O);
		board.setField(3, 0, 0, Mark.O);
		assertFalse(board.hasRow(Mark.O));
		board.setField(0, 0, 0, Mark.O);
		assertTrue(board.hasRow(Mark.O));
	}
	@Test
	public void testHasCol() {
		board.setField(0, 1, 0, Mark.O);
		board.setField(0, 2, 0, Mark.O);
		board.setField(0, 3, 0, Mark.O);
		assertFalse(board.hasColumn(Mark.O));
		board.setField(0, 0, 0, Mark.O);
		assertTrue(board.hasColumn(Mark.O));
	}
	@Test
	public void testHasHeight() {
		board.setField(0, 0, 1, Mark.O);
		board.setField(0, 0, 2, Mark.O);
		board.setField(0, 0, 3, Mark.O);
		assertFalse(board.hasHeight(Mark.O));
		board.setField(0, 0, 0, Mark.O);
		assertTrue(board.hasHeight(Mark.O));
	}
	@Test
	public void testHasRowColumn() {
		board.setField(1, 1, 1, Mark.O);
		board.setField(2, 2, 1, Mark.O);
		board.setField(3, 3, 1, Mark.O);
		assertFalse(board.hasRowColumn(Mark.O));
		board.setField(0, 0, 1, Mark.O);
		assertTrue(board.hasRowColumn(Mark.O));
		board.reset();
		board.setField(0, 3, 2, Mark.O);
		board.setField(1, 2, 2, Mark.O);
		board.setField(2, 1, 2, Mark.O);
		assertFalse(board.hasRowColumn(Mark.O));
		board.setField(3, 0, 2, Mark.O);
		assertTrue(board.hasRowColumn(Mark.O));
	}
}
