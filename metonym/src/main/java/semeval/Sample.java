package semeval;

public class Sample {

	String sample_id;
	String bnc_title;
	String par, par2;

	String annotatedword;
	public int getE_start() {
		return e_start;
	}

	public void setE_start(int e_start) {
		this.e_start = e_start;
	}

	public int getE_end() {
		return e_end;
	}

	public void setE_end(int e_end) {
		this.e_end = e_end;
	}

	int e_start, e_end;
	String reading;
	String metotype;

	public String getSample_id() {
		return sample_id;
	}

	public void setSample_id(String sample_id) {
		this.sample_id = sample_id;
	}

	public String getBnc_title() {
		return bnc_title;
	}

	public void setBnc_title(String bnc_title) {
		this.bnc_title = bnc_title;
	}

	public String getPar() {
		return par;
	}

	public void setPar(String par) {
		this.par = par;
	}

	public String getAnnotatedword() {
		return annotatedword;
	}

	public void setPar2(String secondhalf) {
		// TODO Auto-generated method stub
		this.par2 = secondhalf;
		this.e_start = par.length();
		this.e_end = par.length() + annotatedword.length();
	}

	public String getPar2() {
		return par2;
	}

	public void setAnnotatedword(String annotatedword) {
		this.annotatedword = annotatedword;
	}

	public String getReading() {
		return reading;
	}

	public void setReading(String reading) {
		this.reading = reading;
	}

	public String getMetotype() {
		return metotype;
	}

	public void setMetotype(String metotype) {
		this.metotype = metotype;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	String notes;

	public String toString() {
		return this.sample_id + " " + "title:[" + bnc_title + "] nparagraph:[[" + par + "][" + this.annotatedword + "][" + par2
				+ "] reading:[" + this.reading + "]";
	}

	public String getTotal() {
		return this.par + this.annotatedword + this.par2;
	}
}
