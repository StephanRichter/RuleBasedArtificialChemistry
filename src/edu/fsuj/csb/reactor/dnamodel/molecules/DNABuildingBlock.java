package edu.fsuj.csb.reactor.dnamodel.molecules;

import edu.fsuj.reactor.molecules.Molecule;

public class DNABuildingBlock extends Molecule {

	public String identifier() {
		return getClass().getSimpleName();
	}
}
