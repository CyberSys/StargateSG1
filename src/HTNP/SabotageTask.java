package HTNP;

import Faction.Faction;

public class SabotageTask extends Task {

	public SabotageTask() {
		super(false, "Sabotage Task");
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return new InfiltrateTask().stepsToCompletion(faction) + new CauseHavokTask().stepsToCompletion(faction);
	}

	@Override
	public Task getNextStep(Faction faction) {
		if(new InfiltrateTask().isCompleted(faction)) return new CauseHavokTask();
		return new InfiltrateTask();
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return new CauseHavokTask().isCompleted(faction);
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
