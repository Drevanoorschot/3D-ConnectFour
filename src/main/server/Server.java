package main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import exceptions.InvalidInputException;

public class Server {
	private int port;
	private ServerSocket serverSocket;
	
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
				Thread clientThread = new ClientThread(socket);
				clientThread.start();
			} catch (IOException e) {
				System.out.println("IO exception occured");
			}
		}
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