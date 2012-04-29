package HTNP;

import World.World;
import Faction.Faction;

public class FlyTroopsWithShipsTask extends Task {

	private World from, to;
	public FlyTroopsWithShipsTask(World from, World to, int limit, Task parent) {
		super(true, "Fly Troops With Ships Task", parent);
		this.from = from;
		this.to = to;
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
		//set up world stuff to handle this
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
