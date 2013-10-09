package edu.fsuj.csb.reactor;

import java.util.Random;
import java.util.Vector;

import edu.fsuj.csb.reactor.reactions.ActiveteNucleotide;
import edu.fsuj.csb.reactor.reactions.BuildNucleoside;
import edu.fsuj.csb.reactor.reactions.InflowReaction;
import edu.fsuj.csb.reactor.reactions.Reaction;
import edu.fsuj.reactor.molecules.ATP;
import edu.fsuj.reactor.molecules.Adenine;
import edu.fsuj.reactor.molecules.Cytosine;
import edu.fsuj.reactor.molecules.Deoxyribose;
import edu.fsuj.reactor.molecules.Guanine;
import edu.fsuj.reactor.molecules.Molecule;
import edu.fsuj.reactor.molecules.Nucleotide;
import edu.fsuj.reactor.molecules.Thymin;

public class Reactor extends Thread implements Observable {
	
	Vector<Reaction> registeredReactions=new Vector<Reaction>();
	static MoleculeSet molecules=new MoleculeSet();
	Vector<Integer> reactantCounts=new Vector<Integer>();
	private int reactorSize=1000;
	static Random generator=new Random(1);
	private static Molecule inflowMolecule;

	@Override
	public void run() {
	  super.run();
	  
	  int numberOfRegisteredReactions = registeredReactions.size();

	  System.out.println("Possible reactant counts: "+reactantCounts);
	  while (true){
	  	Reaction reaction = registeredReactions.get(generator.nextInt(numberOfRegisteredReactions));
	  	
	  	if (reaction instanceof InflowReaction){
	  		InflowReaction ifr = (InflowReaction) reaction;
	  		ifr.setActive(molecules.size()<reactorSize);
	  	}
	  	
	  	int numberOfReactants=reaction.numberOfConsumedMolecules();
	  	MoleculeSet substrates = molecules.dice(numberOfReactants);
	  	try {
	  		if (reaction.isSuitable(substrates))apply(reaction,substrates);
      } catch (OutOfMoleculesException e) {
      	e.printStackTrace();
      	break;
      }
	  }
	}	

	private void apply(Reaction reaction, MoleculeSet substrates) throws OutOfMoleculesException {
	  //System.out.println("Trying to apply "+reaction.getClass().getSimpleName()+" on "+substrates);		
	  molecules.modify(reaction.balance(substrates));
		//System.out.println("Molecule set: "+molecules);

  }
	
	public static void main(String[] args) throws InterruptedException {
		MoleculeSet.setRandom(generator);
		Reaction.setRandom(generator);
		
		Reactor reactor=new Reactor();
		reactor.setSize(1000);
		reactor.register(new InflowReaction(new ATP()));
		reactor.register(new InflowReaction(new Adenine()));
		reactor.register(new InflowReaction(new Cytosine()));
		reactor.register(new InflowReaction(new Guanine()));
		reactor.register(new InflowReaction(new Thymin()));
		reactor.register(new InflowReaction(new Deoxyribose()));
		
		reactor.register(new BuildNucleoside());
		reactor.register(new ActiveteNucleotide());
		//reactor.register(new OutflowReaction(new A_Polymer(1)));
		reactor.start();
		
		new Observer(molecules);
		(new Observer(reactor)).setLatency(500);
	}

	private void setSize(int s) {
	  reactorSize=s;
  }

	private void register(Reaction type) {
		registeredReactions.add(type);
		int count = type.numberOfConsumedMolecules();
		if (!reactantCounts.contains(count)) reactantCounts.add(count);
  }

	@Override
  public SnapShot snapShot() {
		int max=0;		
	  int[] values=new int[registeredReactions.size()];
	  int i=0;
	  for (Reaction r:registeredReactions){
	  	int c=r.count();
	  	if (c>max) max=c;
	  	values[i++]=c;
	  	r.resetCounter();
	  }
		return new SnapShot(values, max);
  }
}
