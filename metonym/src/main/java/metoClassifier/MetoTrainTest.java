package metoClassifier;

import java.util.HashMap;

import model.FileReadDependency;
import semeval.SemEval_Dataset;
import svm.SVM_Trainer;

public class MetoTrainTest {

	public static void main(String arvc[]) throws Exception {

		String data = "countries";
		String trainFilename = "src/main/resources/trainTestData/" + data + "/" + data + ".train.data";
		String testFilename = "src/main/resources/trainTestData/" + data + "/" + data + ".test.data";

		String trainDepName = "src/main/resources/trainTestData/" + data + "/" + data + ".train.grammannot";
		String testDepName = "src/main/resources/trainTestData/" + data + "/" + data + ".test.grammannot";
		String modelName = "src/main/resources/output/" + data + ".MetoModel.-31tok.parse";

		SemEval_Dataset semevaltrain = SemEval_Dataset.load(trainFilename);
		SemEval_Dataset semevaltest = SemEval_Dataset.load(testFilename);
		System.out.println("train and test data loaded.");

		HashMap<String, FileReadDependency> trainDeps = DependencyFileReader.readDependency(trainDepName);
		HashMap<String, FileReadDependency> testDeps = DependencyFileReader.readDependency(testDepName);
		System.out.println("train and test dependency data loaded.");

		SVM_Trainer metoModel = new SVM_Trainer(new FeatureExtractor("fileparse"));

		metoModel.train(semevaltrain, trainDeps);
		// metoModel.store(modelName);
		System.out.println(modelName + " constructed.");

		metoModel.test(semevaltest, testDeps);

	}
}
