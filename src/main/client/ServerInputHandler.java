package main.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerInputHandler extends Thread {
	private Socket socket;
	private BufferedReader input;
	private boolean running;

	public ServerInputHandler(Socket sock, BufferedReader reader) throws IOException {
		socket = sock;
		input = reader;
	}

	public void run() {
		running = true;
		
		while (running) {
			try {
				if (input.ready()) {
					System.out.println(input.readLine());
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
