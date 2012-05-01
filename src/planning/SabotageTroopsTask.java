package planning;

import faction.Faction;
import settings.Globals;
import universe.World;

public class SabotageTroopsTask extends Task {

	private Faction target;
	private World world;
	public SabotageTroopsTask(World world, Task parent) {
		super(true, "Sabotage Troops Task", parent);
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
			world.removeTroops(target, random.nextInt(10));
			parent.reportFinished(this);
		}
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return world.hasSpy(faction) && world.getTroopCount(target) > 0;
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return ((world.getTroopCount(target) / (double)Globals.WORLD_TROOP_POPULATION_CAP) * ((faction.getAggression() + faction.getDiplomacy()) / 2.0));
	}

}
