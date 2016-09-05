package assign3;

public class Assign3Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Philosopher ps[] = new Philosopher[5];
		Chopstick cs[] = new Chopstick[5];
		Assign3_Waiter josh = new Assign3_Waiter(5);
		for (int i = 0; i < 5; i++) {			
			cs[i] = new Chopstick();
		}
		for (int i = 0; i < 5; i++) {
			ps[i] = new Philosopher(i, josh, cs[i], cs[(i+1)%5]);
			new Thread(ps[i]).start();
		}
	}

}
