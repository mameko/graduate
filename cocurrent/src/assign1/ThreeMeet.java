package assign1;

public class ThreeMeet {
	int i = 0, j = 0, k = 0;
	int max = 0;
	static int[] f = { 0, 2, 4, 6, 8, 11, 13, 15 };
	static int[] g = { 3, 5, 9, 11, 18, 22 };
	static int[] h = { 1, 7, 10, 11, 26, 30 };

	public static void main(String[] args) {
		ThreeMeet tm = new ThreeMeet();
		tm.find();
	}

	private void find() {
		// TODO Auto-generated method stub

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (!isEqual()) {
					while (f[i] < max) {
						i = i + 1;
					}
					System.out.println("t1 :" + f[i]);
					setMax(f[i]);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (!isEqual()) {
					while (g[j] < max) {
						j = j + 1;
					}
					System.out.println("t2 :" + g[j]);
					setMax(g[j]);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (!isEqual()) {
					while (h[k] < max) {
						k = k + 1;
					}
					System.out.println("t3 :" + h[k]);
					setMax(h[k]);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

	synchronized protected void setMax(int l) {
		// TODO Auto-generated method stub	
		if (l > max) {
			System.out.println("set max to " + l);
			max = l;
		}
		
	}

	boolean isEqual() {
		if (f[i] == g[j] && g[j] == h[k]) {
			System.out.println("i : " + i + " j : " + j + " k : " + k);
			return true;
		} else {
			return false;
		}
	}

}
