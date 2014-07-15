package model;

import java.io.Serializable;

public class Word implements Serializable {

	String word;

	int start, end;
	int wordid;

	String pos;
	
	String entityType;

	String predition;

	boolean[] folds;
	
	public Word(String s, int start, int end, int wordids){
		this();
		this.word=s; this.start = start; this.end = end;
		this.wordid = wordids;
	}
	
	public Word() {
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getWordid() {
		return wordid;
	}

	public void setWordid(int wordid) {
		this.wordid = wordid;
	}

	public String getPredition() {
		return predition;
	}

	public void setPredition(String predition) {
		this.predition = predition;
	}

	public boolean[] getFolds() {
		return folds;
	}

	public void setFolds(boolean[] folds) {
		this.folds = folds;
	}

	public String getPos() {
		return pos;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getLemma() {
		return word.trim().toLowerCase();
	}

	public String getPOS() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String toString() {
		return word;

	}

	public String getCap() {
		if (Character.isUpperCase(this.word.charAt(0)))
			return "A";
		else
			return "a";
		// char[] ch = new char[word.length()];
		// for (int i = 0; i < word.length(); i++) {
		// char c = word.charAt(i);
		//
		// if (Character.isUpperCase(c))
		// ch[i] = 'A';
		// else if (Character.isUpperCase(c))
		// ch[i] = 'a';
		// else if (Character.isDigit(c))
		// ch[i] = '0';
		// }
		// return new String(ch);
	}

	public void setEntityType(String string) {
		// TODO Auto-generated method stub
		this.entityType = string;
	}

	public String getEntityType() {
		// TODO Auto-generated method stub
		return this.entityType;
	}

	public void setPrediction(String label) throws Exception {
		// TODO Auto-generated method stub
		this.predition = label;
	}

	public String getPrediction() {
		// TODO Auto-generated method stub
		return this.predition;
	}

	public void setBooleanFolds(boolean[] rand) {
		// TODO Auto-generated method stub
		this.folds = rand;
	}

	public boolean[] getBooleanFolds() {
		// TODO Auto-generated method stub
		return this.folds;
	}

	/**
	 * Capitalization is not count towards a form. Letter, Number, and
	 * apostrophe, dash
	 * 
	 * @return
	 */
	public String getWordForm() {
		char[] res = new char[word.length()];

		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (c >= 65 && c <= 90)
				res[i] = 'A';
			else if (c >= 97 && c <= 122)
				res[i] = 'a';
			else if (c >= 47 && c <= 57)
				res[i] = 'n';
			else
				res[i] = c;
		}
		String s = new String(res).replaceAll("A[A]+", "A+").replaceAll("a[a]+", "a+").replaceAll("n[n]+", "n+");
		return s;
	}

	public String getSuffix() {
		// TODO Auto-generated method stub
		return word.substring(word.length() > 3 ? (word.length() - 3) : 0);
	}

	public String getSuffix(int i) {
		// TODO Auto-generated method stub
		return word.substring(word.length() > i ? (word.length() - i) : 0);
	}

	public String getPreffix() {
		// TODO Auto-generated method stub
		return word.substring(0, word.length() > 3 ? 3 : word.length());
	}
}
