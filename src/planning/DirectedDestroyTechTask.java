package planning;

import faction.Faction;
import settings.Globals;
import universe.World;

public class DirectedDestroyTechTask extends Task {

	private Faction target;
	private World world;
	private int direction;
	public DirectedDestroyTechTask(World world, int direction, Task parent) {
		super(true, "Directed Destroy Tech Task", parent);
		this.target = world.getControllingFaction();
		this.world = world;
		this.direction = direction;
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
			target.reduceTechLevel(direction);
			parent.reportFinished(this);
		}
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return world.hasSpy(faction) && !target.getTechLevel().isMinimum();
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		double directionFlavor = 0;
		double maxLevel = 0;
		if(direction == Globals.OFFENSE_RESEARCH)
		{
			directionFlavor = ((faction.getDiplomacy() * 3 + faction.getScience() * 2) / 5.0);
			maxLevel = Globals.MAX_OFFENSIVE_CAPABILITIES;
		}
		else if(direction == Globals.DEFENSE_RESEARCH)
		{
			directionFlavor = ((faction.getAggression() * 3 + faction.getScience()) / 4.0);
			maxLevel = Globals.MAX_DEFENSIVE_CAPABILITIES;
		}
		else if(direction == Globals.RESOURCE_RESEARCH)
		{
			directionFlavor = ((faction.getScience() + faction.getAggression() + faction.getDiplomacy()) / 3.0);
			maxLevel = Globals.MAX_RESOURCE_EFFICIENCY;
		}
		
		return (((faction.getTechLevel().getCurrentLevel(direction)) / maxLevel) * directionFlavor);
	}

}
