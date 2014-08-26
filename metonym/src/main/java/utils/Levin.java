package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import libsvm.svm_node;

public class Levin {
	static HashMap<String, HashSet<String>> levin;

	static HashSet<String> ids;
	static {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(
					"src/main/resources/Levin.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		levin = new HashMap<String, HashSet<String>>();
		ids = new HashSet<String>();

		String currentClass = null;
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				if (line.trim().length() < 1)
					continue;
				if (Character.isDigit(line.charAt(1))) {
					currentClass = line.trim().split(" ")[0];
					ids.add(currentClass);
					// System.out.println(currentClass);
				} else if (line.startsWith("        ")) {
					String[] words = line.trim().split(" ");
					for (String word : words) {
						if (levin.containsKey(word) == false) {
							levin.put(
									word,
									new HashSet<String>(
											Arrays.asList(new String[] { currentClass })));
						} else {
							levin.get(word).add(currentClass);
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(levin);
	}

	public static HashSet<String> getLevinCategories(String s) {
		HashSet<String> cats = levin.get(Stemmer.stemTerm(s.trim()
				.toLowerCase()));
		return cats;
	}

	public static ArrayList<String> getLevinCategoryStrings(String s) {
		ArrayList<String> as = new ArrayList<String>();
		HashSet<String> cats = levin.get(Stemmer.stemTerm(s.trim()
				.toLowerCase()));
		if (cats == null) {
			as.add("lvn_"+s.trim().toLowerCase());
		} else {
			for (String cat: cats)
			as.add("lvn_"+cat);
		}
		return as;
	}

	public static ArrayList<svm_node> getGetLevinSvmNodes(String s) {

		ArrayList<svm_node> darray = new ArrayList<svm_node>();

		HashSet<String> cats = levin.get(Stemmer.stemTerm(s.trim()
				.toLowerCase()));

		if (cats != null) {
			int in = 0;
			for (Iterator<String> i = ids.iterator(); i.hasNext();) {
				s = i.next();
				svm_node n = new svm_node();
				n.index = in++;
				n.value = cats.contains(s) ? 1 : 0;
				darray.add(n);
			}
		} else {
			int in = 0;
			for (Iterator<String> i = ids.iterator(); i.hasNext();) {
				s = i.next();
				svm_node n = new svm_node();
				n.index = in++;
				n.value = -1;
				darray.add(n);
			}
		}

		return darray;
	}

	public static void main(String argv[]) {
		ArrayList<svm_node> n = getGetLevinSvmNodes("get");
		for (svm_node node : n)
			System.out.println(node.index + " " + node.value);
	}
}
