package edu.fsuj.csb.reactor.reactions;

import edu.fsuj.csb.reactor.MoleculeSet;
import edu.fsuj.reactor.molecules.Polymer;

public class PolymerBreakdown extends Reaction {

	private static int counter=0;

	@Override
	public boolean isSuitable(MoleculeSet substrates) {
		if (substrates.size()!=1) return false;
		int l=substrates.first().formula().length();
		int r=generator.nextInt(l);
		return r>1;
  }

	@Override
	public MoleculeSet balance(MoleculeSet balance) {
		String formula = balance.first().formula();
		int split=1+generator.nextInt(formula.length()-1);		
		balance.invert();
		balance.add(new Polymer(formula.substring(0, split)));
		balance.add(new Polymer(formula.substring(split)));
		counter++;
		return balance;
	}

	@Override
	public int numberOfConsumedMolecules() {
		return 1;
	}

	public static int count() {
		return counter;
	}
}
