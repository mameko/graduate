package assign1;

public class Worker extends Thread {

	int L = 0;
	int w = 0;
	int[] a = null;
	int[] c = null;
	int[] finish = null;

	public int[] getFinish() {
		return finish;
	}

	public void setFinish(int[] finish) {
		this.finish = finish;
	}

	public int getL() {
		return L;
	}

	public void setL(int l) {
		L = l;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int[] getA() {
		return a;
	}

	public void setA(int[] a) {
		this.a = a;
	}

	@Override
	public void run() {											//{true}
		// TODO Auto-generated method stub
		int first = w*L;										//{first = 0}
		int last = first + L;									//{last = 2}
		if (last > a.length) {
			last = a.length;
		}
																//{loop inv : first <= i <= last ^ c[w] = sum(from first to i-1) a[i]}
		if (a.length!=0) {
			for (int i = first; i < last; i++) {				//{i<last}
				c[w] = c[w] + a[i];								//{first <= i < last ^ c[w] = sum(from first to i) a[i]}
			}
		}
		finish[w] = 1;											
		System.out.println(c[w]+"      "+finish[w]);			//{c[w] = sum(from first to last-1) a[i]}
	}

	public int[] getC() {
		return c;
	}

	public void setC(int[] c) {
		this.c = c;
	}

}
