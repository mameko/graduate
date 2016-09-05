package assign3;

import java.util.Random;
/**
 * This class is use for demonstrating the unpredictability of results
 * @author Shuwen Pan
 *
 */
public class ProcessSim{

	int n = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProcessSim ps = new ProcessSim();		
		long seed = System.currentTimeMillis();
		ps.begin(seed);	
	}
	
	public void begin(long seed){
		Random p = new Random(seed);
		int pc1 = 0;
		int pc2 = 0;
		int acc1 = 0;
		int acc2 = 0;
		int cyc = 0;
		while(cyc<300){
			int ran= p.nextInt(2);
			System.out.println(cyc+++" cycle:");
			if(ran == 0){// p1 run
				acc1 = runProgram("p1",pc1,acc1);
				pc1++;
				pc1 = pc1%3;
				System.out.println("next pc1 :"+pc1);
			}else{// p2 run
				acc2 = runProgram("p2",pc2,acc2);
				pc2++;
				pc2 = pc2%3;
				System.out.println("next pc2 :"+pc2);
			}
		}
	}
	
	public int runProgram(String name, int pc,int acc){
		String pname = name;
		switch (pc) {
		case 0:
			acc = n;
			System.out.println(pname + " executing : acc = n, n = "+n +" ;acc = "+acc);
			break;
		case 1:
			acc++;
			System.out.println(pname + " executing : acc++, n = "+n +" ;acc = "+acc);
			break;
		case 2:
			n = acc;
			System.out.println(pname + " executing : n = acc , n = "+n +" ;acc = "+acc);
			break;
		default:
			System.out.println("oops!");
			break;
		}
		return acc;
	}

}
