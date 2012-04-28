package HTNP;

import Faction.Faction;

public class TransportTroopsByShipTask extends Task {

	public TransportTroopsByShipTask() {
		super(false, "Transport Troops By Ship Task");		
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return new GatherShipTask(faction.getNumArmies() - faction.getNumShips()).stepsToCompletion(faction) + 1;
	}

	@Override
	public Task getNextStep(Faction faction) {
		if(faction.getNumShips() < faction.getNumArmies())
			return new GatherShipTask(faction.getNumArmies());
		return new FlyTroopsWithShipsTask();
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return faction.isReadyToAttack();
	}

	@Override
	public boolean canPerform(Faction faction) {
		return faction.getNumShips() >= faction.getNumArmies();
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
