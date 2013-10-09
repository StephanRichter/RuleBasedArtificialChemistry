package edu.fsuj.csb.reactor.reactions;

import edu.fsuj.csb.reactor.MoleculeSet;
import edu.fsuj.reactor.molecules.Polymer;

public class PolymerBreakdown extends Reaction {

	private static int counter=0;
	private int minLength;

	public PolymerBreakdown(int minLength) {
		this.minLength=minLength;
  }
	
	public PolymerBreakdown() {
		this(2);
  }

	@Override
	public boolean isSuitable(MoleculeSet substrates) {
		if (substrates.size()!=1) return false;
		int l=substrates.first().formula().length();
		if (l<minLength) return false;
		return true;
  }

	@Override
	public MoleculeSet balance(MoleculeSet balance) {
		String formula = balance.first().formula();
		System.out.print("Breaking down A["+formula.length()+"] into ");
		int split=1+generator.nextInt(formula.length()-1);		
		balance.invert();
		Polymer p1 = new Polymer(formula.substring(0, split));
		Polymer p2 = new Polymer(formula.substring(split));
		System.out.println("A["+p1.scale()+"] + A["+p2.scale()+"]");
		balance.add(p1);
		balance.add(p2);
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
