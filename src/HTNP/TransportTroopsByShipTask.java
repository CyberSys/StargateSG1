package HTNP;

import Faction.Faction;

public class TransportTroopsByShipTask extends Task {

	public TransportTroopsByShipTask() {
		super(false, "Transport Troops By Ship Task");
		tasks.add(new GatherShipTask());
		tasks.add(new FlyTroopsWithShipsTask());
		
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return 0;
	}

	@Override
	public Task getNextStep(Faction faction) {
		if(faction.getNumShips() < faction.getNumArmies())
			return tasks.get(0);
		return tasks.get(1);
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
