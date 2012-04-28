package HTNP;

import Faction.Faction;

public class GatherTroopsTask extends Task {

	public GatherTroopsTask() {
		super(true, "Gather Troops Task");
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
		return false;
	}

	@Override
	public boolean canPerform(Faction faction) {
		return true;
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		faction.setNumArmies(faction.getNumArmies() + 1);
	}
	@Override
	public double getFlavorMatch(Faction faction) {
		return 0;
	}

}
