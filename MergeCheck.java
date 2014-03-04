import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;


public class MergeCheck {

	static int listCount = 0, mergeCount = 0, errors = 0;
	
	public static void main (String[] args) throws FileNotFoundException {
		
		if (args.length < 2) {
			System.out.println("Run as follows:");
			System.out.println(" java MergeCheck <concatList> <mergedList>");
			return;
		}
		
		File file1 = new File(args[0]);
		File file2 = new File(args[1]);

		Hashtable<Integer,Integer> hash = hashParse(file2);
		boolean consistent = compareMerged(file1,hash);

		if (consistent) System.out.println("All elements in " + args[0] + " accounted for in " + args[1]);
		else {
			System.out.println("Error: inconsistency between sorted lists and merged list.");
			return;
		}
		
		
		System.out.println("Total number of elements in sorted lists: " + listCount);
		System.out.println("Total number of elements in merged list: " + mergeCount);
		System.out.println("Total number of errors detected: " + errors);
	} // main
	
	public static boolean compareMerged(File file, Hashtable<Integer,Integer> hash) throws FileNotFoundException {
	
		boolean b = true;
		Scanner s = new Scanner(file);
		int i;
		while (s.hasNextInt()) {
			i = s.nextInt();
			listCount++;
			if (!hash.containsKey(i)) {
				b = false;
				System.out.println(" Element " + i + " from sorted lists not found in merged list.");
				errors++;
			}
			else if (hash.get(i) <= 0) {
				b = false;
				System.out.println(" Copy of Element " + i + " missing in merged list.");
				errors++;
			}
			else {
				int t = hash.get(i);
				t--;
				hash.put(i, t);
			}
		}
		s.close();
		return b;	
	} // compareMerged
	
	public static Hashtable<Integer, Integer> hashParse(File file) throws FileNotFoundException {
		
		Hashtable<Integer,Integer> h = new Hashtable<Integer, Integer>();
		Scanner reader = new Scanner(file);
		int i;
		System.out.println("Files found in merged list (ML):");
		while (reader.hasNextInt()) {
			i = reader.nextInt();
			System.out.println("ML_" + mergeCount + ": " + i);
			mergeCount++;
			if (!h.containsKey(i)) h.put(i, 1);
			else h.put(i, h.get(i) + 1);
		}
		reader.close();
		return h;
		
	} // hashParse
	
} // MergeCheck
