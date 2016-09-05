package assign3;

public class Find_gcd_and_inverse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a=18721;
		int b=14702;
		int ti = 0;
		int ti1 = 1;
		int d = 0;
		int original_a = a;
		int original_b = b;
		
		while(b!=0) {
			int c = a;
			d = b;
			a = b;
			b = c%b;
			if (b == 0) {
				break;
			}
			int q = c/d;
			int ti2 = ti - q * ti1;
			ti = ti1;
			ti1 = ti2;
		}
		
		if (ti1<0) {
			ti1 = ti1 + original_a;
		}
		System.out.println("the inverse is :"+ti1);
		System.out.println("gcd("+original_a+","+original_b+") = "+d);
	}
}
