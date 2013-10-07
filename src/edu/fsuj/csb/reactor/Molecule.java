package edu.fsuj.csb.reactor;

public abstract class Molecule {
	
	public abstract String formula();
	
	public String toString() {
	  return formula();
	}
}
