package main.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import main.Protocol;

public class ClientThread extends Thread {
	protected Socket socket;
	protected InputStream input;
	protected OutputStream output;

	public ClientThread(Socket s) throws IOException {
		socket = s;
		input = socket.getInputStream();
		output = socket.getOutputStream();

	}

	@Override
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(output));
		boolean running = true;
		while (running) {
			try {
				if (reader.ready()) {
					String[] text = reader.readLine().split(" ");
					if (text.length >= 2 && text[0].equals("CONNECT")) {
						System.out.println("connected!");
					}
				}
			} catch (IOException e) {
				System.out.println("IO - exception in run");
			}
		}
		
	}

}
