package main;

import exceptions.InvalidInputException;

public class ServerMethods implements Protocol {
	public void checkArguments(String[] args) throws InvalidInputException {
		if (args.length != 1) {
			throw new InvalidInputException("Usage: <port>");
		}
	}
	public void checkPort(String port) {
		if ()
	}
}