package edu.fsuj.csb.reactor;

public class Polymer extends Molecule {

	private String formula;

	public Polymer(Object formula) {
		this.formula=formula.toString();
		if (this.formula.isEmpty()) throw new NullPointerException();
	}

	public String formula() {
		return formula;
	}

}
