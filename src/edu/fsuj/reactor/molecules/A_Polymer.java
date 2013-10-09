package edu.fsuj.reactor.molecules;


public class A_Polymer extends Molecule {

	private String formula;

	public A_Polymer(int i) {
		StringBuffer sb=new StringBuffer();
		while (i-->0){
			sb.append("A");
		}
		formula=sb.toString();
  }

	@Override
	public String formula() {

		return formula;
	}

}
