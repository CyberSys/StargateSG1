package HTNP;

import World.World;
import Faction.Faction;

public class ConquerTask extends Task {

	public ConquerTask() {
		super(true);
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return 1;
	}

	@Override
	public Task getNextStep(Faction faction) {
		return this;
	}

	public boolean isCompleted(Faction faction, World world) {
		return world.getControllingFaction().equals(faction);
	}

	public boolean canPerform(Faction faction, World world) {
		return faction.isReadyToAttack(world);
	}

	public void perform(Faction faction) {
		//figure this out
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isCompleted(Faction faction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPerform(Faction faction) {
		// TODO Auto-generated method stub
		return false;
	}

}
