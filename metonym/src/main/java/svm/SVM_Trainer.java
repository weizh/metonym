package svm;

import gnu.trove.map.TObjectIntMap;
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
		p.cache_size = 100;
		p.C = 1;
		p.eps = 1e-3;
		p.p = 0.1;
		p.shrinking = 1;
		p.nr_weight = 0;
		p.weight_label = new int[3];
		p.weight = new double[3];
		p.probability = 0;
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

		List<ArrayList<svm_node>> featureMatrix = fe.extract(semevaltrain.getDocuments());

		for (Document doc : semevaltrain.getDocuments()) {
			List<Sentence> sents = doc.getParagraphs().get(0).getSentences();
			for (Sentence sent : sents) {
				List<NamedEntity> nes = sent.getNamedEntities();
				if (nes.size() != 0) {
					for (NamedEntity ne : nes) {
						labels.add(ne.getEntityType());
						if (!labelTypes.containsKey(ne.getEntityType())) {
							labelTypes.put(ne.getEntityType(), labelId++);
							labelArray.add(ne.getEntityType());
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
		List<ArrayList<svm_node>> featureMatrix = fe.extract(semevaltest.getDocuments());

		System.out.println(featureMatrix.size());

		ArrayList<String> labels = new ArrayList<String>();

		for (Document doc : semevaltest.getDocuments()) {
			// System.out.println("<<<<<<<<<<<<<" + doc.getDocId());
			boolean hasNE = false;
			for (Sentence sent : doc.getParagraphs().get(0).getSentences()) {
				if (sent.getEntities().size() != 0) {
					labels.add(sent.getEntities().get(0).getEntityType());
					hasNE = true;
				}
			}
			if (hasNE)
				effectiveDoc.add(doc);
		}

		ArrayList<String> predictions = new ArrayList<String>();

		for (int i = 0; i < featureMatrix.size(); i++) {
			svm_node[] testfeaturenotes = featureMatrix.get(i).toArray(new svm_node[] {});
			double[] arg2 = new double[labelTypes.size()];
			double label = svm.svm_predict_probability(this.model, testfeaturenotes, arg2);
			predictions.add(labelArray.get((int) label));
		}

		System.out.println(predictions.size());
		 evaluate(labels, predictions, effectiveDoc);
	}

	private void evaluate(ArrayList<String> labels, ArrayList<String> predictions, ArrayList<Document> docs) {
		// TODO Auto-generated method stub
		double ltotal = 0, mtotal = 0, mixtotal = 0, lpred = 0, mpred = 0, mixpred = 0, lcorrect = 0, mcorrect = 0, mixcorrect = 0;
		double total = labels.size(), correct = 0;
		for (int i = 0; i < labels.size(); i++) {
			if (labels.get(i).equals("literal"))
				ltotal++;
			else if (labels.get(i).equals("metonymic"))
				mtotal++;
			else if (labels.get(i).equals("mixed"))
				mixtotal++;

			if (predictions.get(i).equals("literal"))
				lpred++;
			else if (predictions.get(i).equals("metonymic"))
				mpred++;
			else if (predictions.get(i).equals("mixed"))
				mixpred++;

			if (labels.get(i).equals(predictions.get(i))) {
				correct++;
				if (labels.get(i).equals("literal"))
					lcorrect++;
				else if (labels.get(i).equals("metonymic"))
					mcorrect++;
				else if (labels.get(i).equals("mixed"))
					mixcorrect++;
			} else {
				List<Sentence> sents = docs.get(i).getParagraphs().get(0).getSentences();
				for (Sentence sent : sents) {
					if (sent.getEntities() != null && sent.getEntities().size() != 0) {
						System.out.println(docs.get(i).getDocId());
						System.out.println(sent.getPlainSentence());
						System.out.println("Entity is :  " + sent.getNamedEntities().get(0).getWords());
						System.out.println(labels.get(i) + " :: " + predictions.get(i));
					}

				}
				// System.out.println();
			}
		}
		System.out.println(ltotal);
		System.out.println(mtotal);
		System.out.println(mixtotal);
		
		System.out.println("DeMix- TOTAL:" + (lcorrect + mcorrect ) / (ltotal + mtotal ));
		System.out.println("TOTAL:" + (lcorrect + mcorrect + mixcorrect) / (ltotal + mtotal + mixtotal));
		System.out.printf("Literal: precision: %f, recall: %f, F1: %f\n", lcorrect / lpred, lcorrect / ltotal, 2 * lcorrect
				/ (lpred + ltotal));
		System.out.printf("metonymic: precision: %f, recall: %f, F1: %f\n", mcorrect / mpred, mcorrect / mtotal, 2 * mcorrect
				/ (mpred + mtotal));
		System.out.printf("mixed: precision: %f, recall: %f, F1: %f\n", mixcorrect / mixpred, mixcorrect / mixtotal, 2
				* mixcorrect / (mixpred + mixtotal));

	}

	public static SVM_Trainer load(String file) throws IOException, ClassNotFoundException {
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

}
