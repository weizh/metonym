package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paragraph implements Serializable {

  List<Sentence> sentences;
  public Paragraph(){
	  this.sentences=new ArrayList<Sentence>();
  }
  public List<Sentence> getSentences()  {
    return sentences;
  }
  
  public void addSentence(Sentence sentence){
	  sentences.add(sentence);
  }
}
