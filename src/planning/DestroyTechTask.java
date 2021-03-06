package planning;

import faction.Faction;
import settings.Globals;
import universe.World;

public class DestroyTechTask extends Task {

	private Faction target;
	private World world;
	public DestroyTechTask(World world, Task parent) {
		super(true, "Destroy Tech Task", parent);
		this.target = world.getControllingFaction();
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
			if(parent != null) parent.reportFinished(this);
		}
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return world.hasSpy(faction) && !target.getTechLevel().isMinimum();
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return ((target.getTechLevel().getTotalTechLevel() / Globals.MAX_TECH_LEVEL) * (faction.getDiplomacy() + faction.getAggression()) / 2.0);
	}

}
