package main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidInputException;
import main.Protocol;
import main.client.Client;

public class ServerMethods implements Protocol {
	private ServerSocket serversocket;
	private List<Client> clientList;
	private List<Client> clientReadyList;
	
	public ServerMethods() {
		clientList = new ArrayList<Client>();
		clientReadyList = new ArrayList<Client>();
	}
	
	public List<Client> getClientList() {
		return clientList;
	}
	
	public List<Client> getClientReadyList() {
		return clientReadyList;
	}
	
	public void checkArguments(String[] args) throws InvalidInputException {
		if (args.length != 1) {
			throw new InvalidInputException("Usage: <portnumber>");
		}
	}

	public int setPort(String port) {
		try {
			return (int) Integer.parseInt(port);
		} catch (NumberFormatException e) {
			System.out.println(
					"Port should be given as an integer, please alter run configuration");
			System.exit(0);
		}
		return -1;
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
			System.out.println("client connecting...");
		} catch (IOException e) {
			System.out.println("An IO-Exception occured");
			//TODO make sensible exception handling
		}
		new ClientThread(socket).start();
	}
	
}