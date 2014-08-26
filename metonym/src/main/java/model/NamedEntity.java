package model;

public class NamedEntity extends Phrase {

	String entityType;
	
	boolean traintest;
	String prediction;
	
	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String ty) {
		this.entityType	 = ty;
	}

	/**
	 * start and end is the sentence word position, not char position;
	 * 
	 * @param type
	 * @param start
	 * @param end
	 * @param sent
	 */
	public NamedEntity(String type, int start, int end, Sentence sent) {
		
		super(sent,start,end);

		this.entityType= type;
		
	}
	
	public String toString() {
		return getWords()+" " + entityType + " start:" + start + " end:" + end;
	}

	public String getWords() {
		// TODO Auto-generated method stub
		return super.getWords();
	}

	public String getCap() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLemma() {
		// TODO Auto-generated method stub
		return super.getLemma();
	}

	public String getPOS() {
		// TODO Auto-generated method stub
		return super.getPOS();
	}

	public Word getLastWord() {
		// TODO Auto-generated method stub
		return super.sent.wordAt(super.end);
	}
//	public Word[] getArrayWords(){
//		Word [] ns = new Word[super.end-super.start+1];
//		for (int i=super.start; i<super.end+1; i++){
//			ns [i-super.start] = sent.wordAt(i);
//		}
//		return ns;
//	}
}
