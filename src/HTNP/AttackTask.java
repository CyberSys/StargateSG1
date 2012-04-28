package HTNP;

import World.*;
import Faction.Faction;

public class AttackTask extends Task {

	public AttackTask() {
		super(false, "Attack Task");
	}

	public int stepsToCompletion(Faction faction) {
		// TODO Auto-generated method stub
		//sum of turns until combat strenght up
		//+ turns until transport ready
		//+ turns guessed for assault
		return new GatherTroopsTask(faction.getEnemy().getCombatStrength()).stepsToCompletion(faction)
				+ new TransportTroopsTask().stepsToCompletion(faction)
				+ (new AssaultTask().getFlavorMatch(faction) > new ConquerTask().getFlavorMatch(faction) ?
					new AssaultTask().stepsToCompletion(faction) : new ConquerTask().stepsToCompletion(faction));
	}

	public Task getNextStep(Faction faction) {
		if(!(faction.getCombatStrength() > faction.getEnemy().getCombatStrength()))
			return new GatherTroopsTask(faction.getEnemy().getCombatStrength());
		if(!faction.isReadyToAttack())
			return new TransportTroopsTask();
		if(!new ConquerTask().canPerform(faction))
			return new AssaultTask();
		else return new ConquerTask();
	}

	public boolean isCompleted(Faction faction) {
		return faction.getEnemy().getWorld().getControllingFaction() == faction;
	}

	public boolean canPerform(Faction faction) {
		if(!(faction.getCombatStrength() > faction.getEnemy().getCombatStrength()))
			return new GatherTroopsTask(faction.getEnemy().getCombatStrength()).canPerform(faction);
		if(!faction.isReadyToAttack())
			return new TransportTroopsTask().canPerform(faction);
		if(new AssaultTask().getFlavorMatch(faction) > new ConquerTask().getFlavorMatch(faction))
			return new AssaultTask().canPerform(faction);
		else return new ConquerTask().canPerform(faction);
	}
	
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
