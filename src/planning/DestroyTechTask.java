package planning;

import faction.Faction;
import universe.World;

public class DestroyTechTask extends Task {

	private Faction target;
	private World world;
	public DestroyTechTask(Faction target, World world, Task parent) {
		super(true, "Destroy Tech Task", parent);
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
			target.reduceTechLevel();
			parent.reportFinished(this);
		}
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return world.hasSpy(faction) && !target.getTechLevel().isMinimum();
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		return faction.getDiplomacy() + faction.getAggression()*.5 + faction.getScience();
	}

}
