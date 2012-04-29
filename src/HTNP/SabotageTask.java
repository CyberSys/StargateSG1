package HTNP;

import java.util.ArrayList;
import java.util.List;

import universe.World;

import Faction.Faction;

public class SabotageTask extends Task {

	private World world;
	private Faction target;
	public SabotageTask(Faction target, World world, Task parent) {
		super(false, "Sabotage Task", parent);
		this.world = world;
		this.target = target;
	}

	protected List<Task> getTaskList(Faction faction) {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new SabotageFleetTask(target, world, this));
		taskList.add(new SabotageTroopsTask(target, world, this));
		taskList.add(new StealResourcesTask(target, world, this));
		taskList.add(new StealTechTask(target, world, this));
		taskList.add(new DestroyTechTask(target, world, this));
		//taskList.add(new SpreadDissentTask(target, world, this));
		return taskList;
	}
	
	@Override
	public int stepsToCompletion(Faction faction) {
		Task task = getFlavorMatchTask(faction);
		return new TransportTroopsTask(faction.getHomeWorld(), world, 1, this).stepsToCompletion(faction) + task.stepsToCompletion(faction);
	}

	@Override
	public Task getNextStep(Faction faction) {
		Task task = getFlavorMatchTask(faction);
		if(!world.hasSpy(faction) && world.getTroopCount(faction) == 0 && faction.getNumArmies(faction.getHomeWorld()) == 0)
			return new TrainTroopsTask(1, this);
		if(!world.hasSpy(faction) && world.getTroopCount(faction) == 0)
			return new TransportTroopsTask(faction.getHomeWorld(), world, 1, this);
		if(!world.hasSpy(faction) && world.getTroopCount(faction) > 0)
			return new PlantSpyTask(world, this);
		else //if(world.hasSpy(faction))
			return task;
		//return new TransportTroopsTask(faction.getHomeWorld(), world, 1, this).canPerform(faction);
	}
	
	@Override
	public boolean isCompleted(Faction faction) {
		return didFinish;
	}

	@Override
	public boolean canPerform(Faction faction) {
		Task task = getFlavorMatchTask(faction);
		if(!world.hasSpy(faction) && world.getTroopCount(faction) == 0)
			return new TransportTroopsTask(faction.getHomeWorld(), world, 1, this).canPerform(faction);
		if(!world.hasSpy(faction) && world.getTroopCount(faction) > 0)
			return new PlantSpyTask(world, this).canPerform(faction);
		else //if(world.hasSpy(faction))
			return task.canPerform(faction);
		//return new TransportTroopsTask(faction.getHomeWorld(), world, 1, this).canPerform(faction);
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
