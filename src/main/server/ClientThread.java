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
	/**
	 * Creates a new ClientThread object.
	 * @param s socket of the client
	 * @param svr server where the clientThread is running on
	 * @throws IOException
	 */
	public ClientThread(Socket s, Server svr) throws IOException {
		socket = s;
		input = socket.getInputStream();
		output = socket.getOutputStream();
		server = svr;
		moveBuffer = null;
	}
	/**
	 * {@inheritDoc}
	 * Handles the client input and responds accordingly to the protocol methods
	 * send by the client.
	 */
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
	/**
	 * Handles the <tt>CONNECT</tt> method of the protocol. Puts the <tt>ClientThread</tt>
	 * in the list of connected clients of the server if the chosen name is not already assigned
	 * to another <tt>ClientThread</tt>.
	 * @param text name of the Client
	 * @throws UserAlreadyConnectedException thrown when another <tt>ClientThread</tt>
	 * already has the given name.
	 */
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
	/**
	 * Disconnects the current ClientThread, which means taking the <tt>ClientThread</tt>
	 * out of the connected clients list of the server and sending a message to the client it disconnected
	 * 
	 * @throws IllegalMethodUseException thrown when the <tt>ClientThread</tt> does not
	 * occur in the connected clients list.
	 */
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
	/**
	 * Puts the <tt>ClientThread</tt> inside the ready clients list of the server. And
	 * notifies the client this operation has been done.
	 * @throws IllegalMethodUseException thrown when the client is not in the connected
	 * clients list or is already in the ready clients list.
	 */
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
	/**
	 * Take <tt>ClientThread</tt> out of the ready client list of the server and
	 * notifies the client this operation has done so.
	 * @throws IllegalMethodUseException thrown when the client was not in the
	 * ready client list in the first place.
	 */
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
	/**
	 * Parses the input of the method into a <tt>Integer[]</tt> and assigns
	 * these values to the move buffer. The move buffer can later be used to
	 * determine a move of the player.
	 * @param text the to be parsed content containing the move
	 * @throws NumberFormatException
	 */
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
	/**
	 * Sends the client a list of all the clients that are connected to the server.
	 */
	//@pure
	public void writePlayersAll() {
		System.out.println(this.getName() + " requested player list");
		String players = Protocol.RES_PLAYERS_ALL;
		for (ClientThread ct : server.getConnectedClients()) {
			players = players + " " + ct.getClientName();
		}
		writeToClient(players);
	}
	/**
	 * Returns the name of the client.
	 * @return name of the client
	 */
	//@pure
	public String getClientName() {
		return name;
	}
	/**
	 * Returns the mark the client plays with.
	 * @return mark of the client
	 */
	//@pure
	public Mark getMark() {
		return mark;
	}
	/**
	 * Returns the content of the move buffer.
	 * @return client's move buffer
	 */
	//@pure
	public Integer[] getMoveBuffer() {
		return moveBuffer;
	}
	/**
	 * Returns the server in which this <tt>ClientThread</tt> is being run.
	 * @return server in which the <tt>ClientThread</tt> runs.
	 */
	//@pure
	public Server getServer() {
		return server;
	}
	/**
	 * Returns the <tt>gameThread</tt> in which the <tt>ClientThread</tt> is participating,
	 * if it is participating in one.
	 * @return <tt>GameThread</tt> in which the <tt>ClientThread</tt> is participating
	 * or <tt>null</tt> if the <tt>ClientThread</tt> is not participating in a game at
	 * the moment
	 */
	//@pure
	public ServerGameThread getGameThread() {
		return gameThread;
	}
	/**
	 * Sets the move buffer to 2 coordinates.
	 * @param moveBuffer the move the Client wishes to make
	 */
	//@requires moveBuffer.length == 2;
	//@ensures moveBuffer.equals(getMoveBuffer());
	public void setMoveBuffer(Integer[] moveBuffer) {
		this.moveBuffer = moveBuffer;
	}
	/**
	 * Set the mark where the client will be playing with in a game.
	 * @param mark mark the client is going to play with.
	 */
	//@ensures mark.equals(getMark());
	public void setMark(Mark mark) {
		this.mark = mark;
	}
	/**
	 * Set the gameThread for this <tt>ClientThread</tt> 
	 * @param gameThread
	 */
	//@ensures gameThread.equals(getGameThread());
	public void setGameThread(ServerGameThread gameThread) {
		this.gameThread = gameThread;
	}
	/**
	 * Sends a message to the client.
	 * @param msg String you wish to send to the client
	 */
	//@pure
	public void writeToClient(String msg) {
		writer.println(msg);
		writer.flush();
	}
}
