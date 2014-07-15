package model;

import java.io.Serializable;

public class Dependency implements Serializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A reference to the orignial sentence that the dependency comes from.
	 */
	Sentence sent;
	
	public Sentence getSent() {
		return sent;
	}
	public void setSent(Sentence sent) {
		this.sent = sent;
	}
	public Word getSource() {
		return source;
	}
	public void setSource(Word source) {
		this.source = source;
	}
	public Word getTarget() {
		return target;
	}
	public void setTarget(Word target) {
		this.target = target;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	Word source, target;
	
	String relation;
	public Dependency(Sentence s, Word src, Word tgt, String rel){
		sent = s;
		source = src;
		target = tgt;
		relation = rel;
	}
}
