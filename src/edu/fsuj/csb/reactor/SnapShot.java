package edu.fsuj.csb.reactor;

import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class SnapShot {

	private int max;
	private TreeMap<Object, Integer> values;

	public SnapShot(TreeMap<Object, Integer> values, int max) {
		this.max=max;
		this.values=values;
  }

	public double max() {
	  return max;
  }

	public int size() {
	  return values.size();
  }

	public Set<Entry<Object, Integer>> entries() {
	  return values.entrySet();
  }

}
