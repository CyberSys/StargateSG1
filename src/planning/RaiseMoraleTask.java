package planning;

import faction.Faction;
import settings.Globals;
import universe.World;

public class RaiseMoraleTask extends Task {

	private World world;
	public RaiseMoraleTask(World world, Task parent) {
		super(true, "Raise Morale Task", parent);
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
		return (((Globals.MAX_MORALE - faction.morale) / Globals.MAX_MORALE) * ((faction.getAggression() * 3 + faction.getScience() * 2 + faction.getDiplomacy() * 2) / 7.0));
	}

}
