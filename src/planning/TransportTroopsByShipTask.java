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
		this.limit = Math.min(limit, 100);
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return new GatherShipTask(from, faction.getNumArmies() - faction.getNumShips(), this).stepsToCompletion(faction) + 1;
	}

	@Override
	public Task getNextStep(Faction faction) {
		if(faction.getNumShips() < limit)
			return new GatherShipTask(from, limit, this);
		return new FlyTroopsWithShipsTask(from, to, limit, this);
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return to.getTroopCount(faction) >= limit && to.getShipCount(faction) >= limit;
	}

	@Override
	public boolean canPerform(Faction faction) {
		return faction.getNumShips() >= faction.getNumArmies() && faction.knowsLocation(to);
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
