import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PolyCheck {

	public static void main(String[] args) throws FileNotFoundException {

		if (args.length == 0) {
			System.out.println("Run as:\n java PolyCheck file.txt");
			return;
		}

		int size = sizeCheck(args[0]);
		Point[] p = new Point[size];

		File f = new File(args[0]);
		Scanner s = new Scanner(f);
		int x, y;
		for (int i = 0; i < p.length; i++) {
			x = s.nextInt();
			y = s.nextInt();
			p[i] = new Point(x, y);
		}

		int ok = check(p);
		if (ok == 200)
			System.out.println("OK " + ok + ": Polygon is valid.");
		else if (ok == 400)
			System.out.println("ERR" + ok + ": Polygon features two or fewer data points.");
		else if (ok == 401)
			System.out.println("ERR" + ok + ": Duplicate points detected.");

	} // main

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
	}

	public static int check(Point[] p) {

		int code = 200;
		if (p.length <= 2)
			return 400;

		for (int i = 1; i < p.length; i++) {
			for (int j = 0; j < i; j++)
				if (p[i].x == p[j].x && p[i].y == p[j].y) {
					System.out.println(" Problem: (" + p[i].x + "," + p[i].y + ")");
					code = 401;
				}
		}
		return code;

	} // check

} // PolyCheck
