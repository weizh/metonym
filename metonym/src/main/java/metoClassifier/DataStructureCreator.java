package metoClassifier;

import java.io.IOException;

import semeval.SemEval_Dataset;
import semeval.XMLReader;

public class DataStructureCreator {

	public static void main(String argb[]) throws Exception {
		// create training data, and store the object
//		SemEval_Dataset semevaltrain = SemEval_Dataset
//				.createSemEvalDataset(XMLReader.parse("src/main/resources/countries.train"));
//		semevaltrain.store("countries.train.data");
//		
//		// create test data, and store the object
//		SemEval_Dataset semevaltest = SemEval_Dataset.createSemEvalDataset(XMLReader.parse("src/main/resources/countries.test"));
//		semevaltest.store("countries.test.data");
		
		
		SemEval_Dataset semevalorgtrain = SemEval_Dataset
				.createSemEvalDataset(XMLReader.parse("src/main/resources/companies.train"));
		semevalorgtrain.store("companies.train.data");
		
		// create test data, and store the object
		SemEval_Dataset semevalorgtest = SemEval_Dataset.createSemEvalDataset(XMLReader.parse("src/main/resources/companies.test"));
		semevalorgtest.store("companies.test.data");
	}
}
