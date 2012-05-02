package planning;

import faction.Faction;
import settings.Globals;
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
			world.getControllingFaction().decreaseMorale();
			if(parent != null) parent.reportFinished(this);
		}
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return world.hasSpy(faction) && world.getControllingFaction().morale > Globals.MIN_MORALE;
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		return (world.getControllingFaction().morale / Globals.MAX_MORALE) * ((faction.getDiplomacy() * 3 + faction.getAggression() * 2) / 5.0);
	}

}
