package edu.fsuj.csb.reactor.reactions;

import java.util.Map.Entry;

import edu.fsuj.csb.reactor.MoleculeSet;
import edu.fsuj.reactor.molecules.ADP;
import edu.fsuj.reactor.molecules.Diphosphate;
import edu.fsuj.reactor.molecules.Molecule;
import edu.fsuj.reactor.molecules.Nucleotide;

public class ActiveteNucleotide extends Reaction {

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
}
