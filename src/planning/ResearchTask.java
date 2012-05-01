package planning;

import java.util.*;

import settings.Globals;

import faction.Faction;

public class ResearchTask extends Task {

	
	public ResearchTask(Task parent) {
		super(false, "Research Task", parent);
	}
	
	protected List<Task> getTaskList(Faction faction) {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new SearchForTechnologyTask(this));
		taskList.add(new DirectedResearchTask(Globals.RESOURCE_RESEARCH, this));
		//taskList.add(new DirectedResearchTask(Globals.HYPERDRIVE_RESEARCH, this));
		taskList.add(new DirectedResearchTask(Globals.DEFENSE_RESEARCH, this));
		taskList.add(new DirectedResearchTask(Globals.OFFENSE_RESEARCH, this));
		return taskList;
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return 1;
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
		return getFlavorMatchTask(faction) != null && getFlavorMatchTask(faction).canPerform(faction);
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return ((Globals.MAX_TECH_LEVEL - faction.getTechLevel().getTotalTechLevel()) / Globals.MAX_TECH_LEVEL) * faction.getScience();
	}

}
