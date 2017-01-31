package test;

import controller.Game;
import controller.players.ComputerPlayer;
import controller.players.Player;
import controller.strategies.NaiveStrategy;
import controller.strategies.SmartStrategy;
import model.Mark;

public class GameTest {
	public static void main(String[] args) {
		Player p1 = new ComputerPlayer(Mark.O, new NaiveStrategy());
		Player p2 = new ComputerPlayer(Mark.X, new SmartStrategy());
		Game game = new Game(p1, p2, true);
		game.run();
	}
}
