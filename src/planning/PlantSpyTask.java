package planning;

import java.util.ArrayList;
import java.util.List;

import universe.World;

import faction.Faction;

public class PlantSpyTask extends Task {

	private World world;
	public PlantSpyTask(World world, Task parent) {
		super(false, "Plant Spy Task", parent);
		this.world = world;
	}

	protected List<Task> getTaskList(Faction faction) {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new PlantSpyFromPlanetTask(world, this));
		taskList.add(new PlantSpyByGateTask(faction.getHomeWorld(), world, this));
		taskList.add(new PlantSpyByShipTask(faction.getHomeWorld(), world, this));
		return taskList;
	}
	
	@Override
	public int stepsToCompletion(Faction faction) {
		return getFlavorMatchTask(faction).stepsToCompletion(faction);
	}

	@Override
	public Task getNextStep(Faction faction) {
		return getFlavorMatchTask(faction);
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return didFinish;
	}

	@Override
	public boolean canPerform(Faction faction) {
		return getFlavorMatchTask(faction).canPerform(faction);
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
