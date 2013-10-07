package edu.fsuj.csb.reactor.reactions;

import java.util.Map.Entry;

import edu.fsuj.csb.reactor.MoleculeSet;
import edu.fsuj.reactor.molecules.Molecule;
import edu.fsuj.reactor.molecules.Polymer;

public class A_Condensation extends Reaction {

	@Override
  public boolean isSuitable(MoleculeSet substrates) {
		if (substrates.size()!=2) return false;
		for (Molecule substrate:substrates.types()){
			if (substrate.formula().replaceAll("A", "").length()>0) return false;
		}			
		return true;
  }

  public MoleculeSet balance(MoleculeSet balance) {
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
}
