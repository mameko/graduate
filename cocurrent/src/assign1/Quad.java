package assign1;

public class Quad extends Thread {

	int s;
	int e;
	int t;
	int sum = 0;
	int[] a = null;
	boolean finish = false;

	public Quad(int start, int end, int threshold, int[] ai) {
		// System.out.println("new a quad");
		this.s = start;
		this.e = end;
		this.t = threshold;
		this.a = ai;
	}

	public int q() {														
		int mid = ((e - s) / 2) + s/* +((e - s) % 2) */;
		int re = 0;
		// System.out.println(this.getName()+
		// "before t1: "+s+"------"+e+"------"+mid +"------"+ t);
		if ((e - s + 1) > t) {													
	//		System.out.println(this.getName() + "after if t1: " + s + "------"+ e + "------" + mid + "------" + t);
			Quad tl = new Quad(s, mid, t, a);
			// System.out.println(this.getName() +
			// "    create : "+tl.getName());
			tl.start();

			Quad tr = new Quad(mid + 1, e, t, a);
			// System.out.println(this.getName() +
			// "    create : "+tr.getName());
			tr.start();

			int s1 = 0, s2 = 0;
			while (!tl.isFinish()||!tr.isFinish()) {//if the thread is not finished, make this thread sleep, let the other thread continue.
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			s1 = tl.getSum();
			s2 = tr.getSum();
			sum = s1 + s2;											// sum = result form left + result from right
			
			return sum;
		} else {
		//	System.out.println(this.getName() + "before else: " + s + "------" + e + "------" + mid + "------" + t);
			for (int i = s; i <= e; i++) {							//true
				re = re + a[i];
			}														//re = sum from a[start] to a[end]
			return re;
		}
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public boolean isFinish() {
		return finish;
	}

	@Override
	public void run() {												
		int ss = q();
		sum = ss;													
		finish = true;			
	//	System.out.println(this.getName() + "  return  " + ss);
	}
}
