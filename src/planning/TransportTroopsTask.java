package planning;

import java.util.ArrayList;
import java.util.List;

import faction.Faction;
import settings.Globals;
import universe.World;

public class TransportTroopsTask extends Task {

	private World from, to;
	private int limit;
	public TransportTroopsTask(World from, World to, int limit, Task parent) {
		super(false, "Transport Troops Task", parent);
		this.from = from;
		this.to = to;
		this.limit = limit;
	}

	protected List<Task> getTaskList(Faction faction) {
		limit = Math.min(Globals.WORLD_TROOP_POPULATION_CAP - to.getTroopCount(faction), limit);
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new TransportTroopsByGateTask(from, to, limit, this));
		taskList.add(new TransportTroopsByShipTask(from, to, limit, this));
		return taskList;
	}
	
	@Override
	public int stepsToCompletion(Faction faction) {
		return getFlavorMatchTask(faction) != null ? 1 : -1;
	}

	@Override
	public Task getNextStep(Faction faction) {
		limit = Math.min(Globals.WORLD_TROOP_POPULATION_CAP - to.getTroopCount(faction), limit);
		return getFlavorMatchTask(faction);
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		getNextStep(faction).perform(faction);
	}
	
	@Override
	public boolean isCompleted(Faction faction) {
		return didFinish;
	}

	@Override
	public boolean canPerform(Faction faction) {
		return from != null && to != null && stepsToCompletion(faction) >= 0;
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return (faction.getAggression() + faction.getDiplomacy() + faction.getScience()) / 3.0;
	}

}
