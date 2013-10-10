package edu.fsuj.csb.reactor.molecules;

import java.util.Map.Entry;
import java.util.TreeMap;

import de.srsoftware.tools.ObjectComparator;

public abstract class Molecule {
	
	private static TreeMap<String,Integer> identifierMap=new TreeMap<String, Integer>(ObjectComparator.get());	
	
	public abstract String identifier();
	
	public String toString() {
	  return identifier();
	}
	
	public synchronized int id(){
		String id = identifier();
		Integer number=identifierMap.get(id);
		if (number==null) {
			number=identifierMap.size()+1;
			identifierMap.put(id,number);
		}
		return number;
	}
	
	public static TreeMap<Integer, String> ids(){
		TreeMap<Integer, String> inverseMap=new TreeMap<Integer, String>();
		for (Entry<String, Integer> entry:identifierMap.entrySet()){
			inverseMap.put(entry.getValue(), entry.getKey());
		}
		return inverseMap;
	}
}
