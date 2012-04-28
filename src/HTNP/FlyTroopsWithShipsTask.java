package HTNP;

import Faction.Faction;

public class FlyTroopsWithShipsTask extends Task {

	public FlyTroopsWithShipsTask() {
		super(true, "Fly Troops With Ships Task");
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
		// TODO Auto-generated method stub
		return faction.isReadyToAttack();
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		faction.setIsReadyToAttack(true);
	}
	@Override
	public boolean canPerform(Faction faction) {
		return faction.getNumShips() >= faction.getNumArmies();
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
