package planning;

import faction.Faction;
import universe.World;

public class PlantSpyFromPlanetTask extends Task {

	private World world;
	
	public PlantSpyFromPlanetTask(World world, Task parent) {
		super(true, "Plant Spy From Planet Task", parent);
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

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		world.plantSpy(faction);
		world.removeTroops(faction,  1);
		parent.reportFinished(this);
	}
	
	@Override
	public boolean isCompleted(Faction faction) {
		return world.hasSpy(faction);
	}

	@Override
	public boolean canPerform(Faction faction) {
		return world.getTroopCount(faction) > 0;
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return (faction.getAggression() + faction.getDiplomacy()) / 2.0;
	}

}
