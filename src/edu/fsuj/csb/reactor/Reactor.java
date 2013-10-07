package edu.fsuj.csb.reactor;

import java.util.Random;
import java.util.Vector;

import edu.fsuj.csb.reactor.reactions.AAAAA_Outflow;
import edu.fsuj.csb.reactor.reactions.A_Condensation;
import edu.fsuj.csb.reactor.reactions.InflowReaction;
import edu.fsuj.csb.reactor.reactions.PolymerBreakdown;
import edu.fsuj.csb.reactor.reactions.Reaction;
import edu.fsuj.reactor.molecules.Molecule;
import edu.fsuj.reactor.molecules.MoleculeA;

public class Reactor extends Thread{
	
	Vector<Reaction> registeredReactions=new Vector<Reaction>();
	MoleculeSet molecules=new MoleculeSet();
	Vector<Integer> reactantCounts=new Vector<Integer>();
	static Random generator=new Random(1);
	
	@Override
	public void run() {
	  super.run();

	  System.out.println("Possible reactant counts: "+reactantCounts);
	  while (true){
	  	int numberOfReactants = reactantCounts.get(generator.nextInt(reactantCounts.size()));
	  	MoleculeSet substrates = molecules.dice(numberOfReactants);
	  	//System.out.println("substrates: "+substrates);
	  	Vector<Reaction> suitableReactions=findSuitableReactions(substrates);	  
	  	Reaction reaction=diceReaction(suitableReactions);
	  	try {
	      apply(reaction,substrates);
      } catch (OutOfMoleculesException e) {
      	e.printStackTrace();
      	break;
      } catch (InterruptedException e) {
	      e.printStackTrace();
      }
	  }
	}	

	private void apply(Reaction reaction, MoleculeSet substrates) throws OutOfMoleculesException, InterruptedException {
	  //System.out.println("Trying to apply "+reaction.getClass().getSimpleName()+" on "+substrates);		
	  molecules.modify(reaction.balance(substrates));
		//System.out.println("Molecule set: "+molecules);
	  //Thread.sleep(100);
  }
	
	private Reaction diceReaction(Vector<Reaction> suitableReactions) {
		int l=suitableReactions.size();
		int i=generator.nextInt(l);
	  return suitableReactions.get(i);
  }

	private Vector<Reaction> findSuitableReactions(MoleculeSet substrates) {		
		Vector<Reaction> result=new Vector<Reaction>();
	  for (Reaction type:registeredReactions){
	  	if (type.isSuitable(substrates)) result.add(type);
	  }
		return result;
  }

	public static void main(String[] args) throws InterruptedException {
		MoleculeSet.setRandom(generator);
		Molecule A=new MoleculeA();
		
		Reactor reactor=new Reactor();		
		reactor.register(new InflowReaction(A));
		reactor.register(new A_Condensation());
		reactor.register(new PolymerBreakdown(generator));
		reactor.register(new AAAAA_Outflow());
		reactor.start();
		
		while (true){
			evaluate(reactor);
			Thread.sleep(1000);
		}
	}

	private void register(Reaction type) {
		registeredReactions.add(type);
		int count = type.numberOfConsumedMolecules();
		if (!reactantCounts.contains(count)) reactantCounts.add(count);
  }

	private static void evaluate(Reactor reactor) {
		System.out.print(reactor.molecules);
		System.out.println(" exported: "+AAAAA_Outflow.counter);
  }
}
