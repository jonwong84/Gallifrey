/**
 * Genetic Algorithms Project - Partition (Three Approaches)
 * 
 * This is Karp's Partition problem, solvable using one of three
 * available algorithms: brute-force, random, and heuristic.
 * All three are chosen at the beginning of the program, and are
 * featured in this program as individual classes.
 * 
 * This program also allows for multiple sets of inputs
 * within the same input file.
 * 
 * @author Jonathan Wong
 * 
 */

import java.io.*;
import java.util.Scanner;

public class PartitionMain {

	static Scanner s;
	static BufferedReader inStream;
	static PrintWriter outStream;

	/**
	 * The main method reads in two files: args[0] is used for input, args[1]
	 * for output. For each given set of elements, the data is parsed and
	 * processed accordingly.
	 * 
	 * Main serves as a central hub for the included algorithms, and features
	 * a convenient UI whose design allows for additional options in the future.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		if (args.length < 2)
			throw new FileNotFoundException("One or more missing arguments.");
		setOut(args[1]);
		String line = "";
		inStream = new BufferedReader(new FileReader(args[0]));

		String input;
		s = new Scanner(System.in);
		boolean exit = false;
		boolean output = false;
		System.out.println("Algorithmic Solutions For Karp's Partition Problem \n by Jonathan Wong\n");
		System.out
				.println(" Please input one of the following:");
		System.out.println(" 0. Exit Program");
		System.out.println(" 1. Brute Force");
		System.out.println(" 2. Random Generation");
		System.out.println(" 3. Genetic Algorithm");
		
		while (!exit) {
		input = s.next();

		if (input.equals("1")) {
			BruteForce bf = new BruteForce(inStream, outStream, line);
			bf.run();
			exit = true;
			output = true;
		} else if (input.equals("2")) {
			System.out.print("Number of iterations: ");
			input = s.next();
			int n = Integer.parseInt(input);
			System.out.println();
			RandomAlg ra = new RandomAlg(inStream, outStream, line, n);
			ra.run();
			exit = true;
			output = true;
		} else if (input.equals("3")) {
			GeneticAlg ga = new GeneticAlg(inStream, outStream, line);
			ga.run();
			exit = true;
			output = true;
		} else if (input.equals("0")) {
			exit = true;
		} else
			System.out.println("Incorrect input. Please select an option.");
		}
		
		System.out.print("End of program. ");
		if (output) System.out.println("Please see output file.");
		else System.out.println();
	} // main

	/**
	 * Designates a file that will receive outputs from the program.
	 * 
	 * @param outFile
	 */
	public static void setOut(String outFile) {

		try {
			outStream = new PrintWriter(new FileOutputStream(outFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	} // setOut
} // Main
