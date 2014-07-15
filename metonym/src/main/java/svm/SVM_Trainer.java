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
import java.util.List;

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
	TObjectIntMap<String> dic;
	TObjectIntMap<String> labelTypes;
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
		p.kernel_type = svm_parameter.RBF;
		p.degree = 3;
		p.gamma = 0.5; // 1/k
		p.coef0 = 0;
		p.nu = 0.5;
		p.cache_size = 40;
		p.C = 1;
		p.eps = 1e-3;
		p.p = 0.1;
		p.shrinking = 1;
		p.nr_weight = 0;
		p.weight_label = new int[0];
		p.weight = new double[3];
		p.probability = 1;
	}

	public SVM_Trainer() {
		setDefaultSVMParam();
	}

	public SVM_Trainer(FeatureExtractor featureExtractor) {
		setDefaultSVMParam();
		this.fe = featureExtractor;
	}

	public void train(SemEval_Dataset semevaltrain) {

		List<ArrayList<Feature>> featureMatrix = new ArrayList<ArrayList<Feature>>();
		List<String> labels = new ArrayList<String>();
		for (Document doc : semevaltrain.getDocuments()) {
			List<Sentence> sents = doc.getParagraphs().get(0).getSentences();
			for (Sentence sent : sents) {
				List<NamedEntity> nes = sent.getNamedEntities();
				if (nes.size() != 0) {
					for (NamedEntity ne : nes) {
						labels.add(ne.getEntityType());
					}
				}
			}
			ArrayList<Feature> docFeature = fe.extract(doc);
			featureMatrix.add(docFeature);
		}

		dic = new TObjectIntHashMap<String>();
		labelTypes = new TObjectIntHashMap<String>();
		labelArray = new ArrayList<String>();

		int dicId = 0;
		int typeId = 0;

		for (ArrayList<Feature> featureArray : featureMatrix) {
			for (Feature f : featureArray) {
				String s = f.getStringWithHeader();
				if (!dic.containsKey(s))
					dic.put(s, dicId++);
			}
		}

		for (String label : labels) {
			if (!labelTypes.containsKey(label)) {
				labelTypes.put(label, typeId++);
				labelArray.add(label);
			}
		}

		svm_problem problem = new svm_problem();
		problem.l = featureMatrix.size();
		problem.x = new svm_node[problem.l][];
		problem.y = new double[problem.l];

		for (int i = 0; i < problem.l; i++) {

			ArrayList<Feature> features = featureMatrix.get(i);
			problem.x[i] = oneHotCoding(features, dic);
			problem.y[i] = labelTypes.get(labels.get(i));
		}

		model = svm.svm_train(problem, p);

	}

	public void test(SemEval_Dataset semevaltest) {

		List<ArrayList<Feature>> featureMatrix = new ArrayList<ArrayList<Feature>>();
		
		ArrayList<String> labels = new ArrayList<String>();
		
		for (Document doc : semevaltest.getDocuments()) {
			// System.out.println("<<<<<<<<<<<<<" + doc.getDocId());
			for (Sentence sent : doc.getParagraphs().get(0).getSentences()){
				if (sent.getEntities().size()!=0 )
					labels.add(sent.getEntities().get(0).getEntityType());
			}
			ArrayList<Feature> docFeature = fe.extract(doc);
			featureMatrix.add(docFeature);
		}

		ArrayList<String> predictions = new ArrayList<String>();
		
		for (int i = 0; i < featureMatrix.size(); i++) {
			ArrayList<Feature> features = featureMatrix.get(i);
			svm_node[] testfeaturenotes = oneHotCoding(features, dic);
			double[] arg2 = new double[labelTypes.size()];
			double label = svm.svm_predict_probability(this.model, testfeaturenotes, arg2);
			predictions.add(labelArray.get((int) label));
		}
		
		evaluate(labels,predictions);
	}

	private void evaluate(ArrayList<String> labels, ArrayList<String> predictions) {
		// TODO Auto-generated method stub
		double correct = 0;
		double total = labels.size();
		for (int i=0; i < labels.size(); i++){
			if (labels.get(i).equals(predictions.get(i)))
				correct++;
		}
		System.out.println(correct/total);
	}

	private svm_node[] oneHotCoding(ArrayList<Feature> features, TObjectIntMap<String> dic) {

		svm_node[] nodes = new svm_node[features.size() * dic.size()];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new svm_node();
			nodes[i].index = i;
			nodes[i].value = 0;
		}

		int featureindex = 0;
		for (Feature f : features) {
			int index = dic.get(f.getStringWithHeader());
			nodes[featureindex * dic.size() + index].value = 1;
			featureindex++;
		}

		return nodes;
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
