package threadtest;

public class ConSetTest implements Runnable {
	volatile static boolean set = false;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("in while");
		while(!set);
		System.out.println("after while");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub				
		Thread t= new Thread(new ConSetTest());
		t.start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("before set");
					set = true;
				System.out.println("set");
			}
		}).start();
	}

		
}
