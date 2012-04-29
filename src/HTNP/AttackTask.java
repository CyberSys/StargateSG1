package HTNP;

import World.*;
import Faction.Faction;

//TODO: dynamically determine from world for transport troops
public class AttackTask extends Task {
	
	private World from, to;
	private Faction target;
	public AttackTask(World from, World to, Faction target, Task parent) {
		super(false, "Attack Task", parent);
		this.from = from;
		this.to = to;
		this.target = target;
	}

	public int stepsToCompletion(Faction faction) {
		return new TrainTroopsTask(target.getCombatStrength(), this).stepsToCompletion(faction)
				+ new TransportTroopsTask(faction.getWorld(), target.getWorld(), faction.getCombatStrength(), this).stepsToCompletion(faction)
				+ (new AssaultTask(to, this).getFlavorMatch(faction) > new ConquerTask(to, this).getFlavorMatch(faction) ?
					new AssaultTask(to, this).stepsToCompletion(faction) : new ConquerTask(to, this).stepsToCompletion(faction));
	}

	public Task getNextStep(Faction faction) {
		if(!(new TrainTroopsTask(target.getCombatStrength(), this).isCompleted(faction)))
			return new TrainTroopsTask(target.getCombatStrength(), this);
		if(!faction.isReadyToAttack())
			return new TransportTroopsTask(from, to, faction.getNumArmies(), this);
		if(!new ConquerTask(to, this).canPerform(faction))
			return new AssaultTask(to, this);
		else return new ConquerTask(to, this);
	}

	public boolean isCompleted(Faction faction) {
		return target.getWorld().getControllingFaction() == faction;
	}

	public boolean canPerform(Faction faction) {
		if(!(faction.getCombatStrength() > target.getCombatStrength()))
			return new TrainTroopsTask(target.getCombatStrength(), this).canPerform(faction);
		if(!faction.isReadyToAttack())
			return new TransportTroopsTask(from, to, faction.getNumArmies(), this).canPerform(faction);
		if(new AssaultTask(to, this).getFlavorMatch(faction) > new ConquerTask(to, this).getFlavorMatch(faction))
			return new AssaultTask(to, this).canPerform(faction);
		else return new ConquerTask(to, this).canPerform(faction);
	}
	
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
