package planning;

import java.util.*;

import universe.World;
import faction.Faction;

public class GatherShipTask extends Task {

	private int limit;
	private World world;
	public GatherShipTask(World world, int limit, Task parent) {
		super(false, "Gather Ships Task", parent);
		this.limit = Math.min(limit, 100);
		this.world = world;
	}

	protected List<Task> getTaskList(Faction faction) {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new BuildShipTask(world, limit, this));
		//TODO: Find way to determine steal ship target
		for(Faction enemy : faction.getEnemies()) {
			if(faction.getNumArmies(faction.getHomeWorld()) > 0 && enemy.getNumShips(faction.getHomeWorld()) > 0)
				taskList.add(new StealShipTask(enemy, this));
		}
		taskList.add(new BuyShipTask(limit, this));
		return taskList;
	}
	
	@Override
	public int stepsToCompletion(Faction faction) {
		Task task = getFlavorMatchTask(faction);
//		if(task instanceof BuildShipTask)
//			return task.stepsToCompletion(faction);
//		if(task instanceof StealShipTask)
//			//TODO: Find way to determine steal ship target
//			return task.stepsToCompletion(faction);
//		else //if(task instanceof BuyShipTask)
		return task.stepsToCompletion(faction);
	}

	@Override
	public Task getNextStep(Faction faction) {
		Task task = getFlavorMatchTask(faction);
		if(task instanceof BuildShipTask)
		{
			return task.getNextStep(faction);
		}
		if(task instanceof StealShipTask)
			//TODO: Find way to determine steal ship target
			return task;
		else //if(task instanceof BuyShipTask)
		{
			return task.getNextStep(faction);
		}
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return faction.getNumShips() >= limit;
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
