package planning;

import faction.Faction;
import universe.World;

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
		return world.getControllingFaction() == faction;
	}

	public boolean canPerform(Faction faction) {
		return true;
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		//TODO: check for 'if success'
		world.setControllingFaction(faction);
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
