package assign2;

import java.util.concurrent.Semaphore;

public class assign2_q16b {
	private final static int BUFFER_SIZE = 10; // the actual buffer size is BUFFER_SIZE-1, because I implement a queue
	private final static int CONSUMER_SIZE = 3;// there are 3 consumers here
	static Semaphore[] sForChange = new Semaphore[BUFFER_SIZE];// semaphore for changing data inside the buffer slot
	Semaphore sPut = new Semaphore(BUFFER_SIZE-1);// initial semaphore for the buffer. totally BUFFER_SIZE-1.
	// Semaphore[] sFetch = new Semaphore[BUFFER_SIZE];
	volatile int[] counter = new int[BUFFER_SIZE];// use for countering whether all the consumer get that item
	volatile int rear = 0;// the rear of the queue
	volatile int front = 0;// the front of the queue

	int[] myBuffer = new int[BUFFER_SIZE];// create buffer

	public static void main(String[] args) {
		assign2_q16b a = new assign2_q16b();
		a.dothings();
	}

	void dothings() {
		for (int i = 0; i < myBuffer.length; i++) {
			sForChange[i] = new Semaphore(1);// Initialise the access control semaphore for each buffer slot. 
											// each time only one thread can change the data in that slot.
		}

	//	for (int i = 0; i < 10; i++) {
			new Thread(new MyProducer()).start();//if there are multiple producer, problem will happen. 
	//	}

		for (int i = 0; i < CONSUMER_SIZE; i++) {
			new Thread(new MyConsumer()).start();// create CONSUMER_SIZE consumer
		}

		new Thread(new Coordinator()).start(); // there is a coordinator to check whether all the consumer take the thing at that slot.
											   // then clear the counter for that slot. release the semaphore.
	}

	/**
	 * inv {#produce < buffer_size}
	 */
	
	void put(int item) {
		try {
			while (true) {
		//		System.out.println("in put. wait for sPut");
				
				sPut.acquire(); // this semaphore is use to ensure the buffer is
								// not full when put things there
				/**
				 * {#item < buffer_size}
				 */
		//		System.out.println("in put. passed sput");
				boolean k = false;
				if (rear + 1 == front) {	//check if the queue is full
					k = false;//full
				}else {
					k = true;
				}
				while (true) {
					if (rear + 1 == front) {	//check if the queue is full
						k = false;//full
					}else {
						k = true;
					}
					if (!k) {
//						Thread.sleep(30);
						continue;
					}else {
						break;
					}
					
				}
			//	System.out.println("in put. rear now is : " + rear);
			//	System.out.println("in put. wait for sForchange : "+rear);
				sForChange[rear].acquire(); // this is ensure no two thread can not operate the same element in the buffer
			//	System.out.println("in put. passed sforchange : "+rear);
				
				/** 
				 * {#buffer slot used < buffer_size} 
				 * 
				*/
				myBuffer[rear] = item;
				int cur = rear;
				System.out.println("put item " + item + " in the buffer slot "
						+ rear);
				rear = (rear + 1) % BUFFER_SIZE;
				sForChange[cur].release(); // release the operation right, let
											// other threads to modified it.
			//	System.out.println("in put. release sforchange : "+ cur);
				// sFetch[rear].release();
				break;

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	int fetch(MyConsumer m) {
		int item = 0;

		while (true) {
			try {
				
				int myfront = m.getMyfront();// get the cursor of that consumer thread
				boolean k = false;
		//		System.out.println(m.hashCode()+" rear : "+rear+" myfront : "+myfront);
				if (rear  == myfront) {	//check if the queue has thing inside
					k = false;//nothing inside
				}else {
					k = true;
				}
				while (true) {
					if (rear  == myfront) {	//check if the queue has something inside
						k = false;//nothing inside
					}else {
						k = true;
					}
					if (!k) {
//						Thread.sleep(30);
			//			System.out.println(m.hashCode()+" blocked");
						continue;
					}else {
						break;
					}
					
				}
		//		System.out.println("in fetch. wait for sForchange : "+myfront);
		//		System.out.println("require sForChange : " + myfront);
				sForChange[myfront].acquire();	// this is ensure no two thread can not operate the same element in the buffer
		//		System.out.println("myfront : " + myfront);
		//		System.out.println("in fetch. passed sForchange : "+myfront);
		//		
			//	while (counter[myfront] > CONSUMER_SIZE) {
		//		System.out.println("counter[myfront] : " + counter[myfront]);
				int cur = myfront;
					if (counter[myfront] < CONSUMER_SIZE ) {	// not all the consumer get the item in that slot

						counter[myfront] = counter[myfront] + 1;// increase that consumer's cursor, so it can get next item when it enter next time.
						item = myBuffer[myfront];
						System.out.println("------------------------get item "
								+ item + " from the buffer slot " + myfront
								+ ", counter now is " + counter[myfront]);
//						
//						if (rear  == myfront) {	//check if the queue has something inside
//							k = false;//nothing inside
//						}else {
//							k = true;
//						}
//						if (k) {// if all the element in the queue is already fetch by that thread, not increase the cursor
							myfront = (myfront + 1) % BUFFER_SIZE;
				//			System.out.println("myfront is : " + myfront);
							m.setMyfront(myfront);// set the value back to that thread's local variable
//						}

						sForChange[cur].release();
		//				System.out.println("in fetch. release sForchange : "+cur);
						return item;
					} else {
						sForChange[cur].release();
						continue;
			//		}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	class Coordinator implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub

			while (true) {
//				 try {
//				 Thread.sleep(300);
//				 } catch (InterruptedException e) {
//				 // TODO Auto-generated catch block
//				 e.printStackTrace();
//				 }
			//	 System.out.println("front = "+front+" rear : "+rear);
				if (counter[front] < CONSUMER_SIZE ) {// see if all the consumer get the data
			//		System.out.println("in if, counter is " + counter[front]);
					continue;
				}else {					
				
				/** {counter = consumer_size}*/
				sPut.release();// release the semaphore so the producer can put things in the buffer
				
				counter[front] = 0;// clear counter
				System.out.println("release slot" + front);
//				int cur = front;
//				try {
//					sForChange[front].acquire();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				front = (front + 1) % BUFFER_SIZE;// check next item
//				sForChange[cur].release();
				
			}
			}
		}
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
		int myfront = 0;// record how much item this consumer thread is fetched.

		public int getMyfront() {
			return myfront;
		}

		public void setMyfront(int myfront) {
			this.myfront = myfront;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			while (true) {
				int item = fetch(this);
				System.out.println("get : " + item);
			}
		}

	}

}
