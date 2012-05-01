package planning;

import faction.Faction;
import universe.World;

public class SpreadDissentTask extends Task {

	private World world;
	public SpreadDissentTask(World world, Task parent) {
		super(true, "Spread Dissent Task", parent);
		this.world = world;
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
		if(random.nextBoolean()) //Spy was caught
			world.exposeSpy(faction);
		else {
			world.decreaseMorale();
			parent.reportFinished(this);
		}
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return world.hasSpy(faction);
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		// TODO: Less likely to spread dissent when target's morale is low.
		return (faction.getDiplomacy() * 3 + faction.getAggression() * 2) / 5.0;
	}

}
