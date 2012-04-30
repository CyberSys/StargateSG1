package planning;

import java.util.Random;

import faction.Faction;
import universe.World;

public class AssaultTask extends Task {

	private World world;
	private Faction target;
	public AssaultTask(World world, Faction target, Task parent) {
		super(true, "Assault Task", parent);
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

	public boolean isCompleted(Faction faction) {
		return world.getTroopCount(target) == 0;
	}

	public boolean canPerform(Faction faction) {
		return world.getTroopCount(faction) > 0;
	}

	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		double attackStrength = faction.getAttackStrength(world);
		double defenseStrength = target.getDefenseStrength(world);
		target.decreaseTroops((int)(attackStrength/(2*defenseStrength)), world);
		faction.decreaseTroops((int)((attackStrength/ defenseStrength)), world);
	}
	
	@Override
	public double getFlavorMatch(Faction faction) {
		return faction.getAggression() - .5 * faction.getDiplomacy();
	}

}
