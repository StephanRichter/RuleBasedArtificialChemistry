package edu.fsuj.csb.reactor.reactions;

import java.util.Map.Entry;

import edu.fsuj.csb.reactor.MoleculeSet;
import edu.fsuj.reactor.molecules.Deoxyribose;
import edu.fsuj.reactor.molecules.Molecule;
import edu.fsuj.reactor.molecules.NucleinBase;
import edu.fsuj.reactor.molecules.Nucleotide;
import edu.fsuj.reactor.molecules.Pentose;
import edu.fsuj.reactor.molecules.Ribose;

public class BuildNucleoside extends Reaction {

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

}
