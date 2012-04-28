package HTNP;

import World.World;
import Faction.Faction;

public class TransportTroopsByGateTask extends Task {

	private World world;
	public TransportTroopsByGateTask() {
		super(true);
	}
	
	public void setTargetWorld(World world) {
		this.world = world;
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
		return faction.isReadyToAttack(world);
	}

	@Override
	public boolean canPerform(Faction faction) {
		return false;
		
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
