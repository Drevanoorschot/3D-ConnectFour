package main;

import java.net.ServerSocket;
import java.net.Socket;

import exceptions.InvalidInputException;

public class Server extends ServerMethods {
	public Server() {
		super();
	}
	public static void main(String[] args) {
		int port = 0;
		boolean running = true;
		ServerSocket sock = null;
		Socket socketpair = null;
		Server server = new Server();
		try {
			server.checkArguments(args);
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		port = server.setPort(args[0]);
		server.setServerSocket(port);
		while (running) {
			server.listenForClient();
		}
	}
}