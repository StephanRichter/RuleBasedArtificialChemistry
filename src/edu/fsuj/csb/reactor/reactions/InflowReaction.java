package edu.fsuj.csb.reactor.reactions;

import edu.fsuj.csb.reactor.MoleculeSet;
import edu.fsuj.reactor.molecules.Molecule;


public class InflowReaction extends Reaction {
	
	
	private static int counter=0;
	MoleculeSet production;

	public InflowReaction(Molecule type) {
		production=new MoleculeSet();
		production.add(type);
  }

	@Override
  public boolean isSuitable(MoleculeSet substrates) {
	  return generator.nextInt(100)<30;
  }

	@Override
  public MoleculeSet balance(MoleculeSet substrates) {
		counter++;
	  return production;
  }

	@Override
  public int numberOfConsumedMolecules() {
	  return 0;
  }
	
	public static int count() {
		return counter;
	}
}
