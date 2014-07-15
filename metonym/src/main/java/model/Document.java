package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Document implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String documentID;
	List<Paragraph> paragraphs;

	public Document(String id) {
		this.documentID=id;
		paragraphs = new ArrayList<Paragraph>();
	}

	public List<Paragraph> getParagraphs() {

		return paragraphs;
	}

	public void addParagraph(Paragraph para) {
		// TODO Auto-generated method stub
		this.paragraphs.add(para);
	}

	public String getDocId() {
		// TODO Auto-generated method stub
		return this.documentID;
	}
}
