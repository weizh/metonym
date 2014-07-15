package semeval;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import stanfordnlp.PipeLineAnnotate;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.util.CoreMap;
import model.DataSet;
import model.Dependency;
import model.Document;
import model.NamedEntity;
import model.Paragraph;
import model.Sentence;
import model.Word;

public class SemEval_Dataset extends DataSet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SemEval_Dataset() {
		this.setDocuments(new ArrayList<Document>());
	}

	public void add(Document d) {
		// TODO Auto-generated method stub
		this.getDocuments().add(d);
	}

	/**
	 * Every sample is a doc with only 1 paragraph. A paragraph contains
	 * multiple sentences, and not all sentences contains named entity.
	 * 
	 * @param samples
	 * @return
	 */
	public static SemEval_Dataset createSemEvalDataset(List<Sample> samples) {
		// TODO Auto-generated method stub
		SemEval_Dataset sem = new SemEval_Dataset();

		for (Sample s : samples) {
			System.out.println(s);

			Document d = new Document(s.getSample_id());
			sem.add(d);

			Paragraph para = new Paragraph();
			d.addParagraph(para);

			PipeLineAnnotate annot = new PipeLineAnnotate(s.getTotal());

			for (CoreMap sent : annot.getSentences()) {

				Sentence sentence = new Sentence(sent.get(TextAnnotation.class));
				para.addSentence(sentence);

				ArrayList<Word> words = new ArrayList<Word>();
				sentence.setWords(words);

				int eTokenStart = -1, eTokenEnd = -1;
				int tokenCount = 0;

				for (CoreLabel token : sent.get(TokensAnnotation.class)) {
					int b = token.beginPosition(), e = token.endPosition();
					Word word = new Word(token.getString(TextAnnotation.class), b, e, token.index());
					words.add(word);
					if (b >= s.getE_start() && e <= s.getE_end()) {
						if (eTokenStart == -1)
							eTokenStart = tokenCount;
						eTokenEnd = tokenCount;
						word.setEntityType(s.reading);
					}
					tokenCount++;
				}

				if (eTokenStart != -1 && eTokenEnd != -1) {
					sentence.addNamedEntity(new NamedEntity(s.getReading(), eTokenStart, eTokenEnd, sentence));
				}

				Set<SemanticGraphEdge> dependencies = annot.getDependencies(sent);
				for (SemanticGraphEdge sge : dependencies) {
					IndexedWord src = sge.getSource();
					IndexedWord tgt = sge.getTarget();
					GrammaticalRelation rel = sge.getRelation();
					Word srcw = new Word(src.get(TextAnnotation.class), src.beginPosition(), src.endPosition(), src.index());
					Word tgtw = new Word(tgt.get(TextAnnotation.class), tgt.beginPosition(), tgt.endPosition(), tgt.index());
					Dependency p = new Dependency(sentence, srcw, tgtw, rel.getLongName());
					sentence.addDependency(p);
				}
			}
			// break;
		}

		return sem;
	}

	public static SemEval_Dataset load(String file) throws IOException, ClassNotFoundException {
		FileInputStream in = new FileInputStream(file);
		ObjectInputStream ins = new ObjectInputStream(in);
		SemEval_Dataset copy = (SemEval_Dataset) ins.readObject();
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
