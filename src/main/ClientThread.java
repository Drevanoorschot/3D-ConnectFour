package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientThread extends Thread {
	protected Socket socket;
	
	public ClientThread(Socket s) {
		socket = s;
	}
	@Override
	public void run() {
		BufferedReader inputReader = null;
		BufferedWriter output = null;
		try {
			inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			String input = inputReader.readLine();
			while (!input.equals(Protocol.CONNECT)) {
				output.write("ERROR 111: Method not allowed at this moment");
			}
		} catch (IOException e) {
			// TODO implement proper exception handling
			System.out.println("An IO-Exception Occured");
		}
			
		
	}
}
