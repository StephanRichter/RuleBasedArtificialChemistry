package edu.fsuj.csb.reactor.reactions;

import java.util.Map.Entry;

import edu.fsuj.csb.reactor.MoleculeSet;
import edu.fsuj.reactor.molecules.DNA;
import edu.fsuj.reactor.molecules.Diphosphate;
import edu.fsuj.reactor.molecules.Molecule;
import edu.fsuj.reactor.molecules.Nucleotide;

public class DNAElongation extends Reaction {

	@Override
	public boolean isSuitable(MoleculeSet substrates) {
		if (substrates.size()>2) return false;
		boolean dna=false;
		boolean nuc3p=false;
		for (Entry<Molecule, Integer> entry:substrates.entrySet()){
			if (entry.getValue()<1) continue;
			Molecule molecule = entry.getKey();
			if (molecule instanceof DNA) dna=true;
			if (molecule instanceof Nucleotide) {
	      Nucleotide nucleotide = (Nucleotide) molecule;
	      if (nucleotide.oxidationLevel()==-1 && nucleotide.phosphorylationLevel()==3) nuc3p=true;	      
      }
		}
		return dna && nuc3p;
	}

	@Override
	public int numberOfConsumedMolecules() {
		return 2;
	}

	@Override
	public MoleculeSet getBalance(MoleculeSet substrates) {
		MoleculeSet balance=new MoleculeSet();
		Character base=null;
		DNA dna=null;
		for (Entry<Molecule, Integer> entry:substrates.entrySet()){
			if (entry.getValue()<1) continue;
			Molecule molecule = entry.getKey();
			if (molecule instanceof DNA) {
				dna=(DNA) molecule;
			}
			if (molecule instanceof Nucleotide) {
	      Nucleotide nucleotide = (Nucleotide) molecule;
	      base=nucleotide.baseType();
	      balance.add(nucleotide);
      }
		}
		dna.append(base);
		balance.invert();
		balance.add(new Diphosphate());
		return balance;
	}

}
