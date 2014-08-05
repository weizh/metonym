package utils;

import java.util.HashSet;

public class StringUtils {

	static HashSet<String> determiner;
	static{
		determiner = new HashSet<String>();
		determiner.add("a");
		determiner.add("the");
		determiner.add("an");

	}
	public static boolean isDeterminer(String s){
		s = s.trim().toLowerCase();
		return determiner.contains(s);
	}
}

