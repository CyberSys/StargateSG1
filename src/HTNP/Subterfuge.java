package HTNP;

import Faction.Faction;

public class Subterfuge extends Task {

	@Override
	public int stepsToCompletion(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Task getNextStep(Faction faction) {
		if(!new PlantSpiesTask().isCompleted())
			return new PlantSpiesTask();
		return new SpreadDissentTask();
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
