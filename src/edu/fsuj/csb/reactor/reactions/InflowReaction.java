package edu.fsuj.csb.reactor.reactions;

import edu.fsuj.csb.reactor.MoleculeSet;
import edu.fsuj.reactor.molecules.Molecule;


public class InflowReaction extends Reaction {
	
	private boolean active=true;
	MoleculeSet production;

	public InflowReaction(Molecule type) {
		production=new MoleculeSet();
		production.add(type);
  }

	@Override
  public boolean isSuitable(MoleculeSet substrates) {
	  return active;
  }

	@Override
  public MoleculeSet getBalance(MoleculeSet substrates) {
	  return production;
  }

	@Override
  public int numberOfConsumedMolecules() {
	  return 0;
  }
	
	public void setActive(boolean b) {
		active=b;
  }
	
	public String toString() {
	  return production.first()+" inflow";
	}
}
