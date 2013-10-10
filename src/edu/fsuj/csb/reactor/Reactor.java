package edu.fsuj.csb.reactor;

import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;

import de.srsoftware.tools.ObjectComparator;
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
import edu.fsuj.csb.reactor.reactions.Reaction;

public class Reactor extends Thread implements Observable {
	
	Vector<Reaction> registeredReactions=new Vector<Reaction>();
	static MoleculeSet molecules=new MoleculeSet();
	Vector<Integer> reactantCounts=new Vector<Integer>();
	private int reactorSize=1000;
	private int latency=0;
	private boolean clearReactions=true;
	private Random generator;
	

	public Reactor(String[] args) {
		generator=new Random();
		MoleculeSet.setRandom(generator);
		Reaction.setRandom(generator);
		setParameter(args);
  }

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
	  	
	  	if (reaction instanceof InflowReaction){
	  		InflowReaction ifr = (InflowReaction) reaction;
	  		if (molecules.size()>reactorSize){ // reactor is full
		  		Integer count = molecules.get(ifr.molecule());
		  		if (count==null || count==0){ // but: we have no substrate left in reactor
		  			ifr.setActive(true);
		  		} else{ // reactor is full and contains substrate
		  			ifr.setActive(false);
		  		}
	  		} else ifr.setActive(true);

	  	}

	  	if (latency>0){
	  		try {
	        Thread.sleep(latency);
        } catch (InterruptedException e) {
        }
	  	}
	  }
	}	

	private void apply(Reaction reaction, MoleculeSet substrates) throws OutOfMoleculesException {
	  //System.out.println("Trying to apply "+reaction.getClass().getSimpleName()+" on "+substrates);		
	  molecules.modify(reaction.balance(substrates));
		//System.out.println("Molecule set: "+molecules);

  }
	
	public static void main(String[] args) throws InterruptedException {
		Reactor reactor=new Reactor(args);
		
		molecules.add(new DNA());
		molecules.add(new DNA());
		molecules.add(new DNA());
		molecules.add(new DNA());
		molecules.add(new DNA());
		molecules.add(new DNA());
		molecules.add(new DNA());
		molecules.add(new DNA());
		molecules.add(new DNA());
		
		reactor.register(new InflowReaction(new ATP()));			// 1
		reactor.register(new InflowReaction(new Deoxyribose()));		// 2
		reactor.register(new InflowReaction(new Adenine()));			// 3
		reactor.register(new InflowReaction(new Cytosine()));			// 4
		reactor.register(new InflowReaction(new Guanine()));			// 5
		reactor.register(new InflowReaction(new Thymine()));				// 6
		reactor.register(new NucleosideFormation());									// 7
		reactor.register(new NucleotideActivation());								// 8
		reactor.register(new DNAElongation());										// 9
		reactor.register(new OutflowReaction(new Diphosphate())); // 10
		reactor.register(new OutflowReaction(new ADP())); 				// 11
		reactor.start();
		
		new Observer(molecules);
		new Observer(reactor);//.setLatency(10000);
	}

	private void setParameter(String[] args) {
		boolean commandKnown=false;
		for (String arg:args){
			if (arg.startsWith("--size=") && (commandKnown=true)) setSize(Integer.parseInt(arg.substring(7)));
			if (arg.startsWith("--latency=") && (commandKnown=true)) setLatency(Integer.parseInt(arg.substring(10)));
			if (arg.equals("--clear-reactions") && (commandKnown=true)) enableClearReactions(true);
			if (arg.equals("--no-clear-reactions") && (commandKnown=true)) enableClearReactions(false);
			if (!commandKnown) throw new UnknownError("Argument "+arg+" unknown");
		}
  }

	private void enableClearReactions(boolean b) {
	  clearReactions=b;	  
  }

	private void setLatency(int l) {
		latency=l;	  
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
	  TreeMap<Object, Integer> values=new TreeMap<Object, Integer>(ObjectComparator.get());
	  for (Reaction r:registeredReactions){
	  	int c=r.count();
	  	if (c>max) max=c;	  	
	  	if (clearReactions) r.resetCounter();
	  	values.put(r, c);
	  }
		return new SnapShot(values, max);
  }
}
