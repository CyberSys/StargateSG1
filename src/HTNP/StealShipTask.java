package HTNP;

import java.util.Random;

import Faction.Faction;

public class StealShipTask extends Task {

	public StealShipTask() {
		super(true, "Steal Ship Task");
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
