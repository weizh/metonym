package model;

import java.io.Serializable;

public class FileReadDependency implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getSampleid() {
		return sampleid;
	}

	public void setSampleid(String sampleid) {
		this.sampleid = sampleid;
	}

	public String getLocword() {
		return locword;
	}

	public void setLocword(String locword) {
		this.locword = locword;
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getReading() {
		return reading;
	}

	public void setReading(String reading) {
		this.reading = reading;
	}

	String sampleid;
	String locword;
	String dep;
	String rel;
	String reading;

	public FileReadDependency(String sampleid, String locword, String dep,
			String rel, String reading) {

		this.sampleid = sampleid;
		this.locword = locword;
		this.dep = dep;
		this.rel = rel;
		this.reading = reading;
	}
	public FileReadDependency(String... s) {

		this.sampleid = s[0];
		this.locword = s[2];
		this.dep = s[1];
		this.rel = s[3];
		this.reading = s[4];
	}
}
