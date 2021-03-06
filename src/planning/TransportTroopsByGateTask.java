package planning;

import faction.Faction;
import universe.World;

public class TransportTroopsByGateTask extends Task {

	private World from, to;
	private int limit;
	public TransportTroopsByGateTask(World from, World to, int limit, Task parent) {
		super(true, "Transport Troops By Gate Task", parent);
		this.from = from;
		this.to = to;
		this.limit = limit;
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return canPerform(faction) ? 1 : -1;
	}

	@Override
	public Task getNextStep(Faction faction) {
		return this;
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return to.getTroopCount(faction) >= limit;
	}

	@Override
	public boolean canPerform(Faction faction) {
		return from.hasGate && to.hasGate && faction.knowsGateAddress(to) && from.getTroopCount(faction) >= limit;
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		from.removeTroops(faction, limit);
		to.addTroops(faction, limit);
		if(parent != null) parent.reportFinished(this);
	}
	
	
	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return (faction.getAggression() + faction.getDiplomacy() + faction.getScience()) / 3.0;
	}

}
