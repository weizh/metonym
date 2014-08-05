package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;

public class JWNLUtils {
	static Dictionary wn;
	static {
		try {
			JWNL.initialize(new FileInputStream("wordnet/jwnl-properties.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JWNLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wn = Dictionary.getInstance();
	}

	public static void main(String argvp[]) throws JWNLException {
		String lemma = "speak";
		/**
		 * provide 2332196 1185006 1065210 2381380 2727313 2224224 407888
		 * 
		 * supply 2332196 2484912 1185006 1029183 13799556 13584725 1059124
		 * 
		 * declare 1012145 967903 822462 824028 2449420 2305510 1012879 979721
		 * announce 976399 967903 977459 976205
		 * 
		 * speak 944022 964479 965602 991233 2140072
		 * 
		 * talk 964479 944022 965602 954873 939238 832422
		 * 
		 * 
		 */
		IndexWord a = wn.lookupIndexWord(POS.VERB, "talk");

		Synset[] as = a.getSenses();
		for (Synset s : as){
			System.out.println("\n"+s.getKey());
			Word[] words = s.getWords();
			for (Word word : words){
				System.out.println(word.getLemma());
			}
		}

	}
}
