package planning;

import java.util.Random;

import faction.Faction;

public class StealShipTask extends Task {

	// TODO: Implement this class more fully.  Currently not really up to date.
	private Faction target;
	public StealShipTask(Faction target, Task parent) {
		super(true, "Steal Ship Task", parent);
		this.target = target;
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

	@Override
	public boolean canPerform(Faction faction) {
		return true;
	}
	
	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		if(new Random().nextBoolean()) 
			faction.increaseShips(1, faction.getHomeWorld());
			target.decreaseShips(1, faction.getHomeWorld());
		return;
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		// TODO: flavor based on target
		return (faction.getAggression() * 3 + faction.getDiplomacy()) / 4.0;
	}

}
