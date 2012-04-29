package HTNP;

import java.util.*;

import Faction.Faction;

public class ResearchTask extends Task {

	
	public ResearchTask(Task parent) {
		super(false, "Research Task", parent);
	}

	protected List<Task> getTaskList(Faction faction) {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new SearchForTechnologyTask(this));
		taskList.add(new DirectedResearchTask(determineDirection(), this));
		return taskList;
	}
	
	private int determineDirection() {
		return new Random().nextInt(4);
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
		return true;
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
