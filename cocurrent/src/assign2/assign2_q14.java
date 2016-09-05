package assign2;

import java.util.concurrent.Semaphore;

public class assign2_q14 {
	private final static int BUFFER_SIZE = 10;// buffer size
	static Semaphore[] sForChange = new Semaphore[BUFFER_SIZE];// semaphore for changing data inside the buffer slot
	Semaphore sPut = new Semaphore(BUFFER_SIZE);// semaphore for put things inside the buffer
	Semaphore sFetch = new Semaphore(BUFFER_SIZE);// semaphore for get things from the buffer
	volatile boolean[] used = new boolean[BUFFER_SIZE];// record whether that slot is used 

	int[] myBuffer = new int[BUFFER_SIZE];// create a buffer

	public static void main(String[] args) {
		assign2_q14 a = new assign2_q14();
		a.dothings();
	}

	void dothings() {
		for (int i = 0; i < myBuffer.length; i++) {
			sForChange[i] = new Semaphore(1);// Initialise the access control semaphore for each buffer slot. 
											// each time only one thread can change the data in that slot.
		}

		for (int i = 0; i < 100; i++) {// create 100 producer
			new Thread(new MyProducer()).start();
		}

		for (int i = 0; i < 2; i++) {
			new Thread(new MyConsumer()).start();// create 2 consumer
		}
	}
	/**
	 *  inv. {#produced - #consumed =< buffer_size}
	 *
	 */
	void put(int item) {
		try {
			int i = 0;
			while (true) {					
				if (used[i]) {				// iterate to see which slot is empty 
					i = (i + 1) % BUFFER_SIZE;
					continue;
				} else {
					sPut.acquire();			// this semaphore is use to ensure the buffer is not full when put things there
					/**
					 * {#buffer slot used < buffer_size}
					 * */
					sForChange[i].acquire();// this is ensure no two thread can not operate the same element in the buffer 
					if (!used[i]) {			// this if is for checking whether someone else change "used" of that slot
					/** {used[i]==false}
					 * */
					myBuffer[i] = item;		// Because if more than one thread are waiting to put things in the buffer, the one who wins will change flag "used".
					used[i] = true;			// so even it release the semaphore, the other waiting thread can not put things in that slot.
					System.out.println("put item " + item
							+ " in the buffer slot " + i);
					}
					sFetch.release();		// release semaphore, so that the consumer thread can fetch things.
					sForChange[i].release();// release the semaphore after change the slot status, so that the consumer can change that after they fetch.
					break;
				}

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	int fetch() {
		int item = 0;
		int i = 0;
		while (true) {						// iterate to see which slot has things inside  
			if (!used[i]) {
				i = (i + 1) % BUFFER_SIZE;
				continue;
			} else {
				try {
					sFetch.acquire();		//this semaphore is use to ensure the buffer is not empty when get things
					/**
					 * {#buffer slot used > 0}
					 */
					sForChange[i].acquire();//this is ensure no two thread can not operate the same element in the buffer
					if (used[i]) {			//this if is for checking whether someone else change "used" of that slot
						/**
						 *  {used[i]==true}
						*/
						used[i] = false;	
						item = myBuffer[i];
						System.out.println("------------------------get item "
								+ item + " from the buffer slot " + i);
					}
						sPut.release();		// release semaphore, so that the producer thread can put things in the buffer.
						sForChange[i].release();//release the semaphore after change the slot status, so that the producer can change that after they put things.
						break;
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return item;
	}

	class MyProducer implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				int item = (int) (Math.random() * 100 + 1);
				put(item);
			}
		}

	}

	class MyConsumer implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				int item = fetch();
				System.out.println("get : " + item);
			}
		}
	}
}
