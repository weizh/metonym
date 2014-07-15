package metoClassifier;

import semeval.SemEval_Dataset;
import svm.SVM_Trainer;

public class MetoTester {

	public static void main(String arvc[]) throws Exception {
		
		String name ="countries.test.data";
//		String name ="companies.test.data";
		SemEval_Dataset semevaltest = SemEval_Dataset.load(name);

		String model = "countries.MetoModel";
//		String model = "companies.MetoModel";
		SVM_Trainer metoModel = SVM_Trainer.load(model);
		
		metoModel.test(semevaltest);
		
	}
}
