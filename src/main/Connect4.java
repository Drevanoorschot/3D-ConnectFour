package main;

import controller.Game;
import controller.players.ComputerPlayer;
import controller.players.HumanPlayer;
import controller.players.Player;
import controller.strategies.NaiveStrategy;
import controller.strategies.SmartStrategy;
import model.Mark;

public class Connect4 {
	public static void main(String[] args) {

		Player player1 = new ComputerPlayer(Mark.O, new SmartStrategy());
		Player player2 = new ComputerPlayer(Mark.X, new SmartStrategy());
		Game game = new Game(player2, player1, true);
		game.run();
	}
}