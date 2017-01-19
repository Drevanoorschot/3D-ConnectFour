package main;

import controller.ComputerPlayer;
import controller.Game;
import controller.HumanPlayer;
import controller.NaiveStrategy;
import controller.Player;
import model.Mark;

public class Connect4 {
	public static void main(String[] args) {
		Player player1 = new HumanPlayer(Mark.O, "Bob");
		Player player2 = new ComputerPlayer(Mark.X, new NaiveStrategy());
		Game game = new Game(player2, player1);
		game.play();
	}
}