package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.IndexWordSet;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;

public class JWNLUtils {
	static Dictionary wn;
	static {
		try {
			JWNL.initialize(new FileInputStream("src/main/resources/wordnet/jwnl-properties.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JWNLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wn = Dictionary.getInstance();
	}
	public static String getWordNetSenseHead( model.Word w) throws JWNLException{
		String pos = w.getPOS();
		
		POS p = null;
		if (pos.startsWith("NN"))
			p = POS.NOUN;
		else if (pos.startsWith("VB"))
			p = POS.VERB;
		else if (pos.startsWith("JJ"))
			p =POS.ADJECTIVE;
		else if (pos.startsWith("RB"))
			p = POS.ADVERB;
		else 
			return w.getWordForm();
		
		IndexWord a = wn.lookupIndexWord(p, w.getLemma());
		if (a==null) return w.getLemma();
		Synset[] as = a.getSenses();
		for (Synset s : as){
			return s.getKey().toString();
		}
		return w.getLemma();
	}

	public static String getWordNetSenseHead(String w){
		
		IndexWordSet a = null;
		try {
			a = wn.lookupAllIndexWords(w);
		} catch (JWNLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (a==null) return w;
		for ( IndexWord ws : a.getIndexWordArray()){
			Synset[] as = null;
			try {
				as = ws.getSenses();
			} catch (JWNLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Synset s : as){
				return s.getKey().toString();
			}
		}
		return w;
	}
	
	public static void main(String argvp[]) throws JWNLException {
		String lemma = "234";
		
		model.Word w = new model.Word();
		w.setWord(lemma);
		w.setLemma(lemma);
		w.setPOS("DT");
		System.out.println(getWordNetSenseHead(w));
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
//		IndexWord a = wn.lookupIndexWord(POS.VERB, "supply");
//
//		Synset[] as = a.getSenses();
//		for (Synset s : as){
//			System.out.println("\n"+s.getKey());
//			Word[] words = s.getWords();
//			for (Word word : words){
//				System.out.println(word.getLemma());
//			}
//		}
	}
}
