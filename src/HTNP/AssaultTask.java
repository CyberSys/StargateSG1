package HTNP;

import universe.World;
import Faction.Faction;

public class AssaultTask extends Task {

	private World world;
	private Faction target;
	public AssaultTask(World world, Faction target, Task parent) {
		super(true, "Assault Task", parent);
		this.world = world;
		this.target = target;
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
		return world.getControllingFaction().equals(faction);
	}

	public boolean canPerform(Faction faction) {
		return world.getTroopCount(faction) > 0;
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		target.decreaseTroops(2, world);
		faction.decreaseTroops(3, world);
	}
	
	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
