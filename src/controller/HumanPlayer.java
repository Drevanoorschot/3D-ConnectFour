package controller;

import java.util.Scanner;

import model.Mark;

public class HumanPlayer extends Player {
		
	public HumanPlayer(Mark m, String n) {
		super(m, n);
	}

	@Override
	public int[] makeMove() {
		int[] coord = new int[3];
		System.out.println(getName() + ", your turn!");
		Scanner in = new Scanner(System.in);	
		
		System.out.println("Please enter a column number (starting at 0)\n");
		coord[0] = -1;
		while (coord[0] < 0) {
			try {
				String input = in.nextLine();
				coord[0] = Integer.parseInt(input);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input, please provide a valid column number\n");
				coord[0] = -1;
			}
		}
		
		System.out.println("Please enter a row number (starting at 0)\n");
		coord[1] = -1;
		while (coord[1] < 0) {
			try {
				String input = in.nextLine();
				coord[1] = Integer.parseInt(input);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input, please provide a valid row number\n");
				coord[1] = -1;
			}
		}
		
		System.out.println("Please enter a height number (starting at 0)\n");
		coord[2] = -1;
		while (coord[2] < 0) {
			try {
				String input = in.nextLine();
				coord[2] = Integer.parseInt(input);
			} catch(NumberFormatException e) {
				System.out.println("Invalid input, please provide a valid column number\n");
				coord[2] = -1;
			}
		}
		in.close();
		return coord;
	}

}
