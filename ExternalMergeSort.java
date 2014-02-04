import java.io.*;
import java.util.Scanner;

public class ExternalMergeSort {

	public static void main(String[] args) {
		// Step 0
		Scanner keyboard = new Scanner(System.in);
		Scanner readFirstFile, readSecondFile;
		String firstFileName, secondFileName, thirdFileName;
		int itemOne, itemTwo;
		File firstFile, secondFile;
		PrintWriter outStream;

		// Step 1
		System.out.print("Please provide a filename: ");
		firstFileName = keyboard.next();

		// Step 2
		try {
			firstFile = new File(firstFileName);
			readFirstFile = new Scanner(firstFile);

			itemOne = readFirstFile.nextInt();
			while (readFirstFile.hasNextInt()) {
				itemTwo = readFirstFile.nextInt();
				if (itemTwo < itemOne) {
					System.out.println("File " + firstFileName
							+ " is not sorted.");
					return;
				} else
					itemOne = itemTwo;
			}
			readFirstFile.close();
			readFirstFile = new Scanner(firstFile);
			itemOne = 0;
			itemTwo = 0;

			// Step 3
			System.out.print("Please provide a filename: ");
			secondFileName = keyboard.next();

			// Step 4
			secondFile = new File(secondFileName);
			readSecondFile = new Scanner(secondFile);

			itemTwo = readSecondFile.nextInt();
			while (readSecondFile.hasNextInt()) {
				itemTwo = readSecondFile.nextInt();
				if (itemTwo < itemOne) {
					System.out.println("File " + secondFileName
							+ " is not sorted.");
					return;
				} else
					itemOne = itemTwo;
			}
			readSecondFile.close();
			readSecondFile = new Scanner(secondFile);
			itemOne = 0;
			itemTwo = 0;

			// Step 5
			System.out.print("Please provide an output file name: ");
			thirdFileName = keyboard.next();
			outStream = new PrintWriter(new FileOutputStream(thirdFileName));

			// Step 6
			itemOne = readFirstFile.nextInt();
			itemTwo = readSecondFile.nextInt();

			// Step 8 (looping point)
			while (readFirstFile.hasNextInt() && readSecondFile.hasNextInt()) {
				// Step 7
				if (itemOne <= itemTwo) {
					outStream.print(itemOne + " ");
					itemOne = readFirstFile.nextInt();
				} else {
					outStream.print(itemTwo + " ");
					itemTwo = readSecondFile.nextInt();
				}

				// Step 8
				// return to top of loop
			} // whileLoop

			// Step 9
			while (readFirstFile.hasNextInt()) {
				itemOne = readFirstFile.nextInt();
				outStream.print(itemOne + " ");
			}
			while (readSecondFile.hasNextInt()) {
				itemTwo = readFirstFile.nextInt();
				outStream.print(itemTwo + " ");
			}

			// Step 10
			readFirstFile.close();
			readSecondFile.close();
			outStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	} // main

} // ExternalMergeSort