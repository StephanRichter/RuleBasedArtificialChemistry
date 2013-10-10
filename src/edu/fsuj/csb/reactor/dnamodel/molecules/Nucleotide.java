package edu.fsuj.csb.reactor.dnamodel.molecules;

public class Nucleotide extends DNABuildingBlock {
	
	private String formula;
	private int oxidationLevel;
	private char baseType;
	private int phosphorylationLevel;

	public Nucleotide(Character baseType, int phosphorylationLevel, int oxidationLevel) {
		this.baseType=baseType;
		this.phosphorylationLevel=phosphorylationLevel;
		this.oxidationLevel=oxidationLevel;
		String prefix=""+baseType;
		switch (oxidationLevel){
			case 0: break;
			case -1: prefix="d"+baseType; break;
			default: throw new IndexOutOfBoundsException("Value ("+oxidationLevel+") not allowed. Oxidation Level must be either 0 oder -1!");
		}
		
		switch (phosphorylationLevel){
		case 0: formula=trivialName(baseType,oxidationLevel); break;
		case 1: formula=prefix+"MP"; break;
		case 2: formula=prefix+"DP"; break;
		case 3: formula=prefix+"TP"; break;
		default: throw new IndexOutOfBoundsException("PhosphorylationLevel ("+phosphorylationLevel+") must not be larger than 3!");
		}
		
  }

	private String trivialName(Character baseType, int oxidationLevel) {
		String prefix=""+baseType;
		switch (oxidationLevel){
			case 0: break;
			case -1: prefix="Deoxy"+Character.toLowerCase(baseType); break;
			default: throw new IndexOutOfBoundsException("Value ("+oxidationLevel+") not allowed. Oxidation Level must be either 0 oder -1!");
		}
		switch (baseType){
		case 'A': return prefix+"denosine";
		case 'C': return prefix+"ytidine";
		case 'G': return prefix+"uanosine";
		case 'T': return prefix+"hymidine";
		case 'U': return prefix+"hymidine";
		}
		throw new IndexOutOfBoundsException("Value ("+baseType+") not allowed. Base type must be one out of {A,C,G,T,U}!");
  }
	
	public String identifier() {	 
	  return formula;
	}

	public char baseType() {
	  return baseType;
  }

	public int oxidationLevel() {
	  return oxidationLevel;
  }

	public int phosphorylationLevel() {
	  return phosphorylationLevel;
  }

	public Nucleotide phosphorylate() {
	  return new Nucleotide(baseType, phosphorylationLevel+1, oxidationLevel); 
  }
}
