package HTNP;

import Faction.Faction;

public class TransportTroopsTask extends Task {

	public TransportTroopsTask() {
		super(false, "Transport Troops Task");
		tasks.add(new TransportTroopsByGateTask());
		tasks.add(new TransportTroopsByShipTask());
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		boolean canByGate = false, canByShip = false;
		if(faction.hasGate() && faction.knowsGateAddress(faction.getEnemy().getWorld())) {
			canByGate = true;
		}
		if(faction.knowsLocation(faction.getEnemy().getWorld())) {
			canByShip = true;
		}
		if(canByGate && !canByShip) return tasks.get(0).stepsToCompletion(faction);
		if(canByShip && !canByGate) return tasks.get(1).stepsToCompletion(faction);
		if(canByShip && canByGate) {
			if(tasks.get(0).getFlavorMatch(faction) > tasks.get(1).getFlavorMatch(faction)) return tasks.get(0).stepsToCompletion(faction);
			else return tasks.get(1).stepsToCompletion(faction);
		}
		return -1;
	}

	@Override
	public Task getNextStep(Faction faction) {
		boolean canByGate = false, canByShip = false;
		if(faction.hasGate() && faction.knowsGateAddress(faction.getEnemy().getWorld())) {
			canByGate = true;
		}		
		if(faction.knowsLocation(faction.getEnemy().getWorld())) {
			canByShip = true;
		}
		
		if(canByGate && !canByShip) return tasks.get(0);
		if(canByShip && !canByGate) return tasks.get(1);
		if(canByShip && canByGate) {
			if(tasks.get(0).getFlavorMatch(faction) > tasks.get(1).getFlavorMatch(faction)) return tasks.get(0);
			else return tasks.get(1);
		}
		return null;
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return faction.isReadyToAttack();
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
