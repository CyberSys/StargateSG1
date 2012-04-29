package HTNP;

import java.util.*;

import Faction.Faction;

public class GatherShipTask extends Task {

	private int limit;
	public GatherShipTask(int limit, Task parent) {
		super(false, "Gather Ships Task", parent);
		this.limit = limit;
	}

	protected List<Task> getTaskList() {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new BuildShipTask(limit, this));
		//TODO: Find way to determine steal ship target
		taskList.add(new StealShipTask(limit, target, this));
		taskList.add(new BuyShipTask(limit, this));
		return taskList;
	}
	
	@Override
	public int stepsToCompletion(Faction faction) {
		Task task = getFlavorMatchTask(faction);
		if(task instanceof BuildShipTask)
			return new BuildShipTask(limit, this).stepsToCompletion(faction) + new GatherResourcesTask(limit, this).stepsToCompletion(faction);
		if(task instanceof StealShipTask)
			//TODO: Find way to determine steal ship target
			return new StealShipTask(limit, target, this).stepsToCompletion(faction);
		else //if(task instanceof BuyShipTask)
			return new BuyShipTask(limit, this).stepsToCompletion(faction) + new GatherResourcesTask(limit*2, this).stepsToCompletion(faction);
	}

	@Override
	public Task getNextStep(Faction faction) {
		Task task = getFlavorMatchTask(faction);
		if(task instanceof BuildShipTask)
		{
			if(new BuildShipTask(limit, this).canPerform(faction))
				return new BuildShipTask(limit, this);
			else return new GatherResourcesTask(limit, this);
		}
		if(task instanceof StealShipTask)
			//TODO: Find way to determine steal ship target
			return new StealShipTask(limit, this);
		else //if(task instanceof BuyShipTask)
		{
			if(new BuyShipTask(limit, this).canPerform(faction))
				return new BuyShipTask(limit, this);
			else return new GatherResourcesTask(limit*2, this);
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
