package HTNP;

import universe.World;
import Faction.Faction;

public class SabotageTroopsTask extends Task {

	private Faction target;
	private World world;
	public SabotageTroopsTask(Faction target, World world, Task parent) {
		super(true, "Sabotage Troops Task", parent);
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
		if(random.nextBoolean()) //Spy was caught
			world.exposeSpy(faction);
		else {
			world.removeTroops(target, 1);
			parent.reportFinished();
		}
	}
	
	@Override
	public boolean canPerform(Faction faction) {
		return world.hasSpy(faction) && world.getTroopCount(target) > 0;
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
