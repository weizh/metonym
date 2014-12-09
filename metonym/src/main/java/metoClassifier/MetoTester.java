package metoClassifier;

import java.util.HashMap;

import model.FileReadDependency;
import semeval.SemEval_Dataset;
import svm.SVM_Trainer;

public class MetoTester {

	public static void main(String arvc[]) throws Exception {

		String data = "countries";

		String name = "src/main/resources/trainTestData/"+data+"/"+data+".test.data";
		SemEval_Dataset semevaltest = SemEval_Dataset.load(name);
		
		System.out.println("test data loaded.");
		
		String model = "src/main/resources/output/"+data+".MetoModel.-31tok.parse";
		SVM_Trainer metoModel = SVM_Trainer.load(model);
		System.out.println(model + " loaded.");
		HashMap<String, FileReadDependency> deps = DependencyFileReader
				.readDependency("src/main/resources/trainTestData/"+data+"/"+data+".test.grammannot");
		metoModel.test(semevaltest,deps);
	}
}
