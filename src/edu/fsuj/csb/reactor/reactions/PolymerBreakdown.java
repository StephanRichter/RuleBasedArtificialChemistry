package edu.fsuj.csb.reactor.reactions;

import java.util.Random;

import edu.fsuj.csb.reactor.MoleculeSet;
import edu.fsuj.reactor.molecules.Polymer;

public class PolymerBreakdown extends Reaction {

	private static int counter=0;
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
