package assign1;

public class RecursiveP {

	/**
	 * this is a program for calculating the sum of an array recursively
	 * 
	 * call the recursive method
	 * 
	 * @param args
	 */
	
	static int N = 101;
	static int P = 3;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = new int[N];
		for (int i = 0; i < a.length; i++) {
			a[i]=i;
		}
		int sum = 0;
		int threshold = N/P;
		
		Quad q = new Quad(0, N-1,threshold , a);
		sum = q.q();
		System.out.println("main  "+sum);
	}
}
