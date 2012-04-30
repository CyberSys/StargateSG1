package planning;

import faction.Faction;
import universe.World;

public class SpreadDissentTask extends Task {

	private World world;
	private Faction target;
	public SpreadDissentTask(Faction target, World world, Task parent) {
		super(true, "Spread Dissent", parent);
		this.world = world;
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
		return parent.didFinish;
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		//TODO: here is where things will happen
		parent.reportFinished(this);
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return world.hasSpy(faction);
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
