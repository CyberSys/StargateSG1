package HTNP;

import Faction.Faction;

public class BuildShipTask extends Task {
	
	public BuildShipTask() {
		super(true, "Build Ship Task");
	}
	
	@Override
	public int stepsToCompletion(Faction faction) {
		return 1;
	}

	@Override
	public Task getNextStep(Faction faction) {
		return this;
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return faction.getNumShips() > 0;
	}

	@Override
	public boolean canPerform(Faction faction) {
		return faction.getNumResources() > 0;
	}
	
	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		faction.setNumResources(faction.getNumResources() - 1);
		faction.setNumShips(faction.getNumShips() + 1);
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
