import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SortCheck {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int counter = 0;
		boolean sorted = true;
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Filename: ");
		File file = new File(keyboard.next());
		System.out.println("Elements found in file: ");
		try {
			Scanner in = new Scanner(file);

			int i = in.nextInt();
			System.out.println("S_" + counter + ": " + i);
			counter++;
			while (in.hasNextInt()) {
				int j = in.nextInt();
				System.out.println("S_" + counter + ": " + j);
				counter++;
				if (j < i) {
					System.out
							.println(" Sorting Error: value " + j + " < " + i);
					sorted = false;
				} else
					i = j;
			} // whileloop
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} // try-catch
		if (sorted)
			System.out.println("File " + args[0] + " is sorted.");
		else
			System.out.println("File " + args[0]
					+ " has not been properly sorted.");
	} // main
} // SortCheck
