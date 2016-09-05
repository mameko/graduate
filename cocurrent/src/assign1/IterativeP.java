package assign1;

public class IterativeP {										
																					
	/**
	 * This is a program for calculate the sum of an array. 
	 * 
	 * First divide the tasks to p processors. 
	 * Then calculate how many task each p should take.
	 * Then assign the tasks to each p
	 * Here each p execute one worker thread.
	 * After all the worker thread finished, sum the result up.(The result is in array C)
	 * 
	 * In fact, the last step(sum up) is run in sequential.
	 * 
	 * @param args
	 */
	static int N = 18;// the length of the array		
	static int P = 16;// processor number											
	static int C[] = new int[P];// array use for storing the result coming back from the workers
	static int F[] = new int[P];// array use for recording whether to add the result of that slot of C. if C dosn't contain the final result, then should not add it now.
	static int A[] = new int[N];// array use for storing the values to be calculate.
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Initialise array a
		for (int i = 0; i < N; i++) {//initialize the array with some
			A[i] = i;
		}
		
		//create worker threads
		for (int w = 0; w < P; w++) {
			Worker worker = new Worker();
			int l = N/P;//equally divided the task
			if (N%P != 0) {// however, sometimes the task is not a multiple of processor
				l = l+1;// so round it. ==> this will cause some processor idle
			}
			worker.setW(w);// pass array A and other things to the worker threads
			worker.setL(l);
			worker.setA(A);
			worker.setFinish(F);
			worker.setC(C);
			worker.start();// start the thread
		}
		
		int sum = 0;
		int i = 0;													
		while ( i < F.length) {										
			if (F[i]==1) {			//see weather the thread is finished. 1==>is finish; 0==> not finished									
				sum = sum + C[i];	//add up the result from thread								
				i = i+1;											
			} else {
				try {
					System.out.println("main thread sleep");		
					Thread.sleep(100);// put the thread to sleep so that other thread can execute.
					System.out.println("main thread wake up");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		System.out.println(sum);		
		}
															// {s = a[0] + a[1] + ... + a[n-1]}
}
