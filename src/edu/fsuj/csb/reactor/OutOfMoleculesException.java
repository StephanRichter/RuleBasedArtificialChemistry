package edu.fsuj.csb.reactor;

public class OutOfMoleculesException extends Exception {

	/**
   * 
   */
  private static final long serialVersionUID = 7839639197792604166L;

	public OutOfMoleculesException(Molecule key) {
		super("Not enough molecules of type "+key.toString());
  }

}
