package HTNP;

import java.util.ArrayList;
import java.util.List;

import World.World;

import Faction.Faction;

public class SabotageTask extends Task {

	private World world;
	private Faction target;
	public SabotageTask(Faction faction, World world, Task parent) {
		super(false, "Sabotage Task", parent);
	}

	private List<Task> taskList() {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new SabotageFleetTask(target, world, this));
		taskList.add(new SabotageTroopsTask(target, world, this));
		taskList.add(new StealResourcesTask(target, world, this));
		taskList.add(new DestroyResourceCampTask(target, world, this));
		taskList.add(new StealTechTask(target, world, this));
		taskList.add(new DestroyResearchStationTask(target, world, this));
		taskList.add(new StuntDevelopmentTask(target, world, this));
		return taskList;
	}
	
	@Override
	public int stepsToCompletion(Faction faction) {
		return new InfiltrateTask().stepsToCompletion(faction) + new CauseHavokTask().stepsToCompletion(faction);
	}

	@Override
	public Task getNextStep(Faction faction) {
		if(new InfiltrateTask().isCompleted(faction)) return new CauseHavokTask();
		return new InfiltrateTask();
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return new CauseHavokTask().isCompleted(faction);
	}

	@Override
	public boolean canPerform(Faction faction) {
		return true;
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
