package edu.fsuj.csb.reactor;

import edu.fsuj.csb.reactor.dnamodel.molecules.ADP;
import edu.fsuj.csb.reactor.dnamodel.molecules.ATP;
import edu.fsuj.csb.reactor.dnamodel.molecules.Adenine;
import edu.fsuj.csb.reactor.dnamodel.molecules.Cytosine;
import edu.fsuj.csb.reactor.dnamodel.molecules.DNA;
import edu.fsuj.csb.reactor.dnamodel.molecules.Deoxyribose;
import edu.fsuj.csb.reactor.dnamodel.molecules.Diphosphate;
import edu.fsuj.csb.reactor.dnamodel.molecules.Guanine;
import edu.fsuj.csb.reactor.dnamodel.molecules.Thymine;
import edu.fsuj.csb.reactor.dnamodel.reactions.DNAElongation;
import edu.fsuj.csb.reactor.dnamodel.reactions.NucleosideFormation;
import edu.fsuj.csb.reactor.dnamodel.reactions.NucleotideActivation;
import edu.fsuj.csb.reactor.reactions.InflowReaction;
import edu.fsuj.csb.reactor.reactions.OutflowReaction;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		Reactor reactor=new Reactor(args);
		
		reactor.addMolecule(new DNA());
		reactor.addMolecule(new DNA());
		reactor.addMolecule(new DNA());
		reactor.addMolecule(new DNA());
		reactor.addMolecule(new DNA());
		reactor.addMolecule(new DNA());
		reactor.addMolecule(new DNA());
		reactor.addMolecule(new DNA());
		reactor.addMolecule(new DNA());
		
		reactor.register(new InflowReaction(new ATP()));
		reactor.register(new InflowReaction(new Deoxyribose()));
		reactor.register(new InflowReaction(new Adenine()));
		reactor.register(new InflowReaction(new Cytosine()));
		reactor.register(new InflowReaction(new Guanine()));
		reactor.register(new InflowReaction(new Thymine()));
		reactor.register(new NucleosideFormation());
		reactor.register(new NucleotideActivation());
		reactor.register(new DNAElongation());
		reactor.register(new OutflowReaction(new Diphosphate()));
		reactor.register(new OutflowReaction(new ADP()));
		reactor.start();
	}
}
