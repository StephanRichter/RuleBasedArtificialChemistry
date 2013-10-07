package edu.fsuj.csb.reactor.reactions;

import edu.fsuj.csb.reactor.MoleculeSet;
import edu.fsuj.reactor.molecules.Molecule;


public class InflowReaction extends Reaction {
	
	
	MoleculeSet production;

	public InflowReaction(Molecule type) {
		production=new MoleculeSet();
		production.add(type);
  }

	@Override
  public boolean isSuitable(MoleculeSet substrates) {
	  return true;
  }

	@Override
  public MoleculeSet balance(MoleculeSet substrates) {
	  return production;
  }

	@Override
  public int numberOfConsumedMolecules() {
	  return 0;
  }	
}
