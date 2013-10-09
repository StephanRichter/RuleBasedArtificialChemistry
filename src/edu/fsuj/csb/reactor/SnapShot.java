package edu.fsuj.csb.reactor;

public class SnapShot {

	private int max;
	private int[] values;

	public SnapShot(int[] values, int max) {
		this.max=max;
		this.values=values;
  }

	public double max() {
	  return max;
  }

	public int size() {
	  return values.length;
  }

	public double get(int i) {
	  return values[i];
  }

}
