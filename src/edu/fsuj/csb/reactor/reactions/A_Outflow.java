package edu.fsuj.csb.reactor.reactions;

import edu.fsuj.csb.reactor.MoleculeSet;

public class A_Outflow extends Reaction {

	public boolean isSuitable(MoleculeSet substrates) {
		if (substrates.size()!=1) return false;
		if (!substrates.first().formula().equals("A")) return false;
		return true;
	}

	@Override
	public MoleculeSet getBalance(MoleculeSet substrates) {
		return substrates.invert();
	}

	@Override
	public int numberOfConsumedMolecules() {
		return 1;
	}
}
