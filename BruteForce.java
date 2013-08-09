/**
* BruteForce class by Jonathan Wong
*
* Brute-force algorithm for solving Karp's Partition problem.
* Iterates through all possible combinations of elements in
* their designated subsets, and saves the best possible
* configuration.
*
* BruteForce utilizes a binary counter that assigns elements
* to one subset or the other.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class BruteForce {

  private static int[] elements;
private static int[] binary;
private static boolean overflow;  

private static String s0_config;
private static String s1_config;
private static int s0_saved_sum;
private static int s1_saved_sum;
private static int savedDiff;

private static BufferedReader inStream;
private static PrintWriter outStream;
private static String line;

//BruteForce() {}

/**
* Constructor with parameters.
* @param i
* @param o
* @param input
* @throws IOException
*/
BruteForce(BufferedReader i, PrintWriter o, String input) throws IOException {
inStream = i;
outStream = o;
line = input;
}

/**
* The main driver program for this class.
* @throws IOException
*/
public void run() throws IOException {

while ((line = inStream.readLine()) != null) {

if (line.equals(""))
continue;

String prebatch[] = line.split(" ");

int size = prebatch.length;
binary = new int[size + 1];
elements = new int[size];
parseData(prebatch);

printElements();
displayln("");
eval();
printConfig();
displayln("---\n");

}
closeIO();

} // run

/**
* Elements from input are allocated to an array of integers.
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
overflow = false;
} // parseData

/**
* Recursively increments the binary counter. Also implements overflow
* detection.
*
* @param i
*/
private void increment(int i) {

if (overflow) {
displayln("OVERFLOW");
return;
}

if (i == binary.length)
return;

if (binary[i] == 0) {
binary[i]++;
if (i == binary.length - 1)
overflow = true;
return;
}
else if (binary[i] == 1) {
binary[i] = 0;
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
private int diffCheck(int s0_sum, int s1_sum) {
int num = Math.abs(s1_sum - s0_sum);
return num;
} // diffCheck

/**
* Iterates through every possible way the set can be partitioned. An
* array-implemented binary counter assigns each element to a specific
* subset; it is incremented after each iteration. If an equally-divided
* partition is found, the result is saved and printed out later.
*/
private void eval() {

int count = 0;
String left, right;
int s0_sum, s1_sum;

while (!overflow) {

s0_sum = 0;
s1_sum = 0;
left = " s0: { ";
right = " s1: { ";

for (int i = 0; i < elements.length; i++)
if (binary[i] == 0) {
s0_sum += elements[i];
left = left.concat(elements[i] + " ");
} else if (binary[i] == 1) {
s1_sum += elements[i];
right = right.concat(elements[i] + " ");
}

left = left.concat("} = ");
right = right.concat("} = ");
displayln("Iteration " + count + ":");
displayln(left + s0_sum);
displayln(right + s1_sum);
int tempDiff = diffCheck(s0_sum, s1_sum);

if (tempDiff < savedDiff)
saveConfig(left,right,tempDiff, s0_sum, s1_sum);

count++;
increment(0);

}

} // generate

/**
* Saves the current subset configurations, their sums, and their difference in weights.
* @param left
* @param right
* @param tempDiff
*/
private void saveConfig(String left, String right, int tempDiff, int s0_sum, int s1_sum) {
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
if (savedDiff == 0) displayln("Solution found:");
else displayln("Exact solution not found; best possible configuration:");

displayln(s0_config + " " + s0_saved_sum);
displayln(s1_config + " " + s1_saved_sum);
displayln(" Difference = " + savedDiff);
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
* @throws IOException
*/
public static void closeIO() throws IOException {
inStream.close();
outStream.close();

} // closeIO


}
