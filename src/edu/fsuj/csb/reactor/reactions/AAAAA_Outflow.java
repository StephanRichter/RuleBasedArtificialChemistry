package edu.fsuj.csb.reactor.reactions;

import edu.fsuj.csb.reactor.MoleculeSet;

public class AAAAA_Outflow extends Reaction {

	private static int counter=0;
	
	public boolean isSuitable(MoleculeSet substrates) {
		if (substrates.size()!=1) return false;
		if (!substrates.first().formula().equals("AAA")) return false;
		return true;
	}

	@Override
	public MoleculeSet balance(MoleculeSet substrates) {
		counter++;
		return substrates.invert();
	}

	@Override
	public int numberOfConsumedMolecules() {
		return 1;
	}

	public static int count() {
		return counter;
	}
}
