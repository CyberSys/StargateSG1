package HTNP;

import java.util.ArrayList;
import java.util.List;

import Faction.Faction;

public class InfiltrateTask extends Task {
	
	private List<Task> getTaskList() {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new InfiltrateByGateTask());
		taskList.add(new InfiltrateByShip());
		return taskList;
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Task getNextStep(Faction faction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCompleted(Faction faction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPerform(Faction faction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
