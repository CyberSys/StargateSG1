package planning;

import faction.Faction;
import settings.Globals;
import universe.World;

public class SabotageFleetTask extends Task {

	private Faction target;
	private World world;
	public SabotageFleetTask(World world, Task parent) {
		super(true, "Sabotage Fleet Task", parent);
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
			world.removeShips(target, random.nextInt(10));
			parent.reportFinished(this);
		}
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return world.hasSpy(faction) && target.getNumShips(world) > 0;
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return ((world.getShipCount(target) / Globals.WORLD_SHIP_POPULATION_CAP) * ((faction.getAggression() + faction.getDiplomacy()) / 2.0));
	}

}
