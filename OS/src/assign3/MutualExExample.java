package assign3;

import java.util.Random;

/**
 * This is the example of mutual exclusion using semaphore
 * This example is the mutual exclusion version of ProcessSim
 * @author Shuwen Pan
 *
 */
public class MutualExExample {
	int n = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MutualExExample ps = new MutualExExample();		
		long seed = System.currentTimeMillis();
		ps.begin(seed);	
	}
	
	public void begin(long seed){
		Random p = new Random(seed);// generate random execution sequence
		Semophore mut = new Semophore(1);//mutual exclusion semaphore		
		int acc1 = 0;
		int acc2 = 0;
		int cyc = 0;// current cycle
		while(cyc<300){// let it stop by itself. this cycle has nothing to do with n's value
			int ran= p.nextInt(2);
			cyc++;
			System.out.println();
			if(ran == 0){// p1 run
				System.out.println("p1's turn...");
				acc1 = runProgram("p1",pc1,acc1,mut,1);
			}else{// p2 run
				System.out.println("p2's turn...");
				acc2 = runProgram("p2",pc2,acc2,mut,2);
			}
		}
	}
	
	public int runProgram(String name, int pc,int acc,Semophore m,int pid){
	
			String pname = name;
			switch (pc) {
			case 0:// when executing the first statement of CS, require for semaphore
				if (!m.needToWait(pid) || releasepid == pid) {// first time access || resume the process
					m.wait(pid);
					if (!m.needToWait(pid) || releasepid == pid) {
						acc = n;
						System.out
								.println(pname + " executing : acc = n, n = " + n);
						pc++;
					} else {
						System.out.println(pid + " being block");
					}
				}else {
					System.out.println(pid + " being block");
				}
				break;
			case 1:
				acc++;
				System.out.println(pname + " executing : acc++, n = " + n);
				pc++;
				break;
			case 2:
				n = acc;
				System.out.println(pname + " executing : n = acc , n = "
						+ n);
				pc++;
				break;
			default:
				System.out.println("oops!");
				break;
			}
			
			if (pid == 1) {// increase the corresponding program counter.
				pc1 = pc;				
				if (pc1>=3) {// if the program finished, release the semaphore, let the other program get in CS
					releasepid = m.signal();	
					System.out.println("release "+ releasepid);
				}
				pc1 = pc1%3;
			} else {
				pc2 = pc;
				if (pc2>=3) {
					releasepid = m.signal();	
					System.out.println("release "+ releasepid);
				}	
				pc2 = pc2%3;
							
			}

		return acc;
	}
	int releasepid = 100;
	int pc1 = 0;
	int pc2 = 0;
}
