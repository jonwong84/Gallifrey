/**
 * Member class.
 * 
 * Represents a member of the population. Includes a bit string, fitness
 * measure.
 * 
 * @author Jonathan Wong
 * 
 */

public class Member {

  public int[] bitString;
	public double fitness;
	public boolean selected;

	Member() {
		bitString = null;
		fitness = 0.0;
		selected = false;
	}

	Member(int[] bin, int[] elements) {
		bitString = bin;
		selected = false;
	} // constructor

} // end Member class
