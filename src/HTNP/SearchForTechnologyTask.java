package HTNP;

import java.util.Random;

import Faction.Faction;

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
		parent.reportFinished();
		faction.improveTechLevel(new Random().nextInt(4));		
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
