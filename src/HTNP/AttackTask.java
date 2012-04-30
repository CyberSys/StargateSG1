package HTNP;

import universe.*;
import Faction.Faction;

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
		return new TrainTroopsTask(attackForceSize, this).stepsToCompletion(faction)
				+ new TransportTroopsTask(from, to, attackForceSize, this).stepsToCompletion(faction)
				+ (new AssaultTask(to, target, this).getFlavorMatch(faction) > new ConquerTask(to, this).getFlavorMatch(faction) ?
					new AssaultTask(to, target, this).stepsToCompletion(faction) : new ConquerTask(to, this).stepsToCompletion(faction));
	}

	//TODO: Think about how to determine if you should proceed with the attack - how many troops to move and whatnot
	public Task getNextStep(Faction faction) {
		if(!new TrainTroopsTask(attackForceSize, this).isCompleted(faction) && !(to.getTroopCount(faction) > to.getTroopCount(target)))
			return new TrainTroopsTask(attackForceSize, this);
		if(!(to.getTroopCount(faction) > to.getTroopCount(target)))
			return new TransportTroopsTask(from, to, attackForceSize/2 + to.getTroopCount(faction), this);
		if(!(to.getTroopCount(target) < 5))
			return new AssaultTask(to, target, this);
		else return new ConquerTask(to, this);
		/*int fromEnemyTroops = 0;
		for(Faction f : Universe.factions) {
			fromEnemyTroops += f.getNumArmies(from);
		}
		if(!new TrainTroopsTask(attackForceSize + fromEnemyTroops / 2, this).isCompleted(faction)) //build up enough troops to hold your own
			return new TrainTroopsTask(attackForceSize + fromEnemyTroops, this);
		if(from.getTroopCount(faction) - attackForceSize >= fromEnemyTroops / 2 )
			return new TransportTroopsTask(from, to, attackForceSize, this);
		if(!(to.getTroopCount(faction) >= to.getTroopCount(target)))
			return new AssaultTask(to, target, this);
		return new ConquerTask(to, this);*/
		
	}

	public boolean isCompleted(Faction faction) {
		return to.getControllingFaction() == faction;
	}

	public boolean canPerform(Faction faction) {
		if(!new TrainTroopsTask(attackForceSize, this).isCompleted(faction) && !(to.getTroopCount(faction) > to.getTroopCount(target)))
			return new TrainTroopsTask(attackForceSize, this).canPerform(faction);
		if(!(to.getTroopCount(faction) > to.getTroopCount(target)))
			return new TransportTroopsTask(from, to, faction.getNumArmies(), this).canPerform(faction);
		/*if(new AssaultTask(to, target, this).getFlavorMatch(faction) > new ConquerTask(to, this).getFlavorMatch(faction))
			return new AssaultTask(to, target, this).canPerform(faction);
		else return new ConquerTask(to, this).canPerform(faction);*/ return true;
	}
	
	public double getFlavorMatch(Faction faction) {
		return faction.getAggression();
	}

}
