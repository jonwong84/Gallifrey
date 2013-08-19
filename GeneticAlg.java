/**
 * GeneticAlg class by Jonathan Wong
 * 
 * Genetic algorithm designed to solve the Partition problem.
 * Key features include K-Tournament Selection, bubble-sorting
 * of members by fitness, genotype-level basic crossover and mutation,
 * and incrementing seed for randomization.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GeneticAlg {

  private static BufferedReader inStream;
	private static PrintWriter outStream;
	private static String line;

	private static int[] elements;
	private static Member[] population;
	private static Member[] nextGen;

	private static int generations; // param1
	private static int popSize; // param2
	private static int crossRate; // param3
	private static int mutateRate; // param4
	// private static int eliteRate; // param5
	private static double fitnessThresh; // param6

	private static int seed;
	private static Random r;

	/**
	 * Constructor establishes all used parameters.
	 * 
	 * @param i
	 * @param o
	 * @param input
	 * @throws IOException
	 */
	GeneticAlg(BufferedReader i, PrintWriter o, String input)
			throws IOException {
		inStream = i;
		outStream = o;
		line = input;

		generations = 100;
		popSize = 10;
		crossRate = 5;
		mutateRate = 3;
		// eliteRate = 25;
		fitnessThresh = 0.9970;

		seed = 3;

	} // constructor

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
			elements = new int[size];
			population = new Member[popSize];
			parseData(prebatch);
			printElements();
			populate();
			fitnessCheck(population);
			bubbleSort(population); // fitnessCheck must always occur before
									// bubbleSort
			printIntro();
			displayln("Generation 0:");
			printData(population);
			displayln("");
			eval();
			printConfig(population[0]);
		}
		closeIO();
	} // run

	/**
	 * Evaluation function; processes K-tournament selection, sorting,
	 * crossover, mutation, and printing.
	 */
	private void eval() {
		int counter = 1;
		double thresh = 0;
		while (counter <= generations && thresh < fitnessThresh) {
			displayln("Generation " + counter + ":");
			nextGen = new Member[popSize];
			for (int i = 0; i < nextGen.length; i++) {
				Member parent1 = tournament();
				Member parent2 = tournament();
				nextGen[i] = crossover(parent1, parent2);
				mutate(nextGen[i]);
				parent1.selected = false;
				parent2.selected = false;
			}
			population = nextGen;
			fitnessCheck(population);
			bubbleSort(population);
			thresh = population[0].fitness;

			printData(population);
			displayln("");
			counter++;
			seed++;
		}
	} // eval

	/**
	 * Iterates through a member's bitString and applies mutation via successful
	 * dice rolls.
	 * 
	 * @param m
	 */
	private void mutate(Member m) {
		int diceRoll;
		for (int i = 0; i < m.bitString.length; i++) {
			diceRoll = r.nextInt(seed + 999) % 100;
			if (diceRoll < 0)
				diceRoll *= -1;
			if (diceRoll < mutateRate) {
				if (m.bitString[i] == 0)
					m.bitString[i] = 1;
				else
					m.bitString[i] = 0;
			}
		}
		seed++;
	} // mutate

	/**
	 * Applies bit-by-bit crossover in creating and return a new child.
	 * 
	 * @param p1
	 * @param p2
	 * @return child
	 */
	private Member crossover(Member p1, Member p2) {
		int diceRoll;
		Member child = new Member();
		int[] newCode = new int[elements.length];
		for (int i = 0; i < newCode.length; i++) {
			diceRoll = r.nextInt(seed + 999) % 100;
			if (diceRoll < 0)
				diceRoll *= -1;
			if (diceRoll < crossRate)
				newCode[i] = p2.bitString[i];
			else
				newCode[i] = p1.bitString[i];
		}
		child.bitString = newCode;
		seed++;
		return child;
	} // crossover

	/**
	 * Implementation of K-Tournament Selection. Two players selected randomly
	 * have their fitness scores compared against other
	 * 
	 * @return p1 or p2
	 */
	private Member tournament() {

		seed++;
		Member p1 = select();
		Member p2 = select();

		if (p1.fitness > p2.fitness) {
			p2.selected = false;
			return p1;
		} else if (p1.fitness < p2.fitness) {
			p1.selected = false;
			return p2;
		} else {
			int coinToss = r.nextInt(seed + 9) % 2;
			if (coinToss < 0)
				coinToss *= -1;
			if (coinToss == 0) {
				p2.selected = false;
				return p1;
			} else {
				p1.selected = false;
				return p2;
			}
		}
	} // tournament

	/**
	 * Randomly selects a member from the population and returns it.
	 * 
	 * @return m
	 */
	private Member select() {
		boolean decided = false;
		int i;
		Member m = null;
		while (!decided) {
			i = (r.nextInt(seed + 999)) % popSize;
			if (i < 0)
				i *= -1;
			if (population[i].selected) {
				continue;
			} else {
				decided = true;
				population[i].selected = true;
				m = population[i];
			}
		}
		seed++;
		return m;
	} // end tournament

	/**
	 * Calculates the fitness of the member.
	 * 
	 * @param elements
	 */
	private void fitnessCheck(Member[] set) {
		for (int i = 0; i < set.length; i++) {
			int left = 0;
			int right = 0;
			int sum = 0;
			for (int j = 0; j < elements.length; j++) {
				if (set[i].bitString[j] == 0)
					left += elements[j];
				else if (set[i].bitString[j] == 1)
					right += elements[j];
				sum += elements[j];
			}
			int diff = sum - diffCheck(left, right);
			set[i].fitness = Math
					.round(((double) diff / (double) sum) * 10000.0) / 10000.0;
		}
	} // fitnessCheck

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
	 * Sorts all members of population from highest fitness to lowest using
	 * bubble sort technique.
	 */
	private void bubbleSort(Member[] set) {

		while (true) {
			boolean swapped = false;
			for (int i = 1; i < set.length; i++) {
				if (set[i - 1].fitness < set[i].fitness) {
					Member temp = set[i - 1];
					set[i - 1] = set[i];
					set[i] = temp;
					swapped = true;
				}
			}
			if (!swapped)
				break;
		}

	} // bubbleSort

	/**
	 * Randomizes a bit string's values.
	 */
	private void randomize(int[] bitString) {
		r = new Random();
		int n;
		for (int i = 0; i < bitString.length; i++) {
			n = r.nextInt(seed);
			bitString[i] = n % 2;
		}
	} // randomize

	/**
	 * Elements from input are allocated to an array of integers.
	 * 
	 * @param prebatch
	 */
	private void parseData(String[] prebatch) {
		for (int i = 0; i < prebatch.length; i++)
			elements[i] = Integer.parseInt(prebatch[i]);
	} // parseData

	/**
	 * Populates the population pool with Generation 0 members. Bit strings are
	 * all generated randomly.
	 */
	private void populate() {

		for (int i = 0; i < popSize; i++) {
			int[] bitString = new int[elements.length];
			randomize(bitString);
			population[i] = new Member(bitString, elements);
		}

	} // populate

	private void printData(Member[] set) {
		for (int i = 0; i < set.length; i++) {
			for (int j = 0; j < set[i].bitString.length; j++)
				display(set[i].bitString[j] + " ");
			display("| Fitness = " + set[i].fitness + " ");
			displayln("");
		}
	} // printData

	/**
	 * Prints the parameters of the program.
	 */
	private void printIntro() {

		displayln("Proceeding with the following parameters:");
		displayln(" Max Generations = " + generations);
		displayln(" Population Size = " + popSize);
		displayln(" Crossover Rate = " + crossRate + "%");
		displayln(" Mutation Rate = " + mutateRate + "%");
		displayln(" Fitness Threshold = " + fitnessThresh + "%");
		// displayln(" Elitist Selection Rate = " + eliteRate + "% **DISABLED");
		displayln(" Initial Seed Value For Random = " + seed);
		displayln("");
	} // printIntro

	/**
	 * Prints the saved configuration.
	 */
	private void printConfig(Member m) {
		displayln("\n---");
		displayln("Results of genetic algorithm:");
		int s0_sum = 0;
		int s1_sum = 0;
		String left = " s0: { ";
		String right = " s1: { ";
		int[] bin = m.bitString;
		for (int i = 0; i < bin.length; i++) {
			if (bin[i] == 0) {
				s0_sum += elements[i];
				left = left.concat(elements[i] + " ");
			} else if (bin[i] == 1) {
				s1_sum += elements[i];
				right = right.concat(elements[i] + " ");
			}
		}
		left = left.concat("} = ");
		right = right.concat("} = ");
		displayln(left + s0_sum);
		displayln(right + s1_sum);
		int tempDiff = diffCheck(s0_sum, s1_sum);
		displayln(" Difference = " + tempDiff);
		displayln(" Fitness = " + m.fitness);
		display(" Bitstring : ");
		for (int j = 0; j < bin.length; j++)
			display(bin[j] + " ");
		displayln("");
	}

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
