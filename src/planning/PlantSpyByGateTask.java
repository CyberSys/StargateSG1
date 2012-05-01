package planning;

import faction.Faction;
import universe.World;

public class PlantSpyByGateTask extends Task {

	private World world, from;
	
	public PlantSpyByGateTask(World from, World world, Task parent) {
		super(true, "Plant Spy By Gate Task", parent);
		this.world = world;
		this.from = from;
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
		from.removeTroops(faction,  1);
	}
	
	@Override
	public boolean isCompleted(Faction faction) {
		return world.hasSpy(faction);
	}

	@Override
	public boolean canPerform(Faction faction) {
		return from.getTroopCount(faction) > 0 && from.hasGate && faction.knowsGateAddress(world);
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return (faction.getAggression() + faction.getDiplomacy()) / 2.0;
	}

}
