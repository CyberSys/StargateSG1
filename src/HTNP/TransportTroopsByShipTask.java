package HTNP;

import World.World;
import Faction.Faction;

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
		return new GatherShipTask(faction.getNumArmies() - faction.getNumShips(), this).stepsToCompletion(faction) + 1;
	}

	@Override
	public Task getNextStep(Faction faction) {
		if(faction.getNumShips() < limit)
			return new GatherShipTask(limit, this);
		return new FlyTroopsWithShipsTask(from, to, limit, this);
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return faction.isReadyToAttack();
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
