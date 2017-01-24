package main.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	public static final String EXIT = "exit";

	private String name;
	private int port;
	private InetAddress ipAddress;
	private Socket sock;

	public static void main(String[] args) {
		Client client = new Client();
		boolean infoReady = false;
		while (!infoReady) {
			try {
				client.getConnectionInfo();
				client.sock = new Socket(client.ipAddress, client.port);
				infoReady = true;
			} catch (IOException e) {
				System.out.println("An IO-Exception Occured, please enter information again. " 
						+ "Possible causes:\n"
						+ "- incorrect ip address\n" 
						+ "- incorrect port number\n");
			}
		}
		try {
			Thread serverInputHandler = new ServerInputHandler(client.sock);
			serverInputHandler.start();
			BufferedReader reader = 
					new BufferedReader(new InputStreamReader(client.sock.getInputStream()));
			PrintWriter writer = 
					new PrintWriter(new OutputStreamWriter(client.sock.getOutputStream()));
			client.connect(writer);
		} catch (IOException e) {
			System.out.println("IO exception in main client");
		}
		

	}

	public void getConnectionInfo() throws IOException {
		BufferedReader terminalInput = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("What is your name?");
		String[] nameparts = terminalInput.readLine().split(" ");
		name = "";
		for (int i = 0; i < nameparts.length; i++) {
			name = name + nameparts[i];
		}
		System.out.println("To what ip address do you wish to connect?");
		ipAddress = InetAddress.getByName(terminalInput.readLine());
		System.out.println("To what port do you wish to connect?");
		port = Integer.parseInt(terminalInput.readLine());
	}
	
	public void handleTerminalInput(PrintWriter writer, BufferedReader reader) {
		
	}
	
	public void connect(PrintWriter writer) {
		writer.println("CONNECT " + name);
		writer.flush();
	}
}
