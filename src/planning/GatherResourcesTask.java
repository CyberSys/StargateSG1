package planning;

import faction.Faction;

public class GatherResourcesTask extends Task {

	private int limit;
	public GatherResourcesTask(int limit, Task parent) {
		super(true, "Gather Resources Task", parent);
		this.limit = Math.min(limit, 100);
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
		faction.gainResourcesActive();
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return (faction.getAggression() + faction.getScience() + faction.getDiplomacy()) / 3.0;
	}

}
