package HTNP;

import java.util.Random;

import Faction.Faction;

public class StealShipTask extends Task {

	private int limit;
	private Faction target;
	public StealShipTask(int limit, Faction target, Task parent) {
		super(true, "Steal Ship Task", parent);
		this.limit = limit;
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return limit - faction.getNumShips();
	}

	@Override
	public Task getNextStep(Faction faction) {
		return this;
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return faction.getNumShips() >= limit;
	}

	@Override
	public boolean canPerform(Faction faction) {
		return true;
	}
	
	public void perform(Faction faction) {
		if(new Random().nextBoolean()) 
			faction.increaseShips(1);
			target.increaseShips(-1);
		return;
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return -1;
	}

}
