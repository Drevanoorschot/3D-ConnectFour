package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread {
	protected Socket socket;
	
	public ClientThread(Socket s) {
		socket = s;
	}
	@Override
	public void run() {
		BufferedReader input = null;
		DataOutputStream output = null;
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO implement proper exception handling
			System.out.println("An IO-Exception Occured");
		}
		
	}
}
