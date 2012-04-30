package HTNP;

import Faction.Faction;

public class TrainTroopsTask extends Task {

	public int limit;
	public TrainTroopsTask(int limit, Task parent) {
		super(true, "Train Troops Task", parent);
		this.limit = Math.min(limit, 100);
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
		faction.increaseTroops(1);
	}
	@Override
	public double getFlavorMatch(Faction faction) {
		return 0;
	}

}
