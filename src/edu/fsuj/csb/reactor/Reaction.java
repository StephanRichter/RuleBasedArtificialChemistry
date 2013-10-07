package edu.fsuj.csb.reactor;

import java.util.Map.Entry;
import java.util.TreeMap;

public abstract class Reaction {

	public abstract boolean isSuitable(TreeMap<Molecule, Integer> substrates);
	public abstract TreeMap<Molecule, Integer> producedMolecules(TreeMap<Molecule, Integer> substrates);
	public abstract int numberOfConsumedMolecules();
	
	protected int count(TreeMap<Molecule, Integer> molecules) {
		int sum=0;
		for (Entry<Molecule, Integer> m:molecules.entrySet()){
			Integer v = m.getValue();
			if (v!=null) sum+=v;
		}			
	  return sum;
  }
}
