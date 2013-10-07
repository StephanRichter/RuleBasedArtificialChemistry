package edu.fsuj.csb.reactor;

import java.util.Random;

public class PolymerBreakdown extends Reaction {

	private Random generator;

	public PolymerBreakdown(Random generator) {
		this.generator=generator;
  }

	@Override
	public boolean isSuitable(MoleculeSet substrates) {
		if (substrates.size()!=1) return false;
		if (substrates.first().formula().length()<2) return false;
		return true;
  }

	@Override
	public MoleculeSet balance(MoleculeSet balance) {
		String formula = balance.first().formula();
		int split=1+generator.nextInt(formula.length()-1);		
		balance.invert();
		balance.add(new Polymer(formula.substring(0, split)));
		balance.add(new Polymer(formula.substring(split)));		
		return balance;
	}

	@Override
	public int numberOfConsumedMolecules() {
		return 1;
	}

}
