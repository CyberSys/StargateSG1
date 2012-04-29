package planning;

import faction.Faction;
import universe.World;

public class TransportTroopsTask extends Task {

	private World from, to;
	private int limit;
	public TransportTroopsTask(World from, World to, int limit, Task parent) {
		super(false, "Transport Troops Task", parent);
		this.from = from;
		this.to = to;
		this.limit = limit;
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		boolean canByGate = false, canByShip = false;
		if(from.hasGate && to.hasGate && faction.knowsGateAddress(to)) {
			canByGate = true;
		}
		if(faction.knowsLocation(to)) {
			canByShip = true;
		}
		if(canByGate && !canByShip) return new TransportTroopsByGateTask(from, to, limit, this).stepsToCompletion(faction);
		if(canByShip && !canByGate) return new TransportTroopsByShipTask(from, to, limit, this).stepsToCompletion(faction);
		if(canByShip && canByGate) {
			if(new TransportTroopsByGateTask(from, to, limit, this).getFlavorMatch(faction) > new TransportTroopsByShipTask(from, to, limit, this).getFlavorMatch(faction)) return new TransportTroopsByGateTask(from, to, limit, this).stepsToCompletion(faction);
			else return new TransportTroopsByShipTask(from, to, limit, this).stepsToCompletion(faction);
		}
		return -1;
	}

	@Override
	public Task getNextStep(Faction faction) {
		boolean canByGate = false, canByShip = false;
		if(from.hasGate && to.hasGate && faction.knowsGateAddress(to)) {
			canByGate = true;
		}		
		if(faction.knowsLocation(to)) {
			canByShip = true;
		}
		
		if(canByGate && !canByShip) return new TransportTroopsByGateTask(from, to, limit, this);
		if(canByShip && !canByGate) return new TransportTroopsByShipTask(from, to, limit, this);
		if(canByShip && canByGate) {
			if(new TransportTroopsByGateTask(from, to, limit, this).getFlavorMatch(faction) > new TransportTroopsByShipTask(from, to, limit, this).getFlavorMatch(faction)) return new TransportTroopsByGateTask(from, to, limit, this);
			else return new TransportTroopsByShipTask(from, to, limit, this);
		}
		return null;
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return to.getTroopCount(faction) >= limit;
	}

	@Override
	public boolean canPerform(Faction faction) {
		return stepsToCompletion(faction) >= 0;
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
