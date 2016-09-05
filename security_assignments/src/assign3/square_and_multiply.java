package assign3;

public class square_and_multiply {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = 575;
		int b = 275;
		int n = 1073;
		int result = sm(x,b,n);
		System.out.println(result);
	}

	static public int sm(int x,int b,int n){
		int z = 1;
		String bs = Integer.toBinaryString(b);
		for (int i = bs.length()-1; i>=0; i--) {
			z = (z*z)%n;
			int bi = bs.charAt(bs.length()-1-i)-'0';
			if (bi==1) {
				z = (z*x)%n;
			}
		}
		return z;
	}
}
