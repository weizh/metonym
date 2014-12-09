package metoClassifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.BrownCluster_Str;
import utils.Embeddings_Vec;
import utils.JWNLUtils;
import utils.Levin;
import utils.StringUtils;
import libsvm.svm_node;
import model.Dependency;
import model.Document;
import model.NamedEntity;
import model.NominalFeature;
import model.Sentence;
import model.Word;
import model.FileReadDependency;
import net.didion.jwnl.JWNLException;

import java.util.HashMap;

public class FeatureExtractor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String[] featureHeads; // default is the parse feature only.
	private final HashMap<String, FileReadDependency> deps;
	
	int fValLen;
	NominalFeature feature;

	public int getFValLen() {
		return this.fValLen;
	}

	public FeatureExtractor(HashMap<String, FileReadDependency> deps,
			String... heads) {
		this.deps = deps;
		this.featureHeads = heads;
		feature = new NominalFeature("Feature: parse");

		System.err
				.println("Feautre Value Length is set to 0, "
						+ "then it's not checked for consistancy with feature length generated by feature extractor.");
	}

	public void initialize(ArrayList<Document> docs) throws JWNLException {

		if (docs == null)
			return;
		if (docs.size() == 0)
			return;

		for (String featureHead : featureHeads) {
			if (featureHead.equals("context")) {
				initContextFeature(docs);
			}
			if (featureHead.equals("determiner")) {
				initDeterminerFeature(docs);
			}
			if (featureHead.equals("parse")) {
				initParseFeature(docs);
			}
			if (featureHead.equals("fileparse")) {
				initFileParseFeature(docs);
			}
		}
	}

	public ArrayList<ArrayList<svm_node>> extract(ArrayList<Document> docs)
			throws Exception {

		if (docs == null)
			return null;
		if (docs.size() == 0)
			return null;

		ArrayList<ArrayList<svm_node>> features = null;

		for (String featureHead : featureHeads) {
			ArrayList<ArrayList<svm_node>> feature = null;
			if (featureHead.equals("context")) {
				feature = extractContextFeature(docs);
			}
			if (featureHead.equals("determiner")) {
				feature = extractDeterminerFeature(docs);
			}
			if (featureHead.equals("fileparse")) {
				feature = extractFileReadParseFeature(docs);
			}
			if (featureHead.equals("parse")) {
				feature = extractParseFeature(docs);
			}
			if (features == null)
				features = feature;
			else {
				for (int i = 0; i < feature.size(); i++) {
					features.get(i).addAll(feature.get(i));
				}
			}
		}
		return features;
	}

	private void initDeterminerFeature(ArrayList<Document> docs) {

	}

	private ArrayList<ArrayList<svm_node>> extractDeterminerFeature(
			ArrayList<Document> docs) {

		ArrayList<ArrayList<svm_node>> features = new ArrayList<ArrayList<svm_node>>();
		for (Document doc : docs) {
			List<Sentence> sents = doc.getParagraphs().get(0).getSentences();
			for (Sentence sent : sents) {
				if (sent.getEntities().size() != 0) {
					NamedEntity ne = sent.getNamedEntities().get(0);
					int s = ne.getStart();
					int e = ne.getEnd();

					ArrayList<svm_node> nodes = new ArrayList<svm_node>();

					// Determiner feature
					boolean determiner = false;
					if (s >= 2)
						determiner = utils.StringUtils.isDeterminer(sent
								.getWords().get(s - 2).getWord());
					if (s >= 1)
						determiner = utils.StringUtils.isDeterminer(sent
								.getWords().get(s - 1).getWord());
					svm_node node = new svm_node();
					node.index = 0;
					node.value = determiner ? 1 : 0;
					// nodes.add(new svm_node());

					// demonstrative feature
					boolean demonstrative = false;
					if (s >= 2)
						demonstrative = utils.StringUtils.isDemonstrative(sent
								.getWords().get(s - 2).getWord());
					if (s >= 1)
						demonstrative = utils.StringUtils.isDemonstrative(sent
								.getWords().get(s - 1).getWord());
					node = new svm_node();
					node.index = 0;
					node.value = demonstrative ? 1 : 0;
					// nodes.add(new svm_node());

					// Interrogative feature
					boolean Interrogative = false;
					if (s >= 2)
						Interrogative = utils.StringUtils.isInterrogative(sent
								.getWords().get(s - 2).getWord());
					if (s >= 1)
						Interrogative = utils.StringUtils.isInterrogative(sent
								.getWords().get(s - 1).getWord());
					node = new svm_node();
					node.index = 0;
					node.value = Interrogative ? 1 : 0;
					// nodes.add(new svm_node());

					// Preposition feature
					boolean prep = false;
					// if (s >= 2)
					// prep = utils.StringUtils.isPreposition(sent.getWords()
					// .get(s - 2).getWord());
					if (s >= 1)
						prep = utils.StringUtils.isPreposition(sent.getWords()
								.get(s - 1).getWord());
					node = new svm_node();
					node.index = 0;
					node.value = prep ? 1 : 0;
					nodes.add(new svm_node());

					// apostrophe feature
					boolean apos = false;
					if (e < sent.getWords().size() - 1)
						apos = utils.StringUtils.isApostrophe(sent.getWords()
								.get(e + 1).getWord());
					node = new svm_node();
					node.index = 0;
					node.value = apos ? 1 : 0;
					// nodes.add(new svm_node());

					// first word feature
					boolean firstword = false;
					if (s == 0)
						firstword = true;
					node = new svm_node();
					node.index = 0;
					node.value = firstword ? 1 : 0;
					// nodes.add(new svm_node());

					// add all to fvector.
					features.add(nodes);
				}
			}
		}
		return features;
	}

	private void initParseFeature(ArrayList<Document> docs) {

		for (Document doc : docs) {

			ArrayList<String> t = new ArrayList<String>();
			ArrayList<String> trel = new ArrayList<String>();
			ArrayList<String> tpos = new ArrayList<String>();
			ArrayList<String> wnt = new ArrayList<String>();
			ArrayList<String> lvnt = new ArrayList<String>();
			ArrayList<String> brt = new ArrayList<String>();

			ArrayList<String> s = new ArrayList<String>();
			ArrayList<String> srel = new ArrayList<String>();
			ArrayList<String> spos = new ArrayList<String>();
			ArrayList<String> wns = new ArrayList<String>();
			ArrayList<String> lvns = new ArrayList<String>();
			ArrayList<String> brs = new ArrayList<String>();

			for (Sentence sent : doc.getParagraphs().get(0).getSentences()) {
				if (sent.getEntities().size() == 0)
					continue;
				NamedEntity ne = sent.getNamedEntities().get(0);
				Word head = ne.getLastWord();

				for (Dependency dep : sent.getP()) {
					Word src = dep.getSource();
					Word tgt = dep.getTarget();

					String rel = dep.getRelation();
					if (src.equals(head)) {
						trel.add("tgt_" + rel);
						t.add("tgt_" + tgt.getLemma());
						tpos.add("tgt_" + tgt.getPOS());
						try {
							wnt.add("tgt_" + JWNLUtils.getWordNetSenseHead(tgt));
						} catch (JWNLException e) {
							e.printStackTrace();
						}
						lvnt.addAll(Levin.getLevinCategoryStrings(tgt
								.getLemma()));
						brt.addAll(BrownCluster_Str.getBrownClusterIdArray(
								tgt.getLemma(), 3));

					} else if (tgt.equals(head)) {
						srel.add("src_" + rel);
						s.add("src_" + src.getLemma());
						spos.add("src_" + src.getPOS());
						try {
							wns.add("src_" + JWNLUtils.getWordNetSenseHead(src));
						} catch (JWNLException e) {
							e.printStackTrace();
						}
						lvns.addAll(Levin.getLevinCategoryStrings(src
								.getLemma()));
						brs.addAll(BrownCluster_Str.getBrownClusterIdArray(
								src.getLemma(), 3));
					}
					feature.addFeatureValue(t);
					feature.addFeatureValue(trel);
					feature.addFeatureValue(tpos);
					feature.addFeatureValue(wnt);
					feature.addFeatureValue(lvnt);
					feature.addFeatureValue(brt);

					feature.addFeatureValue(s);
					feature.addFeatureValue(srel);
					feature.addFeatureValue(spos);
					feature.addFeatureValue(wns);
					feature.addFeatureValue(lvns);
					feature.addFeatureValue(brs);

				}

			}
		}
	}

	private ArrayList<ArrayList<svm_node>> extractParseFeature(
			ArrayList<Document> docs) throws Exception {

		ArrayList<ArrayList<svm_node>> features = new ArrayList<ArrayList<svm_node>>();

		for (Document doc : docs) {
			ArrayList<svm_node> feat = new ArrayList<svm_node>();
			ArrayList<String> allstringfeats = new ArrayList<String>();
			ArrayList<String> t = new ArrayList<String>();
			ArrayList<String> trel = new ArrayList<String>();
			ArrayList<String> tpos = new ArrayList<String>();
			ArrayList<String> wnt = new ArrayList<String>();
			ArrayList<String> s = new ArrayList<String>();
			ArrayList<String> srel = new ArrayList<String>();
			ArrayList<String> spos = new ArrayList<String>();
			ArrayList<String> wns = new ArrayList<String>();

			for (Sentence sent : doc.getParagraphs().get(0).getSentences()) {
				if (sent.getEntities().size() == 0)
					continue;
				NamedEntity ne = sent.getNamedEntities().get(0);
				Word head = ne.getLastWord();
				String[] words = ne.getWords().split(" ");
				int length = words.length;
				System.out.println(length);
				System.out.println(sent.getPlainSentence());
				System.out.println("--------- word:	" + head.getLemma());
				for (Dependency dep : sent.getP()) {
					Word src = dep.getSource();
					Word tgt = dep.getTarget();
					String rel = dep.getRelation();
					if (src.equals(head)) {
						if (length > 2) {
							if (tgt.getLemma().equalsIgnoreCase(words[0]))
								continue;
							if (tgt.getLemma().equalsIgnoreCase(words[1]))
								continue;
						}
						if (length > 1) {
							if (tgt.getLemma().equalsIgnoreCase(words[0]))
								continue;
						}
						trel.add("tgt_" + rel);
						t.add("tgt_" + tgt.getLemma());
						tpos.add("tgt_" + tgt.getPOS());
						try {
							wnt.add("tgt_" + JWNLUtils.getWordNetSenseHead(tgt));
						} catch (JWNLException e) {
							e.printStackTrace();
						}

						System.out.println("--target:");
						System.out.println(t);
						System.out.println(tpos);
						System.out.println(trel);
					} else if (tgt.equals(head)) {
						if (length > 2) {
							if (src.getLemma().equalsIgnoreCase(words[0]))
								continue;
							if (src.getLemma().equalsIgnoreCase(words[1]))
								continue;
						}
						if (length > 1) {
							if (src.getLemma().equalsIgnoreCase(words[0]))
								continue;
						}
						srel.add("src_" + rel);
						s.add("src_" + src.getLemma());
						spos.add("src_" + src.getPOS());
						try {
							wns.add("src_" + JWNLUtils.getWordNetSenseHead(src));
						} catch (JWNLException e) {
							e.printStackTrace();
						}

						System.out.println("--source:");
						System.out.println(s);
						System.out.println(spos);
						System.out.println(srel);
					}

				}
				// use wordnet OR
				// use one hot coding (Dont forget to change the t and s
				// generation codes
				// feat.addAll(feature.getSVMNodeVector(new String[] { s }));
				// feat.addAll(feature.getSVMNodeVector(new String[] { tpos }));
				// feat.addAll(feature.getSVMNodeVector(new String[] { spos }));

				// wordnet feature
				// feat.addAll(feature.getSVMNodeVector(new String[] { wnt,
				// wns }));
				// feat.addAll(feature.getSVMNodeVector(new String[] { wns
				// }));

				// use brown cluster
				// feat.addAll(BrownCluster_Str.getBrownClusterIdSvmNode(t, 3));
				// feat.addAll(BrownCluster_Str.getBrownClusterIdSvmNode(s, 3));

				// // use word embeddings
				// feat.addAll(Embeddings_Vec.getEmbeddingSVMNodes(s));
				// feat.addAll(Embeddings_Vec.getEmbeddingSVMNodes(t));

				// levin category
				// feat.addAll(Levin.getGetLevinSvmNodes(s));
				// feat.addAll(Levin.getGetLevinSvmNodes(t));

				// google ngram
				// feat.addAll(GoogleNgram.getGNgramSvmNodeVector(s));
				// feat.addAll(GoogleNgram.getGNgramSvmNodeVector(t));

				for (int i = 0; i < trel.size(); i++) {
					if (tpos.get(i).startsWith("DT"))
						allstringfeats.add(t.get(i));
					if (tpos.get(i).startsWith("VB"))
						allstringfeats.addAll(Levin.getLevinCategoryStrings(t
								.get(i)));
					if (tpos.get(i).startsWith("JJ"))
						allstringfeats.addAll(BrownCluster_Str
								.getBrownClusterIdArray(t.get(i), 3));

				}
				for (int i = 0; i < srel.size(); i++) {
					if (spos.get(i).startsWith("DT"))
						allstringfeats.add(s.get(i));
					if (spos.get(i).startsWith("VB"))
						allstringfeats.addAll(Levin.getLevinCategoryStrings(s
								.get(i)));
					if (spos.get(i).startsWith("JJ"))
						allstringfeats.addAll(BrownCluster_Str
								.getBrownClusterIdArray(s.get(i), 3));

				}

				allstringfeats.addAll(trel);
				allstringfeats.addAll(srel);

				feat.addAll(feature.getSVMNodeVector(allstringfeats
						.toArray(new String[] {})));
				// feat.addAll(feature.getSVMNodeVector(trel));
				// feat.addAll(feature.getSVMNodeVector(srel));

				// feat.addAll(ftPOS.getSVMNodeVector(new String[] { tpos }));
				// feat.addAll(fsPOS.getSVMNodeVector(new String[] { spos }));

				features.add(feat);
			}
		}
		return features;
	}

	private void initContextFeature(ArrayList<Document> docs)
			throws JWNLException {

		for (Document doc : docs) {
			List<Sentence> sents = doc.getParagraphs().get(0).getSentences();
			for (Sentence sent : sents) {
				if (sent.getEntities().size() != 0) {
					NamedEntity ne = sent.getNamedEntities().get(0);
					int s = ne.getStart();
					int e = ne.getEnd();
					String p3 = "[N/A]", p2 = "[N/A]", p1 = "[N/A]", n1 = "[N/A]", n2 = "[N/A]", n3 = "[N/A]";

					if (s >= 3) {
						p3 = sent.getWords().get(s - 3).getLemma();
						// p3 =
						// JWNLUtils.getWordNetSenseHead(sent.getWords().get(
						// s - 3));
					}
					if (s >= 2) {
						p2 = sent.getWords().get(s - 2).getLemma();
						// p2 =
						// JWNLUtils.getWordNetSenseHead(sent.getWords().get(
						// s - 2));
					}
					if (s >= 1) {
						p1 = sent.getWords().get(s - 1).getLemma();
						// p1 =
						// JWNLUtils.getWordNetSenseHead(sent.getWords().get(
						// s - 1));

					}
					if (e < sent.getWords().size() - 1) {
						n1 = sent.getWords().get(e + 1).getLemma();
						// n1 =
						// JWNLUtils.getWordNetSenseHead(sent.getWords().get(
						// e + 1));
					}
					if (e < sent.getWords().size() - 2) {
						n2 = sent.getWords().get(e + 2).getLemma();
						// n2 =
						// JWNLUtils.getWordNetSenseHead(sent.getWords().get(
						// e + 2));
					}
					if (e < sent.getWords().size() - 3) {
						n3 = sent.getWords().get(e + 3).getLemma();
						// n3 =
						// JWNLUtils.getWordNetSenseHead(sent.getWords().get(
						// e + 3));
					}
						feature.addFeatureValue(p1);
				}
			}
		}
	}

	private ArrayList<ArrayList<svm_node>> extractContextFeature(
			ArrayList<Document> docs) throws Exception {

		ArrayList<ArrayList<svm_node>> features = new ArrayList<ArrayList<svm_node>>();

		for (Document doc : docs) {
			List<Sentence> sents = doc.getParagraphs().get(0).getSentences();
			for (Sentence sent : sents) {
				if (sent.getEntities().size() != 0) {
					NamedEntity ne = sent.getNamedEntities().get(0);
					int s = ne.getStart();
					int e = ne.getEnd();
					String p4 = "[N/A]", p3 = "[N/A]", p2 = "[N/A]", p1 = "[N/A]", n1 = "[N/A]", n2 = "[N/A]", n3 = "[N/A]", n4 = "[N/A]";

					if (s >= 4)
						p4 = sent.getWords().get(s - 4).getLemma()
								.toLowerCase();
					if (s >= 3) {
						p3 = sent.getWords().get(s - 3).getLemma()
								.toLowerCase();
						// p3 =
						// JWNLUtils.getWordNetSenseHead(sent.getWords().get(
						// s - 3));
					}
					if (s >= 2) {
						p2 = sent.getWords().get(s - 2).getLemma()
								.toLowerCase();
						// p2 =
						// JWNLUtils.getWordNetSenseHead(sent.getWords().get(
						// s - 2));
					}
					if (s >= 1) {
						p1 = sent.getWords().get(s - 1).getLemma()
								.toLowerCase();
						// p1 =
						// JWNLUtils.getWordNetSenseHead(sent.getWords().get(
						// s - 1));
					}
					if (e < sent.getWords().size() - 1) {
						n1 = sent.getWords().get(e + 1).getLemma()
								.toLowerCase();
						// n1 =
						// JWNLUtils.getWordNetSenseHead(sent.getWords().get(
						// e + 1));

					}
					if (e < sent.getWords().size() - 2) {
						n2 = sent.getWords().get(e + 2).getLemma()
								.toLowerCase();
						// n2 =
						// JWNLUtils.getWordNetSenseHead(sent.getWords().get(
						// e + 2));
					}
					if (e < sent.getWords().size() - 3) {
						n3 = sent.getWords().get(e + 3).getLemma()
								.toLowerCase();
						// n3 =
						// JWNLUtils.getWordNetSenseHead(sent.getWords().get(
						// e + 3));
					}
					if (e < sent.getWords().size() - 4) {
						n4 = sent.getWords().get(e + 4).getLemma()
								.toLowerCase();
					}

					ArrayList<svm_node> feat = new ArrayList<svm_node>();

					// One Hot Coding
					// String[] keys = new String[] { p1, n1};
						feat.addAll(feature.getSVMNodeVector(p1));

					// word embeddings --- ABANDONED
					// feat.addAll(Embeddings_Vec.getEmbeddingSVMNodes(ap1));
					// feat.addAll(Embeddings_Vec.getEmbeddingSVMNodes(an1));

					// // bc:
					// feat.addAll(BrownCluster_Str.getBrownClusterIdSvmNode(p3,
					// 3));
					// feat.addAll(BrownCluster_Str.getBrownClusterIdSvmNode(p2,
					// 3));
					// feat.addAll(BrownCluster_Str.getBrownClusterIdSvmNode(ap1,
					// 3));
					// feat.addAll(BrownCluster_Str.getBrownClusterIdSvmNode(an1,
					// 3));
					// feat.addAll(BrownCluster_Str.getBrownClusterIdSvmNode(n2,
					// 3));
					// feat.addAll(BrownCluster_Str.getBrownClusterIdSvmNode(n3,
					// 3));

					// levin:
					// feat.addAll(Levin.getGetLevinSvmNodes(p2));
					// feat.addAll(Levin.getGetLevinSvmNodes(n2));

					// word net
					// in the context.

					features.add(feat);
				}
			}// end sent loop
		}// end doc loop

		return features;
	}

	private void initFileParseFeature(ArrayList<Document> docs) {
		for (Document doc : docs) {
			FileReadDependency d = deps.get(doc.getDocId());

			String[] toks = d.getDep().split(" ");

			String depword = toks.length > 1 ? toks[toks.length - 1] : toks[0];
			depword = depword.toLowerCase();
			feature.addFeatureValue("rel_" + d.getRel());
			feature.addFeatureValue("dep_" + depword);
			feature.addFeatureValue("bc_"
					+ BrownCluster_Str.getBrownClusterId(depword, 4));
			feature.addFeatureValue("wn_"
					+ JWNLUtils.getWordNetSenseHead(depword));
			feature.addFeatureValue("length_" + toks.length);
			feature.addFeatureValue(Levin.getLevinCategoryStrings(depword));
			if (StringUtils.isPreposition(depword))
				feature.addFeatureValue("prep_true");
			else
				feature.addFeatureValue("prep_false");
		}
	}

	private ArrayList<ArrayList<svm_node>> extractFileReadParseFeature(
			ArrayList<Document> docs) throws Exception {

		ArrayList<ArrayList<svm_node>> features = new ArrayList<ArrayList<svm_node>>();
		for (Document doc : docs) {
			System.err.println(doc.getDocId());
			FileReadDependency d = deps.get(doc.getDocId());
			String[] toks = d.getDep().split(" ");

			String depword = toks.length > 1 ? toks[toks.length - 1] : toks[0];
			depword = depword.toLowerCase();
			String[] s = new String[] { "rel_" + d.getRel(), "dep_" + depword,
					"bc_" + BrownCluster_Str.getBrownClusterId(depword, 4),
					"length_" + toks.length,
					"wn_" + JWNLUtils.getWordNetSenseHead(depword) };
			ArrayList<String> sa = Levin.getLevinCategoryStrings(depword);
			for (String ss : s) {
				sa.add(ss);
			}
			
			sa.add(StringUtils.isPreposition(depword)?"prep_true":"prep_false");
			
			// preposition not working
			// if (StringUtils.isPreposition(d.getDep()))
			// sa.add("prep_true");
			// else
			// sa.add("prep_false");

			ArrayList<svm_node> feat = feature.getSVMNodeVector(sa
					.toArray(new String[] {}));

			feat.addAll(Embeddings_Vec.getEmbeddingSVMNodes(depword));

			features.add(feat);

		}// end doc loop

		return features;
	}

	public void setDeps(HashMap<String, FileReadDependency> deps2) {
		// TODO Auto-generated method stub
		this.deps = deps2;
	}

}
