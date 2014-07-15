package metoClassifier;

import semeval.SemEval_Dataset;
import svm.SVM_Trainer;

public class MetoTrainer {

	public static void main(String arvc[]) throws Exception {
		
		String name ="countries.train.data";
//		String name ="companies.train.data";
		SemEval_Dataset semevaltrain = SemEval_Dataset.load(name);
		
		String model = "countries.MetoModel";
//		String model = "companies.MetoModel";
		SVM_Trainer metoModel = new SVM_Trainer(new FeatureExtractor("context"));
		
		metoModel.train(semevaltrain);
		
		metoModel.store(model);

	}
}
