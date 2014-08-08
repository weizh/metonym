package metoClassifier;

import semeval.SemEval_Dataset;
import svm.SVM_Trainer;

public class MetoTester {

	public static void main(String arvc[]) throws Exception {

//		String name = "countries.test.data";
		String name ="companies.test.data";
		SemEval_Dataset semevaltest = SemEval_Dataset.load(name);
		System.out.println("test data loaded.");
//		semevaltest = SemEval_Dataset.CorrectTestSet(semevaltest);
//		if (true) return;
		// ///////////////////////// SVM ///////////////////////////////////
		String model = "countries.MetoModel.-31tok.parse";
		// String model = "companies.MetoModel";
		SVM_Trainer metoModel = SVM_Trainer.load(model);
		System.out.println(model + " loaded.");
		metoModel.test(semevaltest);
		// ///////////////////////// SVM ///////////////////////////////////

		// ///////////////////////// RF ///////////////////////////////////
//		 String model = "countries.rfMetoModel.-31tok.parse";
//		 // String model = "companies.MetoModel";
//		 RF_Trainer rfmetoModel = RF_Trainer.load(model);
//		 System.out.println(model+" loaded.");
//		 rfmetoModel.test(semevaltest);
		// ///////////////////////// RF ///////////////////////////////////
	}
}
