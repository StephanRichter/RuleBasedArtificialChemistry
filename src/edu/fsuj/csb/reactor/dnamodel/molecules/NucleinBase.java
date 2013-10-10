package edu.fsuj.csb.reactor.dnamodel.molecules;

public class NucleinBase extends DNABuildingBlock {
	public String identifier() {
		return "Base: "+getClass().getSimpleName();
	}

	public Character baseType() {
	  return getClass().getSimpleName().charAt(0);
  }

}
