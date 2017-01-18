package main;

import controller.Game;
import controller.HumanPlayer;
import controller.Player;
import model.Board;
import model.Mark;
import view.TUI;

public class Connect4 {
	public static void main(String[] args) {
		Player player1 = new HumanPlayer(Mark.O, "Dude");
		Player player2 = new HumanPlayer(Mark.X, "Dude2");
		Game game = new Game(player1, player2);
		game.play();
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