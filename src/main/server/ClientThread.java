package main.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import exceptions.generalErrors.UnknownMethodException;
import exceptions.serverErrors.IllegalMethodUseException;
import main.Protocol;

public class ClientThread extends Thread {
	protected Socket socket;
	private boolean connected;
	private String clientName;

	public ClientThread(Socket s) {
		socket = s;
	}

	public void setClientName(String name) {
		clientName = name;
	}

	@Override
	public void run() {
		BufferedReader inputReader = null;
		BufferedWriter output = null;
		try {
			inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			handleInput(inputReader, output);
		} catch (IOException | IllegalMethodUseException | UnknownMethodException e) {
			try {
				output.write(e.getMessage());
			} catch (IOException e1) {
				System.out.println("IO-Exception occured");
			}
		}
	}

	public void handleInput(BufferedReader inputReader, BufferedWriter output)
			throws IOException, IllegalMethodUseException, UnknownMethodException {
		boolean running = true;
		while (running) {
			while (inputReader.ready()) {
				String[] input = inputReader.readLine().split(" ");

				if (input.length >= 2 
						&& (input[0] + input[1]).equals(Protocol.CONNECT)) {
					setClientName(input[1]);
					connected = true;
					System.out.println(getClientName() + " connected!");
				} else {
					throw new UnknownMethodException();
				}
			}
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public String getClientName() {
		return clientName;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
