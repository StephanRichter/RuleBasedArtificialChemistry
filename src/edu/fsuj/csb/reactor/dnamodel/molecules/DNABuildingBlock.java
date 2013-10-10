package edu.fsuj.csb.reactor.dnamodel.molecules;

import edu.fsuj.csb.reactor.molecules.Molecule;

public class DNABuildingBlock extends Molecule {

	public String identifier() {
		return getClass().getSimpleName();
	}
}
