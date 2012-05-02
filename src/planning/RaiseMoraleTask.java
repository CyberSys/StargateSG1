package planning;

import faction.Faction;
import settings.Globals;

public class RaiseMoraleTask extends Task {

	public RaiseMoraleTask(Task parent) {
		super(true, "Raise Morale Task", parent);
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
		return true;
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		faction.decreaseMorale();
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return faction.morale < Globals.MAX_MORALE;
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return (((Globals.MAX_MORALE - faction.morale) / Globals.MAX_MORALE) * ((faction.getAggression() * 3 + faction.getScience() * 2 + faction.getDiplomacy() * 2) / 7.0));
	}

}
