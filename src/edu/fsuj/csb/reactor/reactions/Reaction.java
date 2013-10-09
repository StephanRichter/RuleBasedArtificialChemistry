package edu.fsuj.csb.reactor.reactions;

import java.util.Random;

import edu.fsuj.csb.reactor.MoleculeSet;

public abstract class Reaction {

	protected static Random generator;
	private int counter=0;
	
	public abstract boolean isSuitable(MoleculeSet substrates);
	public abstract int numberOfConsumedMolecules();
	
	public MoleculeSet balance(MoleculeSet substrates){
		counter++;
		return getBalance(substrates);		
	}
	
	public abstract MoleculeSet getBalance(MoleculeSet substrates);
	
	public int count(){
		return counter;
	}
	
	public static void setRandom(Random gen) {
		generator=gen;
  }
	public void resetCounter() {
	  counter=0;	  
  }
	public void pause(MoleculeSet balance) {
		System.out.println(getClass().getSimpleName()+": "+balance);
		try {
	    Thread.sleep(10000);
    } catch (InterruptedException e) {
    }	  
  }
}
