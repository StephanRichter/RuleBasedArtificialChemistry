package edu.fsuj.csb.reactor.reactions;

import java.util.Map.Entry;

import edu.fsuj.csb.reactor.MoleculeSet;
import edu.fsuj.reactor.molecules.Molecule;
import edu.fsuj.reactor.molecules.Polymer;

public class PolymerElongation extends Reaction {

	private static int counter=0;

	@Override
  public boolean isSuitable(MoleculeSet substrates) {
		if (substrates.size()!=2) return false;
		boolean foundA=false;
		for (Molecule substrate:substrates.types()){
			String f = substrate.formula();
			if (f.equals("A")) {
				foundA=true;
				continue;
			}
			if (substrate.formula().replaceAll("A", "").length()>0) return false;
		}			
		return foundA;
  }

  public MoleculeSet balance(MoleculeSet balance) {
  	counter++;
  	StringBuffer formula=new StringBuffer();
  	for (Entry<Molecule, Integer> substrate:balance.entrySet()){
  		Molecule mol = substrate.getKey();
  		Integer number=substrate.getValue();
  		for (int i=0; i<number; i++) {
  			formula.append(mol.formula());
  		}
  	}
  	balance.invert();
  	balance.add(new Polymer(formula));

		return balance;
  }

	@Override
  public int numberOfConsumedMolecules() {
	  return 2;
  }
	
	public static int count() {
		return counter;
	}
}
