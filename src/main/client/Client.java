package main.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import exceptions.serverErrors.UserAlreadyConnectedException;
import main.Protocol;

public class Client {

	public static final String EXIT = "exit";

	private String name;
	private int port;
	private InetAddress ipAddress;
	private Socket sock;
	private BufferedReader reader;
	private PrintWriter writer;

	public static void main(String[] args) {
		Client client = new Client();
		client.reader = null;
		client.writer = null;
		boolean infoReady = false;
		while (!infoReady) {
			try {
				client.getConnectionInfo();
				client.sock = new Socket(client.ipAddress, client.port);
				client.reader = 
						new BufferedReader(new InputStreamReader(client.sock.getInputStream()));
				client.writer = 
						new PrintWriter(new OutputStreamWriter(client.sock.getOutputStream()));
				client.connect();
				if (!client.reader.readLine().equals(Protocol.CONFIRM)) {
					throw new UserAlreadyConnectedException();
				} else {
					infoReady = true;
				}

			} catch (IOException e) {
				System.out.println("An IO-Exception Occured, please enter information again. " 
						+ "Possible causes:\n"
						+ "- incorrect ip address\n" 
						+ "- incorrect port number\n");
			} catch (UserAlreadyConnectedException e) {
				System.out.println(e.getMessage() + ". Please choose a different username");
			}
		}
		try {
			ServerInputHandler serverInputHandler = new ServerInputHandler(client.reader);
			serverInputHandler.start();
			client.handleTerminalInput(serverInputHandler);
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

	public void handleTerminalInput(ServerInputHandler serverInputHandler) throws IOException {
		BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));
		boolean running = true;
		while (running) {
			String input = terminalReader.readLine();
			String[] parsedInput = input.split(" ");
			if (parsedInput.length >= 1 && parsedInput[0].equals(Protocol.DISCONNECT)) {
				writeToServer(input);
				running = false;
				serverInputHandler.stopRunning();
				writer.close();
				terminalReader.close();
				reader.close();
			} else {
				writeToServer(input);
			}
		}
	}

	public void connect() {
		writeToServer(Protocol.CONNECT + " " + name);
	}
	
	public void writeToServer(String msg) {
		writer.println(msg);
		writer.flush();
	}
}
