package planning;

import java.util.*;

import settings.Globals;
import universe.World;
import faction.Faction;

public class GatherShipTask extends Task {

	private int limit;
	private World world;
	public GatherShipTask(World world, int limit, Task parent) {
		super(false, "Gather Ships Task", parent);
		this.limit = Math.min(limit, Globals.WORLD_SHIP_POPULATION_CAP);
		this.world = world;
	}

	protected List<Task> getTaskList(Faction faction) {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new BuildShipTask(world, limit, this));
		for(Faction enemy : faction.getEnemies()) {
			if(faction.getNumArmies(faction.getHomeWorld()) > 0 && enemy.getNumShips(faction.getHomeWorld()) > 0)
				taskList.add(new StealShipTask(enemy, this));
		}
		taskList.add(new BuyShipTask(limit, this));
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
		return faction.getNumShips() >= limit;
	}

	@Override
	public boolean canPerform(Faction faction) {
		return !isCompleted(faction);
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return (faction.getAggression() + faction.getScience() + faction.getDiplomacy()) / 3.0;
	}

}
