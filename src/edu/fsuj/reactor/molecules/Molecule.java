package edu.fsuj.reactor.molecules;

public abstract class Molecule {
	
	public abstract String formula();
	
	public String toString() {
	  return formula();
	}

	public int scale() {
	  return formula().length();
  }
}
