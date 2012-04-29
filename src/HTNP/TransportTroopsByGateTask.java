package HTNP;

import World.World;
import Faction.Faction;

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
		return 1;
	}

	@Override
	public Task getNextStep(Faction faction) {
		return this;
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return faction.isReadyToAttack();
	}

	@Override
	public boolean canPerform(Faction faction) {
		return faction.knowsGateAddress(to);
		
		//figure this out
	}

	public void perform(Faction faction) {
		//figure this out
	}
	
	
	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
