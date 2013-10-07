package edu.fsuj.csb.reactor.reactions;

import edu.fsuj.csb.reactor.MoleculeSet;

public abstract class Reaction {

	public abstract boolean isSuitable(MoleculeSet substrates);
	//public abstract MoleculeSet consumedMolecules(MoleculeSet substrates);
	public abstract int numberOfConsumedMolecules();
	
	public abstract MoleculeSet balance(MoleculeSet substrates);
	public static int count(){
		return 0;
	}
}
