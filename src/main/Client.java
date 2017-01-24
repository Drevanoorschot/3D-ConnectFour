package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	public static final String EXIT = "exit";

	protected String name;
	private int port;
	private InetAddress ipAddress;

	public static void main(String[] args) {
		Client client = new Client();
		boolean infoReady = false;
		while (!infoReady) {
			try {
				client.getConnectionInfo();
				Socket sock = new Socket(client.getIpAddress(), client.getPort());
				infoReady = true;
			} catch (IOException e) {
				System.out.println("An IO-Exception Occured, please enter information again. "
						+ "Possible causes:\n"
						+ "- incorrect ip address\n" 
						+ "- incorrect port number\n");
			}
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setIpAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getName() {
		return name;
	}

	public int getPort() {
		return port;
	}

	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public void getConnectionInfo() throws IOException {
		BufferedReader terminalInput = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("What is your name?");
		String[] nameparts = terminalInput.readLine().split(" ");
		String nameInput = "";
		for (int i = 0; i < nameparts.length; i++) {
			name = name + nameparts[i];
		}
		setName(nameInput);
		System.out.println("To what ip address do you wish to connect?");
		setIpAddress(InetAddress.getByName(terminalInput.readLine()));
		System.out.println("To what port do you wish to connect?");
		setPort(Integer.parseInt(terminalInput.readLine()));
	}
	public void HandleTerminalInput() {
		
	}
}
