package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import exceptions.InvalidInputException;

public class ServerMethods implements Protocol {
	private ServerSocket serversocket;
	
	public void checkArguments(String[] args) throws InvalidInputException {
		if (args.length != 1) {
			throw new InvalidInputException("Usage: <portnumber>");
		}
	}

	public void checkPort(String port) {
		try {
			Integer.parseInt(port);
		} catch (NumberFormatException e) {
			System.out.println(
					"Port should be given as an integer, please alter run configuration");
			System.exit(0);
		}
	}
	
	public void setServerSocket(int port) {
		try {
			serversocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("An IO-Exception occured");
			//TODO make sensible exception handling
		}
	}
	
	public void listenForClient() {
		Socket socket = null;
		try {
			socket = serversocket.accept();
		} catch (IOException e) {
			System.out.println("An IO-Exception occured");
			//TODO make sensible exception handling
		}
		new ClientThread(socket).start();
	}
	
}