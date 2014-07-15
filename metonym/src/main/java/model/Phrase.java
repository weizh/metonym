package model;

import java.io.Serializable;

public class Phrase implements Serializable{

	Sentence sent;

	public Sentence getSent() {
		return sent;
	}
	public void setSent(Sentence sent) {
		this.sent = sent;
	}

	int start, end;

	public Phrase(){
		
	}
	/**
	 * start and end are word positions, not char positions.
	 * @param sent
	 * @param start
	 * @param end
	 */
	public Phrase(Sentence sent, int start, int end){
		this.sent=sent;
		this.start=start;
		this.end=end;
	}
	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public int setStart(int s) {
		this.start = s;
		return start;
	}

	public int getEnd(int e) {
		this.end = e;
		return end;
	}

	public String getLemma() {
		if (start == end)
			return sent.getWords().get(start).getLemma().toString();
		else if (start == end - 1)
			return sent.getWords().get(start).getLemma() + '-' + sent.getWords().get(end).getLemma();
		else if (start == end - 2)
			return sent.getWords().get(start).getLemma() + '-' + sent.getWords().get(start + 1).getLemma() + '-'
					+ sent.getWords().get(end).getLemma();
		else {
			StringBuilder sb = new StringBuilder(sent.getWords().get(start).getLemma());
			for (int i = start + 1; i <= end; i++)
				sb.append("-").append(sent.getWords().get(i).getLemma());
			return sb.toString();
		}
	}

	public String getPOS() {
		if (start == end)
			return sent.getWords().get(start).getPOS();
		else if (start + 1 == end)
			return sent.getWords().get(start).getPOS() + '-' + sent.getWords().get(end).getPOS();
		else if (start + 2 == end)
			return sent.getWords().get(start).getPOS() + '-' + sent.getWords().get(start + 1).getPOS() + '-'
					+ sent.getWords().get(end).getPOS();
		else {
			StringBuilder pos = new StringBuilder(sent.getWords().get(start).getPOS());
			for (int s = start + 1; s <= end; s++)
				pos.append('-').append(sent.getWords().get(s).getPOS());
			return pos.toString();
		}
	}
	public String getWords() {
		// TODO Auto-generated method stub
		
		if (start == end)
			return sent.getWords().get(start).getWord().toString();
		else if (start == end - 1)
			return sent.getWords().get(start).getWord() + ' ' + sent.getWords().get(end).getWord();
		else if (start == end - 2)
			return sent.getWords().get(start).getWord() + ' ' + sent.getWords().get(start + 1).getWord() + ' '
					+ sent.getWords().get(end).getWord();
		else {
			StringBuilder sb = new StringBuilder(sent.getWords().get(start).getWord());
			for (int i = start + 1; i <= end; i++)
				sb.append(" ").append(sent.getWords().get(i).getWord());
			return sb.toString();
		}
	}
}
