package metoClassifier;

import semeval.SemEval_Dataset;
import svm.SVM_Trainer;

public class MetoTrainer {

	public static void main(String arvc[]) throws Exception {

//		String name = "countries.train.data";
		 String name ="companies.train.data";
		SemEval_Dataset semevaltrain = SemEval_Dataset.load(name);

		// /////////////////////////////////////////////////////////// SVM
		// /////////////////////////////////////////
		String model = "countries.MetoModel.-31tok.parse";
		// String model = "companies.MetoModel";

		SVM_Trainer metoModel = new SVM_Trainer(new FeatureExtractor(
				"context"
				,
				"determiner"
				,
				"parse"
				));
		System.out.println(model + " constructed.");

		metoModel.train(semevaltrain);
//		metoModel.test(semevaltrain);
		metoModel.store(model);
		// ///////////////////////////////////////////////////////////SVM end
		// ///////////////////////////////////////

		// ////////////////////////////////////////////////METO
		// /////////////////////////////////////////////////////
		// String model = "countries.rfMetoModel.-31tok.parse";
		//
		// RF_Trainer rfModel = new RF_Trainer(new
		// FeatureExtractor("context","parse"), new String[]{"-S","0", "-K","1",
		// "-D","3","-C"," 0.1"});
		// rfModel.train(semevaltrain);
		// rfModel.store(model);

		// ////////////////////////////////////////////////METO
		// /////////////////////////////////////////////////////
	}
}
