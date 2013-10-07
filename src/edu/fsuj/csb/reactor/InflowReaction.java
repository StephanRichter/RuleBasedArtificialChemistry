package edu.fsuj.csb.reactor;

import java.util.TreeMap;

import de.srsoftware.tools.ObjectComparator;

public class InflowReaction extends Reaction {
	
	
	TreeMap<Molecule, Integer> production;

	public InflowReaction(Molecule type) {
		production=new TreeMap<Molecule, Integer>(ObjectComparator.get());
		production.put(type, 1);
  }

	@Override
  public boolean isSuitable(TreeMap<Molecule, Integer> substrates) {
	  return true;
  }

	@Override
  public TreeMap<Molecule, Integer> producedMolecules(TreeMap<Molecule, Integer> substrates) {
	  return production;
  }

	@Override
  public int numberOfConsumedMolecules() {
	  return 0;
  }	
}
