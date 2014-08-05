import java.io.IOException;
import java.util.HashSet;

import model.Document;
import model.Sentence;

import semeval.SemEval_Dataset;


public class PrintLocations {
public static void main(String argbp[]) throws ClassNotFoundException, IOException{
//	String name ="countries.train.data";
////	String name ="companies.train.data";
//	SemEval_Dataset semevaltrain = SemEval_Dataset.load(name);
//	System.out.println("Training data loaded.");
	
	String name ="countries.test.data";
//	String name ="companies.test.data";
	SemEval_Dataset semevaltest = SemEval_Dataset.load(name);
	System.out.println("test data loaded.");
	
	HashSet<String> words = new HashSet<String>();
//	for (Document doc : semevaltrain.getDocuments()){
//		for (Sentence sent : doc.getParagraphs().get(0).getSentences()){
//			if (sent.getEntities().size()==0)
//				continue;
//			words.add(sent.getEntities().get(0).getWords());
//		}
//	}
	for (Document doc : semevaltest.getDocuments()){
		System.out.println(doc.getDocId());
		for (Sentence sent : doc.getParagraphs().get(0).getSentences()){
			if (sent.getEntities().size()==0)
				continue;
			words.add(sent.getEntities().get(0).getWords());
		}
	}
	
	for (String s : words)
		System.out.println(s);
}
}
