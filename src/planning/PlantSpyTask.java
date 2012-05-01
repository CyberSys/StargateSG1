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
		taskList.add(new PlantSpyFromPlanetTask(world, limit, this));
		taskList.add(new BuyShipTask(limit, this));
		taskList.add(new )
		return taskList;
	}
	
	@Override
	public int stepsToCompletion(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Task getNextStep(Faction faction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCompleted(Faction faction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPerform(Faction faction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
