package edu.fsuj.csb.reactor.reactions;

import edu.fsuj.csb.reactor.MoleculeSet;
import edu.fsuj.reactor.molecules.Polymer;

public class PolymerCutoff extends Reaction {



	@Override
	public boolean isSuitable(MoleculeSet substrates) {
		if (substrates.size()!=1) return false;
		if (substrates.first().formula().length()<2) return false;
		return true;
  }

	@Override
	public MoleculeSet getBalance(MoleculeSet balance) {
		String formula = balance.first().formula();
		balance.invert();
		Polymer p1 = new Polymer(formula.substring(0, 1));
		Polymer p2 = new Polymer(formula.substring(1));
		balance.add(p1);
		balance.add(p2);
		return balance;
	}

	@Override
	public int numberOfConsumedMolecules() {
		return 1;
	}

}
