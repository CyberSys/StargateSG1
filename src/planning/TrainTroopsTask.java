package planning;

import faction.Faction;

public class TrainTroopsTask extends Task {

	public int limit;
	public TrainTroopsTask(int limit, Task parent) {
		super(true, "Train Troops Task", parent);
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
		return faction.getNumArmies() < limit;
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name + " for " + limit + " troops.");
		System.out.println(isCompleted(faction));
		faction.increaseTroops(faction.getHomeWorld().getPassiveTroops(), faction.getHomeWorld());
		if(parent != null) parent.reportFinished(this);
	}
	@Override
	public double getFlavorMatch(Faction faction) {
		return 0;
	}

}
