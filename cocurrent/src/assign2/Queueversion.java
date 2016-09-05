package assign2;
import java.util.concurrent.*;

public class Queueversion {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(5,true);
		
		Producer[] p = new Producer[20];
		for (int i = 0; i < 20; i++) {
			p[i] = new Producer(q);
			new Thread(p[i]).start();
		}
		
		Consumer[] c = new Consumer[1];
		for (int i = 0; i < 1; i++) {
			c[i] = new Consumer(q);
			new Thread(c[i]).start();
		}
		
	}

}

class Producer implements Runnable {
	   private final BlockingQueue<Integer> queue;
	   int item;
	   Producer(BlockingQueue<Integer> q) { queue = q; }
	   public void run() {
	     try {
	       while (true) {	    	   
	    	   queue.put(produce());
	    	   Thread.sleep(29);
	    	   }
	     } catch (InterruptedException ex) {ex.printStackTrace();}
	   }
	   int produce() { 
		  item = (int)(Math.random()*1000);
		  System.out.println(this.hashCode()+" put "+item+" in the queue");
		  return item;
	   }
	 }

	 class Consumer implements Runnable {
	   private final BlockingQueue<Integer> queue;
	   Consumer(BlockingQueue<Integer> q) { queue = q; }
	   public void run() {
	     try {
	       while (true) {
	    	   consume(queue.take()); 
	    	   Thread.sleep(20);
	    	   }
	     } catch (InterruptedException ex) { ex.printStackTrace();}
	   }
	   void consume(Object x) { 
		   //System.out.println("there are " + queue.remainingCapacity()+ " places left");
		   System.out.println("------------------------------"+x + " is taken from the queue"); }
	 }
