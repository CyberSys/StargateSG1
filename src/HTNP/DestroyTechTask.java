package HTNP;

import universe.World;
import Faction.Faction;

public class DestroyTechTask extends Task {

	private Faction target;
	private World world;
	public DestroyTechTask(Faction target, World world, Task parent) {
		super(true, "Destroy Tech Task", parent);
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
		target.reduceTechLevel();
		parent.reportFinished();
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return world.getTroopCount(faction) > 0 && !target.getTechLevel().isMinimum();
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
