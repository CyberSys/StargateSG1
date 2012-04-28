package HTNP;

import World.World;
import Faction.Faction;

public class AssaultTask extends Task {

	public AssaultTask() {
		super(true, "Assault Task");
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return 1;
	}

	@Override
	public Task getNextStep(Faction faction) {
		return this;
	}

	public boolean isCompleted(Faction faction) {
		return faction.getEnemy().getWorld().getControllingFaction().equals(faction);
	}

	public boolean canPerform(Faction faction) {
		return faction.isReadyToAttack();
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		faction.getEnemy().getWorld().setControllingFaction(faction);
	}
	
	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
