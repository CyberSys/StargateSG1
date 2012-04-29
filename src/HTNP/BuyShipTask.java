package HTNP;

import Faction.Faction;

public class BuyShipTask extends Task {

	private int limit;
	public BuyShipTask(int limit, Task parent) {
		super(true, "Buy Ship Task", parent);
		this.limit = limit;
	}

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
		return faction.getNumShips() >= limit;
	}

	@Override
	public boolean canPerform(Faction faction) {
		return faction.getNumResources() >= (limit - faction.getNumShips()) * 2;
	}
	
	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		int numShipsBought = Math.min(faction.getNumResources() / 2, limit - faction.getNumShips());
		faction.setNumShips(faction.getNumShips() + numShipsBought);
		faction.setNumResources(faction.getNumResources() - 2*numShipsBought);
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 1;
	}

}
