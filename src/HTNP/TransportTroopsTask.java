package HTNP;

import Faction.Faction;

public class TransportTroopsTask extends Task {

	public TransportTroopsTask() {
		super(false, "Transport Troops Task");
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
		if(canByGate && !canByShip) return new TransportTroopsByGateTask().stepsToCompletion(faction);
		if(canByShip && !canByGate) return new TransportTroopsByShipTask().stepsToCompletion(faction);
		if(canByShip && canByGate) {
			if(new TransportTroopsByGateTask().getFlavorMatch(faction) > new TransportTroopsByShipTask().getFlavorMatch(faction)) return new TransportTroopsByGateTask().stepsToCompletion(faction);
			else return new TransportTroopsByShipTask().stepsToCompletion(faction);
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
		
		if(canByGate && !canByShip) return new TransportTroopsByGateTask();
		if(canByShip && !canByGate) return new TransportTroopsByShipTask();
		if(canByShip && canByGate) {
			if(new TransportTroopsByGateTask().getFlavorMatch(faction) > new TransportTroopsByShipTask().getFlavorMatch(faction)) return new TransportTroopsByGateTask();
			else return new TransportTroopsByShipTask();
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
