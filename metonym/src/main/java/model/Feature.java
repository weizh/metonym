package model;

import java.io.Serializable;

import semeval.Sample;
import gnu.trove.map.TCharObjectMap;
import gnu.trove.map.TObjectIntMap;


public class Feature implements Serializable{

	/**
	 * feature header
	 */
	String featureHeader;
	/**
	 * double value for the feature
	 */
	private double val;
	/**
	 * surface value ofthe feature
	 */
	private String surface;


	public Feature(String header, String surface, double val){
		this.featureHeader = header;
		this.surface =surface;
		this.val=val;
	}
	public Feature(String header, String surface){
		this(header,surface,-1);
	}
	public Feature(String surface, double val){
		this("[N/A]",surface,val);
	}
	public Feature( double val){
		this("[N/A]","[N/A]",val);
	}
	
	public String getFeatureHeader() {
		return featureHeader;
	}



	public void setFeatureHeader(String featureHeader) {
		this.featureHeader = featureHeader;
	}



	public double getVal() {
		return val;
	}



	public void setVal(double val) {
		this.val = val;
	}



	public String getSurface() {
		return surface;
	}



	public void setSurface(String surface) {
		this.surface = surface;
	}



	public static void main(String argv[]){
		System.out.println();
	}
	public String getStringWithHeader() {
		// TODO Auto-generated method stub
		return this.featureHeader+"-"+this.surface;
	}

}
