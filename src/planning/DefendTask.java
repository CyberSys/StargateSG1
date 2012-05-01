package planning;

import faction.Faction;
import universe.*;

// TODO: THIS WHOLE CLASS!!!!!
public class DefendTask extends Task {
	
	private World world;
	private Faction target;
	public DefendTask(World world, Faction target, Task parent) {
		super(false, "Defend Task", parent);
		this.world = world;
		this.target = target;
	}

	public int stepsToCompletion(Faction faction) {
		return 0;/*new TrainTroopsTask(attackForceSize, this).stepsToCompletion(faction)
				+ new TransportTroopsTask(from, to, attackForceSize, this).stepsToCompletion(faction)
				+ (new AssaultTask(to, target, this).getFlavorMatch(faction) > new ConquerTask(to, this).getFlavorMatch(faction) ?
					new AssaultTask(to, target, this).stepsToCompletion(faction) : new ConquerTask(to, this).stepsToCompletion(faction));*/
	}

	//TODO: Think about how to determine if you should proceed with the attack - how many troops to move and whatnot
	public Task getNextStep(Faction faction) 
	{
		if(!new TrainTroopsTask(world.getTroopCount(target), this).isCompleted(faction))
			return new TrainTroopsTask(world.getTroopCount(target), this);
		return new AssaultTask(world, target, this);
	}

	public boolean isCompleted(Faction faction) {
		return world.getTroopCount(target) == 0;
	}

	public boolean canPerform(Faction faction) {
		if(!new TrainTroopsTask(world.getTroopCount(target), this).isCompleted(faction))
			return new TrainTroopsTask(world.getTroopCount(target), this).canPerform(faction);
		return new AssaultTask(world, target, this).canPerform(faction);
		}
		
	public double getFlavorMatch(Faction faction) 
	{
		return (faction.getAggression() + faction.getDiplomacy() + faction.getScience()) / 3.0;
	}
}
