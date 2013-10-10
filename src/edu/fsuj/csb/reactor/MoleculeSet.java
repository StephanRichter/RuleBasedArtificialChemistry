package edu.fsuj.csb.reactor;

import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import de.srsoftware.tools.ObjectComparator;
import edu.fsuj.csb.reactor.molecules.Molecule;

public class MoleculeSet implements Observable{
	private static Random generator;
	TreeMap<Molecule,Integer> molecules=new TreeMap<Molecule, Integer>(ObjectComparator.get());

	public synchronized void add(Molecule m) {
		Integer count = molecules.get(m);
		if (count==null) count=0;
		molecules.put(m, count+1);
  }

	public synchronized int size() {
		int sum=0;
		for (Entry<Molecule, Integer> entry:molecules.entrySet()){
			Integer val = entry.getValue();
			if (val==null) val=0;
			sum+=val;
		}
		return sum;
  }
	
	synchronized MoleculeSet dice(int numberOfReactants) {
		//System.out.println("Selecting "+numberOfReactants+" reactants out of "+this);
		int poolSize=size();
		MoleculeSet result = new MoleculeSet();
		int resultSize=0;
		while (resultSize<numberOfReactants && resultSize<poolSize){
			int index = generator.nextInt(poolSize);
			//System.out.println("index: "+index);
			Molecule key=get(index);
			//System.out.println("Molecule: "+key);
			Integer count=result.get(key);
			if (count==null) count=0;
			if (count<molecules.get(key)) {
				result.add(key);
				resultSize=result.size();
			}
		}
		/*System.out.println("Result: "+result);
		try {
	    Thread.sleep(1000);
    } catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    } //*/
		return result;
  }

	private synchronized Molecule get(int index) {
		int counter=0;
		for (Entry<Molecule, Integer> entry:molecules.entrySet()){
			counter+=entry.getValue();
			if (counter>index){
				return entry.getKey();
			}
		}
	  return null;
  }

	synchronized Integer get(Molecule m) {
		Integer result = molecules.get(m);
		if (result==null) return 0;
	  return result;
  }

	public static void setRandom(Random g) {
		generator=g;
  }

	public synchronized void modify(MoleculeSet moleculeSet) {
		//System.out.println("adding "+moleculeSet+" to "+molecules);
		for (Entry<Molecule, Integer> entry:moleculeSet.molecules.entrySet()){
			Molecule mol = entry.getKey();
			Integer count = molecules.get(mol);
			if (count==null) count=0;
			count+=entry.getValue();
			if (count==0){
				molecules.remove(mol);
			} else {
			  molecules.put(mol,count);
			}
		}
		//System.out.println("result: "+molecules);
  }

	public Molecule first() {
	  return molecules.firstEntry().getKey();
  }

	public synchronized Set<Molecule> types() {
	  // TODO Auto-generated method stub
	  return molecules.keySet();
  }

	public synchronized Set<Entry<Molecule, Integer>> entrySet() {
	  return molecules.entrySet();
  }
	
	public synchronized String toString() {
	  return molecules.toString();
	}

	public synchronized MoleculeSet invert() {
		for (Entry<Molecule, Integer> entry:molecules.entrySet()){
			entry.setValue(-entry.getValue());
		}
		return this;
  }
	
	TreeMap<Molecule, Integer> keyDecay=new TreeMap<Molecule, Integer>(ObjectComparator.get());
	
  public synchronized SnapShot snapShot() {
		int max=0;
		TreeMap<Object, Integer> values=new TreeMap<Object, Integer>(ObjectComparator.get());		
		for (Entry<Molecule, Integer> entry:molecules.entrySet()){
			Molecule mol = entry.getKey();
			keyDecay.put(mol, 10);
			int val=entry.getValue();
			if (val>max) max=val;
		}
		for (Entry<Molecule, Integer> entry:keyDecay.entrySet()){
			Molecule key = entry.getKey();
			int val=entry.getValue()-1;
			if (val<1) {
				keyDecay.remove(key);
			} else {
				values.put(key, 0);
			}
		}
		values.putAll(molecules);
		SnapShot result=new SnapShot(values,max);
	  return result;
  }
}
