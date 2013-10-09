package edu.fsuj.csb.reactor;

import java.util.Random;
import java.util.Vector;

import edu.fsuj.csb.reactor.reactions.A_Condensation;
import edu.fsuj.csb.reactor.reactions.A_Outflow;
import edu.fsuj.csb.reactor.reactions.InflowReaction;
import edu.fsuj.csb.reactor.reactions.PolymerBreakdown;
import edu.fsuj.csb.reactor.reactions.Reaction;
import edu.fsuj.reactor.molecules.A_Polymer;
import edu.fsuj.reactor.molecules.Molecule;
import edu.fsuj.reactor.molecules.MoleculeA;

public class Reactor extends Thread implements Observable{
	
	Vector<Reaction> registeredReactions=new Vector<Reaction>();
	MoleculeSet molecules=new MoleculeSet();
	Vector<Integer> reactantCounts=new Vector<Integer>();
	static Random generator=new Random(1);
	private static Molecule inflowMolecule;

	@Override
	public void run() {
	  super.run();
	  
	  int numberOfRegisteredReactions = registeredReactions.size();

	  System.out.println("Possible reactant counts: "+reactantCounts);
	  while (true){
	  	Reaction reaction = registeredReactions.get(generator.nextInt(numberOfRegisteredReactions));
	  	
	  	int numberOfReactants=reaction.numberOfConsumedMolecules();
	  	MoleculeSet substrates = molecules.dice(numberOfReactants);
	  	try {
	  		if (reaction.isSuitable(substrates))apply(reaction,substrates);
      } catch (OutOfMoleculesException e) {
      	e.printStackTrace();
      	break;
      }
		  InflowReaction.setActive(molecules.get(inflowMolecule)<10);

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
		inflowMolecule=new MoleculeA();
		A_Polymer outflowMolecue = new A_Polymer(50);
		
		Reactor reactor=new Reactor();		
		reactor.register(new InflowReaction(inflowMolecule));
		reactor.register(new A_Condensation());
		reactor.register(new PolymerBreakdown(60));
		reactor.register(new OutflowReaction(outflowMolecue));
		reactor.start();
		
		new Observer(reactor);
	}

	private void register(Reaction type) {
		registeredReactions.add(type);
		int count = type.numberOfConsumedMolecules();
		if (!reactantCounts.contains(count)) reactantCounts.add(count);
  }

	@Override
  public SnapShot snapShot() {
	  return molecules.snapShot();
  }
}
