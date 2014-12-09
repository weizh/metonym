package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import libsvm.svm_node;

public class Embeddings_Vec {

	static HashMap<String, ArrayList<Double>> embeddings;

	static int length;
	static {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("src/main/resources/wordEmbedding/embeddings-scaled.EMBEDDING_SIZE=25.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		// set according to the vector used.
		int length = 100;

		embeddings = new HashMap<String, ArrayList<Double>>();

		String currentClass = null;
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				if (line.trim().length() < 1)
					continue;

				String[] words = line.trim().split(" ");
				ArrayList<Double> arg1 = new ArrayList<Double>();
				for (int i = 1; i < words.length; i++) {
					arg1.add(Double.parseDouble(words[i]));
				}

				embeddings.put(words[0], arg1);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(embeddings);
	}

	public static void main(String argbv[]) {
		System.out.println(getEmbeddingArray("run"));
		// System.out.println(getEmbeddingArray("run").size());
		// System.out.println(getEmbeddingArray("test").size());

	}

	public static ArrayList<Double> getEmbeddingArray(String string) {
		return embeddings.get(string);
	}

	public static ArrayList<svm_node> getEmbeddingSVMNodes(String s) {
		ArrayList<svm_node> nodes = new ArrayList<svm_node>(length);
		for ( int i=0; i< length; i++)
		{
			svm_node n = new svm_node();
			n.index=i;
			n.value=0;
		}
		ArrayList<Double> str = getEmbeddingArray(s);
		if (str ==null)
			return nodes;
		
		for (int j=0;j<length;j++) {
			svm_node n = nodes.get(j);
			n.value = str.get(j);
		}
		return nodes;
	}

}
