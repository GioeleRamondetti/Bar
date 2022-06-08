package it.polito.tdp.bar.model;

public class Model {
	 private Simulator sim;

	public Model(Simulator sim) {
		super();
		this.sim = sim;
	}
	 public void simula() {
		 sim.init();
		 sim.run();
	 }
}
