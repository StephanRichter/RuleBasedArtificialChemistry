package edu.fsuj.csb.reactor.dnamodel.molecules;

import edu.fsuj.reactor.molecules.Molecule;


public class DNA extends Molecule {

	private static int counter=0;
	private int num;
	private String sequence;
	
	public DNA(String sequence) {
		this.sequence=sequence;
		num=++counter;
  }

	public DNA() {
	  this("");
  }

	public String identifier() {
		return "DNA"+num+":"+sequence;
	}

	public String sequence() {
	  return sequence;
  }

	public void append(Character base) {
		sequence+=base;
		//System.out.println("DNA: "+sequence);
  }
}
