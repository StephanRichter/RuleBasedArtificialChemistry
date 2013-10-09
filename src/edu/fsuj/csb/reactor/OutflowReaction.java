package edu.fsuj.csb.reactor;

import edu.fsuj.csb.reactor.reactions.Reaction;
import edu.fsuj.reactor.molecules.Molecule;

public class OutflowReaction extends Reaction {
	
	MoleculeSet consumption;
	private Molecule outflowMoleculeType;
	

	public OutflowReaction(Molecule type) {
		consumption=new MoleculeSet();
		consumption.add(type);
		consumption.invert();
		outflowMoleculeType=type;
  }

	@Override
  public boolean isSuitable(MoleculeSet substrates) {
		if (substrates.size()!=1) return false;
		return substrates.first().toString().equals(outflowMoleculeType.toString());
  }

	@Override
  public MoleculeSet getBalance(MoleculeSet substrates) {
	  return consumption;
  }

	@Override
  public int numberOfConsumedMolecules() {
	  return 1;
  }
}
