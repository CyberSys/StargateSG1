package planning;

import java.util.Random;

import settings.Globals;

import faction.Faction;


public class SearchForTechnologyTask extends Task {

	public SearchForTechnologyTask(Task parent) {
		super(true, "Search For Technology Task", parent);
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
		return parent.didFinish;
	}
	
	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		if(parent != null) parent.reportFinished(this);
		faction.improveTechLevel(new Random().nextInt(4));		
	}

	@Override
	public boolean canPerform(Faction faction) {
		return !faction.getTechLevel().isMaximum();
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return ((Globals.MAX_TECH_LEVEL - faction.getTechLevel().getTotalTechLevel()) / Globals.MAX_TECH_LEVEL) * faction.getScience();
	}

}
