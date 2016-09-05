package assign3;


/*****************************************************************
 * Memorial University of Newfoundland
 * 7894 Concurrent Programming
 * Final Exam Q3 Sample Solution
 *
 * Workplace.java -- simulate the workplace with shared bathroom.
 *
 * @author Dennis Peters ($Author: dpeters $)
 * @version $Revision: 870 $ $Date: 2009-08-07 09:29:53 -0230 (Fri, 07 Aug 2009) $
 * 
 * $Id: Workplace.java 870 2009-08-07 11:59:53Z dpeters $
 *
 */
public class Workplace {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int numStaff = 20;
		Bathroom loo = new Bathroom(5);
		Thread[] staff = new Thread[numStaff];
		for (int i = 0; i < numStaff; i++) {
			staff[i] = new Thread(new Person(loo, (i%2==0) ? Sex.Male : Sex.Female, i));
			staff[i].start();
		}
	}

}
