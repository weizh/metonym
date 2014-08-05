package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class BrownCluster_Str {

	static HashMap<String, String> bc;

	static {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("src/main/resources/brown-rcv1.clean.tokenized-CoNLL03.txt-c100-freq1.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		bc = new HashMap<String, String>();

		String currentClass = null;
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				if (line.trim().length() < 1)
					continue;

				String[] words = line.trim().split("\t");
				bc.put(words[1], words[0]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// System.out.println(levin);
	public static String getBrownClusterId(String s, int cutoff) {
		// System.out.println(s);
		String t = Stemmer.stemTerm(s.trim().toLowerCase());
		String ss = bc.get(t);
		return ss == null ? s : ss.substring(0, cutoff);
	}

	public static void main(String argv[]) {
		System.out.println(getBrownClusterId("talk",4));
		/**
		 * 1000011001 break 1111010101011 hurt
		 */
	}
}
