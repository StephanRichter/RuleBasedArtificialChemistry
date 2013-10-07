package edu.fsuj.csb.reactor.reactions;

import java.util.Random;

import edu.fsuj.csb.reactor.MoleculeSet;

public abstract class Reaction {

	protected static Random generator;

	
	public abstract boolean isSuitable(MoleculeSet substrates);
	//public abstract MoleculeSet consumedMolecules(MoleculeSet substrates);
	public abstract int numberOfConsumedMolecules();
	
	public abstract MoleculeSet balance(MoleculeSet substrates);
	public static int count(){
		return 0;
	}
	public static void setRandom(Random gen) {
		generator=gen;
  }
}
