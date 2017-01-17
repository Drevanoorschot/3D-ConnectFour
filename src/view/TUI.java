package view;

import model.Board;
import model.Mark;

public class TUI {
	private String graphicBoard;
	private Board board;
	

	public TUI(Board b) {
		Mark m = Mark.EMPTY;
		board = b;
		graphicBoard = 	"     layer 1                 layer 2    \n"
				+      	"+---+---+---+---+	+---+---+---+---+\n"
<<<<<<< HEAD
				+ 	 	"| " + Mark.X + " |   |   |   |  |   |   |   |   |\n"
=======
				+ 	 	"|   |   |   |   |       |   |   |   |   |\n"
>>>>>>> branch 'master' of https://github.com/Drevanoorschot/3D-ConnectFour.git
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
	}
	
	public String toString() {
		return graphicBoard;
	}
}
