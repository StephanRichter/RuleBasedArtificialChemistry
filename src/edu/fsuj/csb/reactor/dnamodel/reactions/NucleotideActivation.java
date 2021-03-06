package edu.fsuj.csb.reactor.dnamodel.reactions;

import java.util.Map.Entry;

import edu.fsuj.csb.reactor.MoleculeSet;
import edu.fsuj.csb.reactor.dnamodel.molecules.ADP;
import edu.fsuj.csb.reactor.dnamodel.molecules.Diphosphate;
import edu.fsuj.csb.reactor.dnamodel.molecules.Nucleotide;
import edu.fsuj.csb.reactor.molecules.Molecule;
import edu.fsuj.csb.reactor.reactions.Reaction;

public class NucleotideActivation extends Reaction {

	@Override
	public boolean isSuitable(MoleculeSet substrates) {
		if (substrates.size()!=2) return false;
		boolean atp=false;
		boolean nucleotide=false;
		for (Entry<Molecule, Integer> entry:substrates.entrySet()){
			if (entry.getValue()<1) continue;
			Molecule mol = entry.getKey();
			if (mol instanceof Nucleotide) {
	      Nucleotide nucl = (Nucleotide) mol;
	      if (nucl.identifier().equals("ATP")) {
	      	atp=true;
	      } else {
	      	int pl=nucl.phosphorylationLevel();
	      	if (pl<3) nucleotide=true;
	      }
	      

      }
		}
		return atp && nucleotide;
	}

	@Override
	public int numberOfConsumedMolecules() {
		return 2;
	}

	@Override
	public MoleculeSet getBalance(MoleculeSet balance) {
		balance.invert();
		for (Entry<Molecule, Integer> entry:balance.entrySet()){
			Molecule mol = entry.getKey();
			if (entry.getValue()>1) continue;
			if (mol instanceof Nucleotide) {
	      Nucleotide nucl = (Nucleotide) mol;
	      if (nucl.phosphorylationLevel()<3){
	        balance.add(nucl.phosphorylate());
	      }
      }
		}
		balance.add(new ADP());
		return balance;
	}
	
	@Override
	public String toString() {
	  return "Nucleotide Activation";
	}

}
