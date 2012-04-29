package HTNP;

import Faction.Faction;

public class BuildShipTask extends Task {
	
	private int limit;
	public BuildShipTask(int limit, Task parent) {
		super(true, "Build Ship Task", parent);
		this.limit = limit;
	}
	
	@Override
	public int stepsToCompletion(Faction faction) {
		return limit - faction.getNumShips();
	}

	@Override
	public Task getNextStep(Faction faction) {
		if(faction.getNumResources() + faction.getNumShips() >= limit)
			return this;
		else
			return new GatherResourcesTask(limit - faction.getNumResources(), this);
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
		System.out.println("Doing " + name);
		faction.removeResources(1);
		faction.increaseShips(1);
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 1;
	}

}
