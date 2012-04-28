package HTNP;

import World.World;
import Faction.Faction;

public class TransportTroopsByShipTask extends Task {

	private World world;
	public TransportTroopsByShipTask(World world) {
		super(false, "Transport Troops By Ship Task");	
		this.world = world;
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
		return faction.getNumShips() >= faction.getNumArmies() && faction.knowsLocation(world);
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
