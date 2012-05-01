package planning;

import universe.World;
import faction.Faction;

public class TrainTroopsTask extends Task {

	public int limit;
	private World world;
	public TrainTroopsTask(World world, int limit, Task parent) {
		super(true, "Train Troops Task", parent);
		this.limit = limit;
		this.world = world;
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		return limit - faction.getNumArmies(world);
	}

	@Override
	public Task getNextStep(Faction faction) {
		return this;
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return faction.getNumArmies(world) >= limit;
	}

	@Override
	public boolean canPerform(Faction faction) {
		return faction.getNumArmies(world) < limit;
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		faction.gainTroopsActive(world);
		if(parent != null) parent.reportFinished(this);
	}
	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return (faction.getAggression() + faction.getDiplomacy() + faction.getScience()) / 3.0;
	}

}
