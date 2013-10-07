package edu.fsuj.csb.reactor;

import java.util.TreeMap;
import java.util.Map.Entry;

import de.srsoftware.tools.ObjectComparator;

public class A_Condensation extends Reaction {

	@Override
  public boolean isSuitable(TreeMap<Molecule, Integer> substrates) {
		int sum=0;
		for (Entry<Molecule, Integer> substrate:substrates.entrySet()){
			Integer count = substrate.getValue();
			if (count!=null) sum+=count;			
			if (sum>2) return false;
			Molecule mol=substrate.getKey();
			System.out.println("mol: "+mol.formula());
			if (mol.formula().replaceAll("A", "")=="");
		}			
	  return sum==2;
  }

  public TreeMap<Molecule, Integer> producedMolecules(TreeMap<Molecule, Integer> substrates) {
  	StringBuffer formula=new StringBuffer();
  	for (Entry<Molecule, Integer> substrate:substrates.entrySet()){
  		Molecule mol = substrate.getKey();
  		Integer number=substrate.getValue();
  		for (int i=0; i<number; i++) {
  			formula.append(mol.formula());
  		}
  	}
  	TreeMap<Molecule, Integer> result=new TreeMap<Molecule, Integer>(ObjectComparator.get());
  	result.put(new Polymer(formula),1);
		return result;
  }

	@Override
  public int numberOfConsumedMolecules() {
	  return 2;
  }
}
