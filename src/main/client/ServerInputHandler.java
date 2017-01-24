package main.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerInputHandler extends Thread {
	private Socket socket;
	private InputStream input;

	public ServerInputHandler(Socket sock) throws IOException {
		socket = sock;
		input = socket.getInputStream();
	}

	public void run() {
		boolean running = true;
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		while (running) {
			try {
				System.out.println(reader.readLine());
			} catch (IOException e) {
				System.out.println("IO-Exception on server occured");
			}
		}
	}
}

