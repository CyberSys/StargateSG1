package planning;

import universe.World;
import faction.Faction;

public class TransferShips extends Task {

	private int limit;
	private World from, to;
	public TransferShips(World from, World to, int limit, Task parent) {
		super(true, "Transfer Ships Task", parent);
		this.limit = Math.min(limit, 100);
		this.from = from;
		this.to = to;
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
		return true;
	}

	public void perform(Faction faction) {
		from.removeShips(faction, limit);
		to.addShips(faction, limit);
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return from.getShipCount(faction) >= limit;
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return (faction.getAggression() + faction.getDiplomacy() + faction.getScience()) / 3.0;
	}

}
