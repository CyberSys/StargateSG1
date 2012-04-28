package HTNP;

import Faction.Faction;

public class GatherTroopsTask extends Task {

	public int limit;
	public GatherTroopsTask(int limit) {
		super(true, "Gather Troops Task");
		this.limit = limit;
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return limit - faction.getNumArmies();
	}

	@Override
	public Task getNextStep(Faction faction) {
		return this;
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return faction.getNumArmies() >= limit;
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
