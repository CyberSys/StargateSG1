package planning;

import faction.Faction;
import universe.World;

public class SabotageFleetTask extends Task {

	private Faction target;
	private World world;
	public SabotageFleetTask(Faction target, World world, Task parent) {
		super(true, "Sabotage Fleet Task", parent);
		this.target = target;
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
		world.removeShips(target, 1);
		parent.reportFinished();
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return world.hasSpy(faction) && target.getNumShips(world) > 0;
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
