import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;


public class MergeCheck {

	static int listCount = 0, mergeCount = 0;
	static final String tempFile = "~merge.tmp";
	
	public static void main (String[] args) throws FileNotFoundException {
		
		if (args.length < 3) {
			System.out.println("Run as follows:");
			System.out.println(" java MergeCheck <list1> <list2> <mergedList>");
			return;
		}
		
		File file1 = new File(args[0]);
		File file2 = new File(args[1]);
		File file3 = new File(args[2]);

		concat(file1,file2);
		Hashtable<Integer,Integer> hash = hashParse(file3);
		File file4 = new File(tempFile);
		boolean consistent = compareMerged(file4,hash);
		file4.delete();

		if (consistent) System.out.println("All elements in " + args[0] + " and " + args[1] + " accounted for in " + args[2]);
		else
			System.out.println("Error: inconsistency between sorted lists and merged list.");
		
		System.out.println("Total number of elements in sorted lists: " + listCount);
		System.out.println("Total number of elements in merged list: " + mergeCount);
	} // main
	
	public static boolean compareMerged(File file, Hashtable<Integer,Integer> hash) throws FileNotFoundException {
	
		boolean b = true;
		Scanner s = new Scanner(file);
		int i;
		while (s.hasNextInt()) {
			i = s.nextInt();
			if (!hash.containsKey(i)) {
				b = false;
				System.out.println(" Element " + i + " from sorted lists not found in merged list.");
			}
			else if (hash.get(i) <= 0) {
				b = false;
				System.out.println(" Copy of Element " + i + " missing in merged list.");
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
	
	public static void concat(File file1, File file2) throws FileNotFoundException {
		
		Scanner read1 = new Scanner(file1);
		Scanner read2 = new Scanner(file2);
		PrintWriter out = new PrintWriter(tempFile);
		System.out.println("Elements found in concatted sorted lists (CAT):");
		
		int get;
		while (read1.hasNextInt()) {
			get = read1.nextInt();
			System.out.println("CAT_" + listCount + ": " + get);
			out.print(get + " ");
			listCount++;
		}
		while (read2.hasNextInt()) {
			get = read2.nextInt();
			System.out.println("CAT_" + listCount + ": " + get);
			out.print(get + " ");
			listCount++;
		}
		out.close();
		read1.close();
		read2.close();
	} // concat
	
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
