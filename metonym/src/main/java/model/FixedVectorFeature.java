package model;

import libsvm.svm_node;

public class FixedVectorFeature extends Feature{

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;

	public FixedVectorFeature(String header) {
		super(header);
		// TODO Auto-generated constructor stub
	}
	
	public svm_node[] getSVM_NodeVector(double[] vec){
		svm_node[] node = new svm_node[vec.length];
					
		for (int i = 0 ; i < vec.length; i++){
			node[i] = new svm_node();
			node[i].index=i;
			node[i].value=vec[i];
		}
		return node;
	}

}
