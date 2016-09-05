package assign3;

import java.util.Random;
/**
 * This is an example of using semaphore to synchronise producer and consumer
 * @author Shuwen Pan
 *
 */
public class SynchronizationExample {
	int n = 0;// products value
	int NUMBER = 30;// number of rounds
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SynchronizationExample ps = new SynchronizationExample();		
		long seed = System.currentTimeMillis();		
		ps.begin(seed);	
	}
	
	public void begin(long seed){
		Random p = new Random(seed);// generate random schedule sequence
		Semophore consumed = new Semophore(0);// initialize the semaphore
		Semophore produced = new Semophore(1);

		while(consumerI<NUMBER){		
//			System.out.println(cyc+++" cycle:");
			System.out.println();
			int ran= p.nextInt(2);
			if(ran == 0){// pid = 0, producer run
				System.out.println("producer' turn...");
				producer(consumed,produced);
			}else{// pid = 1, consumer run
				System.out.println("consumer's turn...");
				consumer(consumed,produced);
			}
		}
	}
	
	public void producer(Semophore consumed, Semophore produced){
		if (!consumed.needToWait(0) ||pidrelease==0) {// resume the process if the process get the semaphore
			
			while (producerI< NUMBER) {				
				consumed.wait(0);// type of producer is 0
				System.out.println("producer required for semaphore");
				if(!consumed.needToWait(0)||pidrelease ==0){
					// go pass the wait statement or resume of the previous process
					n++;
					System.out.println("--------------------------------------producer produce(n)");
					pidrelease= produced.signal();// the process being release
					producerI++;				
				}else{// need to wait || not being resumed		
					System.out.println("blocking producer");
					break;
				}	
			}
		}else {
			System.out.println("blocking producer");
		}		
	}
	
	public void consumer(Semophore consumed, Semophore produced){
		if (!produced.needToWait(1) ||pidrelease == 1) {// resume the process if the process get the semaphore

			while (consumerI < NUMBER) {
				produced.wait(1);// pid of consumer is 1
				System.out.println("consumer required for semaphore");
				if (!produced.needToWait(1) || pidrelease == 1) {
					// go pass the wait statement or resume of the previous process
					System.out.println("n = " + n);
					pidrelease = consumed.signal();// the process being release
					consumerI++;
				} else {// need to wait || not being resumed	
					System.out.println("blocking consumer");
					break;
				}
			}
		}else {
			System.out.println("blocking consumer");
		}
	}
	
	// variables for recording process state, when the process is sleeping
	int producerI = 0;
	int consumerI = 0;
	// pid that is being release. It is set to 100, because here there are only 2 process,
	// one is 0---the producer, the other one is 1---the consumer
	int pidrelease = 100;
}
