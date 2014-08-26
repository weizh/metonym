package model;

import java.util.ArrayList;
import java.util.HashMap;

import libsvm.svm_node;

/**
 * fill out the dictionary, and use getVector function to get node array.
 */

public class NominalFeature extends Feature {

	private static final long serialVersionUID = 1L;
	HashMap<String, Integer> dictionary;

	public HashMap<String, Integer> getDictionary() {
		return dictionary;
	}

	public void setDictionary(HashMap<String, Integer> dictionary) {
		this.dictionary = dictionary;
	}

	int dictid;

	public NominalFeature(String header) {
		super(header);

		dictionary = new HashMap<String, Integer>();
		dictid = -1;
	}

	public void addFeatureValue(String value) {
		if (!dictionary.containsKey(value)) {
			dictionary.put(value, ++dictid);
		}
	}

	/**
	 * Id Start from 0.
	 */
	public int getValueId(String value) {
		if (dictionary.containsKey(value) == false) {
//			System.out.println("dictionary is:" + dictionary);
//			System.out.println("Value is: " + value);
			System.err.println("value not in Dictionary for Nominal Feature "+ value);
			return -1;
		} else
			return dictionary.get(value);
	}

	public ArrayList<svm_node> getSVMNodeVector(String value) throws Exception {
		if (dictionary.size() == 0) {
			throw new Exception("No dictionary for feature "
					+ this.featureHeader);
		}
		ArrayList<svm_node> nodes = new ArrayList<svm_node>();
		for (int i = 0; i < dictionary.size(); i++) {
			svm_node n = new svm_node();
			n.index = i;
			n.value = 0;
			nodes.add(n);
		}
		if (value.endsWith("[N/A]"))
			return nodes;

		if (getValueId(value) == -1)
			return nodes;
		nodes.get(getValueId(value)).value = 1;
		return nodes;
	}

	public ArrayList<svm_node> getSVMNodeVector(String[] value)
			throws Exception {
		if (dictionary.size() == 0) {
			throw new Exception("No dictionary for feature "
					+ this.featureHeader);
		}
		ArrayList<svm_node> nodes = new ArrayList<svm_node>();
		for (int i = 0; i < dictionary.size(); i++) {
			svm_node n = new svm_node();
			n.index = i;
			n.value = 0;
			nodes.add(n);
		}

		for (String v : value) {
			if (v == null)
				continue;
			if (v.endsWith("[N/A]"))
				continue;
			if (getValueId(v) != -1)
				nodes.get(getValueId(v)).value = 1;
		}

		return nodes;
	}

	public void addFeatureValue(ArrayList<String> t) {
		for ( String ts : t){
			addFeatureValue(ts);
		}
	}
}
