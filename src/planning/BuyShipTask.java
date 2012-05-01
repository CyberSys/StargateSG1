package planning;

import faction.Faction;
import settings.Globals;

public class BuyShipTask extends Task {

	private int limit;
	public BuyShipTask(int limit, Task parent) {
		super(true, "Buy Ship Task", parent);
		this.limit = Math.min(limit, 100);
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return 1;
	}

	@Override
	public Task getNextStep(Faction faction) {
		if(faction.getNumResources() >= (limit - faction.getNumShips()) * Globals.SHIP_RESOURCE_BUY_COST)
			return this;
		else
			return new GatherResourcesTask(limit - faction.getNumShips() * Globals.SHIP_RESOURCE_BUY_COST, this);
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
		int numShipsBought = Math.min(faction.getNumResources() / Globals.SHIP_RESOURCE_BUY_COST, limit - faction.getNumShips());
		faction.increaseShips(numShipsBought);
		faction.removeResources(Globals.SHIP_RESOURCE_BUY_COST*numShipsBought);
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		return ((faction.getDiplomacy() * 2) + faction.getAggression() + faction.getScience()) / 4.0;
	}

}
