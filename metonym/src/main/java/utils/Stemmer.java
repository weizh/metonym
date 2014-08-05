package utils;

import org.tartarus.snowball.ext.PorterStemmer;

public class Stemmer {

	public static String stemTerm(String term) {
		PorterStemmer stemmer = new PorterStemmer();
		stemmer.setCurrent(term);
		stemmer.stem();
		return stemmer.getCurrent().toLowerCase();
	}

	public static void main(String argv[]){
		System.out.println(Stemmer.stemTerm("mechanical"));
	}
}