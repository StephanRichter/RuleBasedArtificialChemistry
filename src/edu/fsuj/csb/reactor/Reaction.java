package edu.fsuj.csb.reactor;

import java.util.TreeMap;

public abstract class Reaction {

	public abstract boolean isSuitable(TreeMap<Molecule, Integer> substrates);
	public abstract TreeMap<Molecule, Integer> producedMolecules(TreeMap<Molecule, Integer> substrates);
	public abstract int numberOfConsumedMolecules();
}
