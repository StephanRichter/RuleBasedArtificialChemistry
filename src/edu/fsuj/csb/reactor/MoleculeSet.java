package edu.fsuj.csb.reactor;

import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import de.srsoftware.tools.ObjectComparator;
import edu.fsuj.reactor.molecules.Molecule;

public class MoleculeSet implements Observable{
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

	private Molecule get(int index) {
		int counter=0;
		for (Entry<Molecule, Integer> entry:molecules.entrySet()){
			counter+=entry.getValue();
			if (counter>index){
				return entry.getKey();
			}
		}
	  return null;
  }

	Integer get(Molecule m) {
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

	public synchronized String hist() {
		int height=46;
		int width=120;
		int max=0;
		int length=0;
		for (Entry<Molecule, Integer> entry:molecules.entrySet()){
			int dummy=entry.getValue();			
			if (dummy>max) max=dummy;
			if (dummy>0){
				dummy=entry.getKey().formula().length();
				if (dummy>length) length=dummy;
			}
		}
		if (length>width) length=width;
		if (max<height) height=max;
		char[][] display=new char[height][length];
		for (int x=0; x<length; x++){
			for (int y=0; y<height; y++){
				display[y][x]=' ';
			}
		}
		for (Entry<Molecule, Integer> entry:molecules.entrySet()){
			int x=entry.getKey().formula().length();
			if (x>=length) continue;
			int h=(entry.getValue()*height)/max;
			for (int y=0; y<height; y++){
				if (y<h) display[y][x-1]='❚';
			}
		}
		StringBuffer sb=new StringBuffer();
		for (int i=0; i<length; i++) sb.append(" ");
		sb.append("\n");
		for (int y=0; y<height; y++){
			sb.append(display[height-y-1]);
			sb.append("\n");
		}
		for (int i=0; i<length; i++) sb.append("-");
		return sb.toString();
  }

	@Override
  public synchronized SnapShot snapShot() {
		int length=0;
		int max=0;
		for (Entry<Molecule, Integer> entry:molecules.entrySet()){
			int scale=entry.getKey().scale();
			if (scale>length) length=scale;
			int val=entry.getValue();
			if (val>max) max=val;
		}
		int[] values=new int[length];
		for (int i=0; i<length; i++) values[i]=0;
		for (Entry<Molecule, Integer> entry:molecules.entrySet()){
			values[entry.getKey().scale()-1]=entry.getValue();
		}
		SnapShot result=new SnapShot(values,max);
	  return result;
  }
}
