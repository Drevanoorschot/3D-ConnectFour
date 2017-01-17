package main;

import model.Board;
import model.Mark;
import view.TUI;

public class Connect4 {
	public static void main(String[] args) {
		Board board = new Board();
		TUI view = new TUI(board);
		System.out.println(view.boardToString());
		board.setField(0, 0, 2, Mark.O);
		System.out.println(view.boardToString());
		board.setField(0, 1, 0, Mark.X);
		System.out.println(view.boardToString());
		board.setField(1, 1, 3, Mark.X);
		System.out.println(view.boardToString());
	}
}


/*
graphicBoard = "     layer 1                 layer 2    \n"
+      	"+---+---+---+---+	+---+---+---+---+\n"
+ 	 	"|" + this.fieldToString(0, 0, 0) + "|" + this.fieldToString(0, 1, 0) + "|" + this.fieldToString(0, 2, 0) + "|" + this.fieldToString(0, 3, 0) + "|       " + "|" + this.fieldToString(0, 0, 1) + "|" + this.fieldToString(0, 1, 1) + "|" + this.fieldToString(0, 2, 1) + "|" + this.fieldToString(0, 3, 1) + "|\n"
+	    "+---+---+---+---+	+---+---+---+---+\n"
+ 	    "|   |   |   |   |       |   |   |   |   |\n"
+	    "+---+---+---+---+	+---+---+---+---+\n"
+ 	    "|   |   |   |   |       |   |   |   |   |\n"
+	    "+---+---+---+---+	+---+---+---+---+\n"
+ 	    "|   |   |   |   |       |   |   |   |   |\n"
+	    "+---+---+---+---+	+---+---+---+---+\n"
+		"\n"
+		"     layer 3                 layer 4    \n"
+      	"+---+---+---+---+	+---+---+---+---+\n"
+ 	 	"|   |   |   |   |       |   |   |   |   |\n"
+	    "+---+---+---+---+	+---+---+---+---+\n"
+ 	    "|   |   |   |   |       |   |   |   |   |\n"
+	    "+---+---+---+---+	+---+---+---+---+\n"
+ 	    "|   |   |   |   |       |   |   |   |   |\n"
+	    "+---+---+---+---+	+---+---+---+---+\n"
+ 	    "|   |   |   |   |       |   |   |   |   |\n"
+	    "+---+---+---+---+	+---+---+---+---+\n";

*/