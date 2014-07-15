package metoClassifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import semeval.Sample;

import model.Document;
import model.Feature;
import model.Global;
import model.NamedEntity;
import model.Paragraph;
import model.Sentence;

public class FeatureExtractor implements Serializable{

	String[] featureHeads = { "parse" }; // default is the parse feature only.

	public FeatureExtractor() {
	}

	public FeatureExtractor(String... heads) {
		this.featureHeads = heads;
	}

	public ArrayList<Feature> extract(Document doc) {

		if (doc == null)
			return null;

		if (doc.getParagraphs().size() == 0)
			return null;

		ArrayList<Feature> features = new ArrayList<Feature>();

		for (String featureHead : featureHeads) {
			ArrayList<Feature> feature = null;
			if (featureHead.equals("context")) {
				feature = extractContextFeature(doc);
			}
			features.addAll(feature);
		}

		return features;

	}

	private ArrayList<Feature> extractContextFeature(Document doc) {

		ArrayList<Feature> features = new ArrayList<Feature>();

		List<Sentence> sents = doc.getParagraphs().get(0).getSentences();
		for (Sentence sent : sents) {
			if (sent.getEntities().size() == 0)
				continue;
			for (NamedEntity ne : sent.getNamedEntities()) {
				int s = ne.getStart();
				int e = ne.getEnd();
				String p2 = "[N/A]", p1 = "[N/A]";
				// add -2 word
				if (s >= 2)
					p2 = sent.getWords().get(s - 2).getLemma();
				// add -1 word
				if (s >= 1)
					p1 = sent.getWords().get(s - 1).getLemma();

				features.add(new Feature(Global.P2TOK, p2));
				features.add(new Feature(Global.P1TOK, p1));
				
			}
		}

		return features;
	}

}
