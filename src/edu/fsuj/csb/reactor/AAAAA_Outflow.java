package edu.fsuj.csb.reactor;

public class AAAAA_Outflow extends Reaction {

	static int counter=0;
	
	public boolean isSuitable(MoleculeSet substrates) {
		if (substrates.size()!=1) return false;
		if (!substrates.first().formula().equals("AAAAA")) return false;
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

}
