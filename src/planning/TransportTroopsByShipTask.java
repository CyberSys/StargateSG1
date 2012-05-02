package planning;

import faction.Faction;
import universe.World;

public class TransportTroopsByShipTask extends Task {

	private World from, to;
	private int limit;
	public TransportTroopsByShipTask(World from, World to, int limit, Task parent) {
		super(false, "Transport Troops By Ship Task", parent);	
		this.from = from;
		this.to = to;
		this.limit = limit;
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return new GatherShipTask(from, faction.getNumArmies() - faction.getNumShips(), this).stepsToCompletion(faction) + 1;
	}

	@Override
	public Task getNextStep(Faction faction) {
		if(faction.getNumShips() < (int)Math.ceil(limit/5.))
			return new GatherShipTask(from, (int)Math.ceil(limit/5.), this);
		return new FlyTroopsWithShipsTask(from, to, limit, this);
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return parent.didFinish;
	}

	public void reportFinished() {
		if(parent != null) parent.reportFinished(this);
	}
	
	@Override
	public boolean canPerform(Faction faction) 
	{
		return faction.knowsLocation(to);
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return (faction.getAggression() + faction.getDiplomacy() + faction.getScience()) / 3.0;
	}

}
