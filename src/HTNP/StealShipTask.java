package HTNP;

import java.util.Random;

import Faction.Faction;

public class StealShipTask extends Task {

	private int limit;
	public StealShipTask(int limit) {
		super(true, "Steal Ship Task");
		this.limit = limit;
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return limit - faction.getNumShips();
	}

	@Override
	public Task getNextStep(Faction faction) {
		return this;
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return faction.getNumShips() >= limit;
	}

	@Override
	public boolean canPerform(Faction faction) {
		return true;
	}
	
	public void perform(Faction faction) {
		if(new Random().nextBoolean()) 
			faction.setNumShips(faction.getNumShips() + 1);
		return;
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return -1;
	}

}
