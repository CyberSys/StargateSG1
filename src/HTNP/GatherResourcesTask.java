package HTNP;

import Faction.Faction;

public class GatherResourcesTask extends Task {

	private int limit;
	public GatherResourcesTask(int limit) {
		super(true);
		this.limit = limit;
	}
	
	//Consider resources gathered when limit is reached
	
	@Override
	public int stepsToCompletion(Faction faction) {
		return 1;
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
		faction.setNumResources(faction.getNumResources() + 1);
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
