package main.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import exceptions.serverErrors.IllegalMethodUseException;
import exceptions.serverErrors.UserAlreadyConnectedException;
import main.Protocol;

public class ClientThread extends Thread {
	private Socket socket;
	private InputStream input;
	private OutputStream output;
	private Server server;
	private String name;

	public ClientThread(Socket s, Server svr) throws IOException {
		socket = s;
		input = socket.getInputStream();
		output = socket.getOutputStream();
		server = svr;
	}

	@Override
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(output));
		System.out.println("Client is connecting...");
		boolean running = true;
		while (running) {
			try {
				String[] text = reader.readLine().split(" ");
				if (text.length >= 2 && text[0].equals(Protocol.CONNECT)) {
					connect(text);
				}
				if (text.length >= 1 && text[0].equals(Protocol.DISCONNECT)) {
					disconnect();
					running = false;
				}
			} catch (IOException e) {
				System.out.println("IO - exception in run. Unexpected disconnect.\n"
						+ " Terminating ClientThread...");
				try {
					disconnect();
					running = false;
				} catch (IllegalMethodUseException e1) {
					//do nothing (unexpected behaviour already occured an is repported to server)
				}
				running = false;
			} catch (UserAlreadyConnectedException e) {
				System.out.println("");
				writer.println(e.getMessage());
				writer.flush();
				running = false;
			} catch (IllegalMethodUseException e) {
				writer.println(e.getMessage());
				writer.flush();
			}
		}

	}

	public void connect(String[] text) throws UserAlreadyConnectedException {
		boolean exists = false;
		for (ClientThread clientThread : server.getConnectedClients()) {
			if (clientThread.getClientName().equals(text[1])) {
				exists = true;
			}
		}
		if (!exists) {
			System.out.println(text[1] + " connected");
			name = text[1];
			server.addConnectedClient(this);
		} else {
			throw new UserAlreadyConnectedException();
		}
	}
	
	public void disconnect() throws IllegalMethodUseException {
		if (server.getConnectedClients().contains(this)) {
			server.removeConnectedClient(this);
			System.out.println(name + " disconnected");
		} else {
			throw new IllegalMethodUseException("You are not (properly) connected");
		}
	}
	
	
	public String getClientName() {
		return name;
	}

}
