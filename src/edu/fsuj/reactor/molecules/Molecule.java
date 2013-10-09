package edu.fsuj.reactor.molecules;

public abstract class Molecule {
	
	public abstract String identifier();
	
	public String toString() {
	  return identifier();
	}
}
