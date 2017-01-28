package main;

import controller.Game;
import controller.players.ComputerPlayer;
import controller.players.HumanPlayer;
import controller.players.Player;
import controller.strategies.SmartStrategy;
import model.Board;
import model.Mark;



public class Connect4 {
	public static final String USAGE = 
			"Usage: <Dimensions> <humanplayer/computerplayer> <humanplayer/computerplayer";
	public static void main(String[] args) {
		Player player1 = null;
		Player player2 = null;
		Game game = null;
		if (args.length < 3) {
			exit();
		}
		try {
			Board.setDIM(Integer.parseInt(args[0]));
		} catch (NumberFormatException e) {
			exit();
		}
		if (args[1].equals("humanplayer")) {
			player1 = new HumanPlayer(Mark.O, "player1");
		} else if (args[1].equals("computerplayer")) {
			player1 = new ComputerPlayer(Mark.O, new SmartStrategy());
		} else {
			exit();
		}
		
		if (args[2].equals("humanplayer")) {
			player2 = new HumanPlayer(Mark.X, "player1");
		} else if (args[2].equals("computerplayer")) {
			player2 = new ComputerPlayer(Mark.X, new SmartStrategy());
		} else {
			exit();
		}
		
		game = new Game(player1, player2, true);
		game.run();
	}

	public static void exit() {
		System.out.println(USAGE);
		System.exit(0);
	}

	}