package edu.fsuj.reactor.molecules.reactions;

import java.util.Map.Entry;

import edu.fsuj.csb.reactor.MoleculeSet;
import edu.fsuj.csb.reactor.dnamodel.molecules.Deoxyribose;
import edu.fsuj.csb.reactor.dnamodel.molecules.NucleinBase;
import edu.fsuj.csb.reactor.dnamodel.molecules.Nucleotide;
import edu.fsuj.csb.reactor.dnamodel.molecules.Pentose;
import edu.fsuj.csb.reactor.dnamodel.molecules.Ribose;
import edu.fsuj.csb.reactor.reactions.Reaction;
import edu.fsuj.reactor.molecules.Molecule;

public class NucleosideFormation extends Reaction {

	@Override
	public boolean isSuitable(MoleculeSet substrates) {
		if (substrates.size()!=2) return false;
		boolean sugar=false;
		boolean nucleinbase=false;
		for (Entry<Molecule, Integer> entry:substrates.entrySet()){
			if (entry.getValue()<1) continue;
			if (entry.getKey() instanceof NucleinBase) nucleinbase=true;
			if (entry.getKey() instanceof Deoxyribose) sugar=true;
			if (entry.getKey() instanceof Ribose) sugar=true;
		}
		return sugar && nucleinbase;
	}

	@Override
	public int numberOfConsumedMolecules() {
		return 2;
	}

	@Override
	public MoleculeSet getBalance(MoleculeSet balance) {
		balance.invert();
		Character type=null;
		int ol=-2;
		for (Entry<Molecule, Integer> entry:balance.entrySet()){
			if (entry.getKey() instanceof NucleinBase) type=entry.getKey().identifier().charAt(0);
			if (entry.getKey() instanceof Deoxyribose) ol++;
			if (entry.getKey() instanceof Ribose) ol+=2;
		}
		balance.add(new Nucleotide(type,0,ol));
		return balance;		
	}
	
	@Override
	public String toString() {
	  return "Nucleoside Formation";
	}
}
