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
		board.setField(1, 0, Mark.O);
		board.setField(2, 0, Mark.O);
		board.setField(3, 0, Mark.O);
		assertFalse(board.hasRow(Mark.O));
		board.setField(0, 0, Mark.O);
		assertTrue(board.hasRow(Mark.O));
	}
	@Test
	public void testHasCol() {
		board.setField(0, 1, Mark.O);
		board.setField(0, 2, Mark.O);
		board.setField(0, 3, Mark.O);
		assertFalse(board.hasColumn(Mark.O));
		board.setField(0, 0, Mark.O);
		assertTrue(board.hasColumn(Mark.O));
	}
	@Test
	public void testHasHeight() {
		board.setField(0, 0, Mark.O);
		board.setField(0, 0, Mark.O);
		board.setField(0, 0, Mark.O);
		assertFalse(board.hasHeight(Mark.O));
		board.setField(0, 0, Mark.O);
		assertTrue(board.hasHeight(Mark.O));
	}
	@Test
	public void testHasRowColumn() {
		board.setField(1, 1, Mark.O);
		board.setField(2, 2, Mark.O);
		board.setField(3, 3, Mark.O);
		assertFalse(board.hasRowColumn(Mark.O));
		board.setField(0, 0, Mark.O);
		assertTrue(board.hasRowColumn(Mark.O));
		board.reset();
		board.setField(0, 3, Mark.O);
		board.setField(1, 2, Mark.O);
		board.setField(2, 1, Mark.O);
		assertFalse(board.hasRowColumn(Mark.O));
		board.setField(3, 0, Mark.O);
		assertTrue(board.hasRowColumn(Mark.O));
	}
	@Test
	public void testHasRowHeight() {
		board.setField(1, 2, Mark.O);
		board.setField(2, 2, Mark.O);
		board.setField(3, 2, Mark.O);
		assertFalse(board.hasRowHeight(Mark.O));
		board.setField(0, 2, Mark.O);
		assertTrue(board.hasRowHeight(Mark.O));
		board.reset();
		board.setField(0, 3, Mark.O);
		board.setField(1, 3, Mark.O);
		board.setField(2, 3, Mark.O);
		assertFalse(board.hasRowHeight(Mark.O));
		board.setField(3, 3, Mark.O);
		assertTrue(board.hasRowHeight(Mark.O));
	}
	@Test
	public void testHasColumnHeight() {
		board.setField(3, 1, Mark.O);
		board.setField(3, 2, Mark.O);
		board.setField(3, 3, Mark.O);
		assertFalse(board.hasColumnHeight(Mark.O));
		board.setField(3, 0, Mark.O);
		assertTrue(board.hasColumnHeight(Mark.O));
		board.reset();
		board.setField(2, 0, Mark.O);
		board.setField(2, 1, Mark.O);
		board.setField(2, 2, Mark.O);
		assertFalse(board.hasColumnHeight(Mark.O));
		board.setField(2, 3, Mark.O);
		assertTrue(board.hasColumnHeight(Mark.O));
	}
	@Test
	public void testHasDiagTopLeft() {
		board.setField(0, 0, Mark.O);
		board.setField(1, 1, Mark.O);
		board.setField(2, 2, Mark.O);
		assertFalse(board.hasRowColumnHeight(Mark.O));
		board.setField(3, 3, Mark.O);
		assertTrue(board.hasRowColumnHeight(Mark.O));
	}
	@Test
	public void testHasDiagBottomRight() {
		board.setField(0, 3, Mark.O);
		board.setField(1, 2, Mark.O);
		board.setField(2, 1, Mark.O);
		assertFalse(board.hasRowColumnHeight(Mark.O));
		board.setField(3, 0, Mark.O);
		assertTrue(board.hasRowColumnHeight(Mark.O));
	}
	@Test
	public void testHasDiagBottomLeft() {
		board.setField(3, 0, Mark.O);
		board.setField(2, 1, Mark.O);
		board.setField(1, 2, Mark.O);
		assertFalse(board.hasRowColumnHeight(Mark.O));
		board.setField(0, 3, Mark.O);
		assertTrue(board.hasRowColumnHeight(Mark.O));
	}
	@Test
	public void testHasDiagTopRight() {
		board.setField(0, 0, Mark.O);
		board.setField(1, 1, Mark.O);
		board.setField(2, 2, Mark.O);
		assertFalse(board.hasRowColumnHeight(Mark.O));
		board.setField(3, 3, Mark.O);
		assertTrue(board.hasRowColumnHeight(Mark.O));
	}
}
