package planning;

import faction.Faction;
import settings.Globals;
import universe.*;

public class AttackTask extends Task {
	
	private World from, to;
	private int attackForceSize;
	private boolean completedTroopTraining;
	public AttackTask(World from, World to, Faction target, int attackForceSize, Task parent) {
		super(false, "Attack Task", parent);
		this.from = from;
		this.to = to;
		this.target = target;
		this.attackForceSize = attackForceSize;
	}

	public int stepsToCompletion(Faction faction) 
	{
		// TODO: Implement steps to completion.
		return 0;
	}
	
	public Task getNextStep(Faction faction) {
		
		double fromEnemyTroops = 0;
		for(Faction f : Universe.factions) {
			if(f != faction)
				fromEnemyTroops += f.getAttackStrength(from);
		}
		if(!completedTroopTraining && !(from.getTroopCount(faction) >= attackForceSize))
			return new TrainTroopsTask(from, attackForceSize, this);
		else if((Math.max(0, from.getTroopCount(faction) - attackForceSize)*faction.getTechLevel().offensiveCapabilities) < fromEnemyTroops)
			return new TrainTroopsTask(from, (int)(fromEnemyTroops/faction.getTechLevel().offensiveCapabilities) , this);
		else if((from.getTroopCount(faction) - attackForceSize)*faction.getTechLevel().offensiveCapabilities >= fromEnemyTroops && from.getTroopCount(faction) >= attackForceSize)
			return new TransportTroopsTask(from, to, attackForceSize, this);
		else if(from.getTroopCount(faction)*faction.getTechLevel().offensiveCapabilities < fromEnemyTroops)
			return new TrainTroopsTask(from, (int)(fromEnemyTroops/faction.getTechLevel().offensiveCapabilities), this);
		else {
			completedTroopTraining = false;
			return getNextStep(faction);
		}
	}

	public void reportFinished(Task child) {
		if(child instanceof TrainTroopsTask) completedTroopTraining = true;
		if(child instanceof TransportTroopsTask) didFinish = true;
	}
	
	public boolean isCompleted(Faction faction) {
		return didFinish;
	}

	public boolean canPerform(Faction faction) {
		if(!new TrainTroopsTask(from, attackForceSize, this).isCompleted(faction) && !didFinish)
			return new TrainTroopsTask(from, attackForceSize, this).canPerform(faction);
		if(!didFinish)
			return new TransportTroopsTask(from, to, faction.getNumArmies(), this).canPerform(faction);
		return false;
	}
	
	public double getFlavorMatch(Faction faction) 
	{
		return (((Globals.MAX_REPUTATION - faction.getReputationNumber(target)) / Globals.MAX_REPUTATION) * (faction.getAggression()));
	}

}
