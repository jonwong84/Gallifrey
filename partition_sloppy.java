/**
 * Karp's Partition Problem (Brute-Force)
 * 
 * @author Jonathan Wong
 * 
 */

import java.io.*;
import java.util.LinkedList;

public class Main {

  static int[] n;
	static int[] bit;
	static int l;
	static boolean overflow;
	static int diff;

	static int s0_sum;
	static int s1_sum;
	static LinkedList<Integer> s0;
	static LinkedList<Integer> s1;
	static boolean confirmed;
	static int matchSum;
	static LinkedList<Integer> s0_config;
	static LinkedList<Integer> s1_config;
	static int s0_saved_sum;
	static int s1_saved_sum;

	static PrintWriter outStream;

	/**
	 * The main method reads in two files: args[0] is used for input, args[1]
	 * for output. For each given set of elements, the data is parsed and
	 * processed accordingly. Utilizes an external TextFileInput for easy file
	 * input and processing, and PrintWriter for output.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length < 2)
			throw new KarpException("One or more missing arguments.");
		TextFileInput textFromFile = new TextFileInput(args[0]);
		setOut(args[1]);

		for (String line = textFromFile.readLine(); line != null; line = textFromFile
				.readLine()) {

			if (line.equals(""))
				continue;

			String prebatch[] = line.split(" ");

			l = prebatch.length;
			overflow = false;
			n = new int[l];
			bit = new int[l + 1];
			confirmed = false;

			parseData(prebatch);
			diff = setDiff();
			print();
			generate();
			displayln("");
			matchCheck();
			displayln("---");

		}
		closeIO();

	} // main

	/**
	 * Elements from input are allocated to an array of integers.
	 * 
	 * @param prebatch
	 */
	public static void parseData(String[] prebatch) {
		for (int i = 0; i < prebatch.length; i++)
			n[i] = Integer.parseInt(prebatch[i]);
	} // parseData

	/**
	 * Calculates and returns the sum of the initial set.
	 * 
	 * @return
	 */
	public static int setDiff() {
		int num = 0;
		for (int i = 0; i < n.length; i++)
			num += n[i];
		return num;
	} // setDiff

	/**
	 * Prints out the elements in the main set.
	 */
	public static void print() {
		display("Elements: { ");
		for (int i = 0; i < n.length; i++)
			display(n[i] + " ");
		displayln("} \n");
	} // print

	/**
	 * Recursively increments the binary counter. Also implements overflow
	 * detection.
	 * 
	 * @param i
	 */
	public static void increment(int i) {

		if (overflow == true) {
			displayln("OVERFLOW");
			return;
		}

		if (i == bit.length)
			return;

		if (bit[i] == 0) {
			bit[i]++;

			if (i == bit.length - 1) {
				overflow = true;
			}

			return;
		} else if (bit[i] == 1) {
			bit[i] = 0;
			i++;
			increment(i);
		}
	} // increment

	/**
	 * Calculates and returns the difference between the sums of the two
	 * subsets.
	 * 
	 * @return
	 */
	public static int diffCheck() {
		int num = Math.abs(s1_sum - s0_sum);
		return num;
	} // diffCheck

	/**
	 * Iterates through every possible way the set can be partitioned. An
	 * array-implemented binary counter assigns each element to a specific
	 * subset; it is incremented after each iteration. If an equally-divided
	 * partition is found, the result is saved and printed out later.
	 */
	public static void generate() {

		int count = 0;
		int diffCompare;
		s0 = new LinkedList<Integer>();
		s1 = new LinkedList<Integer>();

		while (!overflow) {
			for (int i = 0; i < l; i++)
				if (bit[i] == 0) {
					s0_sum += n[i];
					s0.add(n[i]);
				} else if (bit[i] == 1) {
					s1_sum += n[i];
					s1.add(n[i]);
				}

			diffCompare = diffCheck();

			if (diffCompare <= diff) {
				saveConfig();
				diff = diffCompare;
			}

			if (diff == 0)
				confirmed = true;

			displayln("Iteration " + count + ":");

			display(" s0: { ");
			while (s0.size() > 0) {
				Integer n = s0.pop();
				display(n + " ");
			}
			display("} = " + s0_sum);
			displayln("");

			display(" s1: { ");
			while (s1.size() > 0) {
				Integer n = s1.pop();
				display(n + " ");
			}
			display("} = " + s1_sum);
			displayln("");

			s0_sum = 0;
			s1_sum = 0;
			count++;
			increment(0);

		}

	} // generate

	/**
	 * Saves the current configuration of subsets and their elements for later
	 * use. Called upon when subsets of equal weights are discovered.
	 */
	public static void saveConfig() {
		matchSum = s0_sum;
		s0_saved_sum = 0;
		s1_saved_sum = 0;

		s0_config = new LinkedList<Integer>();
		s1_config = new LinkedList<Integer>();

		for (int i = 0; i < l; i++)
			if (bit[i] == 0) {
				s0_config.add(n[i]);
				s0_saved_sum += n[i];
			} else if (bit[i] == 1) {
				s1_config.add(n[i]);
				s1_saved_sum += n[i];
			}

	} // saveConfig

	/**
	 * Prints out the saved configuration if two subsets of equal weight are
	 * discovered. Otherwise, indicates that no such partition was found.
	 */
	public static void matchCheck() {

		Integer i = new Integer(0);

		if (confirmed)
			displayln("Equal partition discovered:");
		else
			displayln("No equal partitions found; best result: \n");

		display("Set 0: { ");
		while (s0_config.size() > 0) {
			i = s0_config.pop();
			display(i + " ");
		}
		display("} = " + s0_saved_sum + "\n");

		display("Set 1: { ");
		while (s1_config.size() > 0) {
			i = s1_config.pop();
			display(i + " ");
		}
		display("} = " + s1_saved_sum + "\n");

		if (!confirmed)
			displayln("Difference = " + diff + "\n");
		else
			displayln("");

	} // matchCheck

	/**
	 * Prints output to console and output file, sans new line. Method simulates
	 * the System.out.print function.
	 * 
	 * @param s
	 */
	public static void display(String s) {
		System.out.print(s);
		outStream.print(s);
	} // display

	/**
	 * Prints output to console and output file, with new line. Method simulates
	 * the System.out.println function.
	 * 
	 * @param s
	 */
	public static void displayln(String s) {
		System.out.println(s);
		outStream.println(s);
	} // displayln

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

	/**
	 * Properly closes the output file and saves all data relayed to it.
	 */
	public static void closeIO() {
		outStream.close();

	} // closeIO

} // Main
