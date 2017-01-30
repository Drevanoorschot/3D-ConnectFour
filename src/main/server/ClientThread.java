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
/**
 * Class for handling Client requests to the server and functioning
 * as a player in the gameThreads of the server.
 * @author Dré van Oorschot, Andrei Raureanu
 * @version 1.0
 */
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
	private ServerGameThread gameThread;
	//@private invariant socket != null;
	//@private invariant input != null;
	//@private invariant output != null;
	//@private invariant server != null;
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
	/*@
	requires (\forall ClientThread c; getServer().getConnectedClients().contains(c);
	 !c.getClientName().equals(text));
	ensures (\exists ClientThread c; getServer().getConnectedClients().contains(c);
	 c.getClientName().equals(text));
	ensures getClientName().equals(text);
	*/
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
	//@requires getServer().getConnectedClients().contains(this);
	//@ensures !getServer().getConnectedClients().contains(this);
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
	//@requires getServer().getConnectedClients().contains(this);
	//@requires !getServer().getReadyClients().contains(this);
	//@ensures getServer().getReadyClients().contains(this);
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
	//@requires getServer().getReadyClients().contains(this);
	//@ensures !getServer().getReadyClients().contains(this);
	public void unReadyClient() throws IllegalMethodUseException {
		if (server.getReadyClients().contains(this)) {
			server.getReadyClients().remove(this);
			System.out.println(name + " is not ready to play anymore");
			writeToClient("You are now unready to play a game");
		} else {
			throw new IllegalMethodUseException("You weren't ready so unready couldn't be invoked");
		}
	}
	//@requires text.length > 3;
	//@requires (\exists int i; text[2].equals(i));
	//@requires (\exists int i; text[3].equals(i));
	//@ensures !getMoveBuffer()[0].equals(null);
	//@ensures !getMoveBuffer()[1].equals(null);
	public void doMove(String[] text) throws NumberFormatException {
		moveBuffer = new Integer[2];
		moveBuffer[0] = Integer.parseInt(text[2]);
		moveBuffer[1] = Integer.parseInt(text[3]);
	}
	//@pure
	public void writePlayersAll() {
		String players = Protocol.RES_PLAYERS_ALL;
		for (ClientThread ct : server.getConnectedClients()) {
			players = players + " " + ct.getClientName();
		}
		writeToClient(players);
	}
	
	//@pure
	public String getClientName() {
		return name;
	}
	
	//@pure
	public Mark getMark() {
		return mark;
	}
	//@pure
	public Integer[] getMoveBuffer() {
		return moveBuffer;
	}
	//@pure
	public Server getServer() {
		return server;
	}
	//@pure
	public ServerGameThread getGameThread() {
		return gameThread;
	}

	//@requires moveBuffer.length == 2;
	//@ensures moveBuffer.equals(getMoveBuffer());
	public void setMoveBuffer(Integer[] moveBuffer) {
		this.moveBuffer = moveBuffer;
	}
	
	//@ensures mark.equals(getMark());
	public void setMark(Mark mark) {
		this.mark = mark;
	}
	
	//@ensures gameThread.equals(getGameThread());
	public void setGameThread(ServerGameThread gameThread) {
		this.gameThread = gameThread;
	}
	
	//@pure
	public void writeToClient(String msg) {
		writer.println(msg);
		writer.flush();
	}
}
