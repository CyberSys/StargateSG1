package HTNP;

import universe.World;
import Faction.Faction;

public class ConquerTask extends Task {

	private World world;
	public ConquerTask(World world, Task parent) {
		super(true, "Conquer Task", parent);
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

	public boolean isCompleted(Faction faction) {
		return world.getControllingFaction() == faction;
	}

	public boolean canPerform(Faction faction) {
		return true;
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		double attackStrength = faction.getAttackStrength(world);
		double defenseStrength = world.getControllingFaction().getDefenseStrength(world);
		if(attackStrength > defenseStrength)
			if(random.nextDouble() > defenseStrength/attackStrength)
				world.setControllingFaction(faction);
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
