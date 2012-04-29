package HTNP;

import Faction.Faction;

public class ResearchTask extends Task {

	
	private List<Task> taskList() {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new SearchForTechnologyTask());
		taskList.add(new DirectedResearchTask());
	}
	
	@Override
	public int stepsToCompletion(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Task getNextStep(Faction faction) {
		
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
