package edu.fsuj.csb.reactor;

import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;

import de.srsoftware.tools.ObjectComparator;

public class Reactor extends Thread{
	
	Vector<Reaction> registeredReactions=new Vector<Reaction>();
	TreeMap<Molecule,Integer> molecules=new TreeMap<Molecule, Integer>(ObjectComparator.get());
	Vector<Integer> reactantCounts=new Vector<Integer>();
	Random generator=new Random(1);
	
	@Override
	public void run() {
	  super.run();

	  System.out.println("Possible reactant counts: "+reactantCounts);
	  while (true){
	  	int numberOfReactants = reactantCounts.get(generator.nextInt(reactantCounts.size()));
	  	System.out.println("reactants: "+numberOfReactants);
	  	TreeMap<Molecule, Integer> substrates = diceMolecules(numberOfReactants);
	  	System.out.println("substrates: "+substrates);
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
	
	private TreeMap<Molecule, Integer> diceMolecules(int numberOfReactants) {		
	  TreeMap<Molecule, Integer> subset=new TreeMap<Molecule, Integer>(ObjectComparator.get());
	  if (molecules.size()==0) return subset;
	  for (int i=0; i<numberOfReactants; i++){
	  	int type=generator.nextInt(molecules.size());
	  	Molecule m=null;
	  	Integer available=null;
	  	
	  	for (Entry<Molecule, Integer> entry:molecules.entrySet()){
	  		m=entry.getKey();
	  		available=entry.getValue();
	  		if (--type==0) break;
	  	}
	  	
	  	Integer consumed = subset.get(m);
	  	if (consumed==null) consumed=0;
	  	if (consumed<available){
	  		subset.put(m,consumed+1);
	  	}	  	
	  }
		return subset;
  }

	private void apply(Reaction reaction, TreeMap<Molecule, Integer> substrates) throws OutOfMoleculesException, InterruptedException {
	  System.out.println("Trying to apply "+reaction.getClass().getSimpleName()+" on "+substrates);
		remove(substrates);
	  add(reaction.producedMolecules(substrates));
	  Thread.sleep(1000);
		System.out.println("Molecule set: "+molecules);
  }

	private void add(TreeMap<Molecule, Integer> producedMolecules) {
		for (Entry<Molecule, Integer> entry:producedMolecules.entrySet()){
			Molecule key = entry.getKey();
			Integer number = molecules.get(key);
			if (number==null) number=0;
			number+=entry.getValue();
			molecules.put(key, number);
		}
  }

	private void remove(TreeMap<Molecule, Integer> consumedMolecules) throws OutOfMoleculesException {
		for (Entry<Molecule, Integer> entry:consumedMolecules.entrySet()){
			Molecule key = entry.getKey();
			Integer number = molecules.get(key);
			if (number==null) throw new OutOfMoleculesException(key);
			number-=entry.getValue();
			if (number<0) throw new OutOfMoleculesException(key);
			molecules.put(key, number);
		}
  }

	private Reaction diceReaction(Vector<Reaction> suitableReactions) {
		int l=suitableReactions.size();
		int i=generator.nextInt(l);
	  return suitableReactions.get(i);
  }

	private Vector<Reaction> findSuitableReactions(TreeMap<Molecule, Integer> substrates) {		
		Vector<Reaction> result=new Vector<Reaction>();
	  for (Reaction type:registeredReactions){
	  	if (type.isSuitable(substrates)) result.add(type);
	  }
		return result;
  }

	public static void main(String[] args) throws InterruptedException {
		Molecule A=new MoleculeA();
		
		Reactor reactor=new Reactor();		
		reactor.register(new InflowReaction(A));
		reactor.register(new A_Condensation());
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
		//System.out.println(reactor.molecules);
	  
  }
}
