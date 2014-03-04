import java.io.*;
import java.util.Scanner;


public class Concat {

	static final String tempFile = "concat.txt";
	
	public static void main(String[] args) throws FileNotFoundException {
		
		if (args.length < 2) {
			System.out.println("Run as follows:");
			System.out.println(" java Concat <list1> <list2>");
			return;
		}
		
		File file1 = new File(args[0]);
		File file2 = new File(args[1]);
		concat(file1,file2);
		
	} // main
	
	public static void concat(File file1, File file2) throws FileNotFoundException {
		
		int total = 0;
		int list1 = 0;
		int list2 = 0;
		Scanner read1 = new Scanner(file1);
		Scanner read2 = new Scanner(file2);
		PrintWriter out = new PrintWriter(tempFile);
		System.out.println("Concatting the following elements to " + tempFile + ":");
		
		int get;
		while (read1.hasNextInt()) {
			get = read1.nextInt();
			System.out.println("LIST1_" + list1 + ": " + get);
			out.print(get + " ");
			list1++;
			total++;
		}
		while (read2.hasNextInt()) {
			get = read2.nextInt();
			System.out.println("LIST2_" + list2 + ": " + get);
			out.print(get + " ");
			list2++;
			total++;
		}
		out.close();
		read1.close();
		read2.close();
		System.out.println("Elements in List 1: " + list1);
		System.out.println("Elements in List 2: " + list2);
		System.out.println("Elements Total: " + total);
		System.out.println("Please see " + tempFile  + ".");
	} // concat

} // Concat