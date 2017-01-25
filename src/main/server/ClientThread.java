package main.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import exceptions.generalErrors.UnknownMethodException;
import exceptions.serverErrors.IllegalMethodUseException;
import exceptions.serverErrors.UserAlreadyConnectedException;
import main.Protocol;

public class ClientThread extends Thread {
	private Socket socket;
	private InputStream input;
	private OutputStream output;
	private Server server;
	private String name;
	private BufferedReader reader;
	private PrintWriter writer;

	public ClientThread(Socket s, Server svr) throws IOException {
		socket = s;
		input = socket.getInputStream();
		output = socket.getOutputStream();
		server = svr;
	}

	@Override
	public void run() {
		reader = new BufferedReader(new InputStreamReader(input));
		writer = new PrintWriter(new OutputStreamWriter(output));
		System.out.println("Client is connecting...");
		boolean running = true;
		while (running) {
			try {
				String[] text = reader.readLine().split(" ");
				if (text.length >= 2 && text[0].equals(Protocol.CONNECT)) {
					connect(text);
				} else if (text.length == 1 && text[0].equals(Protocol.DISCONNECT)) {
					disconnect();
					running = false;
				} else if (text.length == 2 && (text[0] + " " + text[1]).equals(Protocol.READY)) {
					readyClient();
				} else if (text.length == 2 && (text[0] + " " + text[1]).equals(Protocol.UNREADY)){
					unReadyClient();
					writer.println("You are now unready to play a game");
					writer.flush();
				} else {
					throw new UnknownMethodException();
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
				System.out.println(e.getMessage());
				writeToClient(e.getMessage());
				running = false;
			} catch (IllegalMethodUseException | UnknownMethodException e) {
				writeToClient(e.getMessage());
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
			writeToClient(Protocol.CONFIRM);
			name = text[1];
			server.getConnectedClients().add(this);
		} else {
			throw new UserAlreadyConnectedException();
		}
	}
	
	public void disconnect() throws IllegalMethodUseException {
		if (server.getConnectedClients().contains(this)) {
			server.getConnectedClients().remove(this);
			server.getReadyClients().remove(this);
			System.out.println(name + " disconnected");
			writeToClient("You disconnected");
		} else {
			throw new IllegalMethodUseException("You are not (properly) connected");
		}
	}
	
	public void readyClient() throws IllegalMethodUseException {
		if (!server.getConnectedClients().contains(this)) {
			throw new IllegalMethodUseException("You are not (properly) connected");
		}
		if (server.getReadyClients().contains(this)) {
			throw new IllegalMethodUseException("You are already ready");
		} else {
			server.getReadyClients().add(this);
			System.out.println(name + " is ready to play");
			writeToClient("You are now ready to play a game");
		}
	}
	
	public void unReadyClient() throws IllegalMethodUseException {
		if (server.getReadyClients().contains(this)) {
			server.getReadyClients().remove(this);
			System.out.println(name + " is not ready to play anymore");
		} else {
			throw new IllegalMethodUseException("You weren't ready so unready couldn't be invoked");
		}
	}
	
	public String getClientName() {
		return name;
	}
	
	public void writeToClient(String msg) {
		writer.println(msg);
		writer.flush();
	}

}
