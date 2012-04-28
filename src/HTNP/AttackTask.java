package HTNP;

import World.*;
import Faction.Faction;

public class AttackTask extends Task {

	public AttackTask() {
		super(false, "Attack Task");
		tasks.add(new GatherTroopsTask()); //0
		tasks.add(new TransportTroopsTask()); //1
		tasks.add(new AssaultTask()); // 2
		tasks.add(new ConquerTask()); // 3
	}

	public int stepsToCompletion(Faction faction) {
		// TODO Auto-generated method stub
		//sum of turns until combat strenght up
		//+ turns until transport ready
		//+ turns guessed for assault
		return 0;
	}

	public Task getNextStep(Faction faction) {
		
		if(!(faction.getCombatStrength() > faction.getEnemy().getCombatStrength()))
			return tasks.get(0);
		if(!faction.isReadyToAttack())
			return tasks.get(1);
		if(tasks.get(2).getFlavorMatch(faction) > tasks.get(3).getFlavorMatch(faction))
			return tasks.get(2);
		else return tasks.get(3);
	}

	public boolean isCompleted(Faction faction) {
		if(tasks.get(2).getFlavorMatch(faction) > tasks.get(3).getFlavorMatch(faction))
			return tasks.get(2).isCompleted(faction);
		else return tasks.get(3).isCompleted(faction);
	}

	public boolean canPerform(Faction faction) {
		if(!(faction.getCombatStrength() > faction.getEnemy().getCombatStrength()))
			return tasks.get(0).canPerform(faction);
		if(!faction.isReadyToAttack())
			return tasks.get(1).canPerform(faction);
		if(tasks.get(2).getFlavorMatch(faction) > tasks.get(3).getFlavorMatch(faction))
			return tasks.get(2).canPerform(faction);
		else return tasks.get(3).canPerform(faction);
	}
	
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
