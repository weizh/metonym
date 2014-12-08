package metoClassifier;

import java.util.HashMap;

import model.FileReadDependency;
import semeval.SemEval_Dataset;
import svm.SVM_Trainer;

public class MetoTester {

	public static void main(String arvc[]) throws Exception {

//		String data = "countries";
		String data="companies";

		String name = "src/main/resources/"+data+".test.data";
		// String name ="src/main/resources/companies.test.data";
		SemEval_Dataset semevaltest = SemEval_Dataset.load(name);
		System.out.println("test data loaded.");
		// semevaltest = SemEval_Dataset.CorrectTestSet(semevaltest);
		// if (true) return;
		// ///////////////////////// SVM ///////////////////////////////////
		String model = data+".MetoModel.-31tok.parse";
		// String model = "companies.MetoModel";
		SVM_Trainer metoModel = SVM_Trainer.load(model);
		System.out.println(model + " loaded.");
		HashMap<String, FileReadDependency> deps = GrammerReader
				.readDependency("src/main/resources/"+data+".test.grammannot");
		metoModel.setDeps(deps);
		metoModel.test(semevaltest);
		// ///////////////////////// SVM ///////////////////////////////////

		// ///////////////////////// RF ////////////////////////////////// /
		// String model = "countries.rfMetoModel.-31tok.parse";
		// // String model = "companies.MetoModel";
		// RF_Trainer rfmetoModel = RF_Trainer.load(model);
		// System.out.println(model+" loaded.");
		// rfmetoModel.test(semevaltest);
		// ///////////////////////// RF ///////////////////////////////////
	}
}
