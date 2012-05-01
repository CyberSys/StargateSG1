package planning;

import faction.Faction;
import universe.World;

public class FlyTroopsWithShipsTask extends Task {

	private World from, to;
	private int limit;
	public FlyTroopsWithShipsTask(World from, World to, int limit, Task parent) {
		super(true, "Fly Troops With Ships Task", parent);
		this.from = from;
		this.to = to;
		this.limit = limit;
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
		from.removeShips(faction,  (int)Math.ceil(limit/5.));
		to.addShips(faction, (int)Math.ceil(limit/5.));
		parent.reportFinished(this);
		
	}
	@Override
	public boolean canPerform(Faction faction) {
		return faction.getNumShips(from) >= (int)Math.ceil(limit/5.);
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return (faction.getAggression() + faction.getScience() + faction.getDiplomacy()) / 3.0;
	}

}
