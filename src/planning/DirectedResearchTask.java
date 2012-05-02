package planning;

import settings.Globals;
import faction.Faction;

public class DirectedResearchTask extends Task {

	private int direction;
	public DirectedResearchTask(int direction, Task parent) {
		super(true, "Directed Research Task", parent);
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
		if(random.nextBoolean()) {
			if(parent != null) parent.reportFinished(this);
			faction.improveTechLevel(direction);		
		}
	}

	@Override
	public boolean canPerform(Faction faction) {
		return !faction.getTechLevel().isMaximum(direction);
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		double directionFlavor = 0;
		double maxLevel = 0;
		if(direction == Globals.OFFENSE_RESEARCH)
		{
			directionFlavor = ((faction.getAggression() * 3 + faction.getScience() * 2) / 5.0);
			maxLevel = Globals.MAX_OFFENSIVE_CAPABILITIES;
		}
		else if(direction == Globals.DEFENSE_RESEARCH)
		{
			directionFlavor = ((faction.getDiplomacy() * 3 + faction.getScience() * 2) / 5.0);
			maxLevel = Globals.MAX_DEFENSIVE_CAPABILITIES;
		}
		else if(direction == Globals.RESOURCE_RESEARCH)
		{
			directionFlavor = ((faction.getScience() * 3 + faction.getAggression() + faction.getDiplomacy()) / 5.0);
			maxLevel = Globals.MAX_RESOURCE_EFFICIENCY;
		}
		
		return (((maxLevel - faction.getTechLevel().getCurrentLevel(direction)) / maxLevel) * directionFlavor);
	}

}
