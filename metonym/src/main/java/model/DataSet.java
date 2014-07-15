package model;

import java.io.Serializable;
import java.util.ArrayList;


public class DataSet implements Serializable{

	ArrayList<Document> documents;

	public ArrayList<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(ArrayList<Document> documents) {
		this.documents = documents;
	}
}
