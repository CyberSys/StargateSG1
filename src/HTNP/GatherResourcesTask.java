package HTNP;

import Faction.Faction;

public class GatherResourcesTask extends Task {

	private int limit;
	public GatherResourcesTask(int limit, Task parent) {
		super(true, "Gather Resources Task", parent);
		this.limit = limit;
	}
	
	//Consider resources gathered when limit is reached
	
	@Override
	public int stepsToCompletion(Faction faction) {
		return limit - faction.getNumResources();
	}

	@Override
	public Task getNextStep(Faction faction) {
		return this;
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return faction.getNumResources() >= limit;
	}

	@Override
	public boolean canPerform(Faction faction) {
		return true;
	}
	
	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		faction.setNumResources(faction.getNumResources() + 1);
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
