package planning;

import faction.Faction;
import settings.Globals;
import universe.World;

public class StealTechTask extends Task {

	private Faction target;
	private World world;
	public StealTechTask(World world, Task parent) {
		super(true, "Steal Tech Task", parent);
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
			faction.improveTechLevel();
			parent.reportFinished(this);
		}
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return world.hasSpy(faction) && (target.getTechLevel().compareTo(faction.getTechLevel())) > 0;
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return ((Globals.MAX_TECH_LEVEL - faction.getTechLevel().getTotalTechLevel()) / Globals.MAX_TECH_LEVEL) * ((((faction.getAggression() + faction.getDiplomacy()) * 3.0) + (faction.getScience() * 2.0)) / 8.0);
	}

}
