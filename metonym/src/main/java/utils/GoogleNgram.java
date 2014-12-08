package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import libsvm.svm_node;

public class GoogleNgram {

	static HashMap<String, Double> human;
	static HashMap<String, Double> loc;

	static {
		human = new HashMap<String, Double>();
		loc = new HashMap<String, Double>();
		BufferedReader br = null;
		BufferedReader br2 = null;

		try {
			br = new BufferedReader(new FileReader(new File("src/main/resources/human/He.txt")));
			br2 = new BufferedReader(new FileReader(new File("src/main/resources/loc/loc.txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = null;
		try {
			double totalh = 0, totall = 0;
			while ((line = br.readLine()) != null) {
				String[] toks = line.split(" ");
				if (toks.length < 2)
					continue;
				String s = toks[0].substring(0, toks[0].length() - 5);
				double a = (double) Integer.parseInt(toks[1]);
				totalh += a;
				if (!human.containsKey(toks[0]))
					human.put(s, a);
				else
					human.put(s, a + human.get(s));
			}
			while ((line = br2.readLine()) != null) {
				String[] toks = line.split(" ");
				if (toks.length < 2)
					continue;
				String s = toks[0].substring(0, toks[0].length() - 5);

				double a = (double) Integer.parseInt(toks[1]);
				totall += a;
				if (!loc.containsKey(s))
					loc.put(s, a);
				else
					loc.put(s, a + loc.get(s));
			}
			
			for (Entry<String, Double> e : human.entrySet()){
				String k = e.getKey();
				double v = e.getValue();
				human.put(k, v/totalh);
			}
			for (Entry<String, Double> e : loc.entrySet()){
				String k = e.getKey();
				double v = e.getValue();
				loc.put(k, v/totalh);
			}

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ArrayList<svm_node> getGNgramSvmNodeVector(String verb) {
		ArrayList<svm_node> nodes = new ArrayList<svm_node>();
		nodes.add(new svm_node());
		nodes.add(new svm_node());
		nodes.get(0).index = 0;
		nodes.get(0).value = -1;
		nodes.get(1).index = 1;
		nodes.get(1).value = -1;
		if (human.containsKey(verb))
			nodes.get(0).value = 1;
		return nodes;
	}
	
	public static void main(String arvgb[]){
		for ( svm_node n : getGNgramSvmNodeVector("agreed")){
			System.out.println(n.index+" "+ n.value);
		}
	}
}
