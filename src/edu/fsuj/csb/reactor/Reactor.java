package edu.fsuj.csb.reactor;

import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import de.srsoftware.tools.ObjectComparator;

public class Reactor extends Thread{
	
	Vector<Reaction> registeredReactions=new Vector<Reaction>();
	TreeMap<Molecule,Integer> molecules=new TreeMap<Molecule, Integer>(ObjectComparator.get());
	Random generator=new Random(1);
	
	@Override
	public void run() {
	  super.run();
	  Vector<Reaction> suitableReactions=findSuitableReactions();	  
	  Reaction reaction=diceReaction(suitableReactions);
	  apply(reaction);
	}	
	
	private void apply(Reaction reaction) {
	  
  }

	private Reaction diceReaction(Vector<Reaction> suitableReactions) {
		int l=suitableReactions.size();
		int i=generator.nextInt(l);
	  return suitableReactions.get(i);
  }

	private Vector<Reaction> findSuitableReactions() {		
		Vector<Reaction> result=new Vector<Reaction>();
	  for (Reaction type:registeredReactions){
	  	if (type.isSuitable(molecules)) result.add(type);
	  }
		return result;
  }

	public static void main(String[] args) {
		Reactor reactor=new Reactor();
		reactor.start();
		
		while (true){
			evaluate(reactor);
		}
	}

	private static void evaluate(Reactor reactor) {
	  // TODO Auto-generated method stub
	  
  }
}
