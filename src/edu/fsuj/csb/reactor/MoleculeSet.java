package edu.fsuj.csb.reactor;

import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import de.srsoftware.tools.ObjectComparator;

public class MoleculeSet {
	private static Random generator;
	TreeMap<Molecule,Integer> molecules=new TreeMap<Molecule, Integer>(ObjectComparator.get());

	public synchronized void add(Molecule m) {
		Integer count = molecules.get(m);
		if (count==null) count=0;
		molecules.put(m, count+1);
  }

	public int size() {
		int sum=0;
		for (Entry<Molecule, Integer> entry:molecules.entrySet()){
			Integer val = entry.getValue();
			if (val==null) val=0;
			sum+=val;
		}
		return sum;
  }
	
	MoleculeSet dice(int numberOfReactants) {
	  MoleculeSet result=new MoleculeSet();
	  if (molecules.size()==0) return result;
	  for (int i=0; i<numberOfReactants; i++){
	  	int type=generator.nextInt(molecules.size());
	  	Molecule m=null;
	  	Integer available=null;
	  	
	  	for (Entry<Molecule, Integer> entry:molecules.entrySet()){
	  		m=entry.getKey();
	  		available=entry.getValue();
	  		if (--type==0) break;
	  	}
	  	
	  	Integer consumed = result.get(m);
	  	if (consumed==null) consumed=0;
	  	if (consumed<available)	result.add(m);
	  }
		return result;
  }

	Integer get(Molecule m) {
	  return molecules.get(m);
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

	public Set<Molecule> types() {
	  // TODO Auto-generated method stub
	  return molecules.keySet();
  }

	public Set<Entry<Molecule, Integer>> entrySet() {
	  return molecules.entrySet();
  }
	
	public synchronized String toString() {
	  return molecules.toString();
	}

	public MoleculeSet invert() {
		for (Entry<Molecule, Integer> entry:molecules.entrySet()){
			entry.setValue(-entry.getValue());
		}
		return this;
  }
}
