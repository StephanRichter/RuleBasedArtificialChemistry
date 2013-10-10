package edu.fsuj.csb.reactor;

import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;

import de.srsoftware.tools.ObjectComparator;
import edu.fsuj.csb.reactor.molecules.Molecule;
import edu.fsuj.csb.reactor.reactions.InflowReaction;
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
	  molecules.modify(reaction.balance(substrates));
  }
	
	@Override
	public synchronized void start() {	 
	  super.start();
		new Observer(molecules);
		new Observer(this);
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

	void register(Reaction type) {
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

	@Override
  public String caption() {
	  return "Reactions";
  }

	public void addMolecule(Molecule m) {
		molecules.add(m);
	  
  }
}
