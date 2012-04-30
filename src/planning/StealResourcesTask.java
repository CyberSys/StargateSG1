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
		if(random.nextBoolean()) //Spy was caught
			world.exposeSpy(faction);
		else {
			int resourcesToTake = Math.min(target.getNumResources(), 100);
			target.removeResources(resourcesToTake);
			faction.addResources(resourcesToTake/2);
			parent.reportFinished(this);
		}
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return world.hasSpy(faction) && target.getNumResources() >= 100;
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
