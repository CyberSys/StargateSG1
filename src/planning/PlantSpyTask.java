package planning;

import faction.Faction;
import universe.World;

public class PlantSpyTask extends Task {

	private World world;
	
	public PlantSpyTask(World world, Task parent) {
		super(true, "Plant Spy Task", parent);
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
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
