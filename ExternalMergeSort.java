import java.io.*;
import java.util.Scanner;

public class ExternalMergeSort {

	public static void main(String[] args) {
		
		int counter = 0;
		
		// Step 0
		int counter = 0;
		Scanner keyboard = new Scanner(System.in);
		Scanner readFirstFile, readSecondFile;
		String firstFileName, secondFileName, thirdFileName;
		int itemOne, itemTwo;
		File firstFile, secondFile;
		PrintWriter outStream;

		// Step 1
		System.out.print("Please provide first filename: ");
		firstFileName = keyboard.next();

		// Step 2
		try {
			firstFile = new File(firstFileName);
			readFirstFile = new Scanner(firstFile);

			itemOne = readFirstFile.nextInt();
			counter++;
			while (readFirstFile.hasNextInt()) {
				itemTwo = readFirstFile.nextInt();
				counter++;
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
			System.out.print("Please provide second filename: ");
			secondFileName = keyboard.next();

			// Step 4
			secondFile = new File(secondFileName);
			readSecondFile = new Scanner(secondFile);

			itemTwo = readSecondFile.nextInt();
			counter++;
			while (readSecondFile.hasNextInt()) {
				itemTwo = readSecondFile.nextInt();
				counter++;
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
			boolean loop = true;
			while (true) {
								
				// Step 7
				if (itemOne <= itemTwo) {
					counter++;
					outStream.print(itemOne + " ");
					if (!loop) {
						counter++;
						outStream.print(itemTwo + " ");
						break;
					}
					itemOne = readFirstFile.nextInt();
					if (!readFirstFile.hasNextInt())
						loop = false;
				} else {
					counter++;
					outStream.print(itemTwo + " ");
					if (!loop) {
						counter++;
						outStream.print(itemOne + " ");
						break;
					}
					itemTwo = readSecondFile.nextInt();
					if (!readSecondFile.hasNextInt())
						loop = false;
				}
				// Step 8
				// return to top of loop
			} // whileLoop

			// Step 9
			while (readFirstFile.hasNextInt()) {
				itemOne = readFirstFile.nextInt();
				counter++;
				outStream.print(itemOne + " ");
			}
			while (readSecondFile.hasNextInt()) {
				itemTwo = readFirstFile.nextInt();
				counter++;
				outStream.print(itemTwo + " ");
			}

			// Step 10
			readFirstFile.close();
			readSecondFile.close();
			outStream.close();
			keyboard.close();
			System.out.println("Counter = " + counter);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Counter = " + counter);
	} // main

} // ExternalMergeSort
