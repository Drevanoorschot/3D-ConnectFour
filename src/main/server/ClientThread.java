package main.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import exceptions.generalErrors.IllegalMethodSyntaxException;
import exceptions.generalErrors.UnknownMethodException;
import exceptions.serverErrors.IllegalMethodUseException;
import exceptions.serverErrors.UserAlreadyConnectedException;
import main.Protocol;
import model.Mark;

public class ClientThread extends Thread {
	private Socket socket;
	private InputStream input;
	private OutputStream output;
	private Server server;
	private String name;
	private BufferedReader reader;
	private PrintWriter writer;
	private Mark mark;
	private Integer[] moveBuffer;

	private GameThread gameThread;

	public ClientThread(Socket s, Server svr) throws IOException {
		socket = s;
		input = socket.getInputStream();
		output = socket.getOutputStream();
		server = svr;
		moveBuffer = null;
	}

	@Override
	public void run() {
		reader = new BufferedReader(new InputStreamReader(input));
		writer = new PrintWriter(new OutputStreamWriter(output));
		System.out.println("Client is connecting...");
		boolean running = true;
		while (running) {
			try {
				String rawText = reader.readLine();
				if (rawText != null) {
					String[] text = rawText.split(" ");
					if (text.length >= 2 && text[0].equals(Protocol.CONNECT)) {
						connect(text);
					} else if (text.length == 1 && text[0].equals(Protocol.DISCONNECT)) {
						disconnect();
						running = false;
					} else if (text.length == 2 && rawText.equals(Protocol.READY)) {
						readyClient();
					} else if (text.length == 2 && rawText.equals(Protocol.UNREADY)) {
						unReadyClient();
					} else if (text.length == 2 && rawText.equals(Protocol.ASK_PLAYERS_ALL)) {
						writePlayersAll();
					} else if (text.length == 4 && rawText.startsWith(Protocol.CLIENT_MOVE)) {
						if (gameThread != null && gameThread.determineTurn().equals(this)) {
							doMove(text);
						} else {
							throw new IllegalMethodUseException(
									"possible causes:\n" + "- not in game\n" + "- not your turn");
						}
					} else {
						throw new UnknownMethodException();
					} 
				} else {
					disconnect();
				}
			} catch (IOException e) {
				System.out.println("IO - exception in run. Unexpected disconnect by" + this.getName()
						+ ".\n Terminating ClientThread...");
				try {
					disconnect();
					running = false;
				} catch (IllegalMethodUseException e1) {
					// do nothing (unexpected behaviour already occured an is
					// repported to server)
				}
				running = false;
			} catch (UserAlreadyConnectedException e) {
				System.out.println(e.getMessage());
				writeToClient(e.getMessage());
				running = false;
			} catch (IllegalMethodUseException | UnknownMethodException e) {
				writeToClient(e.getMessage());
			} catch (NumberFormatException e) {
				try {
					moveBuffer = null;
					throw new IllegalMethodSyntaxException();
				} catch (IllegalMethodSyntaxException e1) {
					writeToClient(e1.getMessage());
				}
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
			if (gameThread != null) {
				gameThread.setDisconnect(true, this);
			}
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
			writeToClient("You are now unready to play a game");
		} else {
			throw new IllegalMethodUseException("You weren't ready so unready couldn't be invoked");
		}
	}

	public void doMove(String[] text) throws NumberFormatException {
		moveBuffer = new Integer[2];
		moveBuffer[0] = Integer.parseInt(text[2]);
		moveBuffer[1] = Integer.parseInt(text[3]);
	}

	public void writePlayersAll() {
		String players = Protocol.RES_PLAYERS_ALL;
		for (ClientThread ct : server.getConnectedClients()) {
			players = players + " " + ct.getClientName();
		}
		writeToClient(players);
	}

	public String getClientName() {
		return name;
	}

	public Mark getMark() {
		return mark;
	}

	public Integer[] getMoveBuffer() {
		return moveBuffer;
	}

	public void setMoveBuffer(Integer[] moveBuffer) {
		this.moveBuffer = moveBuffer;
	}

	public void setMark(Mark mark) {
		this.mark = mark;
	}

	public void setGameThread(GameThread gameThread) {
		this.gameThread = gameThread;
	}

	public void writeToClient(String msg) {
		writer.println(msg);
		writer.flush();
	}
}
