package planning;

import faction.Faction;
import universe.World;

public class StealResourcesTask extends Task {

	private Faction target;
	private World world;
	public StealResourcesTask(Faction target, World world, Task parent) {
		super(true, "Steal Resources Task", parent);
		this.target = target;
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
		return parent.didFinish;
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		target.removeResources(50);
		parent.reportFinished();
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return world.hasSpy(faction) && target.getNumResources() >= 50;
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
