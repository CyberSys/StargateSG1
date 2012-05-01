package planning;

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
		parent.reportFinished(this);
		faction.improveTechLevel(direction);		
	}

	@Override
	public boolean canPerform(Faction faction) {
		return faction.getTechLevel().isMaximum(direction);
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		//TODO based on which direction it is going? (resource, hyperdrive, offense, defense)
		return faction.getScience();
	}

}
