import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class HullCompare {

	
	public static void main(String[] args) throws FileNotFoundException {

		if (args.length < 2) {
			System.out
					.println("Run as:\n java HullCheck goodHull.txt badHull.txt");
			return;
		}

		int goodSize = sizeCheck(args[0]);
		int badSize = sizeCheck(args[1]);
		Point[] good = parseList(args[0],goodSize);
		Point[] bad = parseList(args[1],badSize);
		
		boolean equal = compareLists(good, bad);
		if (equal)
			System.out.println("OK: Lists are equal.");
		else
			System.out.println("ERR: Lists are not equal.");
		
		System.out.println("User Options:");
		System.out.println("0. Exit");
		System.out.println("1. Add Coord");
		System.out.println("2. Sub Coord");
		Scanner s = new Scanner(System.in);
		int input = s.nextInt();
		s.close();
		if (input == 1) bad = randAdd(bad);
		else if (input == 2) bad = randSub(bad);
		else {
			System.out.println("Exiting.");
			return;
		}
		
		printList(good);
		printList(bad);
		
		equal = compareLists(good, bad);
		if (equal)
			System.out.println("OK: Lists are equal.");
		else
			System.out.println("ERR: Lists are not equal.");

	} // main
	
	public static void printList(Point[] p) {
		System.out.println("Runthrough:");
		for (int i = 0; i < p.length; i++)
			System.out.println(p[i]);
	} // printList
	
	public static Point[] parseList(String name, int size) throws FileNotFoundException {
		File f = new File(name);
		Scanner s = new Scanner(f);
		Point[] p = new Point[size];
		int x, y;
		for (int i = 0; i < size; i++) {
			x = s.nextInt();
			y = s.nextInt();
			p[i] = new Point(x, y);
		}
		return p;
	} // parseList
	
	public static Point[] randSub(Point[] p) {
		Point[] p_prime = new Point[p.length-1];
		Random r = new Random();
		int mark = Math.abs(r.nextInt()%p.length);
		System.out.println("Removed Point: " + p[mark]);
		int index = 0;
		for (int i = 0; i < p.length; i++) {
			if (i == mark) continue;
			p_prime[index] = p[i];
			index++;
		}
		return p_prime;
	} // randAdd
	
	public static Point[] randAdd(Point[] p) {
		Point[] p_prime = new Point[p.length+1];
		for (int i = 0; i < p.length; i++)
			p_prime[i] = p[i];
		Random r = new Random();
		Point add = new Point(Math.abs(r.nextInt()%100),Math.abs(r.nextInt()%100));
		System.out.println("New Point: " + add);
		p_prime[p.length] = add; 
		return p_prime;
	} // randAdd

	public static boolean compareLists(Point[] good, Point[] bad) {

		if (good.length != bad.length) {
			System.out.println("Lengths of both lists are inconsistent.");
			return false;
		}

		boolean b = true;
		for (int i = 0; i < good.length; i++)
			if (good[i].x != bad[i].x && good[i].y != bad[i].y) {
				b = false;
				System.out.println(" " + good[i] + " does not match" + bad[i]
						+ ".");
			}
		return b;
	} // compareLists

	public static int sizeCheck(String name) throws FileNotFoundException {
		int i = 0;

		System.out.println("Initial readthrough:");
		File f = new File(name);
		Scanner s = new Scanner(f);
		int x, y;
		while (s.hasNextInt()) {
			x = s.nextInt();
			y = s.nextInt();
			System.out.println("(" + x + "," + y + ")");
			i++;
		}
		s.close();
		return i;
	} // sizeCheck

} // HullCompare
