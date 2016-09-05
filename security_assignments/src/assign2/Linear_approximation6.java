package assign2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Linear_approximation6 {

	/**
	 * @author Shuwen Pan student number 201075900
	 * 
	 * This is a program for processing the linear cryptanalysis result and get
	 * the keys The ciphertext should be put to the root directory of the
	 * program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// This arraylist if use for storing the plaintext and ciphertext pair.
		ArrayList<String> textPair = new ArrayList<String>();
		// Read the text files
		textPair = rf("ciphertext2.txt");

		// The first linear cryptanalysis result
		// Here I store the data in an int array, the number there represent the
		// corresponding bis in the spn network
		// The corresponding ps
		int[] approximationResultP = { 1, 4, 13, 16 };
		// The corresponding us
		int[] approximaxionResultU = { 7, 15 };
		// I get the bias of each round from LAT and multiply them to get the
		// total bias.
		double biasOfAppro = 1.0 / 32;
		// Get the keys using the getKey function
		String key = getKey(textPair, approximationResultP,
				approximaxionResultU, biasOfAppro);
		System.out.println("key[5-8] and key[13-16] is " + key);

		// The second linear cryptanalysis result
		int[] approximationResultP2 = { 14, 16 };
		int[] approximaxionResultU2 = { 3, 11 };
		double biasOfAppro2 = 9.0 / 128;
		String key2 = getKey(textPair, approximationResultP2,
				approximaxionResultU2, biasOfAppro2);
		System.out.println("key[1-4] and key[9-12] is " + key2);

		// Use the putKeysTogether method to construct the key
		String keys = putKeysTogether(key, key2);
		System.out.println(keys);
	}

	// This is the mapping given by question 1 for each s box
	// Here I use binary expression for 'convenience'
	static String[] mapping = { "0000", "1010", "0001", "1000", "0010", "1101",
			"0011", "1001", "0100", "1111", "1011", "0101", "0110", "1100",
			"0111", "1110" };

	/**
	 * This method is use for recovering the sub key bits from the last round.
	 * Pass the result I get from the cryptanalysis phases here to process.
	 * 
	 * @param textpair
	 * @param aResultP
	 * @param aResultU
	 * @param bOfAppro
	 * @return
	 */
	static String getKey(ArrayList<String> textpair, int[] aResultP,
			int[] aResultU, double bOfAppro) {
		// Use to determine which key need to xor.
		// Because each time I just approximize two s boxes in the last round, I
		// need a reference to see whether it belongs to the second half.
		int sum = 0;
		for (int j = 0; j < aResultU.length; j++) {
			// Add the number inside U together, then get the sum. Later it will
			// be divided by 2 and get the referent place
			sum = aResultU[j] + sum;
		}

		// This int array is use for recording the counts for each keys I tried.
		int[] count = new int[256];
		// Iterate for 256 to find exhausted guess the key.
		for (int i = 0; i < 256; i++) {
			// For each guess, we need to run through all the plaintext and
			// ciphertext pair.
			// Because we store the plaintext and ciphertext seperately in two
			// cells, so t need to +2 each round.
			for (int t = 0; t < textpair.size(); t = t + 2) {
				// Use for recording the xor result of all the bits in (*);
				int xorresult = 0;

				// Get the plaintext and ciphertext. They're in binary form and
				// of type String
				String plaintext = textpair.get(t);
				String ciphertext = textpair.get(t + 1);

				// Get the corresponding sub ciphertext
				String relativeCipher = getRightText(ciphertext, aResultU);

				// xor the ps together first.
				for (int j = 0; j < aResultP.length; j++) {
					xorresult = xorresult
							^ (plaintext.charAt(aResultP[j] - 1) - 48);
				}

				// Change the i to keys guess in this round to binary
				String bi = Integer.toBinaryString(i);

				// Add some 0 in front if the length is not enough because we
				// need to xor them with the ciphertext.
				while (bi.length() < 8) {
					bi = "0" + bi;
				}

				// Cut the sub key we guess in this round in half, in order to
				// process seperately to different segment of ciphertext. We can
				// get the us from that.
				String bi1 = bi.substring(0, 4);
				String bi2 = bi.substring(4, 8);
				// Cut the sub ciphertext in half to perform xor with the guess
				// sub key.
				String rc1 = relativeCipher.substring(0, 4);
				String rc2 = relativeCipher.substring(4, 8);

				// Actually do the xor bit by bit. After the operation the
				// result is stored inside uUp and uDown in binary String
				// version.
				String uUp = "";
				String uDown = "";
				int x, y;
				for (int j = 0; j < 4; j++) {
					x = bi1.charAt(j) ^ rc1.charAt(j);
					uUp = uUp + String.valueOf(x);
					y = bi2.charAt(j) ^ rc2.charAt(j);
					uDown = uDown + String.valueOf(y);
				}

				// Change the xor result into decimal form to do the table look
				// up.
				int up = binaryToDecimal(uUp);
				int d = binaryToDecimal(uDown);

				// After looking at the table (the array), we can get the u
				// values before passing the s box in binary String form.
				String beforSboxup = mapping[up];
				String beforSboxdown = mapping[d];

				// Decide which part the xorresult should xor. The reason doing
				// this is because we put the us in an array.
				// So when we read from it, we need to decide where it should
				// xor.(two different s box)
				int sumU = sum / 3 + 1;
				// Iterate through the u array, and xor where it supposed to be
				for (int w = 0; w < aResultU.length; w++) {
					// Get the right index. Because the number inside the array
					// begin at 1, which is different from the array index
					// (begin at 0)
					// Use 4 to mod it is because I want to get the index in
					// relativity form.
					int k = aResultU[w] % 4;
					// When k equals 4's multiplier, the k will become 0. here
					// just to rejust it to the 4th place
					if (k == 0) {
						k = 4;
					}
					// When the value of the u less than the reference
					// place(which I use for deviding the upper or the down s
					// box)
					// If it is less, so xor the bits in the upper part, else
					// the lower part.
					if (aResultU[w] < sumU) {
						xorresult = xorresult
								^ (beforSboxup.charAt(k - 1) - 48);
					} else {
						xorresult = xorresult
								^ (beforSboxdown.charAt(k - 1) - 48);
					}
				}
				// See whethe the equation holds, if it is, increase that sub
				// key's count
				if (xorresult == 0) {
					count[i]++;
				}
			}
		}
		// pick the cloeset number to the bias we get from the calculation
		int closest = 0;
		double min = 1;
		double bias = 0;
		for (int j = 0; j < count.length; j++) {
			bias = Math.abs(count[j] / 10000.0 - 0.5);
			double c = Math.abs(bias - bOfAppro);
			if (c < min) {
				min = c;
				closest = j;
			}
		}
		// Return the key value in binary form.
		return Integer.toBinaryString(closest);
	}

	/**
	 * This method is used for get the right sub ciphertext from the original
	 * text according to the us place.
	 * 
	 * @param text
	 * @param place
	 * @return
	 */
	static String getRightText(String text, int[] place) {
		String texts = "";
		int[] r = new int[4];
		// Divide the number store in the us array by 4 , to see which segment
		// we need to consider.
		// Then we increase the counting for each one.
		for (int i = 0; i < place.length; i++) {
			int p = place[i] / 4;
			switch (p) {
			case 0:
				r[0]++;
				break;
			case 1:
				r[1]++;
				break;
			case 2:
				r[2]++;
				break;
			case 3:
				r[3]++;
				break;
			default:
				break;
			}
		}
		// Find the right place and cut the sub ciphertext out and then paste it
		// together
		for (int i = 0; i < 4; i++) {
			// Each time we just need to know that segment is needed.(when the
			// corresponding cell != 0)
			// When it's not equals to 0, we put that segment into the return
			// string.
			if (r[i] != 0) {
				texts = texts + text.substring(i * 4, i * 4 + 4);
			}
		}

		return texts;
	}

	// Change binary string to integer
	static int binaryToDecimal(String s) {
		int sum = 0;
		for (int i = 0; i < s.length(); i++) {
			sum = sum * 2 + (s.charAt(i) - 48);
		}
		return sum;
	}

	// Change the hex string to binary string
	static String hexToBinary(String s) {
		String bi = "";
		// mapping value
		String[] hexMapToBinary = { "0000", "0001", "0010", "0011", "0100",
				"0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100",
				"1101", "1110", "1111" };
		for (int i = 0; i < s.length(); i++) {
			// get the character and change it to binary. Then paste them together.
			char c = s.charAt(i);
			if (c < 89) {
				bi = bi + hexMapToBinary[c - 48];
			} else {
				switch (c) {
				case 'a':
					bi = bi + hexMapToBinary[10];
					break;
				case 'b':
					bi = bi + hexMapToBinary[11];
					break;
				case 'c':
					bi = bi + hexMapToBinary[12];
					break;
				case 'd':
					bi = bi + hexMapToBinary[13];
					break;
				case 'e':
					bi = bi + hexMapToBinary[14];
					break;
				case 'f':
					bi = bi + hexMapToBinary[15];
					break;
				default:
					break;
				}
			}
		}
		return bi;
	}

	/**
	 * This method is use for loading the ciphertext in the hard disk.
	 * 
	 * @param filename
	 * @return
	 */
	static ArrayList<String> rf(String filename) {
		ArrayList<String> a_r = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			while (br.ready()) {
				// When it's ok to read, read a line each time.
				String s = br.readLine();
				String[] ss = s.split("  ");
				a_r.add(hexToBinary(ss[0]));
				a_r.add(hexToBinary(ss[1]));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return a_r;
	}
	/**
	 * This method is use for processing the keys we get from the recovering sub key stage.
	 * It just simply put the sub key string in to the right place.
	 * 
	 * @param k1
	 * @param k2
	 * @return
	 */
	static String putKeysTogether(String k1, String k2) {
		String key = "";
		// add some 0 in front of the sub keys when it's not long enough for sepereting
		while (k1.length() < 8) {
			k1 = "0" + k1;
		}

		while (k2.length() < 8) {
			k2 = "0" + k2;
		}

		// Cut the sub keys in half, place it in the right place.
		String k11 = k1.substring(0, 4);
		String k12 = k1.substring(4, 8);
		String k21 = k2.substring(0, 4);
		String k22 = k2.substring(4, 8);

		key = k21 + k11 + k22 + k12;
		return key;
	}
}
