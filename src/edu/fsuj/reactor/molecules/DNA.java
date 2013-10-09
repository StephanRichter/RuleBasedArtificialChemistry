package edu.fsuj.reactor.molecules;

public class DNA extends Molecule {

	private String sequence;
	
	public DNA(String sequence) {
		this.sequence=sequence;
  }

	public String identifier() {
		return "dna";
	}

	public String sequence() {
	  return sequence;
  }

	public void append(Character base) {
		sequence+=base;
		//System.out.println("DNA: "+sequence);
  }
}
