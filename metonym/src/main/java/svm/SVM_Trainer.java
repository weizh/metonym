package svm;

import gnu.trove.iterator.TObjectDoubleIterator;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectDoubleHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import semeval.SemEval_Dataset;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;
import metoClassifier.FeatureExtractor;
import model.Document;
import model.Feature;
import model.FileReadDependency;
import model.NamedEntity;
import model.Sentence;

public class SVM_Trainer implements Serializable {

	svm_model model;
	svm_parameter p;
	FeatureExtractor fe;

	TObjectIntMap<String> labelTypes;
	int labelId;
	ArrayList<String> labelArray;

	public FeatureExtractor getFe() {
		return fe;
	}

	public void setFe(FeatureExtractor fe) {
		this.fe = fe;
	}

	void setDefaultSVMParam() {
		p = new svm_parameter();
		p.svm_type = svm_parameter.C_SVC;
		p.kernel_type = svm_parameter.LINEAR;
		p.degree = 3;
		p.gamma = 0.1; // 1/k
		p.coef0 = 0;
		p.nu = 0.5;
		p.cache_size = 300;
		p.C = 1;
		p.eps = 1e-3;
		p.p = 0.1;
		p.shrinking = 1;
		// p.nr_weight = 0;
		// p.weight_label = new int[3];
		// p.weight = new double[3];
		// p.probability = 0;
	}

	public SVM_Trainer() {
		setDefaultSVMParam();
	}

	public SVM_Trainer(FeatureExtractor featureExtractor) {
		setDefaultSVMParam();
		this.fe = featureExtractor;
		this.labelTypes = new TObjectIntHashMap<String>();
		this.labelArray = new ArrayList<String>();
	}

	public void train(SemEval_Dataset semevaltrain) throws Exception {

		List<String> labels = new ArrayList<String>();

		// initialize features
		fe.initialize(semevaltrain.getDocuments());

		List<ArrayList<svm_node>> featureMatrix = fe.extract(semevaltrain
				.getDocuments());

		for (Document doc : semevaltrain.getDocuments()) {
			List<Sentence> sents = doc.getParagraphs().get(0).getSentences();
			for (Sentence sent : sents) {
				List<NamedEntity> nes = sent.getNamedEntities();
				if (nes.size() != 0) {
					for (NamedEntity ne : nes) {
						String type = ne.getEntityType();
									
						 if (!type.equalsIgnoreCase("literal"))
						 type = "non-literal";
						
//						if (!type.equalsIgnoreCase("literal")
//								&& !type.equalsIgnoreCase("mixed"))
//							type = "metonymic";
						
						labels.add(type);
						if (!labelTypes.containsKey(type)) {
							labelTypes.put(type, labelId++);
							labelArray.add(type);
						}

					}
				}
			}
		}

		svm_problem problem = new svm_problem();
		problem.l = featureMatrix.size();
		problem.x = new svm_node[problem.l][];
		problem.y = new double[problem.l];

		for (int i = 0; i < problem.l; i++) {
			ArrayList<svm_node> ar = featureMatrix.get(i);
			for (int j = 0; j < ar.size(); j++)
				ar.get(j).index = j;
			problem.x[i] = ar.toArray(new svm_node[] {});
			problem.y[i] = labelTypes.get(labels.get(i));
		}

		model = svm.svm_train(problem, p);

	}

	public void test(SemEval_Dataset semevaltest) throws Exception {

		ArrayList<Document> effectiveDoc = new ArrayList<Document>();

		List<ArrayList<svm_node>> featureMatrix = fe.extract(semevaltest
				.getDocuments());

		System.out.println(featureMatrix.size());

		ArrayList<String> labels = new ArrayList<String>();

		for (Document doc : semevaltest.getDocuments()) {
			// System.out.println("<<<<<<<<<<<<<" + doc.getDocId());
			boolean hasNE = false;
			for (Sentence sent : doc.getParagraphs().get(0).getSentences()) {
				if (sent.getEntities().size() != 0) {
					String type = sent.getEntities().get(0).getEntityType();
					
					 if (!type.equalsIgnoreCase("literal"))
					 type = "non-literal";
					
//					if (!type.equalsIgnoreCase("literal")
//							&& !type.equalsIgnoreCase("mixed"))
//						type = "metonymic";
					
					labels.add(type);
					hasNE = true;
				}
			}
			if (hasNE)
				effectiveDoc.add(doc);
		}

		ArrayList<String> predictions = new ArrayList<String>();

		for (int i = 0; i < featureMatrix.size(); i++) {
			svm_node[] testfeaturenotes = featureMatrix.get(i).toArray(
					new svm_node[] {});
			double[] arg2 = new double[labelTypes.size()];
			double label = svm.svm_predict_probability(this.model,
					testfeaturenotes, arg2);
			predictions.add(labelArray.get((int) label));
		}

		System.out.println(predictions.size());
		evaluate(labels, predictions, effectiveDoc);
	}

	private void evaluate(ArrayList<String> labels,
			ArrayList<String> predictions, ArrayList<Document> docs) {

		TObjectDoubleHashMap<String> total = new TObjectDoubleHashMap<String>();
		TObjectDoubleHashMap<String> pred = new TObjectDoubleHashMap<String>();
		TObjectDoubleHashMap<String> correct = new TObjectDoubleHashMap<String>();

		double Total = labels.size(), Correct = 0;
		for (int i = 0; i < labels.size(); i++) {
			total.adjustOrPutValue(labels.get(i), 1, 1);
			pred.adjustOrPutValue(predictions.get(i), 1, 1);

			if (labels.get(i).equals(predictions.get(i))) {
				Correct++;
				correct.adjustOrPutValue(predictions.get(i), 1, 1);
			} else {
				List<Sentence> sents = docs.get(i).getParagraphs().get(0)
						.getSentences();
				for (Sentence sent : sents) {
					if (sent.getEntities() != null
							&& sent.getEntities().size() != 0) {
						System.out.println(docs.get(i).getDocId());
						System.out.println(sent.getPlainSentence());
						System.out.println("Entity is :  "
								+ sent.getNamedEntities().get(0).getWords());
						System.out.println("Gold: " + labels.get(i)
								+ " :: Predict:" + predictions.get(i));
					}

				}
				// System.out.println();
			}
		}
		System.out.println(total);

		System.out.println("TOTAL:" + Correct / Total);
		TObjectDoubleIterator<String> i = total.iterator();
		while (i.hasNext()) {
			i.advance();
			String n = i.key();
			Double t = i.value();
			Double c = correct.get(n);
			Double p = pred.get(n);
			System.out.printf(n + ": precision: %f, recall: %f, F1: %f\n", c
					/ p, c / t, 2 * c / (p + t));
		}

	}

	public static SVM_Trainer load(String file) throws IOException,
			ClassNotFoundException {
		FileInputStream in = new FileInputStream(file);
		ObjectInputStream ins = new ObjectInputStream(in);
		SVM_Trainer copy = (SVM_Trainer) ins.readObject();
		in.close();
		ins.close();
		return copy;
	}

	public void store(String file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fos);
		out.writeObject(this);
		out.close();
	}

	public void setDeps(HashMap<String, FileReadDependency> deps) {
		// TODO Auto-generated method stub
		this.fe.setDeps(deps);
	}

}
