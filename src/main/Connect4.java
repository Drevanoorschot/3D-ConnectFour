package main;

import controller.Game;
import controller.HumanPlayer;
import controller.Player;
import model.Mark;

public class Connect4 {
	public static void main(String[] args) {
		Player player1 = new HumanPlayer(Mark.O, "Dude");
		Player player2 = new HumanPlayer(Mark.X, "Dude2");
		Game game = new Game(player1, player2);
		game.play();
	}
}