package HTNP;

import World.World;
import Faction.Faction;

public class TransportTroopsByShipTask extends Task {

	private World world;
	public TransportTroopsByShipTask() {
		super(false);
	}
	
	public void setTargetWorld(World world) {
		this.world = world;
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return 0;
	}

	@Override
	public Task getNextStep(Faction faction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return faction.isReadyToAttack(world);
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
