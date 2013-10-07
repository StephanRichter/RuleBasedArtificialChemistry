package edu.fsuj.csb.reactor;

import java.util.TreeMap;

import de.srsoftware.tools.ObjectComparator;

public class InflowReaction extends Reaction {	
	
	private TreeMap<Molecule, Integer> consumedMolecules,producedMolecules;

	public InflowReaction(Molecule molecule) {
		consumedMolecules=new TreeMap<Molecule, Integer>(ObjectComparator.get());
		producedMolecules=new TreeMap<Molecule, Integer>(ObjectComparator.get());
		producedMolecules.put(molecule, 1);
  }

	@Override
	public TreeMap<Molecule, Integer> consumedMolecules() {
		return consumedMolecules;
	}

	@Override
	public TreeMap<Molecule, Integer> producedMolecules() {
		return producedMolecules;
	}

}
