package HTNP;

import universe.*;
import Faction.Faction;

//TODO: dynamically determine from world for transport troops
public class AttackTask extends Task {
	
	private World from, to;
	private Faction target;
	private int attackForceSize;
	public AttackTask(World from, World to, Faction target, int attackForceSize, Task parent) {
		super(false, "Attack Task", parent);
		this.from = from;
		this.to = to;
		this.target = target;
		this.attackForceSize = attackForceSize;
	}

	public int stepsToCompletion(Faction faction) {
		return new TrainTroopsTask(target.getCombatStrength(), this).stepsToCompletion(faction)
				+ new TransportTroopsTask(from, to, faction.getCombatStrength(), this).stepsToCompletion(faction)
				+ (new AssaultTask(to, target, this).getFlavorMatch(faction) > new ConquerTask(to, this).getFlavorMatch(faction) ?
					new AssaultTask(to, target, this).stepsToCompletion(faction) : new ConquerTask(to, this).stepsToCompletion(faction));
	}

	public Task getNextStep(Faction faction) {
		if(!(new TrainTroopsTask(target.getCombatStrength(), this).isCompleted(faction)))
			return new TrainTroopsTask(target.getCombatStrength(), this);
		if(!new TransportTroopsTask(from, to, attackForceSize, this).isCompleted(faction))
			return new TransportTroopsTask(from, to, attackForceSize, this);
		if(!new ConquerTask(to, this).canPerform(faction))
			return new AssaultTask(to, target, this);
		else return new ConquerTask(to, this);
	}

	public boolean isCompleted(Faction faction) {
		return to.getControllingFaction() == faction;
	}

	public boolean canPerform(Faction faction) {
		if(!new TrainTroopsTask(attackForceSize, this).isCompleted(faction))
			return new TrainTroopsTask(attackForceSize, this).canPerform(faction);
		if(!faction.isReadyToAttack())
			return new TransportTroopsTask(from, to, faction.getNumArmies(), this).canPerform(faction);
		if(new AssaultTask(to, target, this).getFlavorMatch(faction) > new ConquerTask(to, this).getFlavorMatch(faction))
			return new AssaultTask(to, target, this).canPerform(faction);
		else return new ConquerTask(to, this).canPerform(faction);
	}
	
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
