package HTNP;

import World.World;
import Faction.Faction;

public class ConquerTask extends Task {

	private World world;
	public ConquerTask(World world, Task parent) {
		super(true, "Conquer Task", parent);
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

	public boolean isCompleted(Faction faction) {
		return faction.getEnemy().getWorld().getControllingFaction() == faction;
	}

	public boolean canPerform(Faction faction) {
		return faction.getNumArmies() > faction.getEnemy().getNumArmies() *  2;
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
