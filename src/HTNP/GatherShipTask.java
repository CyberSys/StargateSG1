package HTNP;

import java.util.*;

import Faction.Faction;

public class GatherShipTask extends Task {

	private int limit;
	public GatherShipTask(int limit) {
		super(false, "Gather Ships Task");
		this.limit = limit;
	}

	protected List<Task> getTaskList() {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new BuildShipTask(limit));
		taskList.add(new StealShipTask(limit));
		taskList.add(new BuyShipTask(limit));
		return taskList;
	}
	
	@Override
	public int stepsToCompletion(Faction faction) {
		Task task = getFlavorMatchTask(faction);
		if(task instanceof BuildShipTask)
			return new BuildShipTask(limit).stepsToCompletion(faction) + new GatherResourcesTask(limit).stepsToCompletion(faction);
		if(task instanceof StealShipTask)
			return new StealShipTask(limit).stepsToCompletion(faction);
		else //if(task instanceof BuyShipTask)
			return new BuyShipTask(limit).stepsToCompletion(faction) + new GatherResourcesTask(limit*2).stepsToCompletion(faction);
	}

	@Override
	public Task getNextStep(Faction faction) {
		Task task = getFlavorMatchTask(faction);
		if(task instanceof BuildShipTask)
		{
			if(new BuildShipTask(limit).canPerform(faction))
				return new BuildShipTask(limit);
			else return new GatherResourcesTask(limit);
		}
		if(task instanceof StealShipTask)
			return new StealShipTask(limit);
		else //if(task instanceof BuyShipTask)
		{
			if(new BuyShipTask(limit).canPerform(faction))
				return new BuyShipTask(limit);
			else return new GatherResourcesTask(limit*2);
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
