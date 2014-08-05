package model;

import java.io.Serializable;

import semeval.Sample;
import gnu.trove.map.TCharObjectMap;
import gnu.trove.map.TObjectIntMap;

public class Feature implements Serializable {

	/**
	 * feature header
	 */
	String featureHeader;

	public Feature(String header) {
		this.featureHeader = header;
	}

	public String getFeatureHeader() {
		return featureHeader;
	}

	public void setFeatureHeader(String featureHeader) {
		this.featureHeader = featureHeader;
	}
}
