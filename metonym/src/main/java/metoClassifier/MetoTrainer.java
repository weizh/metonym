package metoClassifier;

import java.util.HashMap;

import model.FileReadDependency;
import semeval.SemEval_Dataset;
import svm.SVM_Trainer;

public class MetoTrainer {

	public static void main(String arvc[]) throws Exception {
		String data="countries";
		String name = "src/main/resources/trainTestData/"+data+"/"+data+".train.data";
		SemEval_Dataset semevaltrain = SemEval_Dataset.load(name);
		HashMap<String, FileReadDependency> deps = DependencyFileReader.readDependency("src/main/resources/trainTestData/"+data+"/"+data+".train.grammannot");

		SVM_Trainer metoModel = new SVM_Trainer(new FeatureExtractor(
				deps,
//				"context"
//				,
//				"determiner"
//				,
				"fileparse"
				));
		
		String modelName = "src/main/resources/output/"+data+".MetoModel.-31tok.parse";
		metoModel.train(semevaltrain);
		metoModel.store(modelName);
		System.out.println(modelName + " constructed.");

	}
}
