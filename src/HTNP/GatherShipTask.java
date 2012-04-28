package HTNP;

import Faction.Faction;

public class GatherShipTask extends Task {

	public GatherShipTask() {
		super(false);
		// TODO Auto-generated constructor stub
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
		return faction.getNumShips() > 0;
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
