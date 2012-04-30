package HTNP;

import universe.World;
import Faction.Faction;

public class FlyTroopsWithShipsTask extends Task {

	private World from, to;
	private int limit;
	public FlyTroopsWithShipsTask(World from, World to, int limit, Task parent) {
		super(true, "Fly Troops With Ships Task", parent);
		this.from = from;
		this.to = to;
		this.limit = Math.min(limit, 100);
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
		return to.getTroopCount(faction) >= limit;
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		from.removeTroops(faction, limit);
		to.addTroops(faction, limit);
		from.removeShips(faction,  limit);
		to.addShips(faction, limit);
		
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
