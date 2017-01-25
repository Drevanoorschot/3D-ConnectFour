package main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidInputException;

public class Server {
	private int port;
	private ServerSocket serverSocket;
	private List<ClientThread> connectedClients;
	private List<ClientThread> readyClients;
	
	public Server() {
		connectedClients = new ArrayList<ClientThread>();
		readyClients = new ArrayList<ClientThread>();
	}
	
	public List<ClientThread> getConnectedClients() {
		return connectedClients;
	}
	
	public List<ClientThread> getReadyClients() {
		return readyClients;
	}

	public static void main(String[] args) {
		Server server = new Server();
		try {
			server.checkArguments(args);
			server.setPort(args[0]);
			server.serverSocket = new ServerSocket(server.port);
		} catch (InvalidInputException | IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		boolean running = true;
		while (running) {
			try {
				Socket socket = server.serverSocket.accept();
				Thread clientThread = new ClientThread(socket, server);
				clientThread.start();
				if (server.getReadyClients().size() >= 2) {
					server.startGame();
					
				}
			} catch (IOException e) {
				System.out.println("IO exception occured");
			}
		}
	}

	public void startGame() {
		Thread game = new GameThread(getReadyClients().get(0), getReadyClients().get(1));
		getReadyClients().remove(1);
		getReadyClients().remove(0);
		game.start();
	}
	
	public void checkArguments(String[] args) throws InvalidInputException {
		if (args.length != 1) {
			throw new InvalidInputException("Usage: <portnumber>");
		}
	}

	public void setPort(String arg) {
		try {
			port = Integer.parseInt(arg);
		} catch (NumberFormatException e) {
			System.out.println(
					"Port should be given as an integer, please alter run configuration");
			System.exit(0);
		}
	}

}