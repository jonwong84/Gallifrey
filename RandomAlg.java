/**
 * RandomAlg class by Jonathan Wong
 * 
 * Solves for Karp's Partition problem using randomized combinations
 * of elements allocated to two subsets.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class RandomAlg {

  private static BufferedReader inStream;
	private static PrintWriter outStream;
	private static String line;
	private static int generations;

	private static int[] elements;
	private static int[] binary;
	private static int seed;

	private static String s0_config;
	private static String s1_config;
	private static int s0_saved_sum;
	private static int s1_saved_sum;
	private static int savedDiff;

	/**
	 * Constructor with parameters.
	 * 
	 * @param i
	 * @param o
	 * @param input
	 * @param n
	 * @throws IOException
	 */
	RandomAlg(BufferedReader i, PrintWriter o, String input, int n)
			throws IOException {
		inStream = i;
		outStream = o;
		line = input;
		generations = n;
		seed = 3;
	}

	/**
	 * The main driver program for this class.
	 * 
	 * @throws IOException
	 */
	public void run() throws IOException {

		while ((line = inStream.readLine()) != null) {

			if (line.equals(""))
				continue;

			String prebatch[] = line.split(" ");

			int size = prebatch.length;
			binary = new int[size];
			elements = new int[size];
			parseData(prebatch);

			printElements();
			displayln("");
			for (int i = 0; i < generations; i++) {
				displayln("Iteration " + i + ": ");
				randomize();
				eval();
			}
			printConfig();
			displayln("---\n");
		}
		closeIO();

	} // run

	/**
	 * Elements from input are allocated to an array of integers. Also prepares
	 * the saved configuration states.
	 * 
	 * @param prebatch
	 */
	private void parseData(String[] prebatch) {
		int n = 0;
		for (int i = 0; i < prebatch.length; i++) {
			elements[i] = Integer.parseInt(prebatch[i]);
			n += elements[i];
		}
		savedDiff = n;
		s0_config = "One-sided.";
		s1_config = "One-sided.";
	} // parseData

	/**
	 * Randomizes the binary counted to designate elements to their given
	 * subsets.
	 */
	private void randomize() {
		Random r = new Random();
		int n = r.nextInt(seed);
		for (int i = 0; i < binary.length; i++) {
			n = r.nextInt();
			if (n < 0) n*= -1;
			binary[i] = n % 2;
		}
		seed++;
	} // randomize

	/**
	 * Evaluates the given configuration and continually saves the best
	 * configurations.
	 */
	private void eval() {
		int s0_sum = 0;
		int s1_sum = 0;
		String left = " s0: { ";
		String right = " s1: { ";
		for (int i = 0; i < binary.length; i++) {
			if (binary[i] == 0) {
				s0_sum += elements[i];
				left = left.concat(elements[i] + " ");
			} else if (binary[i] == 1) {
				s1_sum += elements[i];
				right = right.concat(elements[i] + " ");
			}
		}
		left = left.concat("} = ");
		right = right.concat("} = ");
		displayln(left + s0_sum);
		displayln(right + s1_sum);
		int tempDiff = diffCheck(s0_sum, s1_sum);
		if (tempDiff < savedDiff)
			saveConfig(left, right, tempDiff, s0_sum, s1_sum);
	} // eval

	/**
	 * Saves the current subset configurations, their sums, and their difference
	 * in weights.
	 * 
	 * @param left
	 * @param right
	 * @param tempDiff
	 */
	private void saveConfig(String left, String right, int tempDiff,
			int s0_sum, int s1_sum) {
		s0_config = left;
		s1_config = right;
		s0_saved_sum = s0_sum;
		s1_saved_sum = s1_sum;
		savedDiff = tempDiff;
	}

	/**
	 * Prints the saved configuration.
	 */
	private void printConfig() {
		displayln("Best possible configuration found:");
		displayln(s0_config + " " + s0_saved_sum);
		displayln(s1_config + " " + s1_saved_sum);
		displayln(" Difference = " + savedDiff);
	}

	/**
	 * Calculates and returns the difference between the sums of the two
	 * subsets.
	 * 
	 * @return num
	 */
	private int diffCheck(int s0_sum, int s1_sum) {
		int num = Math.abs(s1_sum - s0_sum);
		return num;
	} // diffCheck

	/**
	 * A simple print function for outputting elements.
	 */
	private void printElements() {
		display("Elements: { ");
		for (int i = 0; i < elements.length; i++)
			display(elements[i] + " ");
		displayln("}");
	} // printElements

	/**
	 * Prints output to console and output file, sans new line. Method simulates
	 * the System.out.print function.
	 * 
	 * @param s
	 */
	private static void display(String s) {
		System.out.print(s);
		outStream.print(s);
	} // display

	/**
	 * Prints output to console and output file, with new line. Method simulates
	 * the System.out.println function.
	 * 
	 * @param s
	 */
	private static void displayln(String s) {
		System.out.println(s);
		outStream.println(s);
	} // displayln

	/**
	 * Shuts the I/O streams, saving the data to external files.
	 * 
	 * @throws IOException
	 */
	private static void closeIO() throws IOException {
		inStream.close();
		outStream.close();

	} // closeIO

}
