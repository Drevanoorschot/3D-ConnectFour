package main.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerInputHandler extends Thread {
	private Socket socket;
	private InputStream input;
	private boolean running;

	public ServerInputHandler(Socket sock) throws IOException {
		socket = sock;
		input = socket.getInputStream();
	}

	public void run() {
		running = true;
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		while (running) {
			try {
				if (reader.ready()) {
					System.out.println(reader.readLine());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopRunning() {
		running = false;
	}
}
