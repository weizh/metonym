import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import edu.smu.tspell.wordnet.*;

/**
 * Displays word forms and definitions for synsets containing the word form
 * specified on the command line. To use this application, specify the word form
 * that you wish to view synsets for, as in the following example which displays
 * all synsets containing the word form "airplane": <br>
 * java TestJAWS airplane
 */
public class TestWordNet {
	public static final String DIR_ATT="wordnet.database.dir";

	public static final String DIR_PATH="wordnet";
	
	static WordNetDatabase database;
	static{
		
		System.setProperty(DIR_ATT, DIR_PATH);
		database = WordNetDatabase.getFileInstance();
	}
	/**
	 * Main entry point. The command-line arguments are concatenated together
	 * (separated by spaces) and used as the word form to look up.
	 */
	public static void main(String[] args) {

		// Get the synsets containing the wrod form
		
		String n = "provide";
		Synset[] synsets = database.getSynsets(n); 
		ArrayList<String> syn = getSynonyms(n);
		System.out.println(syn);

		Collections.sort(syn);
			System.out.println(syn);
		// Display the word forms and definitions for synsets retrieved
		if (synsets.length > 0) {
			System.out.println("The following synsets contain '" + n + "' or a possible base form " + "of that text:");
			for (int i = 0; i < synsets.length; i++) {
				System.out.println("");
				String[] wordForms = synsets[i].getWordForms();
				for (int j = 0; j < wordForms.length; j++) {
					System.out.print((j > 0 ? ", " : "") + wordForms[j]);
				}
				System.out.println(": " + synsets[i].getDefinition());
			}
		} else {
			System.err.println("No synsets exist that contain " + "the word form");
		}
	}
	public static ArrayList<String> getSynonyms(String s){
		ArrayList<String> syn = new ArrayList<String>();
		Synset[] synsets = database.getSynsets(s); 
		if (synsets.length==0) return syn;
		for (Synset sy : synsets){
			for (String w : sy.getWordForms())
			syn.add(w);
		}
		
		return syn;
	}
}
