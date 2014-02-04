import java.io.*;
import java.util.Scanner;

public class SelectionSort {

    public static void main(String[] args) {

        // Step 0
        int dataCount = 0;
        Scanner keyboard = new Scanner(System.in);
        Scanner fromFile;
        String filename, outGoing;
        File input;
        int[] dataSorted;
        int startIndex, endIndex, walkerIndex, minIndex, temp;
        PrintWriter outStream;

        // Step 1
        System.out.print("Please provide a filename: ");
        filename = keyboard.next();

        try {
            // Step 2
            input = new File(filename);
            fromFile = new Scanner(input);
            int takeOut;
            while (fromFile.hasNextInt()) {
                dataCount++;
                takeOut = fromFile.nextInt();
            }

            // Step 3
            fromFile.close();

            // Step 4
            dataSorted = new int[dataCount];

            // Step 5
            // Variable "File input" does not need to change.

            // Step 6
            fromFile = new Scanner(input);
            for (int i = 0; i < dataSorted.length; i++) {
                int store = fromFile.nextInt();
                dataSorted[i] = store;
            }

            // Step 7
            startIndex = 0;
            endIndex = dataCount - 1;

            // Step 8
            while (!(startIndex >= endIndex)) {
                minIndex = startIndex;
                walkerIndex = startIndex + 1;

                // Step 9
                while (!(walkerIndex > endIndex)) {
                    if (dataSorted[walkerIndex] < dataSorted[minIndex])
                        minIndex = walkerIndex;

                    // Step 10
                    walkerIndex++;

                    // Step 11
                    // return to whileLoop
                }

                // Step 12
                temp = dataSorted[startIndex];
                dataSorted[startIndex] = dataSorted[minIndex];
                dataSorted[minIndex] = temp;

                // Step 13
                startIndex++;

                // Step 14
                // return to whileLoop
            }

            // Step 15
            System.out.print("Please provide an output file name: ");
            outGoing = keyboard.next();
            outStream = new PrintWriter(new FileOutputStream(outGoing));

            // Step 16
            for (int j = 0; j < dataSorted.length; j++) {
                outStream.print(dataSorted[j] + " ");
            }
            outStream.close();
            keyboard.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    } // main

} // SelectionSort