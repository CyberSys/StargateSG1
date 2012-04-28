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
		if(canByGate && !canByShip) return new TransportTroopsByGateTask(faction.getEnemy().getWorld()).stepsToCompletion(faction);
		if(canByShip && !canByGate) return new TransportTroopsByShipTask(faction.getEnemy().getWorld()).stepsToCompletion(faction);
		if(canByShip && canByGate) {
			if(new TransportTroopsByGateTask(faction.getEnemy().getWorld()).getFlavorMatch(faction) > new TransportTroopsByShipTask(faction.getEnemy().getWorld()).getFlavorMatch(faction)) return new TransportTroopsByGateTask(faction.getEnemy().getWorld()).stepsToCompletion(faction);
			else return new TransportTroopsByShipTask(faction.getEnemy().getWorld()).stepsToCompletion(faction);
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
		
		if(canByGate && !canByShip) return new TransportTroopsByGateTask(faction.getEnemy().getWorld());
		if(canByShip && !canByGate) return new TransportTroopsByShipTask(faction.getEnemy().getWorld());
		if(canByShip && canByGate) {
			if(new TransportTroopsByGateTask(faction.getEnemy().getWorld()).getFlavorMatch(faction) > new TransportTroopsByShipTask(faction.getEnemy().getWorld()).getFlavorMatch(faction)) return new TransportTroopsByGateTask(faction.getEnemy().getWorld());
			else return new TransportTroopsByShipTask(faction.getEnemy().getWorld());
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
