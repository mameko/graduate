package assign4;

public class Miller_Rabin_alg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int cs[]= {2,3,4,5,7,13,15};
		int sum = 0;
		
		for (int i = 0; i < 7; i++) {
			sum = sum + mr(756478309,cs[i]);		
		}
		
		if (sum == 0) {
			System.out.println("inconculsive");
		} else {
			System.out.println("composite");
		}
	}
	
	static int mr(int m, int c){
 		int m2 = m-1;
		int k = 0;
		int z = 0;
		/*String s = Integer.toBinaryString(m2);
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(s.length()-i-1)=='1') {
				k = i;
				break;
			}
			z = Integer.parseInt(s,2)/2;
		}		
			z = z/2;
			*/
		while(m2%2 ==0){
			m2 = m2/2;
			k++;
		}
		z = m2;
		long g;
		int e = 1;
		for (int i = 1; i <= z; i++) {
			e = c*e;
			e = e % m;
		}
		g = (long) (e % m);
		/*
		g = square_and_multiply.sm(c, z, m);*/
		if (g == 1) {
			return 0;
		}
		for (int i = 0; i < k; i++) {
			if (g == m-1) {
				return 0;
			}
			g = (g % m )*(g % m) %m;
			//	g = square_and_multiply.sm(g, 2, m);
		}
		return 1;
	}

}
