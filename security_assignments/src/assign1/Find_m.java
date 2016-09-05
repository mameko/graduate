package assign1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Find_m {

	/**@author Shuwen Pan 
	 * student number: 201075900
	 * @param args
	 * 
	 * This is a program for tackling the Vigenere Cipher with key less than 10.
	 * The ciphertext should be put to the root directory of the program.
	 * 
	 * The plaintext is just print out in the console.
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Find_m fm = new Find_m();
		//Declare a arraylist to hold the cipher text.
		ArrayList<Integer> ciphertext = new ArrayList<Integer>();
		//Use method rf to load the ciphertext file to the ciphertext arraylist.
		ciphertext = rf("ciphertext.txt");
		//Use method find_m to find the key length m.
		int m = fm.find_m(ciphertext);
		System.out.println("m is : " + m);
		//Use method getPlaintext to get the plaintext.
		getPlaintext(mg(m, ciphertext), ciphertext);		
	}

	/**
	 * This method is use to find the key length m.
	 * 
	 * @param  a
	 * @return int
	 */
	public int find_m(ArrayList<Integer> a) {
		// Use an arraylist c to hold the value of every intermediate results
		ArrayList<Double> c = new ArrayList<Double>();
		// loop for 10 times to find which is the most possible length of the key
		for (int m = 1; m <= 10; m++) {
			// Use for finding each line's letter frequency. 
			//If m = 1, we get 1 line, we loop for 1 time to get the letter frequency.
			//So for each value of m we need to loop for m times.
			for (int i = 0; i < m; i++) {
				// This array is use for storing the frequency of each letter inside that line.
				int b[] = new int[26];
				// Use a variable to record the length of each line.
				int n = a.size() / m;
				// Use a loop to calculate the letter frequency
				for (int j = 0; j < n; j++) {
					// Because the arraylist a is one dimension, so we use j*m get to the right letter we need to count.
					// eg. If m = 2, the first line is 0,2,4... so when j = 0, we get 0; j=1 we get 2...
					int sv = a.get(j*m+i);
					// Every time the letter appear we increasing the value stored in the letter's place. 
					b[sv] = b[sv] + 1;
				}
				// Put the result in arraylist c. 
				// The result of m = 1, take up the first chamber of the arraylist; 
				//the result of m = 2, take up the follow 2 chambers of the arraylist; etc.
				// Here we use method ic to get the value of equation of Ic(x).
				c.add(this.ic(b, n+1));
			}
		}
		// Use method rightm to calculate the most possible key lenght.
		int m_r = rightm(c);
		return m_r;
	}
	
	/**
	 * This method is use to calculate the Ic(x).
	 * 
	 * @param b
	 * @param n
	 * @return double
	 */
	double ic(int b[], int n){
		double sum = 0.0;
		double ic_r=0.0;
		// Use the equation for calculate Ic(x).
		for (int i = 0; i < 26; i++) {			
			int f = b[i];
			sum = sum + f*(f-1);
		}
		ic_r = sum/(n*(n-1));
		return ic_r;
	}

	/**
	 * This method is use to compare all the possible length of keys and determine the most possible one.
	 * 
	 * @param c
	 * @return 
	 */
	int rightm(ArrayList<Double> c){
		double min = 1;
		int im = 0;
		double d = 0.0;
		
		int cur = 0;
		// We have 10 possible key length, so loop for 10 times to see which one is most possible.
		for (int m = 1; m <= 10; m++) {	
			double sum = 0.0;
			// Because echa m generated the same amount of values and stored in arraylist c.
			// So use cur to record the current place, and move j from cur to cur + m.
			for (int j = cur; j < cur+m; j++) {
				double v = c.get(j);				
				sum = sum + v;								
			}
			// Move the cur to the starting point for next m.
			cur = cur + m;
			// Sum the values up and then divide it by m.
			d = sum/m;
			// Record the most possible key length.
			if (min >= Math.abs(d-0.065)) {
				min = Math.abs(d-0.065);
				im = m;
			}
		}
		return im;
	}
	
	/**
	 * This method is use for loading the ciphertext in the hard disk to the array.
	 * 
	 * @param filename
	 * @return
	 */
	static ArrayList<Integer> rf(String filename){
		ArrayList<Integer> a_r = new ArrayList<Integer>();
		try {			
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr); 
			while (br.ready()) {
				// When it's ok to read, read a asii each time.
				int k = br.read();
				// Change it to a more convenient value for computing and store it in arraylist a.
				a_r.add(k-65);				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return a_r;
	}
	
	/**
	 * This method is use for compute the equation Mg.
	 * @param m
	 * @param a
	 * @return
	 */
	
	static int[] mg(int m, ArrayList<Integer> a){
		int key[] = new int[m];
		int n = a.size()/m;
		// We need to divide the ciphertext into m lines and calculate the letter frequency for each line.
		// So loop for m times.
		for (int j = 0; j < m; j++) {
			// Use an array b to record the letter frequency.
			int b[] = new int[26];
			for (int i = 0; i < n; i++) {
				int sv = a.get(i*m + j);
				b[sv]= b[sv]+1;
			}
			// Put the letter frequency of English in array c.
			double c[]= {0.08167,0.01492,0.02782,0.04253,0.12702,0.02228,0.02015,0.06094,0.06996,0.00153,0.00772,0.04025,0.02406,0.06749,0.07507,0.01929,0.00095,0.05987,0.06327,0.09056,0.02758,0.00978,0.02360,0.00150,0.01974,0.00074};
									
			double min = 1;
			// s here is use for recording the shift.
			for (int s = 0; s < 26; s++) {	
				double sum = 0;
				// Use the equation.
				for (int i = 0; i < 26; i++) {	
					double bv = b[(s+i)%26];
					sum = sum +c[i]*bv/(n+1);
				}
				// Find out which shift is most probable
				double v = Math.abs(0.065-sum);
				if (min>=v) {
					min = v;
					// Record that value in array key.
					key[j]=s;
				}
			}
		}
		// Print out the most possible key value.
		String s = "";
		for (int i = 0; i < key.length; i++) {
			s = s + (char)(key[i]+65);
		}
		System.out.println("the key is: "+s);
		return key;
	}
	/**
	 * This method is use for getting the plaintext. 
	 * It is the decrytion function for the corresponding cipher.
	 * 
	 * @param key
	 * @param a
	 */
	static void getPlaintext(int[] key, ArrayList<Integer> a){
		// Define an array p to hold the value of the plaintext.
		// The reason for adding key.length space in the tail is the method use below will cause outofboundary exception.
		int p[] = new int[a.size()+key.length];
		// Use the key to decrypt each key.length ciphertext.
		for (int i = 0; i < a.size()/key.length; i++) {
			for (int j = 0; j < key.length; j++) {
				int cur = i*key.length + j;
				int v = (a.get(cur)- key[j]);
				// Here we do the module 26 by adding 26 to it.
				if (v < 0) {
					v = v+26;
				} 
				p[cur] = v+65;
			}
		}
		String s = "";
		for (int i = 0; i < p.length; i++) {	
			// Cast the letter to char so that we can print it out at the console.
			s = s + (char)p[i];			
		}
		System.out.println(s);
	}
}
