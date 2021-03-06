package planning;

import java.util.ArrayList;
import java.util.List;

import faction.Faction;
import faction.GoauldFaction;

import settings.Globals;
import universe.World;


public class SabotageTask extends Task {

	public SabotageTask(World world, Task parent) {
		super(false, "Sabotage Task", parent);
		this.world = world;
		this.target = world.getControllingFaction();
	}

	protected List<Task> getTaskList(Faction faction) {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new SabotageFleetTask(world, this));
		taskList.add(new SabotageTroopsTask(world, this));
		taskList.add(new StealResourcesTask(world, this));
		taskList.add(new StealTechTask(world, this));
		taskList.add(new DestroyTechTask(world, this));
		taskList.add(new SpreadDissentTask(world, this));
		taskList.add(new DirectedDestroyTechTask(world, Globals.RESOURCE_RESEARCH, this));
		taskList.add(new DirectedDestroyTechTask(world, Globals.OFFENSE_RESEARCH, this));
		taskList.add(new DirectedDestroyTechTask(world, Globals.DEFENSE_RESEARCH, this));
		//taskList.add(new DirectedDestroyTechTask(world, Globals.HYPERDRIVE_RESEARCH, this));
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
		if(!world.hasSpy(faction)) {
			if(new PlantSpyFromPlanetTask(world, this).canPerform(faction))
				return new PlantSpyFromPlanetTask(world, this);
			if(new PlantSpyByGateTask(faction.getHomeWorld(), world, this).canPerform(faction))
				return new PlantSpyByGateTask(faction.getHomeWorld(), world, this);
			if(new PlantSpyByShipTask(faction.getHomeWorld(), world, this).canPerform(faction))
				return new PlantSpyByShipTask(faction.getHomeWorld(), world, this);
		}
		else if(world.hasSpy(faction))
			return task;
		return null;
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
			return new PlantSpyFromPlanetTask(world, this).canPerform(faction);
		else
			return task.canPerform(faction);
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return (((Globals.MAX_REPUTATION - faction.getReputationNumber(world.getControllingFaction())) / Globals.MAX_REPUTATION) *(faction.getDiplomacy() + faction.getAggression()) / 2.0);
	}

}
