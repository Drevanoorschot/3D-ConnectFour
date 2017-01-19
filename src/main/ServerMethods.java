package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import exceptions.InvalidInputException;

public class ServerMethods implements Protocol {
	public void checkArguments(String[] args) throws InvalidInputException {
		if (args.length != 1) {
			throw new InvalidInputException("Usage: <port>");
		}
	}

	 public void checkPort(String port) {
		try {
			Integer.parseInt(port);
		} catch (NumberFormatException e) {
			System.out.println("Port should be given as an integer");
		}
	}
	 
	 public void checkServerSocket(ServerSocket sock, int port) {
		 try {
			 sock = new ServerSocket(port);
		 } catch (IOException e) {
			 System.out.println("IOException has occurred");
		 }
	 }
	 
	 public void checkSocketPair(Socket socketpair, ServerSocket sock) {
		 try {
			 socketpair = sock.accept();
		 } catch (IOException e) {
			 System.out.println("IOException has occurred");
		 }
	 }
}