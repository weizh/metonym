package utils;

import java.util.HashSet;

public class StringUtils {

	static HashSet<String> determiner;
	static HashSet<String> demonstrative;
	static HashSet<String> Interrogative;
	static HashSet<String> prepositions;
	static {
		determiner = new HashSet<String>();
		determiner.add("a");
		determiner.add("the");
		determiner.add("an");
		determiner.add("some");

		demonstrative = new HashSet<String>();
		demonstrative.add("this");
		demonstrative.add("that");
		demonstrative.add("these");
		demonstrative.add("those");

		Interrogative = new HashSet<String>();
		Interrogative.add("what");
		Interrogative.add("when");
		Interrogative.add("which");
		Interrogative.add("whatever");
		Interrogative.add("how");
		Interrogative.add("who");
		Interrogative.add("whose");
		Interrogative.add("whom");
		Interrogative.add("whether");
		
		prepositions = new HashSet<String>();
		prepositions.add("above");
		prepositions.add("across");
		prepositions.add("along");
		prepositions.add("amid");
		prepositions.add("around");
		prepositions.add("at");
		prepositions.add("into");
		prepositions.add("in");
		prepositions.add("inside");
		prepositions.add("near");
		prepositions.add("of");
		prepositions.add("off");
		prepositions.add("outside");
		prepositions.add("to");
		prepositions.add("underneath");
		prepositions.add("within");
		prepositions.add("toward");
	}

	public static boolean isDeterminer(String s) {
		s = s.trim().toLowerCase();
		return determiner.contains(s);
	}
	
	public static boolean isDemonstrative(String s) {
		s = s.trim().toLowerCase();
		return demonstrative.contains(s);
	}
	
	public static boolean isInterrogative(String s) {
		s = s.trim().toLowerCase();
		return Interrogative.contains(s);
	}
	
	public static boolean isNumber(String s) {
		s = s.trim().toLowerCase();
		for ( char c : s.toCharArray()){
			if (Character.isDigit(c)==false)
				return false;
		}
		return true;
	}

	public static boolean isApostrophe(String word) {
		word = word.trim().toLowerCase();
		if (word.equals("'s") || word.equals("' s") || word.endsWith("s'"))
			return true;
		return false;
	}

	public static boolean isPreposition(String word) {
		word = word.trim().toLowerCase();
		return prepositions.contains(word);
	}
}
