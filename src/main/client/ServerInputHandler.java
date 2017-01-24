package main.client;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerInputHandler extends Thread {
	private Socket socket;
	private BufferedReader input;

	public ServerInputHandler(Socket sock) throws IOException {
		socket = sock;
		input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	}

	public void run() {
		boolean running = true;
		while (running) {
			try {
				if (input.ready()) {
					System.out.println(input.readLine());
				}
			} catch (IOException e) {
				System.out.println("IO-Exception on server occured");
			}
		}
	}
}
