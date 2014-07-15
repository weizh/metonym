package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sentence implements Serializable {

	/*
	 * The real meats are in the word.
	 */
	List<Word> words;
	String plainSentence;
	List<Dependency> p;
	/*
	 * entities are only positions.
	 */
	List<NamedEntity> entities;

	public Sentence(String sent, List<Word> words, List<NamedEntity> entities, List<Dependency> p) {
		this.plainSentence = sent;
		this.words = words;
		this.entities = entities;
		this.p = p;
	}

	public Sentence(String sent, List<Word> words, List<NamedEntity> entitites) {
		this(sent, words, entitites, new ArrayList<Dependency>());
	}

	public Sentence(String sent, List<Word> words) {
		this(sent, words, new ArrayList<NamedEntity>());
	}

	public Sentence(String plainSentence) {
		this(plainSentence, new ArrayList<Word>());
	}

	public Sentence() {
		this(new String());
	}

	public boolean isEmpty() {
		return words.size() == 0;

	}

	public boolean[] getFolds() {
		Word pivot = words.get(0);
		return pivot.getBooleanFolds();
	}

	public List<NamedEntity> getNamedEntities() {
		return this.entities;
	}

	public int size() {
		return words.size();
	}

	public Word wordAt(int i) {
		return words.get(i);
	}

	public void setNEs(List<NamedEntity> nEs) {
		this.entities = nEs;
	}

	public void setWords(List<Word> words2) {
		this.words = words2;

	}

	public List<Word> getWords() {
		// TODO Auto-generated method stub
		return this.words;
	}

	public void addDependency(Dependency p) {
		// TODO Auto-generated method stub
		this.p.add(p);
	}

	public String getPlainSentence() {
		return plainSentence;
	}

	public void setPlainSentence(String plainSentence) {
		this.plainSentence = plainSentence;

	}
	public List<Dependency> getP() {
		return p;
	}

	public void setP(List<Dependency> p) {
		this.p = p;
	}

	public List<NamedEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<NamedEntity> entities) {
		this.entities = entities;
	}

	public void addNamedEntity(NamedEntity namedEntity) {
		this.entities.add(namedEntity);
	}
}
